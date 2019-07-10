package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DownloadUtil {
    public static void downloadFile(String fileName, File file, HttpServletResponse response) throws IOException {
        byte[] data = getBytesFromFile(file);
        if (fileName == null || "".equals(fileName)) {
            fileName = file.getName();
        }
        download(fileName, data, response);
    }
    
    /**
     * <p>
     * Description: 单个文件下载
     * </p>
     * 
     * @param fileName 下载的文件名称(如：123.txt)，若为空，则为"未命名"
     * @param data 文件的字节数据
     * @param response response
     * @throws IOException IOException
     */
    public static void download(String fileName, byte[] data, HttpServletResponse response) throws IOException {
        if (fileName == null) {
            fileName = "未命名";
        }
        if (data != null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentLength(data.length);
            response.addHeader("Content-Disposition",
                "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            response.setContentType("application/octet-stream");
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
        }
    }
    
    
    /**
     * <p>
     * Description: 文件类型转换为byte
     * </p>
     * 
     * @param file 文件
     * @return byte
     */
    public static byte[] getBytesFromFile(File file) {
        if (file == null) {
            return null;
        }
        FileInputStream stream = null;
        ByteArrayOutputStream out = null;
        try {
            stream = new FileInputStream(file);
            out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1) {
                out.write(b, 0, n);

            }
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
    
    public static String  downloadExcel(HSSFWorkbook workbook,HttpServletResponse response,String fileName) throws IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        workbook.write(os);
        byte[] content=os.toByteArray();
        InputStream is=new ByteArrayInputStream(content);
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }
}
