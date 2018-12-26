<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心页面</title>
    <link href="${appConfig.resourcesRoot}/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
    <script src="${appConfig.resourcesRoot}/js/my.js"></script>
    <style>
        label.error{margin-left: 8px;}
    </style>
</head>
<body>
<jsp:include page="../common/controlheader.jsp"/>
<section>
    <div id="sec_main">
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('zh','acc_manage','authen');</script>
        <div class="main3">
            <div class="container2">
                <div class="msg">
                    <h3>认证信息</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="validation_msg">
                    <div class="app_note">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>1.每个客户须完成企业资质信息认证通过后才能创建应用及进行使用。</p>
                        <p>2.认证名称将作为企业资质审核和开发票的重要依据</p>
                        <p>3.每日16：30分前提交认证申请的将在当日反馈审核结果，超过此时间段，将在次日反馈审核结果，节假日顺延。</p>
                    </div>
                    <p class="id_test">
                        <i class="common_icon de_txt"></i>企业认证信息
                        <%--<span class="alert_base_msg id_left">--%>
                            <%--<i class="common_icon state_txt"></i>状态：--%>
                            <%--<img src="${appConfig.resourcesRoot}/img/noPass.png" alt="图片"/>--%>
                             <%--<a href="<c:url value='/authen/authAgain'/>">重新认证&gt;&gt;</a>--%>
                        <%--</span>--%>
                    </p>
                    <div class="warpper">
                        <form id="authForm" name="authForm" action="<c:url value='/authen/updateAuth'/>" method="post" enctype="multipart/form-data">
                            <div class="developers">
                                <span class="common">开发者类型</span>
                                <input type="radio" name="developers" checked="checked"/>
                                <span class="gs">公司</span>
                                <c:if test="${not empty error}">
                                    <c:if test="${error eq 'error'}"><label class="error">证件图片上传失败，请重新上传！ </label> </c:if>
                                    <c:if test="${error eq 'existed'}"><label class="error">存在重复信息，请核对后重新上传！ </label></c:if>
                                    <c:if test="${error eq 'abc'}"><label class="error">提交失败！您已经存在待审核或已认证的信息。 </label></c:if>
                                </c:if>
                            </div>
                            <!--公司页面内容-->
                            <div class="company">
                                <div class="cpy_intro">
                                    <label>公司名称：</label>
                                    <input type="text" class="l_input_style" size="50" id="com_name" value="<c:out value="${company.name}"/>" name="name" maxlength="30"/><br/>
                                    <label>公司地址：</label>
                                    <input type="text" class="l_input_style" size="50" id="address1" name="address" value="<c:out value="${company.address}"/>" maxlength="50"/><br/>
                                    <label>所属行业：</label>
                                    <select name="tradeId" id="tradeId" >

                                    </select>
                                </div>
                                <div class="organization">
                                    <label>证件类型：</label>
                                    <c:if test="${company.cardType == 0}">
                                        <input type="radio" name="cardType" value="0" class="certificate" onclick="switchTab('#pattern_one', '#pattern_two', '#pattern_three');" checked ="checked" /><span class="organ_msg_common">三证合一(一照一码)</span>
                                        <input type="radio" name="cardType" value="1" class="certificate" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');" /><span class="organ_msg_common">三证合一</span>
                                        <input type="radio" name="cardType" value="2" class="certificate" onclick="switchTab('#pattern_three', '#pattern_two', '#pattern_one');" /><span class="organ_msg_common">三证分离</span><br/>
                                    </c:if>
                                    <c:if test="${company.cardType == 1}">
                                        <input type="radio" name="cardType" value="0" class="certificate" onclick="switchTab('#pattern_one', '#pattern_two', '#pattern_three');" /><span class="organ_msg_common">三证合一(一照一码)</span>
                                        <input type="radio" name="cardType" value="1" class="certificate" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');" checked ="checked" /><span class="organ_msg_common">三证合一</span>
                                        <input type="radio" name="cardType" value="2" class="certificate" onclick="switchTab('#pattern_three', '#pattern_two', '#pattern_one');" /><span class="organ_msg_common">三证分离</span><br/>
                                    </c:if>
                                    <c:if test="${company.cardType == 2}">
                                        <input type="radio" name="cardType" value="0" class="certificate" onclick="switchTab('#pattern_one', '#pattern_two', '#pattern_three');" /><span class="organ_msg_common">三证合一(一照一码)</span>
                                        <input type="radio" name="cardType" value="1" class="certificate" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');"  /><span class="organ_msg_common">三证合一</span>
                                        <input type="radio" name="cardType" value="2" class="certificate" onclick="switchTab('#pattern_three', '#pattern_two', '#pattern_one');" checked ="checked" /><span class="organ_msg_common">三证分离</span><br/>
                                    </c:if>
                                    <!--三证合一(一照一码)-->
                                    <div id="pattern_one">
                                        <label>统一社会<br/>信用代码：</label>
                                        <input type="text" class="l_input_style input_bottom" size="50" id="creditNo" name="creditNo" class="input_bottom" value="<c:out value="${company.creditNo}"/>" maxlength="18"/><i>18位数字或者大写英文字母</i><br/>
                                        <label>营业执照：</label>
                                        <input type="text" id="id_photo1" name="businessLicenseFile" class="l_input_style" value="<c:out value="${company.businessLicensePic}"/>" size="50"/>
                                        <%--<button type="button" class="browse" onclick="document.getElementById('businessLicensePic').click()" >浏览</button>--%>
                                        <span class="parentPosition">
                                        <button type="button" class="browse" >浏览</button>
                                        <input type="file"  class="childPosition"  name="businessLicenseFile" id="businessLicensePic"  onchange="document.getElementById('id_photo1').value=this.value" />
                                        </span>
                                         <c:if test="${not empty company.businessLicensePic}">
                                            <p class="ppt_left"> <img src="<c:url value="/nasimg/anthen/${company.businessLicensePic}"/>" alt="营业执照证"/></p>
                                         </c:if>
                                    </div>

                                    <!--三证合一-->
                                    <div id="pattern_two" style="display: none;" >
                                        <label class="label_right">注册号：</label>
                                        <input type="text" class="l_input_style input_bottom" size="50" class="input_bottom" id="registerNo" name="registerNo" value="<c:out value="${company.registerNo}"/>" maxlength="15"/> <i>15位数字</i><br/>
                                        <label>税务登记号：</label>
                                        <input type="text" class="l_input_style label_left" size="50" class="label_left" id="taxRegNo" name="taxRegNo" value="<c:out value="${company.taxRegNo}"/>" maxlength="15"/> <i>15位数字</i><br/>
                                        <label>营业执照：</label>
                                        <input type="text" id="id_photo2" name="businessLicenseFile" class="l_input_style" value="<c:out value="${company.businessLicensePic}"/>"   size="50"/>
                                        <%--<button type="button" class="browse" onclick="document.getElementById('businessLicensePic1').click()" >浏览</button>--%>
                                        <span class="parentPosition">
                                        <button type="button" class="browse" >浏览</button>
                                        <input type="file" class="childPosition"  name="businessLicenseFile"  id="businessLicensePic1"  onchange="document.getElementById('id_photo2').value=this.value" />
                                        </span>
                                        <c:if test="${not empty company.businessLicensePic}">
                                        <p class="ppt_left"> <img src="<c:url value="/nasimg/anthen/${company.businessLicensePic}"/>" alt="营业执照证"/></p>
                                        </c:if>
                                    </div>

                                    <!--三证分离-->
                                    <div id="pattern_three" style="display: none;">
                                        <label>税务登记号：</label>
                                        <input type="text" class="l_input_style label_left" size="50" class="label_left" id="taxRegNo1" name="taxRegNo" value="<c:out value="${company.taxRegNo}"/>" maxlength="15"/> <i>15位数字</i><br/>
                                        <label>税务登记证：</label>
                                        <input type="text" id="taxRegPic" name="taxRegFile" class="l_input_style" value="<c:out value="${company.taxRegPic}"/>" size="50" style="margin-left:-16px;"/>
                                        <%--<button type="button" class="browse" onclick="document.getElementById('taxRegFile').click()" >浏览</button>--%>
                                        <span class="parentPosition">
                                        <button type="button" class="browse">浏览</button>
                                        <input type="file" class="childPosition"  name="taxRegFile"  id="taxRegFile"  onchange="document.getElementById('taxRegPic').value=this.value" />
                                        </span><br>
                                        <c:if test="${not empty company.taxRegPic}">
                                        <p class="ppt_left"> <img src="<c:url value="/nasimg/anthen/${company.taxRegPic}"/>" alt="税务登记证"/></p>
                                        </c:if>
                                        <label>营业执照号：</label>
                                        <input type="text" class="l_input_style label_left" size="50"
                                               class="label_left" id="businessLicenseNo" name="businessLicenseNo" value="<c:out value="${company.businessLicenseNo}"/>" maxlength="15"/> <i>15位数字</i><br/>
                                        <label>营业执照：</label>
                                        <input type="text" id="id_photo3" name="businessLicenseFile" value="<c:out value="${company.businessLicensePic}"/>" class="l_input_style" size="50"/>
                                        <%--<button type="button" class="browse" onclick="document.getElementById('businessLicensePic3').click()" >浏览</button>--%>
                                        <span class="parentPosition">
                                        <button type="button" class="browse" >浏览</button>
                                        <input type="file" class="childPosition"  name="businessLicenseFile"  id="businessLicensePic3" onchange="document.getElementById('id_photo3').value=this.value" />
                                        </span>
                                        <c:if test="${not empty company.businessLicensePic}">
                                        <p class="ppt_left"> <img src="<c:url value="/nasimg/anthen/${company.businessLicensePic}"/>" alt="营业执照"/></p>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="Legal">
                                    <label for="legal">法定代表人：</label>
                                    <input type="text" id="legal" name="legalRepresentative" class="l_input_style" value="<c:out value="${company.legalRepresentative}"/>"size="50" placeholder="请填写公司的法人代表姓名"  maxlength="20"/>
                                    <br/>
                                    <label for="cpy_number">公司电话：</label>
                                    <input type="text" id="cpy_number" name="telno"  maxlength="15" class="l_input_style" value="<c:out value="${company.telno}"/>"size="50" placeholder="请填写您的联系电话，如010-25465662"/>
                                    <br/>
                                    <label for="cpy_web">公司网站：</label>
                                    <input type="text" id="cpy_web" name="website" class="l_input_style" size="50" maxlength="50" value="<c:out value="${company.website}"/>" placeholder="https://www."/>
                                    <i>*非必填内容</i> <br/>
                                </div>
                                <div>
                                    <label style="color:#FE7D7D;">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：&nbsp;&nbsp;&nbsp;<c:out value="${company.comment}"/></label>
                                </div>
                                <button id="authButton" name="authButton" type="button" class="md_btn certificate_btn">提交</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
    ajaxDicData('${appConfig.authUrl}',"tradeType","tradeId",'${company.tradeId}');

    // 图片格式
    jQuery.validator.addMethod("suffix", function(value, element, param) {
                var reg = /^gif|jpg|jpeg|png$/;

                try{
                    var path = value.substring(value.lastIndexOf(".")).substring(1).toLowerCase();
                    return reg.test(path);
                }catch(e){
                    return false;
                }


            },
            "文件格式不正确！"
    );

    // 图片大小
    jQuery.validator.addMethod("authPic", function(value, element, param) {
                var fileSize = 0;
                if(element.files){ // element.files 是chrome，firefox等浏览器的对戏那个，如果是ie的话他的值就是false
//                    console.info("element.files");
                    var thisFile = element.files[0]; // 获取文件的对像
                    fileSize = thisFile.size;//获取当前上传的文件的大小
                } else { // 如果是ie
//                    console.info("element.files else");

                    /* var imgObj = document.getElementById("img");
                     imgObj.src = element.value;
                     imgObj.style.display="none";

                     if(imgObj.readyState=="complete"){//已经load完毕，直接打印文件大小
                     fileSize = imgObj.fileSize;//ie获取文件大小
                     }
                     fileSize = imgObj.fileSize;*/
                }

//                console.info(fileSize);

                return fileSize < 2097152;
            },
            "文件过大，最大支持2M！");


    $(function(){

        // 第二和第三个tab的表单元素都禁用掉
        $("#pattern_two,#pattern_three").find('input').attr("disabled",true);

        var siginForm = $("#authForm").validate({
            rules: {
                name: {
                    required: true,
                    companyName:true,
                    remote: {
                        url: "<c:url value='/authen/companyUnique'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            name: function() {
                                return $("#com_name").val();
                            }
                        }
                    }
                },
                address: {
                    required: true,
                    address:true
                    <%--remote: {--%>
                        <%--url: "<c:url value='/authen/companyUnique'/>",     //后台处理程序--%>
                        <%--type: "post",               //数据发送方式--%>
                        <%--dataType: "json",           //接受数据格式--%>
                        <%--data: {                     //要传递的数据--%>
                            <%--address: function() {--%>
                                <%--return $("#address1").val();--%>
                            <%--}--%>
                        <%--}--%>
                    <%--}--%>
                },
                tradeId: {
                    required: true
                },
                creditNo : { // 统一社会信用代码
                    required: true,
                    creditNo : true,
                    remote: {
                        url: "<c:url value='/authen/companyUnique'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            creditNo: function() {
                                return $("#creditNo").val();
                            }
                        }
                    }
                },
                registerNo : { // 注册号
                    required: true,
                    registerNo : true,
                    remote: {
                        url: "<c:url value='/authen/companyUnique'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            registerNo: function() {
                                return $("input[name='registerNo']:enabled").val();
                            }
                        }
                    }
                },
                taxRegNo : { // 税务登记号
                    required: true,
                    taxRegNo : true,
                    remote: {
                        url: "<c:url value='/authen/companyUnique'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            taxRegNo: function() {
                                return $("input[name='taxRegNo']:enabled").val();
                            }
                        }
                    }
                },
                taxRegFile : { // 税务登记证
                    required: true,
                    suffix : true,
                    authPic : true
                },
                businessLicenseNo : { // 营业执照号
                    required: true,
                    businessLicenseNo : true,
                    remote: {
                        url: "<c:url value='/authen/companyUnique'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            businessLicenseNo: function() {
                                return $("input[name='businessLicenseNo']:enabled").val();
                            }
                        }
                    }
                },
                businessLicenseFile : { // 营业执照
                    required: true,
                    suffix : true,
                    authPic : true
                },
                legalRepresentative : { // 企业法人
                    required: true,
                    legalRepresentative:true
                },
                telno : { // 公式电话
                    required: true,
                    telno:true
                },
                website : { // 公式电话
                    required: false,
                    website:true
                }
            },
            messages: {
                name: {
                    required: '请输入公司名称！',
                    companyName : '格式不正确，请重新输入！',
                    remote : '该公司已注册！'
                },
                address: {
                    required: '请输入企业地址！'

                },
                tradeId: {
                    required: '请选择行业！'
                },
                creditNo : { // 统一社会信用代码
                    required: '请输入社会信用代码号！',
                    remote : '该代码号已存在！'
                },
                registerNo : { // 注册号
                    required: '请输入注册号！',
                    remote : '该注册号已存在！'
                },
                taxRegNo : { // 税务登记号
                    required: '请输入税务登记号！',
                    remote : '该登记号已存在！'
                },
                taxRegFile : { // 税务登记证
                    required: '请上税务登记照！'
                },
                businessLicenseNo : { // 营业执照号
                    required: '请输入营业执照号！',
                    remote : '该执照号已存在！'
                },
                businessLicenseFile : { // 营业执照
                    required: '请上传营业执照！'
                },
                legalRepresentative : { // 企业法人
                    required: '请输入法定代表人！'
                },
                telno : { // 公式电话
                    required: '请输入公司电话！'
                }
            },
            errorPlacement: function(error, element) {
                if (element.next().is('i')) {
                    error.insertAfter(element.next());
                } else {
                    error.insertAfter(element);
                }
            }
        });

        $('#authButton').click(function() {
            if(siginForm.form()) {
                $(this).attr("disabled","disabled");
                $('#authForm').submit();
            }
        });

        $("input[name='cardType']:checked").click();
    });

    // 切换tab
    function switchTab(sid, hid,fid) {
        $(sid).show().find('input').attr("disabled",false);
        $(hid).hide().find('input').attr("disabled",true);
        $(fid).hide().find('input').attr("disabled",true);
    }


</script>
</body>
</html>

