package com.e9cloud.common;

import com.e9cloud.core.util.FileUtil;
import com.e9cloud.core.util.Tools;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/12/23.
 */
public class ToolsTest {
    @Test
    public void testBit(){
        String bits = Tools.decimalToBitStr(14680064);
        System.out.println(bits);
    }

    @Test
    public void testCreateNewFile() throws IOException {
        File file = new File("D:/A/德邦物理12月.txt");
        FileUtil.mkParentDir(file);
        file.createNewFile();
    }

    @Test
    public void testsub(){
        System.out.println("1234567890".substring(0,7));
    }

    @Test
    public void testHa(){
        String rowNO = "123.6";
        if (rowNO.contains(".")) {
            System.out.println(rowNO.substring(0, rowNO.lastIndexOf(".")));
        }
        //System.out.println(rowNO.substring(0, rowNO.lastIndexOf(".")));
    }
}
