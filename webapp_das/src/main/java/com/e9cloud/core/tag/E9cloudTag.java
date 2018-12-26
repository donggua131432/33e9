package com.e9cloud.core.tag;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.freemarker.DataModelWrapper;
import com.e9cloud.core.freemarker.FreeMarkerUtils;
import com.e9cloud.core.tag.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * e9cloud自定义标签
 *
 * Created by Administrator on 2015/12/16.
 */
public class E9cloudTag extends TagSupport{
    private static final long serialVersionUID = 125554448779L;

    private String primaryKey;

    @Override
    public int doStartTag() throws JspException {
        DataModelWrapper dmw = SpringContextHolder.getBean(TemplateService.class).getDataModelWrapperByPK(primaryKey);

        JspWriter out = this.pageContext.getOut();
        FreeMarkerUtils.process(dmw, out);

        return super.doStartTag();
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }
}
