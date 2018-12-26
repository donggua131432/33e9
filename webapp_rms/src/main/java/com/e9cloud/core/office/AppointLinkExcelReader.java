package com.e9cloud.core.office;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 操作Excel表格的功能类
 */
public class AppointLinkExcelReader implements Serializable{
    private static final long serialVersionUID = -8753668773283663266L;
    private POIFSFileSystem fs;
    private HSSFWorkbook hwb;
    private HSSFSheet hsheet;
    private HSSFRow hrow;


    private Workbook wb;
    private Sheet sheet;
    private Row row;
    /**
     * 读取Excel表格表头的内容
     *
     * @param is
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        try {
            fs = new POIFSFileSystem(is);
            hwb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hsheet = hwb.getSheetAt(0);
        row = hsheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            //title[i] = getStringCellValue(row.getCell((short) i));
            title[i] = getCellFormatValue(hrow.getCell(i));
        }
        return title;
    }

    public Map<Integer, String> readExcelContent(InputStream is){
        return readExcelContent(is, "-");
    }

    /**
     * 读取Excel数据内容
     *
     * @param is
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readExcelContent(InputStream is, String split) {
        Map<Integer, String> content = new HashMap<>();
        String str = "";
        try {
            wb = WorkbookFactory.create(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        //int rowNum = sheet.getLastRowNum();
        int rowNum = sheet.getPhysicalNumberOfRows();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            //判断该行是否为空
            if(null != row && row.getFirstCellNum() == 0){
                int j = 0;
                while (j < colNum) {
                    // 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
                    // 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
                    // str += getStringCellValue(row.getCell((short) j)).trim() +
                    // "-";
                    //str += getCellFormatValue(row.getCell((short)j)).toString().trim() + "    ";
                    if(!"".equals(getStringCellValue(row.getCell(j)))){
                        str += getStringCellValue(row.getCell(j)).trim() + split;
                    }else{
                        str += " " + split;
                    }
                    j++;
                }

                if(!"".equals(str)&&!" - - - - -".equals(str)){
                    content.put(i, str);
                    str = "";
                }
                if(" - - - - -".equals(str)){
                    str = "";
                }
            }
        }
        return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(Cell cell) {
        if(cell== null)
        return "";
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                DecimalFormat df = new DecimalFormat("0");
                strCell = df.format(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                try {
                    strCell = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    strCell = String.valueOf(cell.getNumericCellValue());
                }
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC: {
                    DecimalFormat df = new DecimalFormat("0");
                    cellvalue = df.format(cell.getNumericCellValue());
                    break;
                }
                case HSSFCell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        //cellvalue = cell.getDateCellValue().toLocaleString();
                        //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING: {
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                }

                // 默认的Cell值
                default:cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
}
