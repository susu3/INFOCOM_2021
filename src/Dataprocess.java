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
            for(int i= 1;i<rownum;i++){
                row = sheet.getRow(i);
                if(row !=null ){
                    //start_time>=end_time || coderate <=0
                    if(row.getCell(2).getNumericCellValue()>=row.getCell(3).getNumericCellValue() || row.getCell(4).getNumericCellValue()<=0)
                        sheet.removeRow(row);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.print("start");
        Dataprocess.Userprocess("user.xlsx");
        System.out.print("end");

    }

}
