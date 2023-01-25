package com.swamy.service;

import java.util.List;

import com.swamy.entity.Employee;

public interface IEmployeeService {

	public Employee saveEmployee(Employee employee);

	public List<Employee> getAllEmployees();

	public Employee getOneEmployee(Integer employeeId);

	public Employee updateEmployee(Integer employeeId, Employee employee);

	public String deleteEmployee(Integer employeeId);
}
