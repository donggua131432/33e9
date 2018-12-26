package com.e9cloud.pcweb.resource.action;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.util.CopyVoiceUtil;
import com.e9cloud.core.util.FileUtil;
import com.e9cloud.pcweb.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by Administrator on 2016/3/10.
 */
@Controller
@RequestMapping("/voice")
public class VoiceController extends BaseController {

    @RequestMapping(value = "anthen/{path}/{file}.{suf}", method = RequestMethod.GET)
    public String showAudio(HttpServletRequest request, HttpServletResponse response, @PathVariable("path") String path, @PathVariable("file") String file, @PathVariable("suf") String suf, String type) {
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        String p = appConfig.getNasVoicePath() + path + "/" + file + "." + suf;
        File f = new File(p);
        long length = f.length();
        String range = request.getHeader("Range");
        String[] rs = range.split("\\=");
        range = rs[1].split("\\-")[0];
        if (f.exists()) {
            try {
                FileInputStream  fis = new FileInputStream (f);
                OutputStream os = response.getOutputStream();
                int irange = Integer.parseInt(range);
                length = length - irange;

                response.addHeader("Accept-Ranges", "bytes");
                response.addHeader("Content-Length", length + "");
                response.addHeader("Content-Range", "bytes " + range + "-" + length + "/" + length);
                response.addHeader("Content-Type", "audio/wav;charset=UTF-8");

                int len = 0;
                byte[] b = new byte[1024];
                while ((len = fis.read(b)) != -1) {
                    os.write(b, 0, len);
                }
                fis.close();
                os.close();
                return null;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping(value = "temp/{path}/{file}.{suf}", method = RequestMethod.GET)
    public String showTempAudio(HttpServletRequest request, HttpServletResponse response, @PathVariable("path") String path, @PathVariable("file") String file, @PathVariable("suf") String suf, String type) {
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        String p = appConfig.getLocalTempVoicePath() + path + "/" + file + "." + suf;
        File f = new File(p);
        long length = f.length();
        String range = request.getHeader("Range");
        String[] rs = range.split("\\=");
        range = rs[1].split("\\-")[0];
        if (f.exists()) {
            try {
                FileInputStream  fis = new FileInputStream (f);
                OutputStream os = response.getOutputStream();
                int irange = Integer.parseInt(range);
                length = length - irange;

                response.addHeader("Accept-Ranges", "bytes");
                response.addHeader("Content-Length", length + "");
                response.addHeader("Content-Range", "bytes " + range + "-" + length + "/" + length);
                response.addHeader("Content-Type", "audio/wav;charset=UTF-8");

                int len = 0;
                byte[] b = new byte[1024];
                while ((len = fis.read(b)) != -1) {
                    os.write(b, 0, len);
                }
                fis.close();
                os.close();
                return null;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    @RequestMapping(value = "verify/{path}/{file}.{suf}", method = RequestMethod.GET)
    public String showVerifyTempAudio(HttpServletRequest request, HttpServletResponse response, @PathVariable("path") String path, @PathVariable("file") String file, @PathVariable("suf") String suf, String type) {
        AppConfig appConfig = SpringContextHolder.getBean(AppConfig.class);
        String p = appConfig.getLocalVoiceVerifyTempPath() + path + "/" + file + "." + suf;
        File f = new File(p);
        long length = f.length();
        String range = request.getHeader("Range");
        String[] rs = range.split("\\=");
        range = rs[1].split("\\-")[0];
        if (f.exists()) {
            try {
                FileInputStream  fis = new FileInputStream (f);
                OutputStream os = response.getOutputStream();
                int irange = Integer.parseInt(range);
                length = length - irange;

                response.addHeader("Accept-Ranges", "bytes");
                response.addHeader("Content-Length", length + "");
                response.addHeader("Content-Range", "bytes " + range + "-" + length + "/" + length);
                response.addHeader("Content-Type", "audio/wav;charset=UTF-8");

                int len = 0;
                byte[] b = new byte[1024];
                while ((len = fis.read(b)) != -1) {
                    os.write(b, 0, len);
                }
                fis.close();
                os.close();
                return null;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
