package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.controller.GraphController;
import com.entity.Tree;

public class NcBom {
    //NCBOM格式的导入
    public static Tree getHSSFSheet(String pid,File filepath,Tree root) throws IOException{
        //创建excel，读取文件
        HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(filepath));
        //读取默认的第一个工作表sheet
        HSSFSheet Hsheet = workbook.getSheetAt(0);
        //获取sheet中最后一行行号
        int lastRowNum = Hsheet.getLastRowNum();
        //获取真实行
        int realRowNum=0;
        Tree tree=new Tree();
        for(int i=0;i<=lastRowNum;i++){
            HSSFRow currentRow=Hsheet.getRow(i);
            int count=0;
            if(currentRow==null){
                realRowNum=i;
                break;
            }else{
                for(int j=0;j<=currentRow.getLastCellNum();j++){
                    if(currentRow.getCell(j)!=null){
                        if(currentRow.getCell(j).getStringCellValue()!=null&&!currentRow.getCell(j).getStringCellValue().isEmpty()){
                            break;  
                          }else{
                              count++;
                          }
                    }
                }
                if(count==currentRow.getLastCellNum()){
                    realRowNum=i-1;
                    //realRowNum=i;
                    break;
                }
                realRowNum=i+1;
            }
        }
        
        List<Tree> list=new ArrayList<Tree>();
        String treeValue[]=new String[11];
        tree.setId(SysUtil.toNumeric(Hsheet, 1, 0));
        treeValue[0]=SysUtil.toNumeric(Hsheet, 1, 1);
        tree.setPid("");
        tree.setBg_color("#9BBACF");
        tree.setType("ass");
        //读取excel表格，将数据存入list集合
        for(int i=1;i<realRowNum;i++){
            Tree t=new Tree();
            HSSFRow row = Hsheet.getRow(i);
            t.setPid(SysUtil.toNumeric(Hsheet, i, 0));     //pid
            t.setpName(SysUtil.toNumeric(Hsheet, i, 1));   //pname
            t.setId(SysUtil.toNumeric(Hsheet, i, 2));      //id
            
            String str[]=new String[13];
            for(int j=2;j<row.getLastCellNum();j++){
                str[j]=SysUtil.toNumeric(Hsheet, i, j);
            }
            
            String value[]=new String[12];
            value[0]=str[2];        //编码（子项编码）
            value[1]=str[3];        //名称（子项名称）
            value[2]="";            //源名称
            value[3]=str[5];        //数量（子项数）
            value[4]=str[10];       //活动配置（到冲方）
            value[5]=str[11];       //图号（控制标）
            value[6]=str[7];        //规格型号（子项类）
            value[7]=str[8];        //文档名称（备料）
            value[8]=str[9];        //来源（供应方）  
            value[9]=str[12];       //备注
            value[10]=str[4];       //下料长度
            value[11]=str[6];       //底数
            
            t.setValue0(str[2]);
            t.setValue1(str[3]);
            t.setValue2("");
            t.setValue3(str[5]);
            t.setValue4(str[10]);
            t.setValue5(str[11]);
            t.setValue6(str[7]);
            t.setValue7(str[8]);
            t.setValue8(str[9]);
            t.setValue9(str[12]);
            t.setValue10(str[4]);
            t.setValue11(str[6]);
            
            t.setValue(value);
            t.setBg_color("#D7DBDE");
            t.setType("part");
            list.add(t);
        }
        GraphController.merge(tree,list,0);
        
        return tree;
    }  
    
    
  //NCBOM格式的导入
    public static Tree getXSSFSheet(String pid,File filepath,Tree root) throws IOException{
        //创建excel，读取文件
        XSSFWorkbook workbook = new XSSFWorkbook(FileUtils.openInputStream(filepath));
        //读取默认的第一个工作表sheet
        XSSFSheet Xsheet = workbook.getSheetAt(0);
        //获取sheet中最后一行行号
        int lastRowNum = Xsheet.getLastRowNum();
        //获取真实行
        int realRowNum=0;
        Tree tree=new Tree();
        
        for(int i=0;i<=lastRowNum;i++){
            XSSFRow currentRow=Xsheet.getRow(i);
            int count=0;
            if(currentRow==null){
                realRowNum=i;
                break;
            }else{
                for(int j=0;j<=currentRow.getLastCellNum();j++){
                    if(currentRow.getCell(j)!=null){
                        if(currentRow.getCell(j).getStringCellValue()!=null&&!currentRow.getCell(j).getStringCellValue().isEmpty()){
                            break;  
                          }else{
                              count++;
                          }
                    }
                }
                if(count==currentRow.getLastCellNum()){
                    realRowNum=i-1;
                    break;
                }
                realRowNum=i+1;
            }
        }
       
        List<Tree> list=new ArrayList<Tree>();
        String treeValue[]=new String[11];
        tree.setId(SysUtil.XssfToNumeric(Xsheet, 1, 0));
        treeValue[0]=SysUtil.XssfToNumeric(Xsheet, 1, 1);
        tree.setPid("");
        tree.setBg_color("#9BBACF");
        //读取excel表格，将数据存入list集合
        for(int i=1;i<realRowNum;i++){
            Tree t=new Tree();
            XSSFRow row = Xsheet.getRow(i);
            t.setPid(SysUtil.XssfToNumeric(Xsheet, i, 0));     //pid
            t.setpName(SysUtil.XssfToNumeric(Xsheet, i, 1));   //pname
            t.setId(SysUtil.XssfToNumeric(Xsheet, i, 2));      //id
            
            String str[]=new String[13];
            for(int j=2;j<row.getLastCellNum();j++){
                str[j]=SysUtil.XssfToNumeric(Xsheet, i, j);
            }
            
            String value[]=new String[12];
            value[0]=str[2];        //编码（子项编码）
            value[1]=str[3];        //名称（子项名称）
            value[2]="";            //源名称
            value[3]=str[5];        //数量（子项数）
            value[4]=str[10];       //活动配置（到冲方）
            value[5]=str[11];       //图号（控制标）
            value[6]=str[7];        //规格型号（子项类）
            value[7]=str[8];        //文档名称（备料）
            value[8]=str[9];        //来源（供应方）  
            value[9]=str[12];       //备注
            value[10]=str[4];       //下料长度
            value[11]=str[6];       //底数
            
            
            t.setValue0(str[2]);       //编码（子项编码）
            t.setValue1(str[3]);        //名称（子项名称）
            t.setValue2("");            //源名称
            t.setValue3(str[5]);        //数量（子项数）
            t.setValue4(str[10]);       //活动配置（到冲方）
            t.setValue5(str[11]);       //图号（控制标）
            t.setValue6(str[7]);        //规格型号（子项类）
            t.setValue7(str[8]);        //文档名称（备料）
            t.setValue8(str[9]);        //来源（供应方）  
            t.setValue9(str[12]);       //备注
            t.setValue10(str[4]);       //下料长度
            t.setValue11(str[6]);       //底数
            
            
            t.setValue(value);
            t.setBg_color("#D7DBDE");
            t.setType("part");
            list.add(t);
        }
        GraphController.merge(tree,list,0);
        return tree;
    }  
    
    public static void main(String [] arg) throws Exception{
        Runtime rn = Runtime.getRuntime();
        String str[]={"D:/MaterialService.exe","30200101012119"};
        Process p = rn.exec(str);
    }
}
