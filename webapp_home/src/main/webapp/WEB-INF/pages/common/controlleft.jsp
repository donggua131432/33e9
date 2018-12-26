<%@ page language="java" import="java.util.*,com.e9cloud.core.util.RSAUtil" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$(function(){
		//设置菜单的隐藏显示
		$("div.left-common > span").on("click", function(){
			if($(this).find("i").attr("class") == "NumberDeal TriTB"){
				$(this).find("i").attr("class" , "NumberDeal TriTR");
				$(this).next().hide();
			}else{
				$(this).find("i").attr("class" , "NumberDeal TriTB");
				$(this).next().show();
			}
		});
	});

	function showSubMenu(fid,sid,tid){
		//$('#'+fid).siblings("li").removeClass();
		//$('#'+fid).addClass('hover');
		$('#'+sid).show();
		$('#'+tid).siblings("a").removeClass();
		$('#'+tid).addClass('p_hover');

		var $this = $('#'+fid);

		var idx=$this.index()+1;
		var id=fid;
		if(id=="zh"){
			$("#zn").find("i").removeClass("sec_im2Hover").addClass("sec_im2");
			$("#kf").find("i").removeClass("sec_im3Hover").addClass("sec_im3");
			$("#sp").find("i").removeClass("sec_im4Hover").addClass("sec_im4");
			$("#sphone").find("i").removeClass("sec_im5Hover").addClass("sec_im5");
			$("#ecc").find("i").removeClass("sec_im6Hover").addClass("sec_im6");
			$("#Ac_manage").show();
			$("#ZNYDD").hide();
			$("#open_interface").hide();
			$("#sip").hide();
			$("#yhj").hide();
			$("#yzj").hide();
		}else if(id=="zn"){
			$("#zh").find("i").removeClass("sec_im1Hover").addClass("sec_im1");
			$("#kf").find("i").removeClass("sec_im3Hover").addClass("sec_im3");
			$("#sp").find("i").removeClass("sec_im4Hover").addClass("sec_im4");
			$("#sphone").find("i").removeClass("sec_im5Hover").addClass("sec_im5");
			$("#ecc").find("i").removeClass("sec_im6Hover").addClass("sec_im6");
			$("#Ac_manage").hide();
			$("#ZNYDD").show();
			$("#open_interface").hide();
			$("#sip").hide();
			$("#yhj").hide();
			$("#yzj").hide();
		}else if(id=="kf"){
			$("#zh").find("i").removeClass("sec_im1Hover").addClass("sec_im1");
			$("#zn").find("i").removeClass("sec_im2Hover").addClass("sec_im2");
			$("#sp").find("i").removeClass("sec_im4Hover").addClass("sec_im4");
			$("#sphone").find("i").removeClass("sec_im5Hover").addClass("sec_im5");
			$("#ecc").find("i").removeClass("sec_im6Hover").addClass("sec_im6");
			$("#Ac_manage").hide();
			$("#ZNYDD").hide();
			$("#open_interface").show();
			$("#sip").hide();
			$("#yhj").hide();
			$("#yzj").hide();
		}else if(id=="sp"){
			$("#zh").find("i").removeClass("sec_im1Hover").addClass("sec_im1");
			$("#zn").find("i").removeClass("sec_im2Hover").addClass("sec_im2");
			$("#kf").find("i").removeClass("sec_im3Hover").addClass("sec_im3");
			$("#sphone").find("i").removeClass("sec_im5Hover").addClass("sec_im5");
			$("#ecc").find("i").removeClass("sec_im6Hover").addClass("sec_im6");
			$("#Ac_manage").hide();
			$("#ZNYDD").hide();
			$("#open_interface").hide();
			$("#sip").show();
			$("#yhj").hide();
			$("#yzj").hide();
		}else if(id=="sphone"){
			$("#zh").find("i").removeClass("sec_im1Hover").addClass("sec_im1");
			$("#zn").find("i").removeClass("sec_im2Hover").addClass("sec_im2");
			$("#kf").find("i").removeClass("sec_im3Hover").addClass("sec_im3");
			$("#sp").find("i").removeClass("sec_im4Hover").addClass("sec_im4");
			$("#ecc").find("i").removeClass("sec_im6Hover").addClass("sec_im6");
			$("#Ac_manage").hide();
			$("#ZNYDD").hide();
			$("#open_interface").hide();
			$("#sip").hide();
			$("#yhj").show();
			$("#yzj").hide();
		}
		else if(id=="ecc"){
			$("#zh").find("i").removeClass("sec_im1Hover").addClass("sec_im1");
			$("#zn").find("i").removeClass("sec_im2Hover").addClass("sec_im2");
			$("#kf").find("i").removeClass("sec_im3Hover").addClass("sec_im3");
			$("#sp").find("i").removeClass("sec_im4Hover").addClass("sec_im4");
			$("#sphone").find("i").removeClass("sec_im5Hover").addClass("sec_im5");
			$("#Ac_manage").hide();
			$("#ZNYDD").hide();
			$("#open_interface").hide();
			$("#sip").hide();
			$("#yhj").hide();
			$("#yzj").show();
		}else{return false}
		$this.siblings("li").removeClass("hover");
		$this.siblings("li").find("span").removeClass("fontHover").addClass("font");
		$this.addClass("hover");
		$this.find('i').removeClass("sec_im"+idx).addClass("sec_im"+idx+"Hover");
		$this.find("span").removeClass("font").addClass("fontHover");
	}

	// 退出
	function logout(){
		window.location.href = "<c:url value='/auth/logout'/>";
	}

