<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>认证信息</title>
    <script type="text/javascript">

        function closeEditDialog(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

        function submitToAuth(){
            var status = $("#pass").val();
            var comment = $('#comment').val();
            var id = $("#id").val();
            var uid = $("#uid").val();
            if(status == "请选择") {
                $(".status").css("display", "block");
            }else if(comment=='' && status == "2"){
                $(".comment").css("display", "block");
            }else if((status == "2" && comment!='') || status == "1"){
                $(".status").css("display", "none");
                $("#sure").attr("disabled", "disabled");
                $.post("<c:url value='/developers/editAudit'/>",{status:status, comment:comment, id:id, uid:uid},function(result){
                    /*parent.refresh();*/
                    $('#sarch', parent.document).click();
                    closeEditDialog();
                });
            }
        }

        function removeSpan(){
            $(".status").css("display", "none");
            $(".comment").css("display", "none");
        }

        function limitNum(){
            var len = $("#comment").val().length;
            if(len > 300){
                $("#comment").val($("#comment").val().substring(0,300));
            }
        }

        function checkComment(){
            $(".comment").css("display", "none");
        }
    </script>
</head>
<body>
<div class="pd-20">

    <form class="form form-horizontal" id="form-edit">
        <input type="hidden" id="id" name="id" value="${authenCompanyRecords.id}"/>
        <input type="hidden" id="uid" name="uid" value="${authenCompanyRecords.uid}">
        <div class="cl" style="margin-top: 0px">
            <label class="form-label col-4">公司名称：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${authenCompanyRecords.name}
            </div>
        </div>

        <div class="cl">
            <label class="form-label col-4">公司地址：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${authenCompanyRecords.address}
            </div>
        </div>

        <div class="cl">
            <label class="form-label col-4">所属行业：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${dicData.name}
            </div>
        </div>


        <br>
        <center><hr align="center" style="height:1px;border:none;border-top:1px dashed black;width: 80%" /></center>
        <br>

        <c:if test="${authenCompanyRecords.cardType == '0' }">
            <div class="row cl">
                <label class="form-label col-4">证件类型：</label>
                <div class="formControls col-8" style="line-height: 2">
                    三证合一(一照一码)
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">统一社会&nbsp;&nbsp;&nbsp;<br>信用代码：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompanyRecords.creditNo}
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">营业执照：</label>
                <div class="formControls col-8" style="line-height: 2">
                    <img src="<c:url value="/img/anthen/${authenCompanyRecords.businessLicensePic}"/>" class="thumbnail" alt="营业执照"/>
                </div>
            </div>
        </c:if>

        <c:if test="${authenCompanyRecords.cardType == '1' }">
            <div class="row cl">
                <label class="form-label col-4">证件类型：</label>
                <div class="formControls col-8" style="line-height: 2">
                    三证合一
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">注册号：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompanyRecords.registerNo}
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">税务登记号：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompanyRecords.taxRegNo}
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">营业执照：</label>
                <div class="formControls col-8" style="line-height: 2">
                    <img src="<c:url value="/img/anthen/${authenCompanyRecords.businessLicensePic}"/>" class="thumbnail" alt="营业执照"/>
                </div>
            </div>
        </c:if>

        <c:if test="${authenCompanyRecords.cardType == '2' }">
            <div class="row cl">
                <label class="form-label col-4">证件类型：</label>
                <div class="formControls col-8" style="line-height: 2">
                    三证分离
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">税务登记号：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompanyRecords.taxRegNo}
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">税务登记证：</label>
                <div class="formControls col-8" style="line-height: 2">
                    <img src="<c:url value="/img/anthen/${authenCompanyRecords.taxRegPic}"/>" class="thumbnail" alt="税务登记证"/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">营业执照号：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompanyRecords.businessLicenseNo}
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">营业执照：</label>
                <div class="formControls col-8" style="line-height: 2">
                    <img src="<c:url value="/img/anthen/${authenCompanyRecords.businessLicensePic}"/>" class="thumbnail" alt="营业执照"/>
                </div>
            </div>
        </c:if>

        <br>
        <center><hr align="center" style="height:1px;border:none;border-top:1px dashed black;width:80%" /></center>
        <br>

        <div class="cl">
            <label class="form-label col-4">法人代表：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${authenCompanyRecords.legalRepresentative}
            </div>
        </div>
        <div class="cl">
            <label class="form-label col-4">公司电话：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${authenCompanyRecords.telno}
            </div>
        </div>
        <div class="cl">
            <label class="form-label col-4">公司网站：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${authenCompanyRecords.website}
            </div>
        </div>


        <div class="cl">
            <label class="form-label col-4"><span class="c-red">*</span>信息审核：</label>
            <div class="formControls col-8" id="status" style="line-height: 2;width: 150px">
                <select id="pass" name="status" class="select" onchange="removeSpan();">
                    <option selected>请选择</option>
                    <option value="1">通过</option>
                    <option value="2">不通过</option>
                </select>
            </div>


            <span class="status" style="line-height: 2;display: none;color: red">请选择</span>
        </div>
        <div class="row cl">
            <label class="form-label col-4">备注：</label>
            <div class="formControls col-8" style="line-height: 2">
                <textarea onkeyup="limitNum();" oninput="checkComment();" id="comment" name="comment" style="width: 300px;height: 100px" placeholder="如果审核不通过，请备注原因"></textarea><br>
                <span class="comment" style="line-height: 2;display: none;color: red">请输入备注</span>
            </div>
        </div>
        <br>
        <div class="cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="button" id="sure" onclick="submitToAuth();" value="&nbsp;&nbsp;确认&nbsp;&nbsp;">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input class="btn btn-default radius" type="reset" onclick="closeEditDialog();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
            </div>
        </div>

    </form>
</div>
</body>
</html>