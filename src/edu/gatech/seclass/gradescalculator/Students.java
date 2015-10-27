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


public class Students {
	static String studentsDB = "";
	static ArrayList<ArrayList<String>> studentinfo = new ArrayList<ArrayList<String>>();
	static ArrayList<ArrayList<String>> teaminfo = new ArrayList<ArrayList<String>>();

	public Students(String lstudentsDB) {
		studentsDB = lstudentsDB;
		
		if (studentinfo.isEmpty()){
		try {
		     
		    FileInputStream file = new FileInputStream(new File(studentsDB));
		     
		    //Get the workbook instance for XLS file 
		    XSSFWorkbook workbook = new XSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
			XSSFSheet sheet0 = workbook.getSheetAt(0);
			readDB(sheet0, studentinfo);

			//Get Second from the workbook
			XSSFSheet sheet1 = workbook.getSheetAt(1);
			readDB(sheet1, teaminfo);
		    
		    file.close();
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		}
	}

	private void readDB(XSSFSheet sheet, ArrayList<ArrayList<String>> sheetdata) {
		//Iterate through each rows
	    Iterator<Row> rowIterator = sheet.iterator();
	    while(rowIterator.hasNext()) {
	        Row row = rowIterator.next();
	        ArrayList<String> rowlist = new ArrayList<String>();

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
		        rowlist.add(data);
	        }
	        sheetdata.add(rowlist);
	    }
	    

	}

	public ArrayList<ArrayList<String>> GetStudentInfo()
	{
		return studentinfo;
	}
	
	public ArrayList<ArrayList<String>> GetTeams()
	{
		return teaminfo;
	}

}