</script>
<div class="leftMenu">
<div class="main1">
	<ul>
		<li id="zh" class="hover"><a href="<c:url value='/accMgr/index'/>"><i class="sec_img sec_im1Hover"></i><span class="fontHover">账户管理</span></a></li>

		<c:if test='${sessionScope.userInfo.busTypes.contains("02") or sessionScope.userInfo.busTypes.contains("01") or sessionScope.userInfo.busTypes.contains("03")}' >
			<li id="kf" ><a href="<c:url value='/appMgr/index'/>"><i class="sec_img sec_im3Hover"></i><span>接口业务</span></a></li>
		</c:if>

		<c:if test='${sessionScope.userInfo.busTypes.contains("01")}' >
			<li id="zn"><a href="<c:url value='/znydd/bill/consume'/>"><i class="sec_img sec_im2Hover"></i><span>智能云调度</span></a></li>
		</c:if>

		<c:if test='${sessionScope.userInfo.busTypes.contains("03")}' >
			<li id="sp"><a href="<c:url value='/sipConsumeView/index'/>"><i class="sec_img sec_im4Hover"></i><span>SIP对接</span></a></li>
		</c:if>

		<c:if test='${sessionScope.userInfo.busTypes.contains("05")}' >
			<li id="sphone"><a href="<c:url value='/phoneApp/index'/>"><i class="sec_img sec_im5Hover"></i><span>云话机</span></a></li>
		</c:if>
		<c:if test='${sessionScope.userInfo.busTypes.contains("06")}' >
		    <li id="ecc"><a href="<c:url value='/eccApp/index'/>"><i class="sec_img sec_im6Hover"></i><span>云总机</span></a></li>
		</c:if>
	</ul>
</div>

