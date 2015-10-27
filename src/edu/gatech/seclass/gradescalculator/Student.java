package edu.gatech.seclass.gradescalculator;


public class Student {
	
	int attendance;
	String name;
	String gtid;
	String team;
	
	public Student(double myattendance, String myname, String myGtID, String myteam)
	{
		attendance = (int) myattendance;
		name = myname;
		gtid = myGtID;
		team = myteam;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getGtid()
	{
		return gtid;
	}
	
	public int getAttendance()
	{
		return attendance;
	}
	
	public String getTeam()
	{
		return team;
	}
	
	public Student(String myname, String myGtID, Course course){
		name = myname;
		gtid = myGtID;
		
		for (int i =1; i< course.GetGrades().GetAttendance().size(); i++)
		{
			if(course.GetGrades().GetAttendance().get(i).equals(name))
				attendance = Integer.parseInt(course.GetGrades().GetAttendance().get(i).get(1));
		}
		
		for (int i=1; i < course.GetStudents().GetTeams().size(); i++)
		{
			for(int j=1; j< course.GetStudents().GetTeams().get(i).size(); j++)
			{
				if (course.GetStudents().GetTeams().get(i).get(j).equals(name))
					team = course.GetStudents().GetTeams().get(i).get(0);
			}
		}		       
    }
	
	public void setTeam(String s)
	{
		team = s;
	}
	public Student(String myname2, String mygtid2)
	{
		name = myname2;
		gtid = mygtid2;
		attendance = 0;
		team = "";
	}
	
}
