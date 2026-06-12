package com.demo;

import java.sql.*;
import java.util.*;

public class EmployeeRepository {
	private final String url = "jdbc:mysql://localhost:3307/employees";
	private final String user = "root";
	private final String password = "mysql2620";

	public List<EmployeeInfo> getEmployeeData(int empNo) {
		List<EmployeeInfo> result = new ArrayList<>();
		String sql = "SELECT e.first_name, e.last_name, e.gender, d.dept_name, s.salary " + "FROM employees e "
				+ "JOIN salaries s ON s.emp_no = e.emp_no " + "JOIN dept_emp de ON de.emp_no = e.emp_no "
				+ "JOIN departments d ON d.dept_no = de.dept_no " + "WHERE e.emp_no = ? " ;

		try (Connection conn = DriverManager.getConnection(url, user, password);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, empNo); // primer ?

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(new EmployeeInfo(rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("gender"), rs.getString("dept_name"), rs.getDouble("salary")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}