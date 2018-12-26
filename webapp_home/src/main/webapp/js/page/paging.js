// 创建一个闭包
(function($) {
	"use strict";
	
	var opts = {},
		paging = {},
		pagewrapper={page:1,totalPage:1,total:0,fromIndex:0,toIndex:0};
	
	// 插件的定义  
	$.fn.page = function(options) {
		// build main options before element iteration  
		opts = $.extend({}, $.fn.page.defaults, options);
		
		paging = {page:opts.page,pageSize:opts.pageSize,params:opts.param};
		//paging = $.extend({},{page:opts.page,pageSize:opts.pageSize},opts.param);

		if(opts.pagination && $(opts.pagination)){
			loadPagination($(opts.pagination));
		}
		
		getDataList();
		
		// iterate and reformat each matched element  
		return this.each(function() {  
			var $this = $(this);  
			// build element specific options  
			var o = $.meta ? $.extend({}, opts, $this.data()) : opts;  
		});
	};

	// 定义暴露函数
	$.fn.reload = function(_paging,is_not_new_p) {
		if (!is_not_new_p){
			paging = $.extend({}, paging, {"page":1});
		}
		paging = $.extend({}, paging, _paging);
		getDataList();
	};

	// 插件的defaults
	$.fn.page.defaults = {
			pageSize : 10, // 每页的记录数
			page : 1,// 当前页数
	        url : "", // 请求地址
	        integralBody : "#integralBody",// 将模版加入到某个元素的ID
	        integralTemplate : "#integralTemplate",// tmpl中的模版ID
	        pagination : "#pagination", // 分页选项容器
	        param : {},
	        dataRowCallBack : null
	};

	function getDataList() {

		$("#shadeWin").show();
		$("#loadPaginationMsg").show();
		if(!opts.url){
			return;
		}

	    $.ajax({
			async : false,
	        type : "POST",
	        url : opts.url,
	        data : paging,
	        dataType : 'json',
	        success : function(data) {

		        var rows = dataCallBack(data.rows,{"fromIndex":data.fromIndex,"pageSize":data.pageSize});
		        $(opts.integralBody).empty();
		        if (rows && rows.length > 0) {
			        $(opts.integralTemplate).tmpl(rows).appendTo($(opts.integralBody));
		        } else {
					var length = 0;
					try {
						var _td = $(opts.integralBody).parents('table').find('tr').children();
						_td.each(function(i, n){
							var c = $(this).attr("colspan") || 1;
							length += parseInt(c);
						});
					} catch (e){
						console.log(e);
					}

					$(opts.integralBody).append('<tr><td colspan="' + length + '">没有数据</td></tr>');
				}
		        paging.page = data.page;
		        setShowNum(data.pageSize,data.total,data.page,data.totalPage);

				$("#shadeWin").hide();
				$("#loadPaginationMsg").hide();

				$("html,body").scrollTop();
	        },
	        error : function(data) {
				$("#shadeWin").hide();
				$("#loadPaginationMsg").hide();
		        // alert("出现异常");
	        }
	    });
    }

	function loadPagination($pagination){
		var paginationTemplate = '\
				<span class="pageFont">共有<span id="showTotal">0</span>条消息，每页显示<span id="showPageSize">10</span>条，共<span id="showTotalPage">10</span>页</span>\
				<span class="flip_page">\
					<b id="toHead" class="common_icon firstPageTxt" title="首页"></b>\
					<b id="toPrev" class="common_icon nextPageTxt" title="上页"></b>\
					<span id="showPage" class="show_page">1</span>\
					<b id="toNext" class="common_icon frontPageTxt" title="下页"></b>\
					<b id="toEnd" class="common_icon lastPageTxt" title="尾页"></b>\
				</span>\
				<span class="page_jump">\
					第&nbsp;<input id="toGoPage" onkeyup="value=value.replace(/[^0-9]/g,\'\')" type="text"/>&nbsp;页\
					<b id="toSkip" class="common_icon flipTxt" title="跳转"></b>\
				</span>\
				<div id="loadPaginationMsg" class="pageLoading"><li class="imgLoading"></li></div>\
			';

		$pagination.html(paginationTemplate);

		$("#toSkip,#toHead,#toPrev,#toNext,#toEnd").bind("click",toSkip);
		//$("#changePageSize").bind("change",changePageSize);
	}

	function setShowNum(pageSize,total,page,totalPage){
		pagewrapper =  $.extend({}, pagewrapper, {total:total,page:page,totalPage:totalPage});

		$("#showTotal").text(total);
		$("#showPage").html(page);
		$("#showPageSize").text(pageSize);
		$("#showTotalPage").text(totalPage);
	}

	function toSkip(){
		var _paging = {};
		if(this.id == "toSkip"){
			var toGoPage = $("#toGoPage").val();
			if(!toGoPage && isNaN(toGoPage)){
				return;
			}
			if(toGoPage>pagewrapper.totalPage || toGoPage<1 || toGoPage == pagewrapper.page){
				return;
			}
			_paging = {page:toGoPage};
		}
		if(this.id == "toHead"){
			if(1 == pagewrapper.page){
				return;
			}
			_paging = {page:1};
		}
		if(this.id == "toPrev"){
			if(pagewrapper.page <= 1){
				return;
			}
			_paging = {page:paging.page - 1};
		}
		if(this.id == "toNext"){
			if(pagewrapper.page >= pagewrapper.totalPage){
				return;
			}
			_paging = {page:paging.page + 1};
		}
		if(this.id == "toEnd"){
			if(pagewrapper.page == pagewrapper.totalPage){
				return;
			}
			_paging = {page:pagewrapper.totalPage};
		}
		$.fn.reload(_paging,true);
	}

	function changePageSize(){
		var _paging = {pageSize:this.value,page:1};
		$.fn.reload(_paging,true);
	}

	// 回调函数
	function dataCallBack(rows,data){
		if(opts.dataRowCallBack && typeof opts.dataRowCallBack == "function"){
			var _rows = [];
			$.each(rows,function(i,row){
				_rows.push(opts.dataRowCallBack(row,(data.fromIndex+i+1)));
			});
			return _rows;
		}
		return {rows:rows,data:data};
	}
	$.fn.getCurrentPage = function(){
		return paging.page;
	}


	//导出记录为零时弹层
	function toshow() {
		var shadeWins = '\
	         <div id="shadeWin" class="shade_on_win" style="display: none;"></div>\
	         ';
		var msgto_show = '\
			<div id="msgDialog" class="modal-box  phone_t_code" style="display: none;">\
			    <div class="modal_head">\
			        <h4>导出记录</h4>\
			        <div class="p_right"><a id="close" class="common_icon close_txt"></a></div>\
			    </div>\
			    <div class="modal-common_body mdl_pt_body">\
			        <div class="test_success test_success_left">\
			            <i class="attention"></i>\
	                    <span>您暂无数据</span>\
	                </div>\
	            </div>\
	        </div>\
			';

		$('#toshow').html(shadeWins+msgto_show)
		$("#shadeWin").css('display','block')
		$("#msgDialog").css('display','block')

		$("#close").css('cursor','pointer').click(function(){
			$("#shadeWin").css('display','none');
			$("#msgDialog").css('display','none');
		})
	}


	// 下载报表
	$.fn.downloadReport = function(url,params){
		var join_f = "?", join = "&";
		if (url) {

			if (pagewrapper.total < 1) {
				toshow();
				//alert("很抱歉，您暂无数据可导出！");
			    return;
		}

			join_f = url.indexOf("?")>-1 ? "&" : "?";

			/*var param = join_f;
			param += $.param(paging);
			if (params) {
				param += join;
				param += $.param(params);
			}
			url = url + param;*/

			var param = join_f;
			if (params) {
				param += join;
				param += $.param($.extend({}, paging, params));
			}else{
				param += $.param(paging);
			}
			url = url + param;
			window.open(url);
		}
	}
	// 闭包结束  
})(jQuery);

//全选 （是/否）
function selectAll(){
	 var checklist = document.getElementsByName ("ids");
	 if(document.getElementById("zcheckbox").checked){
		for(var i=0;i<checklist.length;i++){
	      checklist[i].checked = 1;
	 	} 
	 }else{
	  for(var j=0;j<checklist.length;j++){
	     checklist[j].checked = 0;
	  }
	 }
}

