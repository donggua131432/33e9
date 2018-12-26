package com.e9cloud.core.freemarker;

import com.e9cloud.core.util.Exceptions;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 *
 * freemarker 工具类
 * Created by wangzhongjun on 2015/12/15.
 */
public class FreeMarkerUtils {

    /**
     * 根据模板文件生成文件
     * @param srcFile 源文件所在的文件夹
     * @param fileName 模板文件名
     * @param dataModel 数据模型
     * @param out 输出
     */
    public static void process(String srcFile,String fileName,Map<String,Object> dataModel,Writer out){
        try {
            Template temp = loadTemplate(srcFile,fileName);

			/* 将模板和数据模型合并 */
            temp.process(dataModel, out);
            out.flush();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 根据模板文件生成文件
     * @param srcFile 源文件所在的文件夹
     * @param fileName 模板文件名
     * @param dataModel 数据模型
     * @param targetFile 目标文件
     */
    public static void process(String srcFile,String fileName,Map<String,Object> dataModel,String targetFile){
        try {
            Template temp = loadTemplate(srcFile,fileName);

			/* 将模板和数据模型合并 */
            Writer out = new OutputStreamWriter(new FileOutputStream(targetFile));
            temp.process(dataModel, out);
            out.flush();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 根据字符模板和数据模型输出到相应的地方
     *
     * @param stringTemplate 字符模板
     * @param dataModel 数据模型
     * @param out 输出
     */
    public static void process(String stringTemplate, Map<String, Object> dataModel, Writer out) {

        try {
            Template temp = convertStringToTemplate(stringTemplate);

			/* 将模板和数据模型合并 */
            temp.process(dataModel, out);
            out.flush();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 根据字符模板和数据模型生成文件到目标路径
     *
     * @param stringTemplate 字符模板
     * @param dataModel 数据模型
     * @param targetFile 目标位置
     */
    public static void process(String stringTemplate, Map<String, Object> dataModel,String targetFile) {

        try {
            Template temp = convertStringToTemplate(stringTemplate);

			/* 将模板和数据模型合并 */
            Writer out = new OutputStreamWriter(new FileOutputStream(targetFile));
            temp.process(dataModel, out);
            out.flush();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 根据字符模板和数据模型生成文件到目标路径
     *
     * @param dataModelWrapper 字符模板
     * @param out 输出
     */
    public static void process(DataModelWrapper dataModelWrapper,Writer out) {

        try {
            Template temp = convertStringToTemplate(dataModelWrapper.getStringTemplate());

			/* 将模板和数据模型合并 */
            temp.process(dataModelWrapper.getDataModel(), out);
            out.flush();
        } catch (Exception e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 把字符模板转换为模板
     *
     * @param stringTemplate 字符木模板
     * @return 模板
     * @throws IOException
     */
    private static Template convertStringToTemplate(String stringTemplate) throws IOException {
        /* 在整个应用的生命周期中，这个工作你应该只做一次。 */
        /* 创建和调整配置。 */
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("", stringTemplate);
        cfg.setTemplateLoader(stringLoader);

        /* 在整个应用的生命周期中，这个工作你可以执行多次 */
		/* 获取或创建模板 */
        Template temp = cfg.getTemplate("");

        return temp;
    }

    /**
     * 根据模板文件加载模板
     * @param srcFile 源文件所在的文件夹
     * @param fileName 模板文件名
     * @return  template 模板
     */
    private static Template loadTemplate(String srcFile,String fileName)throws IOException{
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(srcFile));

        Template temp = cfg.getTemplate(fileName);

        return temp;
    }
}
