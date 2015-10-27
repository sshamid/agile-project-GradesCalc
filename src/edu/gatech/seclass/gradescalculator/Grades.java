package edu.gatech.seclass.gradescalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Grades {
	
	String myformula;
	String gradesDB ="";
	ArrayList<ArrayList<String>> attendance         = new ArrayList<ArrayList<String>>();	
	ArrayList<ArrayList<String>> individualgrades   = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> individualcontribs = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> teamgrades         = new ArrayList<ArrayList<String>>();

	public Grades(String lgradesDB)
	{
		gradesDB = lgradesDB;

		try
		{
			FileInputStream file = new FileInputStream(new File(gradesDB));
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first sheet from the workbook
			XSSFSheet sheet0 = workbook.getSheetAt(0);
			readDB(sheet0, attendance);

			//Get Second from the workbook
			XSSFSheet sheet1 = workbook.getSheetAt(1);
			readDB(sheet1, individualgrades);
	
			//Get Third from the workbook
			XSSFSheet sheet2 = workbook.getSheetAt(2);
			readDB(sheet2, individualcontribs);

			//Get Fourth from the workbook
			XSSFSheet sheet3 = workbook.getSheetAt(3);
			readDB(sheet3, teamgrades);

			file.close();
		}
		catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		{

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
	
	public ArrayList<ArrayList<String>> GetAttendance()
	{
		return attendance;
	}
	
	public ArrayList<ArrayList<String>> GetIndividualGrades()
	{
		return individualgrades;
	}
	
	public ArrayList<ArrayList<String>> GetIndividualContribs()
	{
		return individualcontribs;
	}
	
	public ArrayList<ArrayList<String>> GetTeamGrades()
	{
		return teamgrades;
	}
	
	public void updateGradesDB(ArrayList<ArrayList<String>> NewGrades, ArrayList<ArrayList<String>> NewProjectGrades)

	{
		try {
		    FileInputStream file = new FileInputStream(new File(gradesDB));
		 
		    XSSFWorkbook workbook = new XSSFWorkbook(file);
		    XSSFSheet sheet = workbook.getSheetAt(1);
		    
		    for (int i=0; i<NewGrades.size(); i++)
		    {
		    	for(int j=0;j<NewGrades.get(i).size();j++)
		    	{
		    		Cell cell = null;
		    			    		
		    		cell = sheet.getRow(i).getCell(j);
		    		
		    		if(cell == null)
		    		{
		    			sheet.getRow(i).createCell(j);
		    			cell = sheet.getRow(i).getCell(j);		    			
		    		}
		    			
		    		cell.setCellValue(NewGrades.get(i).get(j)); 
		    	}
		    }	
		    
		    XSSFSheet sheet2 = workbook.getSheetAt(2);
		    
		    for (int i=0; i<NewProjectGrades.size(); i++)
		    {
		    	for(int j=0;j<NewProjectGrades.get(i).size();j++)
		    	{
		    		Cell cell = null;
		    			    		
		    		cell = sheet2.getRow(i).getCell(j);
		    		
		    		if(cell == null)
		    		{
		    			sheet2.getRow(i).createCell(j);
		    			cell = sheet2.getRow(i).getCell(j);		    			
		    		}
		    			
		    		cell.setCellValue(NewProjectGrades.get(i).get(j)); 
		    	}
		    }	
		     
		    FileOutputStream outFile =new FileOutputStream(new File(gradesDB));
		    workbook.write(outFile);
		    file.close();
		    outFile.close();
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public String getFormula() {
		return this.gradesDB;
	}

	public void setFormula(String formula) {
		this.gradesDB = formula;
	}

}
