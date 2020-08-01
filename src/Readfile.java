import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Random;
import java.util.Map.Entry;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook; //对应xls格式的xcel文档
import org.apache.poi.ss.usermodel.Cell; //对应一个单元格
import org.apache.poi.ss.usermodel.DateUtil; //
import org.apache.poi.ss.usermodel.Row;  //对应一个sheet中的一行
import org.apache.poi.ss.usermodel.Sheet;  //对应一个sheet
import org.apache.poi.ss.usermodel.Workbook;  //对应Excel文档
import org.apache.poi.xssf.usermodel.XSSFWorkbook; //对应xlsx格式的excel文档
import org.apache.commons.compress.*;

public class Readfile {


    //读取用户文件
    //String filePath = "user.xlsx";
    //String columns[] = {"stream","label","start_time","end_time","coderate"};
    //num：取多少条记录
    public static LinkedList<Request> getUser(LinkedList<Request> request, String filePath, int num){
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        String cellDatas = null;
        int cellDatan=0;

        wb = Readfile.readExcel(filePath);


        if(wb != null){
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            //获取第一行
            row = sheet.getRow(0);

            for (int i = 1; i<rownum && i<num; i++) {
                Request request1= new Request();
                row = sheet.getRow(i);
                if(row !=null && colnum<=5){
                    request1.setID(i);
                    cellDatas=row.getCell(1).getStringCellValue();
                    request1.setLabel(cellDatas);
                    cellDatan=(int)row.getCell(2).getNumericCellValue();
                    request1.setArrivetime(cellDatan-1591790399);   //使时间从0算起
                    cellDatan=(int)row.getCell(3).getNumericCellValue();
                    request1.setEndtime(cellDatan-1591790399);
                    cellDatan=(int)row.getCell(4).getNumericCellValue();
                    request1.setSpeed(cellDatan);
                }else{
                    break;
                }
                request.add(request1);
            }
        }
        return request;
    }

    //读取CDN文件
    //ArrayList<CDN> cdn =new ArrayList<CDN>();
    //String filePath = "user.xlsx";
    //String columns[] = {"node_name","label"};
    //num：取多少条记录
    public static ArrayList<CDN> getCDN(ArrayList<CDN> cdn, String filePath, int num){
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        String cellDatas = null;

        wb = Readfile.readExcel(filePath);

        if(wb != null){
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取第一行
            row = sheet.getRow(0);

            for (int i = 1; i<rownum && i<num; i++) {
                CDN cdn1= new CDN();
                row = sheet.getRow(i);
                if(row !=null && colnum<=5){
                    cdn1.setID(i);
                    cellDatas=row.getCell(1).getStringCellValue();
                    cdn1.setLabel(cellDatas);
                }else{
                    break;
                }
                //cdn1.setBandwidth(); //?????????????????????????????????
                //cdn1.setBcost();  //?????????????????????????????
                cdn.add(cdn1);
            }
        }
        return cdn;
    }

    private static double randomNum(){
        Random ran= new Random();
        double num=ran.nextGaussian();
        return num;
    }

    //读取excel
    public static Workbook readExcel(String filePath){
        Workbook wb = null;

        if(filePath==null){
            return null;
        }

        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wb;
    }

}
