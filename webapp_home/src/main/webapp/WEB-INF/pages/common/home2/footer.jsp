<%@ page language="java" import="java.util.*,com.e9cloud.core.util.RSAUtil" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--底部-->
<div class="footer">
	<div class="ftMain">
		<div class="ftMain_text">
			<h2>产品</h2>
			<a href="${appConfig.url}dedicated_voice.html" target="_blank">专线语音</a>
			<a href="${appConfig.url}smart_cloud.html" target="_blank">智能云调度</a>
			<a href="${appConfig.url}special_through.html" target="_blank">专通号</a>
			<a href="javascript:;">语音通知</a>
		</div>

		<div class="ftMain_text">
			<h2>解决方案</h2>
			<a href="${appConfig.url}financial.html" target="_blank">金融行业</a>
			<a href="${appConfig.url}logistics.html" target="_blank">物流行业</a>
			<a href="${appConfig.url}estate.html" target="_blank">地产行业</a>
			<a href="${appConfig.url}o2o.html" target="_blank">O2O行业</a>
		</div>
		<div class="ftMain_text devement">
			<h2>开发者中心</h2>
             <span>
                 <a href="${appConfig.url}api/UseGuide.html" target="_blank">新手指引</a>
                 <a href="${appConfig.url}api/PlatformAudit.html" target="_blank">平台政策规范</a>
                 <a href="${appConfig.url}api/RESTInterface.html" target="_blank">Rest API</a>
             </span>
             <span class="deveR">
                <a href="${appConfig.url}api/CallbackInterface.html" target="_blank">AS回调通知接口</a>
                <a href="${appConfig.url}api/globalErrorCode.html" target="_blank">全局错误码</a>
                <a href="javascript:;">常见问题</a>
             </span>
		</div>
		<div class="ftMain_text">
			<h2>联系</h2>
			<a id="contactUs" style="cursor: pointer;" target="_blank">联系客服</a>
			<a href="${appConfig.url}contact_us.html" target="_blank">联系我们</a>
			<a href="${appConfig.url}about_us.html" target="_blank">关于我们</a>
			<a href="${appConfig.url}company_news.html" target="_blank">公司新闻</a>
		</div>
		<div class="ftMain_text lineNo">
			<h2 class="text_center">微信</h2>
			<p class="weiImg"><img src="${appConfig.url}images/weixi.png"> </p>
		</div>
		<div class="clear"></div>
		<p class="copyright">Copyright ©深圳市大众通信技术有限公司.All Rights Reserved.玖云平台 版权所有<br/><a href="http://www.miitbeian.gov.cn" target="_blank">粤ICP备08007507号-2</a></p>
	</div>
</div>
<!--底部 end-->