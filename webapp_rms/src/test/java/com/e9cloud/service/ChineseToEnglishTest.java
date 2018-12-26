package com.e9cloud.service;

import com.e9cloud.base.BaseTest;
import com.e9cloud.core.util.PinyinUtils;
import com.e9cloud.core.util.Tools;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/1.
 */

public class ChineseToEnglishTest extends BaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Map<String, String> provincePer = new HashMap<>();

    private Map<String, String> provinceSuf = new HashMap<>();

    private Map<String, String> provincecapital = new HashMap<>();

    private Map<String, String> provincePerSuf = new HashMap<>();

    private String[] provincecapitals = {
            "哈尔滨","长春","沈阳","南昌","南京",
            "济南","合肥","石家庄","郑州","武汉",
            "长沙","西安","太原","成都","西宁",
            "海口","广州","贵阳","杭州","福州",
            "兰州","昆明","呼和浩特","银川","乌鲁木齐",
            "拉萨","南宁","香港", "澳门","台北",
            "北京", "上海", "天津", "重庆"
    };

    @Before
    public void testBefore() {

        // 处理 直辖市
        provincePer.put("北京", "a1");
        provincePer.put("上海", "a2");
        provincePer.put("天津", "a3");
        provincePer.put("重庆", "a4");

        // 处理 港澳台
        provinceSuf.put("香港", "_1");
        provinceSuf.put("澳门", "_2");
        provinceSuf.put("台湾", "_3");

        provincePerSuf.putAll(provincePer);
        provincePerSuf.putAll(provinceSuf);

        // 处理 省会城市
        provincecapital.put("哈尔滨", "a1");
        provincecapital.put("长春", "a1");
        provincecapital.put("沈阳", "a1");
        provincecapital.put("南昌", "a1");
        provincecapital.put("南京", "a1");

        provincecapital.put("济南", "a1");
        provincecapital.put("合肥", "a1");
        provincecapital.put("石家庄", "a1");
        provincecapital.put("郑州", "a1");
        provincecapital.put("武汉", "a1");

        provincecapital.put("长沙", "a1");
        provincecapital.put("西安", "a1");
        provincecapital.put("太原", "a1");
        provincecapital.put("成都", "a1");
        provincecapital.put("西宁", "a1");

        provincecapital.put("海口", "a1");
        provincecapital.put("广州", "a1");
        provincecapital.put("贵阳", "a1");
        provincecapital.put("杭州", "a1");
        provincecapital.put("福州", "a1");

        provincecapital.put("兰州", "a1");
        provincecapital.put("昆明", "a1");
        provincecapital.put("呼和浩特", "a1");
        provincecapital.put("银川", "a1");
        provincecapital.put("乌鲁木齐", "a1");

        provincecapital.put("拉萨", "a1");
        provincecapital.put("南宁", "a1");
        provincecapital.put("香港", "a1");
        provincecapital.put("澳门", "a1");
        provincecapital.put("台北", "a1");

        provincecapital.put("北京", "a1");
        provincecapital.put("上海", "a1");
        provincecapital.put("天津", "a1");
        provincecapital.put("重庆", "a1");

    }

    // 公司
    @Test
    //@Rollback(false)
    public void testCompanyName() {
        String querySql = "SELECT id,name FROM tb_user_admin_authen_company";
        List<Map<String, Object>> compays = jdbcTemplate.queryForList(querySql);
        String[] sqls = new String[compays.size()];

        for (int i = 0; i < compays.size(); i++) {
            Map<String, Object> map = compays.get(i);
            String pinyin = null;
            try {
                pinyin = PinyinUtils.toPinYin(Tools.toStr(map.get("name")));
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            sqls[i] = "UPDATE tb_user_admin_authen_company SET pinyin = '"+ pinyin + "' WHERE id = " + map.get("id");
            System.out.println(sqls[i] + ";");
        }

        //jdbcTemplate.batchUpdate(sqls);
    }

    // 省份
    @Test
    //@Rollback(false)
    public void testTelnoProvince() {
        String querySql = "SELECT id,pname FROM tb_telno_province;";
        List<Map<String, Object>> compays = jdbcTemplate.queryForList(querySql);
        String[] sqls = new String[compays.size()];

        for (int i = 0; i < compays.size(); i++) {
            Map<String, Object> map = compays.get(i);
            String pname = Tools.toStr(map.get("pname"));
            String per = getPer(provincePerSuf, pname);

            String pinyin = null;
            try {
                pinyin = PinyinUtils.toPinYin(pname);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            sqls[i] = "UPDATE tb_telno_province SET pinyin = '"+ per + pinyin + "' WHERE id = " + map.get("id");
            System.out.println(sqls[i] + ";");
        }

        // jdbcTemplate.batchUpdate(sqls);
    }

    // 城市
    @Test
    @Rollback(true)
    public void testTelnoCity() {
        String querySql = "SELECT id,cname FROM tb_telno_city";
        List<Map<String, Object>> compays = jdbcTemplate.queryForList(querySql);
        String[] sqls = new String[compays.size()];

        for (int i = 0; i < compays.size(); i++) {
            Map<String, Object> map = compays.get(i);
            String cname = Tools.toStr(map.get("cname"));
            String per = getPer(provincecapital, cname);
            String pinyin = null;
            try {
                pinyin = PinyinUtils.toPinYin(cname);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            sqls[i] = "UPDATE tb_telno_city SET pinyin = '"+ per + pinyin + "' WHERE id = " + map.get("id");
            System.out.println(sqls[i] + ";");
        }


        //jdbcTemplate.batchUpdate(sqls);
    }

    // 区域
    @Test
    @Rollback(false)
    public void testCcErea() {
        String querySql = "SELECT id,aname FROM tb_cc_area";
        List<Map<String, Object>> compays = jdbcTemplate.queryForList(querySql);
        String[] sqls = new String[compays.size()];

        for (int i = 0; i < compays.size(); i++) {
            Map<String, Object> map = compays.get(i);
            String pinyin = null;
            try {
                pinyin = PinyinUtils.toPinYin(Tools.toStr(map.get("aname")));
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            sqls[i] = "UPDATE tb_cc_area SET pinyin = '"+ pinyin + "' WHERE id = " + map.get("id");
            System.out.println(sqls[i] + ";");
        }

        //jdbcTemplate.batchUpdate(sqls);
    }

    // 应用
    @Test
    @Rollback(false)
    public void testAppInfo() {
        String querySql = "SELECT id,app_name FROM tb_app_info";
        List<Map<String, Object>> compays = jdbcTemplate.queryForList(querySql);
        String[] sqls = new String[compays.size()];

        for (int i = 0; i < compays.size(); i++) {
            Map<String, Object> map = compays.get(i);
            String pinyin = null;
            try {
                pinyin = PinyinUtils.toPinYin(Tools.toStr(map.get("app_name")));
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
            sqls[i] = "UPDATE tb_app_info SET pinyin = '"+ pinyin + "' WHERE id = " + map.get("id");
            System.out.println(sqls[i] + ";");
        }

//        jdbcTemplate.batchUpdate(sqls);
    }

    private String getPer(Map<String, String> p, String name) {
        String str = "";
        if (Tools.isNotNullStr(name) && name.length() > 1) {
            String key2 = name.substring(0, 2);
            str = p.get(key2);
            if (Tools.isNullStr(str) && name.length() > 2) {
                String key3 = name.substring(0, 3);
                str = p.get(key3);
                if (Tools.isNullStr(str) && name.length() > 3) {
                    String key4 = name.substring(0, 4);
                    str = p.get(key4);
                }
            }
        }
        return Tools.toStr(str);
    }
}
