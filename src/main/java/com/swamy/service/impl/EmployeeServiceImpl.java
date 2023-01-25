package com.swamy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swamy.entity.Employee;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getOneEmployee(Integer employeeId) {
		return employeeRepository.findById(employeeId).get();
	}

	@Override
	public Employee updateEmployee(Integer employeeId, Employee employee) {
		Employee emp = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee Not Found With Id : " + employeeId));
		emp.setEmployeeName(employee.getEmployeeName());
		emp.setEmployeeAddress(employee.getEmployeeAddress());
		return employeeRepository.save(emp);
	}

	@Override
	public String deleteEmployee(Integer employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee Not Found With Id : " + employeeId));
		employeeRepository.delete(employee);
		return "Employee Deleted Successfully";
	}

}
