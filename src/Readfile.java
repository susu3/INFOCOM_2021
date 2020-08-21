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


    //读取用户文件,request用LinkedList来保存
    //String filePath = "user.xlsx";
    //String columns[] = {"stream","label","start_time","end_time","coderate"};
    //num：取多少条记录
    public static void getUser(LinkedList<Request> request, String filePath, int num){
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        String cellDatastring = null;
        int cellDataint=0;
        double cellDatad=0;

        wb = Readfile.readExcel(filePath);
        request.clear();

        if(wb != null){
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            //int colnum = row.getPhysicalNumberOfCells();

            for (int i = 1; i<rownum && i<num+1; i++) {
                Request request1= new Request();
                row = sheet.getRow(i);
                if(row !=null){
                    request1.setID(i);
                    cellDatastring=row.getCell(1).getStringCellValue();
                    request1.setLabel(cellDatastring);
                    cellDataint=(int)row.getCell(2).getNumericCellValue();
                    request1.setArrivetime(cellDataint-1591790399);   //使时间从0算起
                    cellDataint=(int)row.getCell(3).getNumericCellValue();
                    request1.setEndtime(cellDataint-1591790399);
                    cellDatad=row.getCell(4).getNumericCellValue();
                    request1.setSpeed(cellDatad);
                }else{
                    break;
                }
                request.add(request1);
            }
        }
        //return request;
    }

    //读取CDN文件，CDN用HashMap来保存
    //ArrayList<CDN> cdn =new ArrayList<CDN>();
    //String filePath = "CDN.xlsx";
    //String columns[] = {"label","name","peak"};
    public static void getCDN(HashMap<String,ArrayList<CDN>> cdn, String filePath, int num){
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        String cellDatastring = null;
        double cellDataint = 0;

        wb = Readfile.readExcel(filePath);
        cdn.clear();

        if(wb != null){
            sheet = wb.getSheetAt(0);
            row = sheet.getRow(0);
            int rownum = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i<rownum && i<num+1; i++) {
                if(row !=null){
                    row = sheet.getRow(i);
                    cellDatastring=row.getCell(0).getStringCellValue();  //get label

                    CDN cdn1= new CDN();
                    cdn1.setID(i);
                    cdn1.setLabel(cellDatastring);
                    cellDataint=row.getCell(2).getNumericCellValue();
                    cdn1.setBandwidth(cellDataint*1.2);   //bandwidth capacity = peak * 1.2
                    cdn1.setBandwidth_cap(cellDataint*1.2);
                    cdn1.setBcost(randomBcost(cellDataint/1024));  //
                    cdn1.setPeak(0);

                    if(cdn.containsKey(cellDatastring)){
                        ArrayList<CDN> cdn2 =cdn.get(cellDatastring);
                        cdn2.add(cdn1);
                        cdn.put(cellDatastring,cdn2);
                    }else{
                        ArrayList<CDN> cdn2 = new ArrayList<CDN>();
                        cdn2.add(cdn1);
                        cdn.put(cellDatastring,cdn2);
                    }
                }else{
                    break;
                }
            }
        }
        //return cdn;
    }

    //读取CDN文件，不考虑label的情况下，CDN用ArrayList来保存
    //ArrayList<CDN> cdn =new ArrayList<CDN>();
    //String filePath = "CDN.xlsx";
    //String columns[] = {"label","name","peak"};
    //
    public static void getCDN(ArrayList<CDN> cdn, String filePath, int num){
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        String cellDatastring = null;
        double cellDataint = 0;

        wb = Readfile.readExcel(filePath);
        cdn.clear();

        if(wb != null){
            sheet = wb.getSheetAt(0);
            row = sheet.getRow(0);
            int rownum = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i<num+1 && i<rownum; i++) {
                if(row !=null){
                    row = sheet.getRow(i);
                    cellDataint = row.getCell(2).getNumericCellValue();
                    if(cellDataint!= 0) {
                        CDN cdn1 = new CDN();

                        cdn1.setID(i);
                        cellDatastring = row.getCell(0).getStringCellValue();  //get label
                        cdn1.setLabel(cellDatastring);
                        cdn1.setBandwidth(cellDataint * 1.2);   //bandwidth capacity = peak * 1.2
                        cdn1.setBandwidth_cap(cellDataint * 1.2);
                        cdn1.setBcost(randomBcost(cellDataint / 1024));  //set cost
                        //cdn1.setBcost(1);  //set cost
                        cdn1.setPeak(0);

                        cdn.add(cdn1);
                    }else
                        num++;
                }else
                    break;
            }
        }
        //return cdn;
    }

    private static double randomBcost(double bandwidth){
        if(bandwidth>0 && bandwidth<=100)
            return 0.082;
        else if(bandwidth> 100 && bandwidth<=500)
            return 0.08;
        else if(bandwidth> 5 && bandwidth<=5*1024)
            return 0.077;
        else if(bandwidth>5*1024)
            return 0.075;
        else
            return 0;
    }

    //读取excel
    public static Workbook readExcel(String filePath){
        Workbook wb = null;

        if(filePath==null){
            System.out.println("filePath == null");
            System.exit(0);
            //return null;
        }

        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                System.out.println("not a .xlsx file");
                System.exit(0);
                //return wb = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(1);
        return wb;
    }

    public static void main(String[] args) {
        //test:
        //public static LinkedList<Request> getUser(LinkedList<Request> request, String filePath, int num)
//        LinkedList<Request> a = new LinkedList<Request>();
//        Readfile.getUser(a,"user.xlsx",10);
//        for(Request s:a)
//            System.out.println(s.getID()+" "+s.getArrivetime()+" "+s.getEndtime()+" "+s.getSpeed()+" "+s.getLabel());
        //public static HashMap<String,ArrayList<CDN>> getCDN(HashMap<String,ArrayList<CDN>> cdn, String filePath){
        HashMap<String,ArrayList<CDN>> b = new HashMap<String,ArrayList<CDN>>();
        Readfile.getCDN(b,"CDN.xlsx",10);
        Set<String> keys=b.keySet();
        Iterator<String> iterator1=keys.iterator();
        while (iterator1.hasNext()){
            System.out.print(iterator1.next() +", ");
        }
        ArrayList<CDN> c = b.get("安徽");
        for(CDN c1:c)
            System.out.println(c1.getID()+" "+c1.getBandwidth()+" "+c1.getBcost()+" "+c1.getLabel());




    }


}
