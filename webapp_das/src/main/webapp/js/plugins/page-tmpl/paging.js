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
	$.fn.reload = function(_paging) {
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
		        var rows = dataCallBack(data.rows);
		        $(opts.integralBody).empty();
		        if (rows) {
			        $(opts.integralTemplate).tmpl(rows).appendTo($(opts.integralBody));
		        }
		        paging.page = data.page;
		        setShowNum(data.total,data.page,data.totalPage);

	        },
	        error : function(data) {
		        alert("出现异常");
	        }
	    });
    }

	function loadPagination($pagination){
		var paginationTemplate = '<ul>\
				<li><a>共<font id="showTotal" color="red">0</font>条</a></li>\
				<li><input type="number" value="" id="toGoPage" style="width:50px;text-align:center;float:left" placeholder="页码"></li>\
				<li style="cursor:pointer;"><a id="toSkip" class="btn btn-mini btn-success">跳转</a></li>\
				<li><a id="toHead">首页</a></li>\
				<li><a id="toPrev">上页</a></li>\
				<li><a id="showPage"><font color="#808080">1</font></a></li>\
				<li><a id="toNext">下页</a></li>\
				<li><a id="toEnd">尾页</a></li>\
				<li><a>共<font id="showTotalPage">1</font>页</a></li>\
				<li>\
					<select id="changePageSize" title="显示条数" style="width:55px;float:left;">\
						<option value="10">10</option>\
						<option value="20">20</option>\
						<option value="30">30</option>\
						<option value="50">50</option>\
					</select>\
				</li>\
			</ul>';
		$pagination.html(paginationTemplate);

		$("#toSkip,#toHead,#toPrev,#toNext,#toEnd").bind("click",toSkip);
		$("#changePageSize").bind("change",changePageSize);
	}

	function setShowNum(total,page,totalPage){
		pagewrapper =  $.extend({}, pagewrapper, {total:total,page:page,totalPage:totalPage});
		$("#showTotal").text(total);
		$("#showPage").text(page);
		$("#showTotalPage").text(totalPage);
	}

	function toSkip(){
		var _paging = {};
		if(this.id == "toSkip"){
			var toGoPage = $("#toGoPage").val();
			if(!toGoPage && isNaN(toGoPage)){
				return;
			}
			if(toGoPage>pagewrapper.total || toGoPage<1 || toGoPage == pagewrapper.page){
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
			if(pagewrapper.page == 1){
				return;
			}
			_paging = {page:paging.page - 1};
		}
		if(this.id == "toNext"){
			if(pagewrapper.page == pagewrapper.totalPage){
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
		$.fn.reload(_paging);
	}

	function changePageSize(){
		var _paging = {pageSize:this.value,page:1};
		$.fn.reload(_paging);
	}

	// 回调函数
	function dataCallBack(rows){
		if(opts.dataRowCallBack && typeof opts.dataRowCallBack == "function"){
			var _rows = [];
			$.each(rows,function(i,row){
				_rows.push(opts.dataRowCallBack(row));
			});
			return _rows;
		}
		return rows;
	}
	$.fn.getCurrentPage = function(){
		return paging.page;
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
