package com.swamy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swamy.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


}
