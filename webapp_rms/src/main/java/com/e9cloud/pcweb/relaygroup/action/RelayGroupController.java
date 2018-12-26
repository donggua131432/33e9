package com.e9cloud.pcweb.relaygroup.action;

import com.e9cloud.core.office.ExcelReader;
import com.e9cloud.core.office.POIXlsView;
import com.e9cloud.core.page.Page;
import com.e9cloud.core.page.PageWrapper;
import com.e9cloud.core.util.JSonMessageSubmit;
import com.e9cloud.core.util.R;
import com.e9cloud.core.util.Tools;
import com.e9cloud.mybatis.domain.AppointLink;
import com.e9cloud.mybatis.domain.RelayGroup;
import com.e9cloud.mybatis.service.RelayGroup1Service;
import com.e9cloud.pcweb.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中继群 公共操作
 * Created by Administrator on 2016/9/9.
 */
@Controller
@RequestMapping("/relaygroup")
public class RelayGroupController extends BaseController {

    @Autowired
    private RelayGroup1Service relayGroup1Service;

    // 导入表格
    @RequestMapping("toImportGroup1")
    public String toImportGroup1(Model model, String g) {
        model.addAttribute("g", g);
        return "relayGroup/importAppointLink";
    }

}
