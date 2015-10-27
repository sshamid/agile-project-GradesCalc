package edu.gatech.seclass.gradescalculator;

import java.util.HashMap;
import java.util.HashSet;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Course {

	HashSet<Student> studentsRost = new HashSet<Student>();	
	Students mystudents;
	Grades   mygrades;
	String   myformula;
	
	public Students GetStudents(){ return mystudents;}
	public Grades  GetGrades(){ return mygrades;}

	public Course(Students students, Grades grades)
	{
		mystudents = students;
		mygrades = grades;
		myformula = "AT * 0.2 + AA * 0.4 + AP * 0.4";

		for(int i=1; i<mystudents.GetStudentInfo().size(); i++)
		{
			String szName = mystudents.GetStudentInfo().get(i).get(0);
			String szGtID = mystudents.GetStudentInfo().get(i).get(1);
			String szTeam = "";
			Double dAttendance = 0.0;

			for(int j=1; j<mystudents.GetTeams().size(); j++)
			{
				for(int k=1; k< mystudents.GetTeams().get(j).size(); k++)
				{
					if(mystudents.GetTeams().get(j).get(k).equals(szName))
						szTeam = mystudents.GetTeams().get(j).get(0);
				}
			}

			for(int j=1; j<mygrades.GetAttendance().size(); j++)
			{
				for(int k=0; k<mygrades.GetAttendance().get(j).size(); k++)
				{
					if(mygrades.GetAttendance().get(j).get(k).equals(szGtID))
						dAttendance = Double.parseDouble(mygrades.GetAttendance().get(j).get(1));
				}
			}
			
			Student student = new Student(dAttendance, szName, szGtID, szTeam);
			studentsRost.add(student);
		}
	}

	public int getNumStudents()
	{
		return mystudents.GetStudentInfo().size() -1;		
	}

	public int getNumAssignments()
	{
		if(mygrades.GetIndividualGrades().size() > 0)
			return mygrades.GetIndividualGrades().get(0).size()-1;
		else
			return 0;
	}

	public int getNumProjects()
	{
		if(mygrades.GetIndividualContribs().size() > 0)
			return mygrades.GetIndividualContribs().get(0).size()-1;
		else
			return 0;
	}

	public HashSet<Student> getStudents()
	{
		return studentsRost;	
	}

	public Student getStudentByName(String szStudentName)
	{	
		for(Student student : studentsRost)
		{
			if (student.getName().equals(szStudentName))
				return student;
		}
		
		Student student = null;
		return student;
	}

	public Student getStudentByID(String szStudentID)
	{
		for(Student student : studentsRost)
		{
			if (student.getGtid().equals(szStudentID))
				return student;
		}
		
		Student student = null;
		return student;
	}

	public int getAttendance(Student student)
	{
		
		return student.getAttendance();
	}
	
	public String getTeam(Student student)
	{
		return student.getTeam();
	}

	public void addAssignment(String assignmentname)
	{
		for(int i=0;i < mygrades.GetIndividualGrades().size(); i++)
		{
			if (i == 0)
				mygrades.GetIndividualGrades().get(i).add(assignmentname);
			else
				mygrades.GetIndividualGrades().get(i).add("");
		}
	}
				
	public void updateGrades(Grades grades)
	{
		grades.updateGradesDB(mygrades.GetIndividualGrades(), mygrades.GetIndividualContribs());
	}
	
	public void addGradesForAssignment(String assignmentname, HashMap<Student, Integer> grades)
	{
		for (Student student : grades.keySet()) 
		{
			int row = 0;
			int col = 0;
			
			for (int i =1; i<mygrades.GetIndividualGrades().size(); i++)
			{
				if(mygrades.GetIndividualGrades().get(i).get(0).equals(student.getGtid()))
					row = i;	
			}
			
			for (int j = 1; j< mygrades.GetIndividualGrades().get(0).size();j++)
			{
				if(mygrades.GetIndividualGrades().get(0).get(j).equals(assignmentname))
					col = j;
			}
			
			if (row !=0 && col !=0)
				mygrades.GetIndividualGrades().get(row).set(col, grades.get(student).toString());	
		}		
	}
	
	public int getAverageAssignmentsGrade(Student student)
	{
		int noOfAssignments = 0;
		
		for (int i=1; i < mygrades.GetIndividualGrades().size(); i++)
		{
			int total = 0;
			if(mygrades.GetIndividualGrades().get(i).get(0).equals(student.getGtid()))
			{
				
				for (int j = 1; j < mygrades.GetIndividualGrades().get(i).size(); j++)
				{	
					try
					{
						total += Integer.parseInt(mygrades.GetIndividualGrades().get(i).get(j));
						noOfAssignments++;		
					}
					catch(Exception e) {}
				}
				try{
				return ((int) Math.round((double)total / (double)noOfAssignments));
				}
				catch(Exception e){return 0;}
			}
		}
		
		return 0;		
	}
	
	public int getAverageProjectsGrade(Student student)
	{
		int noOfProjects = 0;
		double total = 0;
		
		if(student.getTeam().equals(""))
		{
			for (int i=1; i < mystudents.GetTeams().size(); i++)
			{
				for(int j=1; j< mystudents.GetTeams().get(i).size(); j++)
				{
					if (mystudents.GetTeams().get(i).get(j).equals(student.getName()))
						student.setTeam(mystudents.GetTeams().get(i).get(0));
				}
			}
		}
		
		try{
		for (int i=1; i < mygrades.GetIndividualContribs().size(); i++)
		{		
			if(mygrades.GetIndividualContribs().get(i).get(0).equals(student.getGtid()))
			{			
				for (int j = 1; j < mygrades.GetIndividualContribs().get(i).size(); j++)
				{					
					String szProjectName = mygrades.GetIndividualContribs().get(0).get(j);
					
					for (int k =1; k<mygrades.GetTeamGrades().size();k++)
						for (int l =1;l<mygrades.GetTeamGrades().get(0).size();l++)
							if(mygrades.GetTeamGrades().get(0).get(l).equals(szProjectName) && mygrades.GetTeamGrades().get(k).get(0).equals(student.getTeam()))
						    {
								total = total + ((Double.parseDouble(mygrades.GetIndividualContribs().get(i).get(j))/100) * Double.parseDouble(mygrades.GetTeamGrades().get(k).get(l)));
								noOfProjects++;
						    }
				}
			}
		}
		
		return ((int) Math.round((double)total / (double)noOfProjects));}
		catch(Exception e){return 0;}
	}
	
	public void addIndividualContributions(String projectName, HashMap<Student, Integer> contributions)
	{
		for (Student student : contributions.keySet()) 
		{
			int row = 0;
			int col = 0;
			
			for (int i =1; i<mygrades.GetIndividualContribs().size(); i++)
			{
				if(mygrades.GetIndividualContribs().get(i).get(0).equals(student.getGtid()))
					row = i;	
			}
			
			for (int j = 1; j< mygrades.GetIndividualContribs().get(0).size();j++)
			{
				if(mygrades.GetIndividualContribs().get(0).get(j).equals(projectName))
					col = j;
			}
			
			if (row !=0 && col !=0)
				mygrades.GetIndividualContribs().get(row).set(col, contributions.get(student).toString());	
		}
	}
		
	public String getEmail(Student student)
	{
		for (int i =1; i < mystudents.GetStudentInfo().size(); i++)
		{
			if(mystudents.GetStudentInfo().get(i).get(0).equals(student.getName()))
				return mystudents.GetStudentInfo().get(i).get(2);
		}
		return "";
	}
	
	public void addStudent(Student student)
	{
	}
	
	public void updateStudents(Students students)
	{
	}
	
	public void addProject(String projectname)
	{
	}
	
	public void addGradesForProject(String projectName, HashMap<Student, Integer> grades)
	{
	}
	
	public int getTotalTeamProjectGrade(Student student)
	{
		return 0;
	}
	
	public void setFormula(String formula)
	{
		this.myformula = formula;
	}
	
	public String getFormula()
	{
		return this.myformula;
	}
	
	public int getOverallGrade(Student student)
	{
		try{
			int AT = student.getAttendance();
			int AA = getAverageAssignmentsGrade(student);
			int AP = getAverageProjectsGrade(student);

			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");
			engine.put("AT", AT);
			engine.put("AA", AA);
			engine.put("AP", AP);

			Object obj = engine.eval(myformula); 
			return (int)Math.round(Double.parseDouble(obj.toString()));
		}
		catch(Exception e)
		{
			throw new GradeFormulaException("Exception found for formula :" + myformula);
		}	
	}
}
