
function ajaxDicData(ctx,typekey,selectId,defaultValue,defaultKey,defaultshow,rsync){
	var _async=true;
	if(rsync){
		_async=false;
	}
	var idKey="code";
	var nameshow="name";
	if(defaultKey){
		idKey=defaultKey;
	}
	var _ctx=ctx+"dicdata/type";
	jQuery.ajax({    
        type:"post",        
        url:_ctx,
        async:_async,
        dataType:"json",
		data:{"typekey":typekey},
        success:function(_data){
        	jQuery("#"+selectId).empty();
        	jQuery("#"+selectId).append("<option value=''>全部</option>");
        	jQuery(_data.data).each(function(i,_json){
        	 	jQuery("#"+selectId).append("<option value='"+_json[idKey]+"'>"+_json[nameshow]+"</option>");
        	});
        	//回选 下拉框
        	jQuery("#"+selectId+" option[value='"+defaultValue+"']").attr("selected",true);
        }
    });    
}

function ajaxDicDataByPid(ctx,pid,selectId,defaultValue,defaultKey,defaultshow,rsync){
	var _async=true;
	if(rsync){
		_async=false;
	}
	var idKey="code";
	var nameshow="name";
	if(defaultKey){
		idKey=defaultKey;
	}
	var _ctx=ctx+"dicdata/pid";
	jQuery.ajax({
		type:"post",
		url:_ctx,
		async:_async,
		dataType:"json",
		data:{"pid":pid},
		success:function(_data){
			jQuery("#"+selectId).empty();
			jQuery("#"+selectId).append("<option value=''>===请选择===</option>");
			jQuery(_data.data).each(function(i,_json){
				jQuery("#"+selectId).append("<option value='"+_json[idKey]+"'>"+_json[nameshow]+"</option>");
			});
			//回选 下拉框
			jQuery("#"+selectId+" option[value='"+defaultValue+"']").attr("selected",true);
		}
	});
}

/**
 * ajax 获取字典,并使用复选框的方式渲染到指定容器
 * @param pathkey
 * @param inputId
 * @param containerId
 * @param defaultValue
 * @param defaultValue
 * @param defaultKey
 * @param defaultshow
 * @param rsync
 */
function ajaxCheckboxDicData(ctx,pathkey,inputId,containerId,defaultValue,defaultKey,defaultshow,rsync){
	var _ctx=ctx+"dicdata/type";
	ajaxCheckboxData(_ctx,inputId,containerId,defaultValue,defaultKey,defaultshow,rsync)
}





/**
 * ajax 获取字典,并使用复选框的方式渲染到指定容器
 * @param pathkey
 * @param inputId
 * @param containerId
 * @param defaultValue
 * @param defaultValue
 * @param defaultKey
 * @param defaultshow
 * @param rsync
 */
function ajaxCheckboxData(_ctx,inputId,containerId,defaultValue,defaultKey,defaultshow,rsync){
	var _async=true;
	if(rsync){
		_async=false;
	}
	var idKey="code";
	var nameshow="name";
	if(defaultKey){
		idKey=defaultKey;
	}
	jQuery.ajax({
		type:"post",
		url:_ctx,
		async:_async,
		dataType:"json",
		data:{"typekey":containerId},
		success:function(data){
			jQuery("#"+containerId).empty();
			var clickstr=" onclick=setValueByCheckBox(this,'"+inputId+"','"+containerId+"');";
			jQuery(data).each(function(i,_json){
				jQuery("#"+containerId).append("<input  type='checkbox' "+clickstr+"   value='"+_json[idKey]+"' />"+_json[nameshow]+"&nbsp;&nbsp;");
			});
			//回选 复选框
			if(defaultValue){
				var vl=defaultValue.split(",");
				jQuery(vl).each(function(_i,_value){
					jQuery(":checkbox[value='"+_value+"']",	jQuery("#"+containerId)).attr("checked", true);
				});
			}
		}
	});
}