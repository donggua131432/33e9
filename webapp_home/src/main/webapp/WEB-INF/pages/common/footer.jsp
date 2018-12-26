<%@ page language="java" import="java.util.*,com.e9cloud.core.util.RSAUtil" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="footer">
	<div class="foot_box">
		<div class="foot1">
			<div class="linePro list_right f_left">
				<h4 class="HLeft">产品</h4>
				<p><a href="<c:url value='/product/hbrest'/>">专线语音</a></p>
				<p><a href="<c:url value='/product/designed'/>">专号通</a></p>
				<p><a href="<c:url value='/product/znydd'/>">智能云调度</a></p>
				<p><a href="<c:url value='/price/index'/>">价格</a></p>
			</div>
			<div class="lineCase list_right f_left">
				<h4 class="HRight">解决方案</h4>
				<p><a href="<c:url value='/case/financial'/>">金融行业</a></p>
				<p><a href="<c:url value='/case/logistics'/>">物流行业</a></p>
				<p><a href="<c:url value='/case/estate'/>">地产行业</a></p>
				<%--<p><a href="<c:url value='/case/education'/>">教育行业</a></p>--%>
			</div>
			<div class="line1 list_right f_left" style="width:310px;">
				<h4>API文档</h4>
				<div class="SWidth1 f_left">
					<p><a href="<c:url value="/api/UseGuide"/>" class="ALeft">新手指南</a></p>
					<p><a href="<c:url value="/api/PlatformAudit"/>" class="ALeft">平台政策规范</a></p>
					<p><a href="<c:url value="/api/RESTInterface"/>">REST&nbsp;API</a></p>
					<p><a href="<c:url value="/api/CallbackInterface"/>">AS回调通知接口</a></p>
				</div>
				<div class="SWidth2 f_right">
					<p><a href="<c:url value="/api/globalErrorCode"/>">全局错误码</a></p>
					<p><a href="javascript:void(0);">常见问题</a></p>
				</div>
			</div>
			<div class="line2 list_right f_left">
				<h4 class="HRight">联系</h4>
				<p><a href="javascript:void(0);" id="contactus">联系客服</a></p>
				<p><a href="<c:url value='/contactus'/>">联系我们</a></p>
				<p><a href="<c:url value='/aboutas'/>">关于我们</a></p>
				<p><a href="javascript:void(0);">0755-33548988</a></p>
			</div>
			<div class="line3 list_right f_left">
				<h4>微信</h4>
				<div class="ewm">
					<img src="${appConfig.resourcesRoot}/master/img/ewm.png" alt="二维码"/>
				</div>
			</div>
			<div class="line4 list_right f_left">
				<h4>分享</h4>
				<div class="list">
					<a href="javascript:void(0);" name="weixin" target="_blank"><img src="${appConfig.resourcesRoot}/master/img/weixin.png" alt="微信"/></a>
					<a href="javascript:void(0);" name="tsina" target="_blank"><img src="${appConfig.resourcesRoot}/master/img/weibo.png" alt="微博"/></a>
					<a href="javascript:void(0);" name="cqq" target="_blank"><img src="${appConfig.resourcesRoot}/master/img/QQ.png" alt=" QQ "/></a>
				</div>
			</div>

		</div>
		<div class="foot2 clear">
			<p>Copyright &copy;深圳市大众通信技术有限公司.All Rights Reserved.玖云平台 版权所有<br/><a href="http://www.miitbeian.gov.cn" target="_blank">粤ICP备08007507号-2</a></p>
		</div>
	</div>
</div>

<jsp:include page="qq_tel.jsp"/>
<script type="text/javascript">

	$(".list img").hover(function(){
		var src =  $(this).attr("src");
		$(this).attr("src", src.replace(".png","1.png"));
	},function(){
		var src =  $(this).attr("src");
		$(this).attr("src", src.replace("1.png",".png"));
	});

	var jiathis_url = "http://www.jiathis.com/send/?";
	var jiathis_title = "玖云平台";
	var jiathis_summary = "通讯无界  云端共享";
	var url = "http://www.33e9cloud.com";

	// 添加分享链接
	$('div.list a').each(function(){
		var webid = $(this).attr('name');
		var jiathis_herf = jiathis_url + "webid=" + webid + "&url=" + url + "&title=" + jiathis_title + "&summary=" + jiathis_summary + "&uid=1";

		$(this).attr('href', jiathis_herf);
	});
</script>