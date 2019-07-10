package com.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entity.BdMaterialV;
import com.entity.Tree;
import com.service.BdMaterialVService;

import jxl.demo.Write;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.EBom;
import util.EBom2;
import util.NcBom;
import util.TreeHouse;
@Controller
public class GraphController {
    @Autowired
   // private BdBomBService bd;
    private BdMaterialVService bdMaterialVService;
    private static Logger log = Logger.getLogger(GraphController.class);
    int count=0;
    //TREEHOUSE格式导入
    @RequestMapping("/import.do")
    @ResponseBody//(springmvc的Jackson注解，返回json字符串)
    public List<Tree>  importDarta(HttpServletRequest request ,@RequestParam("file") MultipartFile file,String pid,String fileType) throws Exception{
        String path="d:/upload/";
        String filename=null;
        File filepath=null;
        //如果文件不为空，写上上传路径
        
        if (! file.isEmpty()) {
            filename=file.getOriginalFilename();
            filepath=new File(path,filename);
            if(!filepath.getParentFile().exists()){
                filepath.getParentFile().mkdirs();
            }
          //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
        }
        Tree root = null;
        //判断当前文件是xls结尾还是xlsx结尾
        if("TREEHOUSE".equals(fileType)){
            if(filename.endsWith("xls")){
                root=TreeHouse.getHSSFSheet(pid,filepath,root); 
            }else{
                root=TreeHouse.getXSSFSheet(pid,filepath,root); 
            }
        }else if("EBOM".equals(fileType)){
            if(filename.endsWith("xls")){
                root=EBom.getHSSFSheet(pid,filepath,root);
            }else{
                root=EBom.getXSSFSheet(pid,filepath,root); 
            }
        }else if("EBOM2".equals(fileType)){
            if(filename.endsWith("xls")){
                root=EBom2.getHSSFSheet(pid,filepath,root);
            }else{
                root=EBom2.getXSSFSheet(pid,filepath,root); 
            }
        } else if("NCBOM".equals(fileType)) {
            if(filename.endsWith("xls")){
                root=NcBom.getHSSFSheet(pid,filepath,root);
            }else{
                root=NcBom.getXSSFSheet(pid,filepath,root); 
            }
        }
        //删除文件
        filepath.delete();
      return root.getChildren();
    }
    
