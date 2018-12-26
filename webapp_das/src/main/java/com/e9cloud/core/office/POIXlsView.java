package com.e9cloud.core.office;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 封装了有关excel表格导出的相关操作。(xls)
 * @author wzj 2016-01-22
 *
 */
public class POIXlsView extends AbstractXlsView {

    /** 列中文名（中文列头） */
    private List<String> columnCnames;

    /** 列英文名（英文列头） */
    private List<String> columnEnames;

    /** 标题 */
    private String title;

    /**
     * Application-provided subclasses must implement this method to populate
     * the Excel workbook document, given the model.
     *
     * @param model    the model Map
     * @param workbook the Excel workbook to populate
     * @param request  in case we need locale etc. Shouldn't look at attributes.
     * @param response in case we need to set cookies. Shouldn't write to it.
     */
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        HSSFWorkbook wb = (HSSFWorkbook)workbook;

        columnCnames = (List<String>)model.get("titles");
        columnEnames = (List<String>)model.get("columns");
        title = (String)model.get("title");

        String excelName = (String)model.get("excelName");

        if(columnCnames == null || columnCnames.size() == 0){
            throw new RuntimeException("表头为空!");
        }

        if(columnEnames == null || columnEnames.size() == 0){
            throw new RuntimeException("列名为空!");
        }
        /*****************************************************/
        //添加序号列
        //columnCnames.add(0, "序号");
        //columnEnames.add(0, "no");

        excelName = (excelName == null ? "默认表格名称.xls" : (excelName + ".xls"));
        // 设置response方式,使执行此controller时??自动出现下载页面,而非直接使用excel打开
        response.setContentType("APPLICATION/OCTET-STREAM");
        if(request.getHeader( "USER-AGENT" ).toLowerCase().indexOf( "firefox" ) >  0 ){
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(excelName.getBytes("GB2312"),"ISO-8859-1"));
        }else{
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(excelName, "UTF-8"));
        }

        //创建第一个工作表
        HSSFSheet ws = wb.createSheet(title == null? "Sheet1" : title); // sheet名称

        addColumNameToWsheet(ws, wb);

        List<Map<String, Object>> list = (List<Map<String, Object>>) model.get("list");

        //添加数据
        writeContext(ws, list, wb);

    }

    //添加表头
    private void addColumNameToWsheet(HSSFSheet hssfSheet, HSSFWorkbook wb) {
        int len = columnCnames.size();

        // 生成一个样式
        HSSFCellStyle styleHeader = wb.createCellStyle();

        // 设置这些样式
        styleHeader.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFRow rowHeader = hssfSheet.createRow(0);
        HSSFCell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellValue(title);
        cellHeader.setCellStyle(styleHeader);

        hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, len-1)); // 合并单元格

        // 生成一个样式
        HSSFCellStyle style = wb.createCellStyle();

        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 生成一个字体
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        // 把字体应用到当前的样式
        style.setFont(font);

        // 产生表格标题行
        HSSFRow row = hssfSheet.createRow(1);
        for (int i = 0; i < len; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(columnCnames.get(i));
            cell.setCellValue(text);
        }
    }

    /**
     * 向工作表中写数据。
     * @param hssfSheet	工作表。
     * @param list		表数据。
     */
    private void writeContext(HSSFSheet hssfSheet, List<Map<String, Object>> list, HSSFWorkbook wb) {
        String columnName = null;
        Object value = null;
        int rows = list.size();

        // 生成并设置另一个样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成另一个字体
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

        // 把字体应用到当前的样式
        style.setFont(font);

        // 产生表格标题行
        HSSFRow row;
        for (int i = 0; i < rows; i++) {
            Map<String, Object> map = (Map<String, Object>) list.get(i);
            row = hssfSheet.createRow(i + 2);
            for (int j = 0, len = columnEnames.size(); j < len; j++) {
                HSSFCell cell = row.createCell(j);
                String valueCell = String.valueOf(map.get(columnEnames.get(j)));
                cell.setCellValue(valueCell);
                cell.setCellStyle(style);
            }
        }
    }

}
