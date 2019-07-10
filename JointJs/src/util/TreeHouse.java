package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class TreeHouse {
    //TREEHOUSE格式的导入
    public static Tree getHSSFSheet(String pid,File filepath,Tree root) throws IOException{
        List<Tree> list=new ArrayList<>();
        HashMap<Integer, List<Integer>> map=new HashMap<>();
        
        int Tree=0;
        //创建excel，读取文件
        HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(filepath));
        //读取默认的第一个工作表sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        int firstRowNum = 0;
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();
        for (int i = firstRowNum+1; i <=lastRowNum; i++) {
            
            Tree tree=new Tree();
            int level=0;
            HSSFRow row = sheet.getRow(i);
            //获取当前行最后单元格列号
            //int lastCellNum = row.getLastCellNum();
            String [] value=new String[12];
            level=(SysUtil.toNumeric(sheet, i, 0).length()-SysUtil.toNumeric(sheet, i, 0).trim().length())/2;
            
            if (map.get(level) == null) {
                Tree=0;
                List<Integer> v = new ArrayList<Integer>();
                v.add(Tree++);
                map.put(level, v);
            } else {
                List<Integer> v = map.get(level);
                v.add(Tree++);
                map.put(level, v);
            }
            tree.setId("");
            tree.setNode(Tree);
            tree.setLevel(level);
            
            value[0]=SysUtil.toNumeric(sheet, i, 3);           //编号
            value[1]=SysUtil.toNumeric(sheet, i, 6);           //名称
            value[2]="";           //源名称
            value[3]=SysUtil.toNumeric(sheet, i, 2);          //数量
            value[4]=SysUtil.toNumeric(sheet, i, 1);           //活动配置
            value[5]=SysUtil.toNumeric(sheet, i, 4);           //图号
            value[6]=SysUtil.toNumeric(sheet, i, 5);           //规格型号
            value[7]=SysUtil.toNumeric(sheet, i, 0);           //文档名称
            value[8]=SysUtil.toNumeric(sheet, i, 7);           //来源
            value[9]=SysUtil.toNumeric(sheet, i, 8);           //备注
            value[10]=SysUtil.toNumeric(sheet, i, 9);          //下料长度
            value[11]=SysUtil.toNumeric(sheet, i, 10);          //底数
            
            //同理复制一份（此处主要为了匹配与easyui表格对应）
            tree.setValue0(SysUtil.toNumeric(sheet, i, 3));          //编号
            tree.setValue1(SysUtil.toNumeric(sheet, i, 6));          //名称
            tree.setValue2("");          //源名称
            tree.setValue3(SysUtil.toNumeric(sheet, i, 2));          //数量
            tree.setValue4(SysUtil.toNumeric(sheet, i, 1));           //活动配置
            tree.setValue5(SysUtil.toNumeric(sheet, i, 4));           //图号
            tree.setValue6(SysUtil.toNumeric(sheet, i, 5));           //规格型号
            tree.setValue7(SysUtil.toNumeric(sheet, i, 0));           //文档名称
            tree.setValue8(SysUtil.toNumeric(sheet, i, 7));           //来源
            tree.setValue9(SysUtil.toNumeric(sheet, i, 8));           //备注
            tree.setValue10(SysUtil.toNumeric(sheet, i, 9));          //下料长度
            tree.setValue11(SysUtil.toNumeric(sheet, i, 10));          //底数
            
          
            if(value[0].endsWith("ASM")){
                tree.setBg_color("#9BBACF");
                tree.setType("ass");
            }else{
                tree.setBg_color("#D7DBDE");
                tree.setType("part");
            }
            tree.setValue(value);
            tree.setBorder(false);
            tree.setBorder_height(0);
            tree.setBorder_width(0);
            tree.setBorder_x(0);
            tree.setBorder_y(0);
            tree.setSelf_x(0);
            tree.setSelf_y(0);
            
            if (level==0) {
                root=tree;
                root.setId(pid);
            }
            list.add(tree);
        }
        //设置数据的id,pid
        GraphController.setValue(list);
        //将数据转变成树状结构
        GraphController.merge(root,list,0);
        return root;
    }
    
    public static Tree getXSSFSheet(String pid,File filepath,Tree root) throws IOException{
        List<Tree> list=new ArrayList<>();
        HashMap<Integer, List<Integer>> map=new HashMap<>();
        
        int Tree=0;
        //创建excel，读取文件
        XSSFWorkbook workbook = new XSSFWorkbook(FileUtils.openInputStream(filepath));
        //读取默认的第一个工作表sheet
        XSSFSheet sheet = workbook.getSheetAt(0);
        int firstRowNum = 0;
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();
        for (int i = firstRowNum+1; i <=lastRowNum; i++) {
            
            Tree tree=new Tree();
            int level=0;
            XSSFRow row = sheet.getRow(i);
            //获取当前行最后单元格列号
            int lastCellNum = row.getLastCellNum();
            String [] value=new String[lastCellNum];
            level=(SysUtil.XssfToNumeric(sheet, i, 0).length()-SysUtil.XssfToNumeric(sheet, i, 0).trim().length())/2;
            
            if (map.get(level) == null) {
                Tree=0;
                List<Integer> v = new ArrayList<Integer>();
                v.add(Tree++);
                map.put(level, v);
            } else {
                List<Integer> v = map.get(level);
                v.add(Tree++);
                map.put(level, v);
            }
            tree.setId("");
            tree.setNode(Tree);
            tree.setLevel(level);
            
            value[0]=SysUtil.XssfToNumeric(sheet, i, 3);           //编号
            value[1]=SysUtil.XssfToNumeric(sheet, i, 6);           //名称
            value[2]="";           //源名称
            value[3]=SysUtil.XssfToNumeric(sheet, i, 2);          //数量
            value[4]=SysUtil.XssfToNumeric(sheet, i, 1);           //活动配置
            value[5]=SysUtil.XssfToNumeric(sheet, i, 4);           //图号
            value[6]=SysUtil.XssfToNumeric(sheet, i, 5);           //规格型号
            value[7]=SysUtil.XssfToNumeric(sheet, i, 0);           //文档名称
            value[8]=SysUtil.XssfToNumeric(sheet, i, 7);           //来源
            value[9]=SysUtil.XssfToNumeric(sheet, i, 8);           //备注
            value[10]=SysUtil.XssfToNumeric(sheet, i, 9);          //下料长度
            value[11]=SysUtil.XssfToNumeric(sheet, i, 10);          //底数
            
            //同理复制一份（此处主要为了匹配与easyui表格对应）
            tree.setValue0(SysUtil.XssfToNumeric(sheet, i, 3));          //编号
            tree.setValue1(SysUtil.XssfToNumeric(sheet, i, 6));          //名称
            tree.setValue2("");          //源名称
            tree.setValue3(SysUtil.XssfToNumeric(sheet, i, 2));          //数量
            tree.setValue4(SysUtil.XssfToNumeric(sheet, i, 1));           //活动配置
            tree.setValue5(SysUtil.XssfToNumeric(sheet, i, 4));           //图号
            tree.setValue6(SysUtil.XssfToNumeric(sheet, i, 5));           //规格型号
            tree.setValue7(SysUtil.XssfToNumeric(sheet, i, 0));           //文档名称
            tree.setValue8(SysUtil.XssfToNumeric(sheet, i, 7));           //来源
            tree.setValue9(SysUtil.XssfToNumeric(sheet, i, 8));           //备注
            tree.setValue10(SysUtil.XssfToNumeric(sheet, i, 9));          //下料长度
            tree.setValue11(SysUtil.XssfToNumeric(sheet, i, 10));          //底数
            
           /* for(int j=0;j<lastCellNum;j++){
                value[j]=SysUtil.XssfToNumeric(sheet, i, j);
            }*/
            
            if(value[0].endsWith("ASM")){
                tree.setBg_color("#9BBACF");
                tree.setType("ass");
            }else{
                tree.setBg_color("#D7DBDE");
                tree.setType("part");
            }
            tree.setValue(value);
            tree.setBorder(false);
            tree.setBorder_height(0);
            tree.setBorder_width(0);
            tree.setBorder_x(0);
            tree.setBorder_y(0);
            tree.setSelf_x(0);
            tree.setSelf_y(0);
            
            if (level==0) {
                root=tree;
                root.setId(pid);
            }
            list.add(tree);
        }
        //设置数据的id,pid
        GraphController.setValue(list);
        //将数据转变成树状结构
        GraphController.merge(root,list,0);
        return root;
    }
    
}
