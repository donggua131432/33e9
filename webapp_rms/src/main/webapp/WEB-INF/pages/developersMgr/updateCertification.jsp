<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>创建账户</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script src="${appConfig.resourcesRoot}/js/plugins/upload/upimg.js"></script>

</head>
<body>

<div class="pd-20">
    <form action="<c:url value='/developers/updateCertification'/>" method="post" class="form form-horizontal" id="form-admin-add"  enctype="multipart/form-data">
        <input type="hidden" name="id" value="${authenCompany.id}"/>
        <input type="hidden" name="uid" id="uid" value="${authenCompany.uid}">
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>公司名称：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="${authenCompany.name}" placeholder="请输入公司名称" maxlength="30" id="companyName" name="companyName" datatype="_ajax" data-ajax="{'url':'<c:url value="/userAdmin/checkUniqueCompany"/>',param:[{name:'id',value:'${authenCompany.id}'}],msg:'该企业已经存在',_regex:{type:'companyName',msg:'输入的格式不正确'}}" nullmsg="请输入公司名称"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>公司地址：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="${authenCompany.address}"  placeholder="请输入公司地址" id="authAddress" name="authAddress"  maxlength="50" datatype="/^[\u4e00-\u9fa5-a-zA-Z0-9]+$/"  errormsg="格式不正确，请重新输入" nullmsg="请输入公司地址"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>所属行业：</label>
            <div class="formControls col-5">
                <span class="select-box">
                    <select id="tradeId" name="tradeId"  class="select" disabled="disabled" datatype="*"  nullmsg="请选择行业！" >

                    </select>
                </span>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>证件类型：</label>
            <div class="formControls col-5">
                <div class="skin-minimal">
                  <c:if test="${authenCompany.cardType == 0}">
                    <div class="radio-box" style="margin-left:-30px;">
                        <input type="radio" id="cardType1" name="cardType" value="0" onclick="switchTab('#pattern_one', '#pattern_two', '#pattern_three');" checked="checked"><label for="cardType1">三证合一(一照一证)</label>
                    </div>
                    <div class="radio-box">
                        <input type="radio" id="cardType2" name="cardType" value="1" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');"><label for="cardType2">三证合一</label>
                    </div>
                    <div class="radio-box" style="margin-left:-30px;">
                        <input type="radio" id="cardType3" name="cardType" value="2" onclick="switchTab('#pattern_three', '#pattern_two', '#pattern_one');" ><label for="cardType3">三证分离</label>
                    </div>
                  </c:if>

                    <c:if test="${authenCompany.cardType == 1}">
                        <div class="radio-box" style="margin-left:-30px;">
                            <input type="radio" id="cardType11" name="cardType" value="0" onclick="switchTab('#pattern_one', '#pattern_two', '#pattern_three');" ><label for="cardType11">三证合一(一照一证)</label>
                        </div>
                        <div class="radio-box">
                            <input type="radio" id="cardType21" name="cardType" value="1" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');" checked="checked" ><label for="cardType21">三证合一</label>
                        </div>
                        <div class="radio-box" style="margin-left:-30px;">
                            <input type="radio" id="cardType31" name="cardType" value="2" onclick="switchTab('#pattern_three', '#pattern_two', '#pattern_one');" ><label for="cardType31">三证分离</label>
                        </div>
                    </c:if>

                    <c:if test="${authenCompany.cardType == 2}">
                        <div class="radio-box" style="margin-left:-30px;">
                            <input type="radio" id="cardType12" name="cardType" value="0" onclick="switchTab('#pattern_one', '#pattern_two', '#pattern_three');" checked="checked"><label for="cardType12">三证合一(一照一证)</label>
                        </div>
                        <div class="radio-box">
                            <input type="radio" id="cardType22" name="cardType" value="1" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');"><label for="cardType22">三证合一</label>
                        </div>
                        <div class="radio-box" style="margin-left:-30px;">
                            <input type="radio" id="cardType32" name="cardType" value="2" onclick="switchTab('#pattern_three', '#pattern_two', '#pattern_one');" checked="checked" ><label for="cardType32">三证分离</label>
                        </div>
                    </c:if>

                </div>
            </div>
            <div class="col-2"></div>
        </div>

        <!--三证合一(一照一码)-->
        <div id="pattern_one">
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>统一社会信用代码：</label>
                <div class="formControls col-5">
                    <input type="text" class="input-text" value="${authenCompany.creditNo}"  placeholder="请输入统一信用代码" id="creditNo" name="creditNo"   maxlength="18" datatype="_ajax" data-ajax="{'url':'<c:url value="/userAdmin/authcheckUniqueCompany"/>',param:[{name:'uid',value:'${authenCompany.uid}'}],msg:'该代码号已存在',_regex:{type:'creditNo',msg:'格式不正确，请重新输入'}}" nullmsg="请输入社会信用代码号"/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>营业执照证：</label>
                <div class="formControls col-3">

                    <div class="webuploader-pick">选择图片</div>
                    <div onclick="document.getElementById('id_photo1').click();" style="position: absolute; top: 0px; left: 0px; width: 80px; height: 30px; overflow: hidden; bottom: auto; right: auto;">
                        <input id="id_photo1" type="file"  name="businessLicenseFile" class="webuploader-element-invisible" >
                        <label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
                    </div>
                    <button onclick="removeImg('id_photo1','id_photo1_file');" type="button" class="btn btn-default btn-uploadstar radius ml-10">删除图片</button>
                </div>


                <div class="col-1"></div>

            </div>
            <div class="row cl">
                <div class="formControls col-3">
                </div>
                <div class="col-4">
                    <img id="id_photo1_file" src="" alt="营业执照证" class="thumbnail col-2" style="display: none;">
                    <span id="id_photo1_error" class=""></span>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-3">
                </div>
                <label class="form-label col-6"> <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" class="thumbnail" alt="营业执照"/></label>
            </div>

        </div>
        <!--三证合一-->
        <div id="pattern_two" style="display: none;" >
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>注册号：</label>
                <div class="formControls col-5">
                    <input type="text" class="input-text" value="${authenCompany.registerNo}"  placeholder="请输入注册号" id="registerNo" name="registerNo"   datatype="_ajax" maxlength="15" data-ajax="{'url':'<c:url value="/userAdmin/authcheckUniqueCompany"/>',param:[{name:'uid',value:'${authenCompany.uid}'}],msg:'该注册号已存在',_regex:{type:'taxReg',msg:'格式不正确，请重新输入'}}" nullmsg="请输入注册号"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>税务登记号：</label>
                <div class="formControls col-5">
                    <input type="text" class="input-text" value="${authenCompany.taxRegNo}"  placeholder="请输入税务登记号" id="taxRegNo" name="taxRegNo" datatype="_ajax" maxlength="15" data-ajax="{'url':'<c:url value="/userAdmin/authcheckUniqueCompany"/>',param:[{name:'uid',value:'${authenCompany.uid}'}],msg:'该登记号已存在',_regex:{type:'taxReg',msg:'格式不正确，请重新输入'}}" nullmsg="请输入税务登记号"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>营业执照证：</label>
                <div class="formControls col-5">
                    <div class="webuploader-pick">选择图片</div>
                    <div onclick="document.getElementById('id_photo2').click();" style="position: absolute; top: 0px; left: 0px; width: 80px; height: 30px; overflow: hidden; bottom: auto; right: auto;">
                        <input id="id_photo2" type="file" name="businessLicenseFile" class="webuploader-element-invisible" multiple="multiple" accept="image/*" >
                        <label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
                    </div>
                    <button onclick="removeImg('id_photo2','id_photo2_file');" type="button" class="btn btn-default btn-uploadstar radius ml-10">删除图片</button>
                </div>

                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <div class="formControls col-3">
                </div>
                <div class="col-4">
                    <img id="id_photo2_file" src="" alt="营业执照证" class="thumbnail col-2" style="display: none;">
                    <span id="id_photo2_error" class=""></span>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-3">
                </div>
                <label class="form-label col-6"> <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" class="thumbnail" alt="营业执照"/></label>
            </div>
        </div>
        <!--三证分离-->
        <div id="pattern_three" style="display: none;">
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>税务登记号：</label>
                <div class="formControls col-5">
                    <input type="text" class="input-text" value="${authenCompany.taxRegNo}"  placeholder="请输入税务登记号" id="taxRegNo1" name="taxRegNo" datatype="_ajax" maxlength="15" data-ajax="{'url':'<c:url value="/userAdmin/authcheckUniqueCompany"/>',param:[{name:'uid',value:'${authenCompany.uid}'}],msg:'该登记号已存在',_regex:{type:'taxReg',msg:'格式不正确，请重新输入'}}" nullmsg="请输入税务登记号"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>税务登记证：</label>
                <div class="formControls col-5">
                    <div class="webuploader-pick">选择图片</div>
                    <div onclick="document.getElementById('taxRegPic').click();" style="position: absolute; top: 0px; left: 0px; width: 80px; height: 30px; overflow: hidden; bottom: auto; right: auto;">
                        <input id="taxRegPic" type="file" name="taxRegFile" class="webuploader-element-invisible" multiple="multiple" accept="image/*" >
                        <label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
                    </div>
                    <button onclick="removeImg('taxRegPic','tax_reg_pic_file');" type="button" class="btn btn-default btn-uploadstar radius ml-10">删除图片</button>
                </div>

                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <div class="formControls col-3">
                </div>
                <div class="col-4">
                    <img id="tax_reg_pic_file" src="" alt="税务登记证" class="thumbnail col-2" style="display: none;">
                    <span id="taxRegPic_error" class=""></span>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-3">
                </div>
                <label class="form-label col-6"> <img src="<c:url value="/img/anthen/${authenCompany.taxRegPic}"/>" class="thumbnail" alt="税务登记证"/></label>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>营业执照号：</label>
                <div class="formControls col-5">
                    <input type="text" class="input-text" value="${authenCompany.businessLicenseNo}"  placeholder="请输入营业执照号" id="businessLicenseNo" name="businessLicenseNo" maxlength="15" datatype="_ajax" data-ajax="{'url':'<c:url value="/userAdmin/authcheckUniqueCompany"/>',param:[{name:'uid',value:'${authenCompany.uid}'}],msg:'该执照号已存在',_regex:{type:'businessLicense',msg:'格式不正确，请重新输入'}}" nullmsg="请输入营业执照号"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>营业执照证：</label>
                <div class="formControls col-5">
                    <div class="webuploader-pick">选择图片</div>
                    <div onclick="document.getElementById('id_photo3').click();" style="position: absolute; top: 0px; left: 0px; width: 80px; height: 30px; overflow: hidden; bottom: auto; right: auto;" >
                        <input id="id_photo3" type="file" name="businessLicenseFile" class="webuploader-element-invisible" multiple="multiple" accept="image/*" >
                        <label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>
                    </div>
                    <button onclick="removeImg('id_photo3','id_photo3_file');" type="button" class="btn btn-default btn-uploadstar radius ml-10">删除图片</button>
                </div>

                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <div class="formControls col-3">
                </div>
                <div class="col-4">
                    <img id="id_photo3_file" src="" alt="营业执照证" class="thumbnail col-2" style="display: none;">
                    <span id="id_photo3_error" class=""></span>
                </div>
            </div>
            <div class="row cl">
                <div class="formControls col-3">
                </div>
                <label class="form-label col-6"> <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" class="thumbnail" alt="营业执照"/></label>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>法定代表人：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="${authenCompany.legalRepresentative}"  placeholder="请输入法定代表人" id="legalRepresentative" name="legalRepresentative"  maxlength="10" datatype="/^[\u4e00-\u9fa5-a-zA-Z]{2,10}$/"  errormsg="格式不正确，请重新输入" nullmsg="请输入法定代表人"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>公司电话：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="${authenCompany.telno}"  placeholder="请输入公司电话" id="telno" name="telno"  maxlength="30" datatype="/^[0-9\-\(\)]{3,15}$/"  errormsg="格式不正确，请重新输入" nullmsg="请输入公司电话"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>公司网站：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="${authenCompany.website}"  placeholder="请输入公司网站" id="website" name="website"  maxlength="30" datatype="/^((http(s)?:\/\/)?(www\.)?[\w-]+\.\w{2,4}(\/)?)?$/"  errormsg="格式不正确，请重新输入"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <div class="col-6 col-offset-5">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
                <input class="btn btn-default radius" type="reset"  onclick="closeEditDialog();"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    function closeEditDialog(){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    var result = '${result}';
    if(result){
        if(result=='00'){
            layer.alert('修改认证信息成功', {
                icon: 1,
                skin: 'layui-layer-molv' ,//样式类名
                closeBtn: 0
            });
            parent.datatables.fnDraw();
            closeEditDialog();
        }else  if(result=='01'){
            layer.alert('税务登记证图片太大或格式不正确', {
                icon: 2,
                skin: 'layui-layer-molv' //样式类名
                ,closeBtn: 0
            });
        }else  if(result=='02'){
            layer.alert('营业执照证图片太大或格式不正确', {
                icon: 2,
                skin: 'layui-layer-molv' //样式类名
                ,closeBtn: 0
            });
        }else  if(result=='error'){
            layer.alert('系统异常，请重新创建', {
                icon: 2,
                skin: 'layui-layer-molv' //样式类名
                ,closeBtn: 0
            });

        }

    }
    $(function(){

        // 第二和第三个tab的表单元素都禁用掉
        $("#pattern_two,#pattern_three").find('input').attr("disabled",true).attr("ignore","ignore");
//        $('.skin-minimal input').iCheck({
//            checkboxClass: 'icheckbox-blue',
//            radioClass: 'iradio-blue',
//            increaseArea: '20%'
//        });


        // 表单校验
        $("#form-admin-add").Validform({
            tiptype:2,
            ignoreHidden:false,
            callback:function(form){
                form[0].submit();

            }
        });


        // 图片预览绑定事件
        $("#id_photo1").uploadPreview({ Img: "id_photo1_file", Width: 260, Height: 200, ImgType: ["gif","jpg", "png"]});
        $("#id_photo2").uploadPreview({ Img: "id_photo2_file", Width: 260, Height: 200, ImgType: ["gif","jpg", "png"]});
        $("#id_photo3").uploadPreview({ Img: "id_photo3_file", Width: 260, Height: 200, ImgType: ["gif","jpg", "png"]});
        $("#taxRegPic").uploadPreview({ Img: "tax_reg_pic_file", Width: 260, Height: 200, ImgType: ["gif","jpg","png"]});
        // 加载所属行业列表
        getTradeOption();
        $("input[name='cardType']:checked").click();
    });

    // 加载所属行业列表
    function getTradeOption() {
        $.ajax({
            type: "POST",
            url: "<c:url value='/dicdata/type'/>",
            data: {"typekey" : "tradeType"},
            success: function(msg){
                if (msg.data) {
                    $("#tradeId").html("");
                    var options = "<option value=''>请选择所属行业</option>";
                    $.each(msg.data, function(n,v) {
                        options += "<option value='" + v.code + "'>" + v.name + "</option>";
                    });
                    $("#tradeId").append(options);
                    $("#tradeId").val('${authenCompany.tradeId}');

                }
            }
        });
    }

    // 移除图片
    function removeImg(file,img) {
        $("#" + file).val("");
        $("#" + img).attr("src","").hide();
    }
    // 切换tab
    function switchTab(sid, hid,fid) {
        $(sid).show().find('input').attr("disabled",false).attr("ignore","");
        $(hid).hide().find('input').attr("disabled",true).attr("ignore","ignore").val("");
        $(fid).hide().find('input').attr("disabled",true).attr("ignore","ignore").val("");
        $("input:file").each( function() {//遍历页面里所有的input:file
            var id =this.id;
            var ignore = $("#"+id).attr("ignore")
            if(ignore){
                $('#'+this.id+"_error").empty().hide();
            }
        });
    }
</script>
</body>
</html>
