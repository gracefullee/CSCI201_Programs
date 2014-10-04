package com.uscInc;

public class Employee {

	private String name;
	private float hourlyRate;
	
	public Employee(String name, float hourlyRate)
	{
		this.name = name;
		this.hourlyRate = hourlyRate;
	}
	
	public String getName()
	{
		return name;
	}
	
	public float getHourlyRate()
	{
		return hourlyRate;
	}
	
	public float getSalary()
	{
		return hourlyRate * 52.0f * 40.0f;
	}
}