    @RequestMapping(value="/import1.do")
    @ResponseBody
    public void getStr(HttpServletRequest request , HttpServletResponse response,@RequestParam("file") MultipartFile file,String pid,String fileType) throws Exception{
        response.setCharacterEncoding("UTF-8");
        String path="d:/upload/";
        String filename=null;
        File filepath=null;
        //如果文件不为空，写上上传路径
        if (! file.isEmpty()) {
            filename=file.getOriginalFilename();
            log.info("------------------文件名：");
            filepath=new File(path,filename);
            if(!filepath.getParentFile().exists()){
                filepath.getParentFile().mkdirs();
            }
          //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
        }
        Tree root = null;
        //判断当前文件是xls结尾还是xlsx结尾
        if("TREEHOUSE".equals(fileType)){
            if(filename.endsWith("xls")){
                root=TreeHouse.getHSSFSheet(pid,filepath,root); 
            }else{
                root=TreeHouse.getXSSFSheet(pid,filepath,root); 
            }
        }else if("EBOM".equals(fileType)){
            if(filename.endsWith("xls")){
                root=EBom.getHSSFSheet(pid,filepath,root);
            }else{
                root=EBom.getXSSFSheet(pid,filepath,root); 
            }
        }else if("EBOM2".equals(fileType)){
            if(filename.endsWith("xls")){
                root=EBom2.getHSSFSheet(pid,filepath,root);
            }else{
                root=EBom2.getXSSFSheet(pid,filepath,root); 
            }
        } else if("NCBOM".equals(fileType)) {
            if(filename.endsWith("xls")){
                root=NcBom.getHSSFSheet(pid,filepath,root);
            }else{
                root=NcBom.getXSSFSheet(pid,filepath,root); 
            }
        }
        //删除文件
        filepath.delete();
        JSONArray jsonArray = JSONArray.fromObject(root.getChildren());
        String string=jsonArray.toString();
      response.getWriter().print(string);
    }
    
    
    
    
    @RequestMapping("/exportTh.do")
    public void  exportTreeHouse(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        String root=request.getParameter("root");
        String data=request.getParameter("data");
        JSONObject json= JSONObject.fromObject(data);
        String exportName;
        String  [] title={"文档名称","活动配置","数量","编号","图号","规格型号","名称","来源","备注","下料长度","底数","类型"};
        //创建Excel工作簿
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建一个工作表
        HSSFSheet sheet=workbook.createSheet();
        workbook.setSheetName(0, "Sheet1");
        //创建第一行
        HSSFRow row=sheet.createRow(0);
        //创建单元格
        HSSFCell cell=null;
        //设置标题
        for (int i = 0; i < title.length; i++) {
          cell=row.createCell(i);
          cell.setCellValue(title[i]);
        }
        //单个root节点(没有children这个属性)
        if("one".equals(root)){
            String str= json.getString("value").substring(1,json.getString("value").length()-1);
            String value[]=str.split(",");
            exportName=value[1];
            String type=json.getString("type");
            HSSFRow nextrow=sheet.createRow(1);
            for(int i=0;i<11;i++){
               HSSFCell cellValue=nextrow.createCell(i);
               cellValue.setCellValue(value[i].replace("\"", ""));
            }
            HSSFCell cellValue=nextrow.createCell(11);
            cellValue.setCellValue(type);
        }else{
            HashMap mapClass=new HashMap();
            mapClass.put("children", Tree.class);
            Tree tree = (Tree)JSONObject.toBean(json, Tree.class,mapClass);
            //设置行内值
            String value[]=tree.getValue();
            exportName=value[1];
            HSSFRow nextrow=sheet.createRow(1);
            String  blank="";
          
            HSSFCell cellValue0=nextrow.createCell(0);       //文档名称(7)
            if(value[1]==null){
                cellValue0.setCellValue(value[7]);
            }else{
                cellValue0.setCellValue(value[7].trim());
            }
            blank="    ";
            
            HSSFCell cellValue1=nextrow.createCell(1);       //活动配置(4)
            cellValue1.setCellValue(value[4]);
            HSSFCell cellValue2=nextrow.createCell(2);       //数量(3)
            cellValue2.setCellValue(value[3]);
            HSSFCell cellValue3=nextrow.createCell(3);       //编号(0)
            cellValue3.setCellValue(value[0]);
            HSSFCell cellValue4=nextrow.createCell(4);       //图号(5)
            cellValue4.setCellValue(value[5]);
            HSSFCell cellValue5=nextrow.createCell(5);       //规格型号(6)
            cellValue5.setCellValue(value[6]);
            HSSFCell cellValue6=nextrow.createCell(6);       //名称(1)
            cellValue6.setCellValue(value[1]);
            HSSFCell cellValue7=nextrow.createCell(7);       //来源(8)
            cellValue7.setCellValue(value[8]);
            HSSFCell cellValue8=nextrow.createCell(8);       //备注(9)
            cellValue8.setCellValue(value[9]);
            HSSFCell cellValue9=nextrow.createCell(9);       //下料长度(10)
            cellValue9.setCellValue(value[10]);
            HSSFCell cellValue10=nextrow.createCell(10);     //底数(11)
            cellValue10.setCellValue(value[11]);
            HSSFCell cellValue11=nextrow.createCell(11);
            cellValue11.setCellValue(tree.getType());
            int rowCount=1;
            if(tree.getChildren()!=null){
                rowCount++;
                getTree(tree.getChildren(),rowCount, sheet,blank);
            }
          
        }
        
        String path="d:/upload/";
        //创建一个文件夹
        File file=new File(path,"ThreeHouse_"+exportName+".xls");
        path=file.getAbsolutePath();
        try {
          file.createNewFile();
          //将excel内容存盘
          FileOutputStream stream=FileUtils.openOutputStream(file);
          //将流写入到文件中
          workbook.write(stream);
          file.delete();
          System.out.println("导出成功");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        response.getWriter().print(path);
    }
    
    
    @RequestMapping("/exportNb.do")
    public void  exportNcBom(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        String exportName;
        count=1;
        String root=request.getParameter("root");
        String data=request.getParameter("data");
        JSONObject json= JSONObject.fromObject(data);
        String  [] title={"*父项编码","*父项名称","*子项编码","*子项名称","*下料长度毫米","*子项数量","*底数","*子项类型","*备料来源","*供应方式","*倒冲方式","*控制标志","*备注"};
        //创建Excel工作簿
        HSSFWorkbook workbook=new HSSFWorkbook();
        
        //创建一个工作表
        HSSFSheet sheet=workbook.createSheet();
        workbook.setSheetName(0, "Sheet1");
        //设置列宽
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 9000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 9000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 2000);
        sheet.setColumnWidth(6, 2000);
        sheet.setColumnWidth(7, 2000);
        sheet.setColumnWidth(8, 2000);
        sheet.setColumnWidth(9, 3500);
        sheet.setColumnWidth(10, 2000);
        sheet.setColumnWidth(11, 2000);
        //创建第一行
        HSSFRow row=sheet.createRow(0);
        row.setHeight((short)500);
        HSSFFont sidefont = workbook.createFont();  
        sidefont.setFontName("宋体");  
        sidefont.setFontHeightInPoints((short)12);//设置字体大小   
        HSSFCellStyle sideStyle = workbook.createCellStyle();
        //setStyle单元格居中
        sideStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        sideStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中  
        sideStyle.setFont(sidefont);
        
        HSSFFont titlefont = workbook.createFont();  
        titlefont.setFontName("宋体");  
        titlefont.setColor(HSSFColor.RED.index);
        titlefont.setFontHeightInPoints((short)14);//设置字体大小   
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        //setStyle单元格居中
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中  
        titleStyle.setFont(titlefont);
        
        int rowNum=1;
        //创建单元格
        HSSFCell cell=null;
        //设置标题
        for (int i = 0; i < title.length; i++) {
          cell=row.createCell(i);
          cell.setCellValue(title[i]);
          cell.setCellStyle(titleStyle);
        }
        //单个root节点(没有children这个属性)
        if("one".equals(root)){
            String str= json.getString("value").substring(1,json.getString("value").length()-1);
            String value[]=str.split(",");
            exportName=value[1].replace("\"","");
            String type=json.getString("type");
            HSSFRow nextrow=sheet.createRow(1);
            for(int i=0;i<11;i++){
               HSSFCell cellValue=nextrow.createCell(i);
               cellValue.setCellValue(value[i].replace("\"", ""));
            }
            HSSFCell cellValue=nextrow.createCell(12);
            cellValue.setCellValue(type);
        }else{
            HashMap mapClass=new HashMap();
            mapClass.put("children", Tree.class);
            Tree tree = (Tree)JSONObject.toBean(json, Tree.class,mapClass);
            String[] value=tree.getValue();
            exportName=value[1];
            getChildren(tree,sheet,rowNum,sideStyle);
        }
        
        String path="d:/upload";
        //创建一个文件夹
        File file=new File(path,"NCBOM_"+exportName+".xls");
        path=file.getAbsolutePath();
        try {
          file.createNewFile();
          //将excel内容存盘
          FileOutputStream stream=FileUtils.openOutputStream(file);
          //将流写入到文件中
          workbook.write(stream);
          System.out.println("导出成功");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        response.getWriter().print(path);
    }
    
    
   
    
    public void getChildren(Tree tree,HSSFSheet sheet,int rowNum,HSSFCellStyle sideStyle){
        List<Tree> list=tree.getChildren();
        String pValue[]=tree.getValue();
        for(int i=0;i<list.size();i++){
            //创建行    左毛坯116
            HSSFRow row=sheet.createRow(count);
            row.setHeight((short)500);
            //创建12列
            for(int j=0;j<13;j++){
                HSSFCell tCell=row.createCell(j);
                tCell.setCellStyle(sideStyle);
            }
            //当前的value值
            String value[]=list.get(i).getValue();
            //给每列赋值
            row.getCell(0).setCellValue(pValue[0]);         //父编码
            row.getCell(1).setCellValue(pValue[1]);         //父文档名称
            row.getCell(2).setCellValue(value[0]);          //编码(0)
            row.getCell(3).setCellValue(value[1]);          //名称(1)
            row.getCell(4).setCellValue(value[10]);         //下料长度(10)
            row.getCell(5).setCellValue(value[3]);          //子项数(数量3)
            row.getCell(6).setCellValue(value[11]);         //底数(11)
            row.getCell(7).setCellValue(value[6]);          //子项类（规格型号6）
            row.getCell(8).setCellValue(value[7]);          //备料（文档名称7）
            row.getCell(9).setCellValue(value[8]);          //供应方（来源8）
            row.getCell(10).setCellValue(value[4]);         //倒冲方（活动配置）
            row.getCell(11).setCellValue(value[5]);         //控制标（图号）
            row.getCell(12).setCellValue(value[9]);         //备注
            count++;
        }
        
        //先给每一行赋值，再进行遍历
        for(int i=0;i<list.size();i++){
            if(list.get(i).getChildren()!=null){
                if(list.get(i).getChildren().size()>0){
                    getChildren(list.get(i),sheet,rowNum,sideStyle); 
                }
            }
        }
    }
    
  
    
    //EBom格式只有3层
    @RequestMapping("/exportEb.do")
    public static void  exportEBom(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        String exportName;
        String root=request.getParameter("root");
        String data=request.getParameter("data");
        JSONObject json= JSONObject.fromObject(data);
        
        String  [] title={"","编号","图号","名称","单台数量","规格型号","来源","备注"};
        
        //创建Excel工作簿
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建一个工作表
        HSSFSheet sheet=workbook.createSheet();
        workbook.setSheetName(0, "Sheet1");
        //设置列宽
        sheet.setColumnWidth(1, 4666);
        sheet.setColumnWidth(2, 4666);
        sheet.setColumnWidth(3, 8666);
        sheet.setColumnWidth(4, 1400);
        sheet.setColumnWidth(5, 9666);
        
        //设置单元格格式
        HSSFFont titlefont = workbook.createFont();  
        titlefont.setFontName("黑体");  
        titlefont.setFontHeightInPoints((short) 20);//设置字体大小   
        titlefont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示   
        
        HSSFFont sidefont = workbook.createFont();  
        sidefont.setFontName("黑体");  
        sidefont.setFontHeightInPoints((short)12);//设置字体大小   
        sidefont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示   
        
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        //setStyle单元格居中
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中  
        titleStyle.setFont(titlefont);
     
        //设置副标题
        HSSFCellStyle sideStyle = workbook.createCellStyle();
        //setStyle单元格居中
        sideStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        sideStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中  
        sideStyle.setFont(sidefont);
        sideStyle.setWrapText(true);
        
        //第一列
        HSSFCellStyle columnStyle = workbook.createCellStyle();
        //setStyle单元格居中
        columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中  
        
        //设置后4列的样式
        HSSFCellStyle laterStyle = workbook.createCellStyle();
        //setStyle单元格居中
        laterStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中  
        
        //创建第一行 
        HSSFRow t=sheet.createRow(0);
        //合并单元格 （起始行 结尾行 起始列 结尾列）
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));     
        HSSFCell tCell=t.createCell(0);
        //标题名称==root的名称
        HSSFRow row=sheet.createRow(1);
        //创建单元格
        HSSFCell cell=null;
        //设置标题
        for (int i = 0; i < title.length; i++) {
          cell=row.createCell(i);
          cell.setCellValue(title[i]);
          cell.setCellStyle(sideStyle);
        }
        
