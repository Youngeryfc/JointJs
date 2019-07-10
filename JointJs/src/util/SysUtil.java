package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SysUtil {
    public static String toNumeric(HSSFSheet sheet, int i, int colIndex) {
        Cell cell = sheet.getRow(i).getCell(colIndex);
        String val = null;
        if(cell!=null){
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                val = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                Boolean bol = cell.getBooleanCellValue();
                val = bol.toString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) { //日期类型
                    val = cell.toString();
                } else {
                    DecimalFormat df = new DecimalFormat("0");
                    val = df.format(cell.getNumericCellValue()); //数值类型的
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            default:
                break;
            }
        }
        return val;
    }

    public static String XssfToNumeric(XSSFSheet sheet, int i, int colIndex) {
        Cell cell = sheet.getRow(i).getCell(colIndex);
        String val = null;
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                val = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                Boolean bol = cell.getBooleanCellValue();
                val = bol.toString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                DecimalFormat df = new DecimalFormat("0");
                val = df.format(cell.getNumericCellValue()); //数值类型的
                break;
            default:
                break;
            }
        }
        return val;
    }

    /**
     * 
     * <p>
     * Description: 下载Excel
     * </p>
     * 
     * @param fileName 下载文件名
     * @param workbook Excel
     */
    public static void downloadExcel(String fileName, HSSFWorkbook workbook, HttpServletResponse response) {
        FileOutputStream fos = null;
        String tmpFile = "d:/" + System.currentTimeMillis() + ".xls";
        try {
            fos = new FileOutputStream(tmpFile);
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        SysUtil.downloadFile(fileName, tmpFile, true, response);
        //download(tmpFile, response);
    }

    public static void downloadFile(String fileName, String filePath, boolean delete, HttpServletResponse response) {
        try {
            File file = new File(filePath);
            DownloadUtil.downloadFile(fileName, file, response);
            if (delete) {
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpServletResponse download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

}
