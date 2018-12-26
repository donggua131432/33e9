package com.e9cloud.core.util;

import com.google.common.collect.ArrayListMultimap;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * Created by Administrator on 2016/12/1.
 */
public class PinyinUtils {

    private static final ArrayListMultimap<String,String> duoYinZiMap = ArrayListMultimap.create(512, 8);

    public static final String pinyin_sep = "#";

    public static final String word_sep = "/";

    public static enum Type {
        UPPERCASE,              //全部大写
        LOWERCASE,              //全部小写
        FIRSTUPPER              //首字母大写
    }

    //加载多音字词典
    static{
        String filename = "duoyinzi_pinyin.txt";
        System.out.println("load dict:"+filename);
        BufferedReader br = null;
        try {
            InputStream in = PinyinUtils.class.getClassLoader().getResourceAsStream(filename);
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line = null;
            while((line=br.readLine())!=null){

                String[] arr = line.split(pinyin_sep);

                if(StringUtils.isNotEmpty(arr[1])){
                    String[] dyzs = arr[1].split(word_sep);
                    for (String dyz : dyzs) {
                        if(StringUtils.isNotEmpty(dyz)){
                            duoYinZiMap.put(arr[0], dyz.trim());
                        }
                    }
                }
            }

            System.out.println("duoYinZiMap key size:"+duoYinZiMap.keySet().size()+", size:"+duoYinZiMap.size());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String toPinYin(String str) throws BadHanyuPinyinOutputFormatCombination {
        return toPinYin(str, "", Type.FIRSTUPPER);
    }

    public static String toPinYin(String str, String spera) throws BadHanyuPinyinOutputFormatCombination{
        return toPinYin(str, spera, Type.FIRSTUPPER);
    }

    /**
     * get chinese words pin yin
     * @param chinese
     * @param spera 连接符
     * @param type
     * @return
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String toPinYin(String chinese, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination{
        if(StringUtils.isEmpty(chinese)){
            return null;
        }

        //chinese = chinese.replaceAll("[\\.，\\,！·\\!？\\?；\\;\\(\\)（）\\[\\]\\:： ]+", " ").trim();

        char[] chs = chinese.toCharArray();
        StringBuilder py_sb = new StringBuilder(20);

        for (int i=0; i<chs.length; i++) {
            String[] py_arr = chineseToPinYin(chs[i]);
            if(chs[i] < 128 || py_arr==null || py_arr.length<1){
                py_sb.append(chs[i]);
                continue;
            }
            if(py_arr.length==1){
                py_sb.append(convertInitialToUpperCase(py_arr[0], type));
            }else if(py_arr.length==2 && py_arr[0].equals(py_arr[1])){
                py_sb.append(convertInitialToUpperCase(py_arr[0], type));
            }else{
                String resultPy = null, defaultPy = null;;
                for (String py : py_arr) {
                    String left = null; //向左多取一个字,例如 银[行]
                    if(i>=1 && i+1<=chinese.length()){
                        left = chinese.substring(i-1,i+1);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(left)){
                            resultPy = py;
                            break;
                        }
                    }

                    String right = null;    //向右多取一个字,例如 [长]沙
                    if(i<=chinese.length()-2){
                        right = chinese.substring(i,i+2);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(right)){
                            resultPy = py;
                            break;
                        }
                    }

                    String middle = null;   //左右各多取一个字,例如 龙[爪]槐
                    if(i>=1 && i+2<=chinese.length()){
                        middle = chinese.substring(i-1,i+2);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(middle)){
                            resultPy = py;
                            break;
                        }
                    }
                    String left3 = null;    //向左多取2个字,如 芈月[传],列车长
                    if(i>=2 && i+1<=chinese.length()){
                        left3 = chinese.substring(i-2,i+1);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(left3)){
                            resultPy = py;
                            break;
                        }
                    }

                    String right3 = null;   //向右多取2个字,如 [长]孙无忌
                    if(i<=chinese.length()-3){
                        right3 = chinese.substring(i,i+3);
                        if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(right3)){
                            resultPy = py;
                            break;
                        }
                    }

                    if(duoYinZiMap.containsKey(py) && duoYinZiMap.get(py).contains(String.valueOf(chs[i]))){    //默认拼音
                        defaultPy = py;
                    }
                }

                if(StringUtils.isEmpty(resultPy)){
                    if(StringUtils.isNotEmpty(defaultPy)){
                        resultPy = defaultPy;
                    }else{
                        resultPy = py_arr[0];
                    }
                }
                py_sb.append(convertInitialToUpperCase(resultPy, type));
            }
            if (chs[i] > 128) {
                py_sb.append(i== chs.length-1 ? "" : spera);
            }
        }

        return py_sb.toString();
    }

    private static String[] chineseToPinYin(char chineseCharacter) throws BadHanyuPinyinOutputFormatCombination{
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        if(chineseCharacter>=32 && chineseCharacter<=125){    //ASCII >=33 ASCII<=125的直接返回 ,ASCII码表：http://www.asciitable.com/
            return new String[]{String.valueOf(chineseCharacter)};
        }

        return PinyinHelper.toHanyuPinyinStringArray(chineseCharacter, outputFormat);
    }

    private static String convertInitialToUpperCase(String str, Type type) {
        if (str == null || str.length()==0) {
            return "";
        }

        String cstr = "";

        switch (type) {
            case UPPERCASE : cstr = str.toUpperCase(); break;
            case LOWERCASE : cstr = str.toLowerCase(); break;
            default: cstr = str.substring(0, 1).toUpperCase()+str.substring(1);
        }

        return cstr;
    }

}
