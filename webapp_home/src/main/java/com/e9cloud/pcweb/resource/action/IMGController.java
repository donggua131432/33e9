package com.e9cloud.pcweb.resource.action;

import com.e9cloud.core.util.FileUtil;
import com.e9cloud.core.util.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Administrator on 2016/3/10.
 */
@Controller
@RequestMapping("/nasimg")
public class IMGController {

    @RequestMapping(value = "anthen/{path}/{file}.{suf}", method = RequestMethod.GET)
    public String showImg(HttpServletResponse response, @PathVariable("path") String path, @PathVariable("file") String file, @PathVariable("suf") String suf, String type) {
        BufferedInputStream bis = FileUtil.readFileToStream(path, file + "." + suf);
        BufferedOutputStream bos = null;
        if (bis != null) {
            try {
                // response.setContentType("image/*");
                bos = new BufferedOutputStream(response.getOutputStream());

                int len = 1024;
                byte[] b = new byte[len];
                while ((len = bis.read(b)) != -1){
                    bos.write(b, 0, len);
                }
                bos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return null;
    }

}
