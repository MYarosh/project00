import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;

public class Writer {

    private String path;
    private int rownumber = 0;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private String graphPath;

    public void add(String string0, String string1){
        Row row;
        Cell cell;
        row = sheet.createRow(rownumber);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue(string0);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue(string1);
        rownumber++;
    }

    public void add(String string0, String string1, String string2){
        Row row;
        Cell cell;
        row = sheet.createRow(rownumber);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue(string0);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue(string1);
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(string2);
        rownumber++;
    }

    public void write(){
        File file = new File(path);
        FileOutputStream outFile = null;
        try {
            outFile = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte setPath(String path) {
        this.path = path;
        FileOutputStream outFile = null;
        try{
            workbook = new HSSFWorkbook();
            sheet = workbook.createSheet("Корреляционно-регрессионный анализ");
            File file = new File(path);
            outFile = new FileOutputStream(file);
            return 1;
        }catch (Exception e){
            System.out.println("Не возможно записать в файл. Записи не будет.");
            return 0;
        }
    }

    public void setGraphPath(String path) {
        graphPath = path;
    }

    public String getGraphPath() {
        return graphPath;
    }
}
