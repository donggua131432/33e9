<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<div class="services_box">
		<div class="Online_box">
                   <span class="Nine_platform_main">
                       <span class="nine_top">玖&nbsp;云&nbsp;平&nbsp;台</span><br/>
                       <span>在&nbsp;线&nbsp;客&nbsp;服</span>
                   </span>
                   <span class="Nine_platform">
                       <img  id="onlineService" src="${appConfig.resourcesRoot}/master/img/Online_services.png" alt="图片"/>
                   </span>
		</div>
		<div class="service_box">
                   <span class="Service_tel_main">
                       <span class="tel1">客&nbsp;服&nbsp;电&nbsp;话：</span><br/>
                       <span class="tel2">0755-33548988</span>
                   </span>
                   <span class="Service_tel">
                       <img src="${appConfig.resourcesRoot}/master/img/service_tel%20.png" alt="图片"/>
                   </span>
		</div>
	</div>
</div>
<%--<script charset="utf-8" src="http://wpa.b.qq.com/cgi/wpa.php"></script>--%>
<script type="text/javascript">
	//单个
	/*BizQQWPA.addCustom({
		aty: '0', //指定工号类型,0-自动分流，1-指定工号，2-指定分组
		a:'0',
		nameAccount: '800164600', //营销 QQ 号码
		selector: 'onlineService' //WPA 被放置的元素
	});*/

	//单个
	/*BizQQWPA.addCustom({
		aty: '0', //指定工号类型,0-自动分流，1-指定工号，2-指定分组
		a:'0',
		nameAccount: '800164600', //营销 QQ 号码
		selector: 'contactus' //WPA 被放置的元素
	});*/

	$(".Nine_platform").hover(function(){
		$(".Nine_platform_main").animate({right:57},{"duration":200});
	},function(){
		$(".Nine_platform_main").animate({"right":-118},{"duration":200}).delay(3)
	});
	$(".Service_tel").hover(function(){
		$(".Service_tel_main").animate({"right":57},{"duration":200});
	},function(){
		$(".Service_tel_main").animate({"right":-153},{"duration":200});
	});

</script>