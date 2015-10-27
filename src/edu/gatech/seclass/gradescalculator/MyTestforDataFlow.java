package edu.gatech.seclass.gradescalculator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class MyTestforDataFlow {
	String m_szStudentDB ="";
    static final String gradesDB = "DB/GradesDatabase6300-grades.xlsx";
	static final String studentsDB = "DB/GradesDatabase6300-students.xlsx";

	static ArrayList<ArrayList<String>> myStudentInfo = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> myTeams = new ArrayList<ArrayList<String>>();

	public static void main(String[] args) {
		try {
		     
		    readData();
		   	     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		System.out.println(myStudentInfo);
	}

	private static void readData() throws FileNotFoundException, IOException {
		FileInputStream file = new FileInputStream(new File(studentsDB));
		 
		//Get the workbook instance for XLS file 
		XSSFWorkbook workbook = new XSSFWorkbook(file);
 
		//Get first sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);
		 
		//Iterate through each rows from first sheet
		Iterator<Row> rowIterator = sheet.iterator();
		

		while(rowIterator.hasNext()) {
		    Row row = rowIterator.next();
		    ArrayList<String> array = new ArrayList<String>();
   
		    //For each row, iterate through each columns
		    Iterator<Cell> cellIterator = row.cellIterator();
		    while(cellIterator.hasNext()) {
		        Cell cell = cellIterator.next();
		        String data="";
		        switch(cell.getCellType()) {
		            case Cell.CELL_TYPE_BOOLEAN:
		                data = Boolean.toString((boolean)(cell.getBooleanCellValue()));
		                break;
		            case Cell.CELL_TYPE_NUMERIC:
						data =  Integer.toString((int)(cell.getNumericCellValue()));
		                break;
		            case Cell.CELL_TYPE_STRING:
						data = cell.getStringCellValue();
		                break;
		   

		        }
		        array.add(data);
		        
		    }
		    myStudentInfo.add(array);
		}
		file.close();
	}

}