       //只有root节点时
        if("one".equals(root)){
            Tree tree = (Tree)JSONObject.toBean(json, Tree.class);
            String a= json.getString("value");
            String str= json.getString("value").substring(1,json.getString("value").length()-1);
            String value[]=str.split(",");
            exportName=value[1];
            String type=json.getString("type");
            HSSFRow nextrow=sheet.createRow(1);
            for(int i=0;i<11;i++){
               HSSFCell cellValue=nextrow.createCell(i);
               cellValue.setCellValue(value[i].replace("\"", ""));
            }
            HSSFCell cellValue=nextrow.createCell(11);
            cellValue.setCellValue(type);
        }else{
            HashMap mapClass=new HashMap();
            mapClass.put("children", Tree.class);
            Tree tree = (Tree)JSONObject.toBean(json, Tree.class,mapClass);
            String str[]=tree.getValue();
            //设置首行的标题
            exportName=str[1];
            tCell.setCellValue(str[1]);
            tCell.setCellStyle(titleStyle);
            List<Tree>list=tree.getChildren();
            int countRow=2;
            for(int i=0;i<list.size();i++){                   //第一层
                List<Tree>children=list.get(i).getChildren();
                for(int j=0;j<children.size();j++){    //第二层（创建行）
                    HSSFRow nextrow=sheet.createRow(countRow);
                    //取当前行的值
                    String value[]=children.get(j).getValue();
                    String pValue[]=new String[8];
                    pValue[0]="";
                    pValue[1]=value[0];         //编号
                    pValue[2]=value[5];         //图号
                    pValue[3]=value[1];         //名称
                    pValue[4]=value[3];         //单台数量
                    pValue[5]=value[6];         //规格型号
                    pValue[6]=value[8];         //来源
                    pValue[7]=value[9];         //备注
                    
                    for(int m=0;m<8;m++){
                        HSSFCell nextCell=nextrow.createCell(m);        //创建列
                        if(m>3){
                            nextCell.setCellStyle(laterStyle);
                        }
                        nextCell.setCellValue(pValue[m]);
                    }
                    countRow++;
                }
                
                String []p=list.get(i).getValue();
                //创建合并单元格
                sheet.addMergedRegion(new CellRangeAddress(countRow-list.get(i).getChildren().size(),countRow-1,0,0));  
                //给合并单元格赋值
                sheet.getRow(countRow-list.get(i).getChildren().size()).getCell(0).setCellValue(p[1]);
                //给合并单元格样式
                sheet.getRow(countRow-list.get(i).getChildren().size()).getCell(0).setCellStyle(columnStyle);
            }
        }
        
        
        String path="d:/upload";
        //创建一个文件夹
        File file=new File(path,"EBOM_"+exportName+".xls");
        path=file.getAbsolutePath();
        try {
          file.createNewFile();
          //将excel内容存盘
          FileOutputStream stream=FileUtils.openOutputStream(file);
          //将流写入到文件中
          workbook.write(stream);
          System.out.println("导出成功");
          file.delete();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        response.getWriter().print(path);
    }
    
    
    
    
    @RequestMapping("/download.do")
    public void downloads( HttpServletRequest request,HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String filePath=request.getParameter("path");
        File file = new File(filePath);
        InputStream ins = null;
        BufferedInputStream bins = null;
        OutputStream outs = null;
        BufferedOutputStream bouts = null;
        //获取文件名
        String file_name=filePath.substring(10, filePath.length());
        try {
            if (!"".equals(file_name)) {
                if (file.exists()) {
                    ins = new FileInputStream(filePath);
                    bins = new BufferedInputStream(ins);
                    outs = response.getOutputStream();
                    bouts = new BufferedOutputStream(outs);
                    response.setContentType("application/x-download");
                    response.setHeader("Content-disposition",
                            "attachment;filename=" + URLEncoder.encode(file_name, "UTF-8"));
                    int bytesRead = 0;
                    byte[] buffer = new byte[8192];
                    while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
                        bouts.write(buffer, 0, bytesRead);
                    }
                    file.delete();
                    bouts.flush();
                    
                } else {
                    throw new Exception("下载的文件不存在！");
                }
            } else {
                throw new Exception("导出文件时发生错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ins) {
                ins.close();
            }
            if (null != bins) {
                bins.close();
            }
            if (null != outs) {
                outs.close();
            }
            if (null != bouts) {
                bouts.close();
            }
            
        }
    }
    
    
    //赋值，id,pid
    public static void setValue(List<Tree>list){
        for(int i=1;i<list.size();i++){
            if(list.get(i).getLevel()>list.get(i-1).getLevel()){
                list.get(i).setPid(list.get(i-1).getId());
                list.get(i).setId(list.get(i-1).getId()+"_"+list.get(i).getNode());
            }else{
                list.get(i).setPid(list.get(i-1).getPid());
                list.get(i).setId(list.get(i).getPid()+"_"+list.get(i).getNode());
            }
        }
        
    }
    
