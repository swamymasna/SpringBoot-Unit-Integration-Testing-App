package com.swamy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.entity.Employee;
import com.swamy.service.IEmployeeService;

@RestController
@RequestMapping("/api/v1")
public class EmployeeRestController {

	@Autowired
	private IEmployeeService employeeService;

	@PostMapping("/employee/save")
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}

	@GetMapping("/employee/list")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
	}

	@GetMapping("/employee/emp/{emp-id}")
	public ResponseEntity<Employee> getOneEmployee(@PathVariable(value = "emp-id") Integer empId) {
		return new ResponseEntity<>(employeeService.getOneEmployee(empId), HttpStatus.OK);
	}
	
	@PutMapping("/employee/update/{emp-id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "emp-id") Integer empId, @RequestBody Employee employee) {
		return new ResponseEntity<>(employeeService.updateEmployee(empId, employee), HttpStatus.OK);
	}
	
	@DeleteMapping("/employee/delete/{emp-id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable(value = "emp-id") Integer empId) {
		return new ResponseEntity<>(employeeService.deleteEmployee(empId), HttpStatus.OK);
	}
}


