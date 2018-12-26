package com.e9cloud.pcweb.rate.action;

import com.e9cloud.pcweb.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/6/14.
 */
@Controller
@RequestMapping(value = "/feeRate")
public class FeeRateController extends BaseController {

    @RequestMapping("feeRateConfig")
    public String feeRateConfig(){
        return "rate/feeRateConfig";
    }
}
