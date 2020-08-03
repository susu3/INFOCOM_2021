import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Cell; //对应一个单元格
import org.apache.poi.ss.usermodel.DateUtil; //
import org.apache.poi.ss.usermodel.Row;  //对应一个sheet中的一行
import org.apache.poi.ss.usermodel.Sheet;  //对应一个sheet
import org.apache.poi.ss.usermodel.Workbook;  //对应Excel文档
import org.apache.poi.xssf.usermodel.XSSFWorkbook; //对应xlsx格式的excel文档
import org.apache.commons.compress.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Dataprocess {

    //String columns[] = {"stream","label","start_time","end_time","coderate"};
    public static void Userprocess(String filePath){
        Workbook wb =null;
        wb = Readfile.readExcel(filePath);
        //String columns[] = {"stream","label","start_time","end_time","coderate"};

        Sheet sheet = null;
        Row row = null;

        if(wb != null){
            //获取最大行数
            sheet = wb.getSheetAt(0);
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取最大列数
            //int colnum = row.getPhysicalNumberOfCells();
//            row = sheet
            for(int i= 1;i<sheet.getLastRowNum();i++){
            //for(int i= 1;i<100;i++){
                System.out.print(i);
                row = sheet.getRow(i);
                if(row !=null ) {
                    //start_time>=end_time || coderate <=0
                    if (row.getCell(2).getNumericCellValue() >= row.getCell(3).getNumericCellValue() || row.getCell(4).getNumericCellValue() <= 0){
//                        sheet.removeRow(row);
                        sheet.shiftRows(row.getRowNum() + 1, sheet.getLastRowNum() + 1, -1);
                    i--;
                }

                }
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream("new_"+filePath);
            wb.write(outputStream);
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            }
    }


    public static void main(String[] args) {
        //Dataprocess.Userprocess("user.xlsx");
    }

}
