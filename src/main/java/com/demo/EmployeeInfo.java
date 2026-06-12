package com.demo;

import java.io.Serializable;

public class EmployeeInfo implements Serializable {
    private String firstName;
    private String lastName;
    private String gender;
    private String deptName;
    private double salary;

    public EmployeeInfo(String firstName, String lastName, String gender, String deptName, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.deptName = deptName;
        this.salary = salary;
    }

    
    public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public double getSalary() {
		return salary;
	}


	public void setSalary(double salary) {
		this.salary = salary;
	}


	@Override
    public String toString() {
        return firstName + " " + lastName + " (" + gender + ") - " + deptName + " - $" + salary;
    }
}