<div class="main2">
	<div id="acc_manage" style="display:none;">
		<div class="left-common">
			<span class="M_title1"><i class="NumberDeal TriTB"></i>账户信息</span>
			<div class="left">
				<a href="<c:url value='/accMgr/index'/>" id="accIndex">账户主页</a>
			</div>
		</div>
		<div class="left-common">
			<span class="M_title2"><i class="NumberDeal TriTB"></i>基础资料</span>
			<div class="left">
				<a href="<c:url value='/accMgr/query'/>" id="accInfo">基本资料</a>
				<a href="<c:url value='/authen/authstatus'/>" id="authen">认证信息</a>
				<a href="<c:url value='/accMgr/pwd'/>" id="accPwd">密码设置</a>
				<a href="<c:url value='/msgMgr/query'/>" id="message">消息管理</a>
			</div>
		</div>
		<div class="left-common">
			<span class="M_title3"><i class="NumberDeal TriTB"></i>账户管理</span>
			<div class="left">
				<a href="<c:url value='/rechargeRecord/query'/>" id="rechargelog">充值记录</a>
				<a href="<c:url value='/feelist/query'/>" id="feelist">资费列表</a>
			</div>
		</div>
	</div>

	<c:if test="${sessionScope.userInfo.busTypes.contains('01')}" >
		<div id="smart_cloud" style="display:none;">
			<%--<div class="left-common">
				<span class="M_title5"><i class="NumberDeal TriTB"></i>智能云调度</span>
				<div class="left">
					<a href="#">账&nbsp;户&nbsp;总&nbsp;览</a>
					<a href="#">充&nbsp;值&nbsp;记&nbsp;录</a>
				</div>
			</div>--%>
			<div class="left-common">
				<span class="M_title4"><i class="NumberDeal TriTB"></i>账单管理</span>
				<div class="left">
					<a href="<c:url value='/callCenter/query'/>" id="callcenter">呼叫中心</a>
					<a href="<c:url value='/znydd/bill/consume'/>" id="consume">消费总览</a>
					<a href="<c:url value='/znydd/bill/monthProc'/>" id="monthProc">月结账单</a>
					<a href="<c:url value='/znydd/bill/recentCall'/>" id="recentCall">通话记录</a>
				</div>
			</div>
			<div class="left-common">
				<span class="M_title5"><i class="NumberDeal TriTB"></i>话务管理</span>
				<div class="left">
					<a href="<c:url value='/smartCloud/traffic/monitor'/>" id="monitor">话务监控</a>
					<a href="<c:url value='/smartCloud/traffic/scan'/>" id="scan">话务总览</a>
					<a href="<c:url value='/smartCloud/traffic/stat'/>" id="stat">话务统计</a>
					<a href="<c:url value='/smartCloud/traffic/dispatch'/>" id="dispatch">话务调度</a>
					<a href="<c:url value='/smartCloud/traffic/ccareacity'/>" id="ccareacity">区域管理</a>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${sessionScope.userInfo.busTypes.contains('01') or sessionScope.userInfo.busTypes.contains('02') or sessionScope.userInfo.busTypes.contains('03')or sessionScope.userInfo.busTypes.contains('04')}" >
	<div id="appMgr" style="display:none;">
		<div class="left-common">
			<span class="M_title6"><i class="NumberDeal TriTB"></i>应用管理</span>
			<div class="left">
				<a href="<c:url value='/appMgr/savePage'/>" id="appSave">创建应用</a>
				<a href="<c:url value='/appMgr/index'/>" id="appList">应用列表</a>
				<a href="<c:url value='/numMgr/index'/>" id="numMgr">号码管理</a>
				<a href="<c:url value='/voiceMgr/index'/>" id="voiceList">铃声管理</a>
			</div>
		</div>
		<div class="left-common">
			<span class="M_title7"><i class="NumberDeal TriTB"></i>专线语音</span>
			<div class="left">
				<a href="<c:url value='/monthlySta/getConsumeView'/>" id="consumeView">消费总览</a>
				<a href="<c:url value='/monthlySta/callRecord'/>" id="callList">通话记录</a>
				<a href="<c:url value='/dataAnalysis/dataDailyPage'/>" id="dataDaily">数据日报</a>
			</div>
		</div>

		<div class="left-common">
			<span class="M_title8"><i class="NumberDeal TriTB"></i>专号通</span>
			<div class="left">
				<a href="<c:url value='/seNumMgr/index'/>" id="numList">号码展示</a>
				<a href="<c:url value='/secretMgr/index'/>" id="secretList">失效音管理</a>
				<a href="<c:url value='/maskRecord/callRecord'/>" id="maskCallRecord">通话记录</a>
			</div>
		</div>

		<div class="left-common">
			<span class="M_title8"><i class="NumberDeal TriTB"></i>语音通知</span>
			<div class="left">
				<a href="<c:url value='/mouldMgr/goApp'/>" id="mouldList">模板管理</a>
				<a href="<c:url value='/voiceRecord/index'/>" id="speechRecord">通话记录</a>
			</div>
		</div>

		<div class="left-common">
			<span class="M_title8"><i class="NumberDeal TriTB"></i>语音验证码</span>
			<div class="left">
				<a href="<c:url value='/voiceverifyTemp/list'/>" id="voiceVerifyList">模板管理</a>
				<a href="<c:url value='/voiceVerifyRecord/index'/>" id="voiceVerifyRecord">通话记录</a>
			</div>
		</div>

		<c:if test='${sessionScope.userInfo.busTypes.contains("07")}' >
		<div class="left-common">
			<span class="M_title8"><i class="NumberDeal TriTB"></i>虚拟小号</span>
			<div class="left">
				<a href ="<c:url value ='/axbNumMgr/index'/>" id ="axbNumList">号码展示</a>
				<a href ="<c:url value ='/maskBRecord/callRecord'/>" id="maskBRecord">通话记录</a>
			</div>
		</div>
		</c:if>


	</div>
	</c:if>
	<c:if test="${sessionScope.userInfo.busTypes.contains('03')}" >
	<div id="sipMgr" style="display:none;">
		<div class="left-common">
			<span class="M_title"><i class="NumberDeal TriTB"></i>SIP管理</span>
			<div class="left">
				<a href="<c:url value='/sipConsumeView/index'/>" id="sipConsumeView">消费总览</a>
				<a href="<c:url value='/sipAppDetail/index'/>" id="sipAppDetail">应用详情</a>
				<a href="<c:url value='/sipRepeaterList/index'/>" id="sipRepeaterList">子账号列表</a>
			</div>

			<span class="M_title"><i class="NumberDeal TriTB"></i>话务管理</span>
			<div class="left">
				<!--<a href="#">话务监控</a>-->
				<a href="<c:url value='/sipTrafficStatistics/index'/>" id="sipTrafficStatistics">话务统计</a>
				<a href="<c:url value='/sipCallRecord/index'/>" id="sipCallRecord">通话记录</a>
			</div>
		</div>
	</div>
	</c:if>
	<c:if test="${sessionScope.userInfo.busTypes.contains('05')}" >
		<div id="yhj" style="display:none;">
			<div class="left-common">
				<span class="M_title"><i class="NumberDeal TriTB"></i>应用管理</span>
				<div class="left">
					<a href="<c:url value='/phoneApp/savePage'/>" id="appSavePhone">创建应用</a>
					<a href="<c:url value='/phoneApp/index'/>" id="appListPhone">应用列表</a>
					<a href="<c:url value='/sipPhoneAppMgr/index'/>" id="sipPhoneNumMgr">号码管理</a>
					<a href="<c:url value='/voicePhone/index'/>" id="voiceListPhone">铃声管理</a>
				</div>

				<span class="M_title"><i class="NumberDeal TriTB"></i>业务管理</span>
				<div class="left">
					<!--<a href="#">话务监控</a>-->
					<a href="<c:url value='/sipPhoneAppMgr/callRecord'/>" id="callRecord">通话记录</a>
					<a href="<c:url value='/sipPhoneAppMgr/dataStatistics'/>" id="dataStatistics">话务统计</a>
					<a href="<c:url value='/sipPhoneAppMgr/consumptionStatistics'/>" id="consumptionStatistics">消费统计</a>
				</div>
			</div>
		</div>
	</c:if>


	<c:if test="${sessionScope.userInfo.busTypes.contains('06')}" >
		<div id="yzj" style="display:none;">
			<div class="left-common">
				<span class="M_title"><i class="NumberDeal TriTB"></i>应用管理</span>
				<div class="left">
					<a href="<c:url value='/eccApp/index'/>" id="appListEcc">应用列表</a>
				</div>

				<span class="M_title"><i class="NumberDeal TriTB"></i>账单清单</span>
				<div class="left">
					<a href="<c:url value='/eccMonthBill/toMonthEccBill'/>" id="eccMonthBill">月账单查询</a>
					<a href="<c:url value='/eccApp/dataStatistics'/>" id="dataStatisticsEcc">话务统计</a>
					<a href="<c:url value='/eccRecord/index'/>" id="eccRecord">通话记录</a>
				</div>
			</div>
		</div>
	</c:if>
</div>
</div>