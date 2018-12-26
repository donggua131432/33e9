package com.e9cloud.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.*;

public class ReadXmlUtil {
    /**
     *Function: readXML: 解析字符串xml 仅支持一级根元素，多级再迭代
     *
     *@param strXML
     *@return Map
     */
    public static Map<String,String> readXML(String strXML) throws Exception {
        Map<String,String> map=new HashMap<String,String>();
        //获得解析器
        SAXReader sr = new SAXReader();
        //解析xml文件
        //String str="<RS><TYPE>03</TYPE><CID>43840554</CID><CNAME>majunjie</CNAME><CNO>430505199910109999</CNO></RS>";
        Document doc = sr.read(new ByteArrayInputStream(strXML.getBytes("utf-8")));
        //获得xml根元素
        Element root = doc.getRootElement();
        //System.out.println(root.getName());
        map.put("root",root.getName());
        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            //System.out.println(e.getName() + ": " + e.getText());
            map.put(e.getName(),e.getTextTrim());
        }

        return map;
    }

    /**
     *Function: readListXML: 解析字符串list xml
     *
     *@param strXML
     *@return Map
     */
    public static Map<String,Object> readListXML(String strXML) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        //获得解析器
        SAXReader sr = new SAXReader();
        //解析xml文件
//        String str= "<req>" +
//                    "<appId>b6f55ae1e5344782bccf03a8fc526460</appId>" +
//                    "<virtualNumberList>" +
//                    "<virtualNumber>" +
//                    "<caller>135123412341</caller>" +
//                    "<called>13412341235</called>" +
//                    "<needRecord>1</needRecord>" +
//                    "<validTime>3600</validTime>" +
//                    "</virtualNumber>" +
//                    "<virtualNumber>" +
//                    "<caller>13512345678</caller>" +
//                    "<called>13412345679</called>" +
//                    "<needRecord>1</needRecord>" +
//                    "<validTime>7200</validTime>" +
//                    "</virtualNumber>" +
//                    "</virtualNumberList>" +
//                    "</req>";
        Document doc = sr.read(new ByteArrayInputStream(strXML.getBytes("utf-8")));
        //获得xml根元素
        Element root = doc.getRootElement();
        System.out.println(root.getName());
        map.put("root",root.getName());

        Iterator itert = root.elementIterator("appId"); // 获取根节点下的子节点head
        // 遍历head节点
        while (itert.hasNext()) {
            Element recordEle = (Element) itert.next();
            System.out.println(recordEle.getName() + ": " + recordEle.getTextTrim());
            map.put(recordEle.getName(),recordEle.getTextTrim());
        }

        Iterator iter = root.elementIterator("virtualNumberList"); // 获取根节点下的子节点head
        // 遍历head节点
        while (iter.hasNext()) {
            Element recordEle = (Element) iter.next();
            Iterator iters = recordEle.elementIterator("virtualNumber"); // 获取子节点head下的子节点script
            List list=new ArrayList<>();
            // 遍历Header节点下的Response节点
            while (iters.hasNext()) {
                Element itemEle = (Element) iters.next();

                Iterator itemElet = itemEle.elementIterator(); // 获取根节点下的子节点head
                Map mapTmp=new HashMap<>();
                // 遍历head节点
                while (itemElet.hasNext()) {
                    Element itemdEle = (Element) itemElet.next();
                    System.out.println(itemdEle.getName() + ": " + itemdEle.getTextTrim());
                    mapTmp.put(itemdEle.getName(),itemdEle.getTextTrim());
                }
                list.add(mapTmp);
            }
            map.put("virtualNumberList",list);
        }

        return map;
    }

    public static void main(String[] args)
    {
        try {
//            String str="<RS><TYPE></TYPE><TYPE>03</TYPE><CID>43840554</CID><CNAME>majunjie</CNAME><CNO>430505199910109999</CNO></RS>";
//            Map<String,String> map=readXML(str);
//            System.out.println(map.get("TYPE"));
//            System.out.println(map.get("CID"));
            String str= "<req>" +
                            "<appId>b6f55ae1e5344782bccf03a8fc526460</appId>" +
                            "<virtualNumberList>" +
                                "<virtualNumber>" +
                                    "<caller>135123412341</caller>" +
                                    "<called>13412341235</called>" +
                                    "<needRecord>1</needRecord>" +
                                    "<validTime>3600</validTime>" +
                                "</virtualNumber>" +
                                "<virtualNumber>" +
                                    "<caller>13512345678</caller>" +
                                    "<called>13412345679</called>" +
                                    "<needRecord>1</needRecord>" +
                                    "<validTime>7200</validTime>" +
                                "</virtualNumber>" +
                            "</virtualNumberList>" +
                        "</req>";
            readListXML(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
