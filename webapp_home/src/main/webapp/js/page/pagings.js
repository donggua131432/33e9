/**
 * wzj
 */
// 创建一个闭包
(function($) {
	"use strict";
	var GridTable = function (ele, options) {
		this.pagewrapper = {page:1,totalPage:1,total:0,fromIndex:0,toIndex:0};
		this.$element = ele;
		this.total = 0; // 总条数
		this.defaults = {
			pageSize : 10, // 每页的记录数
			page : 1,// 当前页数
			url : "", // 请求地址
			integralBody : "#integralBody",// 将模版加入到某个元素的ID
			integralTemplate : "#integralTemplate",// tmpl中的模版ID
			pagination : "#pagination", // 分页选项容器
			param : {},
			dataRowCallBack : null
		};
		this.opts = $.extend({}, this.defaults, options);
		this.paging ={"page" : this.opts.page, "pageSize" : this.opts.pageSize, "params" : this.opts.param};
		var data = $.p.loadData(this.opts,this.paging);
		if(data){
			this.total = data.total;
		}
		var $pagination = $(this.opts.pagination);
		if ($pagination) {
			$.p.initPagination(this.opts,$pagination,this);
		}
		if(data){
			$.p.initGrid(this.opts,data,$pagination,this);
		}
	};

	GridTable.prototype = {
		// 重新加载
		reload : function (p) {
			this.paging = $.extend({}, this.paging, p);
			var data = $.p.loadData(this.opts,this.paging);
			if(data) this.total = data.total;
			var $pagination = $(this.opts.pagination);
			$.p.initGrid(this.opts,data,$pagination,this);
		},
		// 得到当前页数
		getCurrentPage : function(){
			return this.paging.page;
		},
		changePageSize : function(){
			var _paging = {pageSize:this.value,page:1};
			this.reload(_paging);
		},
		//全选
		selectAll : function () {
		},
		// 下载报表
		downloadReport : function (url, params) {
			var join_f = "?", join = "&";
			if (url) {
				if (this.total < 1) {
					//alert("没有数据");
					$("#shadeWin").show();
					$("#msgDialog").show();
					return;
				}
				join_f = url.indexOf("?")>-1 ? "&" : "?";
				var param = join_f;
				if (params) {
					param += join;
					param += $.param($.extend({}, this.paging, params));
				}else{
					param += $.param(this.paging);
				}
				url = url + param;
				window.open(url);
			}
		}
	};


	$.p = {
		// 加载数据
		loadData : function (o,p) {
			$("#shadeWin").show();
			$("div[loadPaginationMsg]").show();
			var _data;
			if(!o.url){
				return;
			}
			$.ajax({
				async : false,
				type : "POST",
				url : o.url,
				data : p,
				dataType : 'json',
				success : function(data) {
					_data = data;
					$("#shadeWin").hide();
					$("div[loadPaginationMsg]").hide();
					$("html,body").scrollTop();
				},
				error : function(data) {
					$("#shadeWin").hide();
					$("div[loadPaginationMsg]").hide();

					//alert("出现异常");
				}
			});
			return _data;
		},

		// 初始化表格
		initGrid : function (o,data,$pagination,$grid) {
			var rows = $.p.dataCallBack(o,data.rows,{"fromIndex":data.fromIndex,"pageSize":data.pageSize});

			$(o.integralBody).empty();
			if (rows && rows.length > 0) {
				$(o.integralTemplate).tmpl(rows).appendTo($(o.integralBody));
			} else {
				var length = 0;
				try {
					var _td = $(o.integralBody).parents('table').find('tr').children();
					_td.each(function(i, n){
						var c = $(this).attr("colspan") || 1;
						length += parseInt(c);
					});
				} catch (e){
					console.log(e);
				}

				$(o.integralBody).append('<tr><td colspan="' + length + '">没有数据</td></tr>');
			}

			if ($pagination) {
				$grid.pagewrapper =  $.extend({}, $grid.pagewrapper, {total:data.total,page:data.page,totalPage:data.totalPage});
				$.p.setShowNum($pagination,data.pageSize, data.total, data.page, data.totalPage);

			}

		},

		// 初始化分页器
		initPagination : function(o,$pagination,$gird){
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
				<div loadPaginationMsg class="pageLoading"><li class="imgLoading"></li></div>\
			';
			$pagination.html(paginationTemplate);
			$("#toSkip,#toHead,#toPrev,#toNext,#toEnd",$pagination).bind("click", {d : $gird, p : $pagination}, $.p.toSkip);
		},


		// 定义各种跳转
		toSkip : function(obj){
			var _paging = {};
			var $gird = obj.data.d;
			var $pagination = obj.data.p;
			var pagewrapper = $gird.pagewrapper;

			if(this.id == "toSkip"){
				var toGoPage = $("#toGoPage",$pagination).val();
				if(!toGoPage && isNaN(toGoPage)){
					return;
				}
				if(toGoPage > pagewrapper.totalPage || toGoPage<1 || toGoPage == pagewrapper.page){
					return;
				}
				_paging = {"page" : toGoPage};
			}
			if(this.id == "toHead"){
				if(1 == pagewrapper.page){
					return;
				}
				_paging = {"page" : 1};
			}
			if(this.id == "toPrev"){
				if(pagewrapper.page <= 1){
					return;
				}
				_paging = {"page" : pagewrapper.page - 1};
			}
			if(this.id == "toNext"){
				if(pagewrapper.page >= pagewrapper.totalPage){
					return;
				}
				_paging = {"page" : pagewrapper.page + 1};
			}
			if(this.id == "toEnd"){
				if(pagewrapper.page == pagewrapper.totalPage){
					return;
				}
				_paging = {"page" : pagewrapper.totalPage};
			}

			$gird.reload(_paging);
		},

		// 回调函数
		dataCallBack : function(opts,rows,data){
			if(opts.dataRowCallBack && typeof opts.dataRowCallBack == "function"){
				var _rows = [];
				$.each(rows,function(i,row){
					_rows.push(opts.dataRowCallBack(row,(data.fromIndex+i+1)));

				});
				return _rows;
			}
			return rows;
		},

		// 设置显示
		setShowNum : function($pagination,pageSize, total, page, totalPage){
			$("#showTotal",$pagination).text(total);
			$("#showPage",$pagination).text(page);
			$("#showPageSize",$pagination).text(pageSize);
			$("#showTotalPage",$pagination).text(totalPage);
		}

	};


	// 定义暴露函数
	$.fn.page = function (options) {
		var grid = new GridTable(this, options);
		return grid;
	};

// ---------------------------------------------------------------------------------------------------------------------------------- //

	// 闭包结束  
})(jQuery);
