package com.e9cloud.common;

import com.e9cloud.core.util.PinyinUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/12/1.
 */
public class PinYinTest {

    @Test
    public void testPY() throws BadHanyuPinyinOutputFormatCombination {

        System.out.println(PinyinUtils.toPinYin("深圳ABC"));
        System.out.println(PinyinUtils.toPinYin("深圳ABC", "_"));
        System.out.println(PinyinUtils.toPinYin("深圳ABC", "_", PinyinUtils.Type.LOWERCASE));
        System.out.println(PinyinUtils.toPinYin("深圳ABC", "_", PinyinUtils.Type.UPPERCASE));

        System.out.println(PinyinUtils.toPinYin("德邦"));

        System.out.println(PinyinUtils.toPinYin("重复"));
        System.out.println(PinyinUtils.toPinYin("重要"));
        System.out.println(PinyinUtils.toPinYin("旅行"));
        System.out.println(PinyinUtils.toPinYin("银行"));
        System.out.println(PinyinUtils.toPinYin("很行"));

        System.out.println(Arrays.toString(PinyinHelper.toHanyuPinyinStringArray('重')));
    }


}