    public static void merge(Tree root,List<Tree>list,int count){
        List<Tree>result=new ArrayList<>();
        int i;
        for(i=count;i<list.size();i++){
            if(root.getId().equals(list.get(i).getPid())){
                System.out.println("当前序号："+i);
                System.out.println(i+":id:"+list.get(i).getId()+"   pid:"+list.get(i).getPid());
                result.add(list.get(i));
                merge(list.get(i), list,i+1);
            }
        }
        root.setChildren(result);
    }
    
    
    public static void merge1(Tree root,List<Tree>list){
        List<Tree>result=new ArrayList<>();
        int i=0;
        List<Tree> tList=null;
        for(Tree tree:list){
            tList=list;
            tList.remove(i);
            if(root.getId().equals(tree.getPid())){
                System.out.println(i+":id:"+tree.getId()+"   pid:"+tree.getPid());
                result.add(tree);
                merge1(tree, tList);
                i++;
            }
        }
        root.setChildren(result);
    }
    
    public static void print(List<Tree>list){
        for(Tree Tree:list){
            System.out.println(Tree);
        }
        for(Tree Tree:list){
            if(Tree.getChildren()!=null){
                print(Tree.getChildren());
            }
        }
    }
    
   
    //读取当前对象的值
    public static Integer getTree(List<Tree> list,int rowCount,HSSFSheet sheet,String blank){
        
       for(int i=0;i<list.size();i++){
           HSSFRow nextrow=sheet.createRow(rowCount);
           String value[]=list.get(i).getValue();
           String str[]=new String[11];
           str[0]=value[7];
           str[1]=value[4];
           str[2]=value[3];
           str[3]=value[0];
           str[4]=value[5];
           str[5]=value[6];
           str[6]=value[1];
           str[7]=value[8];
           str[8]=value[9];
           str[9]=value[10];
           str[10]=value[11];
           for(int j=0;j<11;j++){
               if(j==0){
                   HSSFCell cellValue=nextrow.createCell(j);
                   if(str[j]==null){
                       cellValue.setCellValue(blank+str[j]);
                   }else{
                       cellValue.setCellValue(blank+str[j].trim());
                   }
               }else{
                   HSSFCell cellValue=nextrow.createCell(j);
                   cellValue.setCellValue(str[j]);
               }
               
           }
           HSSFCell cellValue=nextrow.createCell(11);
           cellValue.setCellValue(list.get(i).getType());
           rowCount++;
           if(list.get(i).getChildren()!=null&&list.get(i).getChildren().size()>0){
               rowCount=getTree(list.get(i).getChildren(),rowCount, sheet,blank+"    ");
           }
       }
       return rowCount;
    }
    
