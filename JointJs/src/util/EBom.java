package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hslf.model.Sheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.controller.GraphController;
import com.entity.Tree;

public class EBom {
    //EBom格式的导入

    /**
     * 读取excel文件
     * 
     * @param wb
     * @param sheetIndex sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine 去除最后读取的行
     * @throws IOException 
     * @throws InvalidFormatException 
     *///String pid, File filepath, Tree root
    public static Tree getHSSFSheet(String pid, File filepath, Tree root) throws Exception {
        root=new Tree();
        HSSFWorkbook workbook = new HSSFWorkbook(FileUtils.openInputStream(filepath));
        //读取默认的第一个工作表sheet
        HSSFSheet Hsheet = workbook.getSheetAt(0);
        //获取sheet中最后一行行号
        int lastRowNum = Hsheet.getLastRowNum();
        int realRowNum=0;
        for(int i=1;i<lastRowNum;i++){
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
                    //realRowNum=i-1;
                    realRowNum=i;
                    break;
                }
            }
        }
        
        //给root复制
        root.setId(pid);
        root.setPid("");
        root.setLevel(1);
        HashMap<String, List<Integer>> map=new HashMap<String, List<Integer>>();
        
        int node=0;
        int pNode=1;
        List<Tree> list=new ArrayList<>();
        list.add(root);
        for(int i=2;i < realRowNum; i++){
            HSSFRow row = Hsheet.getRow(i);
            
            //暂时给定11个单位的长度
            String value[]=new String[row.getLastCellNum()+1];
            String cValue[]=new String[12];
            String pName;
            Tree tree=new Tree();
            
            boolean isMerge = isMergedRegion(Hsheet, i, 0);
            //判断是否具有合并单元格
            if (isMerge) {
                pName = getMergedRegionValue(Hsheet, i, 0);
            } else {
                pName=SysUtil.toNumeric(Hsheet, i, 0);
            }
            
            
            for(int j=1;j<row.getLastCellNum();j++){
               
               value[j]=SysUtil.toNumeric(Hsheet, i, j);
            }
            //给value重新赋值
            cValue[0]=value[1];         //编号0
            cValue[1]=value[3];         //名称1
            cValue[3]=value[4];         //单台数量3
            cValue[5]=value[2];         //图号5
            cValue[6]=value[5];         //规格型号6
            cValue[8]=value[6];         //来源8
            cValue[9]=value[7];         //备注9
            tree.setValue(cValue);
            
            tree.setValue0(value[1]);
            tree.setValue1(value[3]);
            tree.setValue3(value[4]);
            tree.setValue5(value[2]);
            tree.setValue6(value[5]);
            tree.setValue8(value[6]);
            tree.setValue9(value[7]);
            
            if(map.get(pName)==null){
                //第二层级
                String[] pValue=new String[12];
                pValue[1]=pName;
                Tree p=new Tree();
                p.setLevel(2);
                p.setNode(pNode);
                p.setValue(pValue);
                p.setValue1(pName);
                p.setBg_color("#9BBACF");
                p.setType("ass");
                pNode++;
                list.add(p);
                List<Integer> v=new ArrayList<Integer>();
                node=1;
                v.add(node);
                map.put(pName,v);
            }else{
               List<Integer> v=map.get(pName); 
               v.add(node++);
               map.put(pName, v);
            }
            tree.setBg_color("#D7DBDE");
            tree.setType("part");
            tree.setLevel(3);
            tree.setNode(node);
            list.add(tree);
            
        }
        
        setValue(list);
        GraphController.merge(root,list,0);
        //将list集合转换成tree结构
        return root;
    }

    
    
    public static Tree getXSSFSheet(String pid, File filepath, Tree root) throws Exception {
        root=new Tree();
        XSSFWorkbook workbook = new XSSFWorkbook(FileUtils.openInputStream(filepath));
        //读取默认的第一个工作表sheet
        XSSFSheet Xsheet = workbook.getSheetAt(0);
        //获取sheet中最后一行行号
        int lastRowNum = Xsheet.getLastRowNum();
        int realRowNum=0;
        for(int i=1;i<lastRowNum;i++){
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
            }
        }
        
        //给root复制
        root.setId(pid);
        root.setPid("");
        root.setLevel(1);
        HashMap<String, List<Integer>> map=new HashMap<String, List<Integer>>();
        
        int node=0;
        int pNode=1;
        List<Tree> list=new ArrayList<>();
        list.add(root);
        for(int i=2;i <realRowNum; i++){
            XSSFRow row = Xsheet.getRow(i);
            
            //暂时给定11个单位的长度
            String value[]=new String[row.getLastCellNum()+1];
            String cValue[]=new String[12];
            String pName;
            Tree tree=new Tree();
            
            boolean isMerge = isMergedRegion(Xsheet, i, 0);
            //判断是否具有合并单元格
            if (isMerge) {
                pName = getMergedRegionValue(Xsheet, i, 0);
            } else {
                pName=SysUtil.XssfToNumeric(Xsheet, i, 0);
            }
            
            
            for(int j=1;j<row.getLastCellNum();j++){
               value[j]=SysUtil.XssfToNumeric(Xsheet, i, j);
            }
            //给value重新赋值
            cValue[0]=value[1];         //编号0
            cValue[1]=value[3];         //名称1
            cValue[2]="";
            cValue[3]=value[4];         //单台数量3
            cValue[5]=value[2];         //图号5
            cValue[6]=value[5];         //规格型号6
            cValue[8]=value[6];         //来源8
            cValue[9]=value[7];         //备注9
            tree.setValue(cValue);
            
            tree.setValue0(value[1]);
            tree.setValue1(value[3]);
            tree.setValue2("");
            tree.setValue3(value[4]);
            tree.setValue5(value[2]);
            tree.setValue6(value[5]);
            tree.setValue8(value[6]);
            tree.setValue9(value[7]);
            
            if(map.get(pName)==null){
                //第二层级
                String[] pValue=new String[12];
                pValue[1]=pName;
                Tree p=new Tree();
                p.setLevel(2);
                p.setNode(pNode);
                p.setValue(pValue);
                p.setValue1(pName);
                p.setBg_color("#9BBACF");
                p.setType("ass");
                pNode++;
                list.add(p);
                List<Integer> v=new ArrayList<Integer>();
                node=1;
                v.add(node);
                map.put(pName,v);
            }else{
               List<Integer> v=map.get(pName); 
               v.add(node++);
               map.put(pName, v);
            }
            tree.setBg_color("#D7DBDE");
            tree.setLevel(3);
            tree.setNode(node);
            tree.setValue(cValue);
            tree.setType("part");
            list.add(tree);
        }
        
        setValue(list);
        GraphController.merge(root,list,0);
        //将list集合转换成tree结构
        return root;
    }
    
    
    
    /**
     * 获取合并单元格的值
     * 
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(HSSFSheet sheet, int row, int column) {
        int sheetMergeCount = ((org.apache.poi.ss.usermodel.Sheet) sheet).getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = ((org.apache.poi.ss.usermodel.Sheet) sheet).getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {

                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = ((org.apache.poi.ss.usermodel.Sheet) sheet).getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }

        return null;
    }

    /**
     * 获取合并单元格的值
     * 
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(XSSFSheet sheet, int row, int column) {
        int sheetMergeCount = ((org.apache.poi.ss.usermodel.Sheet) sheet).getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = ((org.apache.poi.ss.usermodel.Sheet) sheet).getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {

                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = ((org.apache.poi.ss.usermodel.Sheet) sheet).getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }

        return null;
    }

    
    /**
     * 判断合并了行
     * 
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private boolean isMergedRow(Sheet sheet, int row, int column) {
        int sheetMergeCount = ((org.apache.poi.ss.usermodel.Sheet) sheet).getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = ((org.apache.poi.ss.usermodel.Sheet) sheet).getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row == firstRow && row == lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     * 
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private static boolean isMergedRegion(HSSFSheet sheet, int row, int column) {
        int sheetMergeCount = ((org.apache.poi.ss.usermodel.Sheet) sheet).getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = ((org.apache.poi.ss.usermodel.Sheet) sheet).getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isMergedRegion(XSSFSheet sheet, int row, int column) {
        int sheetMergeCount = ((org.apache.poi.ss.usermodel.Sheet) sheet).getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = ((org.apache.poi.ss.usermodel.Sheet) sheet).getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 获取单元格的值
     * 
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {

        if (cell == null)
            return "";

        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

            return cell.getStringCellValue();

        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

            return String.valueOf(cell.getBooleanCellValue());

        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

            return cell.getCellFormula();

        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {

            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }

    
    public static void main(String [] arg) throws Exception{
        File filepath=new File("d:/upload","EBOM.xls");
        Tree tree = new Tree();
        String pid="root";
        getHSSFSheet(pid,filepath,tree);
    }
    
    //赋值，id,pid
    public static void setValue(List<Tree>list){
        for(int i=1;i<list.size();i++){
            if(list.get(i).getLevel()>list.get(i-1).getLevel()){
                list.get(i).setPid(list.get(i-1).getId());
                list.get(i).setId(list.get(i-1).getId()+"_"+list.get(i).getNode());
            }else if(list.get(i).getLevel()<list.get(i-1).getLevel()){
                list.get(i).setPid(list.get(0).getId());
                list.get(i).setId(list.get(0).getId()+"_"+list.get(i).getNode());
            }else{
                list.get(i).setPid(list.get(i-1).getPid());
                list.get(i).setId(list.get(i).getPid()+"_"+list.get(i).getNode());
            }
        }
        
    }
}