    @RequestMapping("/search.do")
    @ResponseBody       
    public List<BdMaterialV> search(String bh,String name){
        BdMaterialV record=new BdMaterialV() ;
        if(!"".equals(bh) && bh!=null){
            record.setCode(bh);
        }
        if(!"".equals(name) && name!=null){
            record.setName("%"+name+"%");
        }
        List<BdMaterialV> list=bdMaterialVService.selectByPrimary(record);
        return list;
    }
    
    @RequestMapping("/importJson.do")
    @ResponseBody       
    public void importJson(HttpServletResponse response,@RequestParam("file") MultipartFile file) throws Exception{
        response.setCharacterEncoding("UTF-8");
        String path="d:/upload/";
        String filename=null;
        File filepath=null;
        //如果文件不为空，写上上传路径
        if (! file.isEmpty()) {
            filename=file.getOriginalFilename();
            filepath=new File(path,filename);
            if(!filepath.getParentFile().exists()){
                filepath.getParentFile().mkdirs();
            }
          //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path + File.separator + filename));
        }
        //读取文件的内容
        String str=FileUtils.readFileToString(filepath,"utf-8");
        filepath.delete();
        response.getWriter().print(str);
    }
    
    
    @RequestMapping("/exportJson.do")
    @ResponseBody       
    public void exportJson(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String jsonStr=request.getParameter("data");
        String name=request.getParameter("a");
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");   
        
        String path="d:/upload/";
        
        //创建一个文件夹
        File file=new File(path,sdf.format(date)+".json");
        path=file.getAbsolutePath();
        try {
          file.createNewFile();
          //将excel内容存盘
          Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
          //将流写入到文件中
          write.write(jsonStr);
          write.flush();
          write.close();
          System.out.println("导出成功");
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        response.getWriter().print(path);
    }
}
    
    
    
    
    
    
    
    
    
    
    
    
