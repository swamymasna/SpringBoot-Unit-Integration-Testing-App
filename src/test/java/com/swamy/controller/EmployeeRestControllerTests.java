package com.swamy.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swamy.entity.Employee;
import com.swamy.service.IEmployeeService;

@WebMvcTest
public class EmployeeRestControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IEmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	private Employee employee;

	@BeforeEach
	public void setup() {
		employee = Employee.builder().id(1).employeeName("swamy").employeeAddress("hyd").build();
	}

	@DisplayName("JUnit Test For Save Employee Operation")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployee() throws Exception {

		when(employeeService.saveEmployee(employee)).thenReturn(employee);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/employee/save")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee));

//		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andDo(print());
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andDo(print())
				.andExpect(jsonPath("$.employeeName", is(employee.getEmployeeName())))
				.andExpect(jsonPath("$.employeeAddress", is(employee.getEmployeeAddress())));
	}

	@DisplayName("JUnit Test For Save Employee Operation (Negative Scenario)")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployeeNS() throws Exception {

		when(employeeService.saveEmployee(employee)).thenReturn(employee);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/employee/save")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null));

//		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andDo(print());
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("JUnit Test For Get All Employees Operation")
	@Test
	public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(employee);

		when(employeeService.getAllEmployees()).thenReturn(employeeList);

		mockMvc.perform(get("/api/v1/employee/list")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(employeeList.size())));

	}

	@DisplayName("JUnit Test For Get One Employee By Id Operation")
	@Test
	public void givenEmployeeObject_whenGetOneEmployee_thenReturnEmployee() throws Exception {

		when(employeeService.getOneEmployee(employee.getId())).thenReturn(employee);

		ResultActions resultActions = mockMvc.perform(get("/api/v1/employee/emp/" + employee.getId()));
		resultActions.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.employeeName", is(employee.getEmployeeName())))
				.andExpect(jsonPath("$.employeeAddress", is(employee.getEmployeeAddress())));
	}

	@DisplayName("JUnit Test For Update Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {

		when(employeeService.updateEmployee(employee.getId(), employee)).thenReturn(employee);

		ResultActions resultActions = mockMvc.perform(put("/api/v1/employee/update/" + employee.getId())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

		resultActions.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.employeeName", is(employee.getEmployeeName())))
				.andExpect(jsonPath("$.employeeAddress", is(employee.getEmployeeAddress())));

	}

	@DisplayName("JUnit Test For Update Employee By Id Operation [Negative Scenario]")
	@Test
	public void givenEmployeeId_whenUpdateEmployee_thenReturnUpdatedEmployeeNS() throws Exception {

		when(employeeService.updateEmployee(employee.getId(), employee)).thenReturn(employee);

		ResultActions resultActions = mockMvc.perform(put("/api/v1/employee/update/" + employee.getId())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null)));

		resultActions.andDo(print()).andExpect(status().isBadRequest());

	}

	@DisplayName("JUnit Test For Delete Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenReturnDeleteSuccessResponse() throws Exception {

		when(employeeService.deleteEmployee(employee.getId())).thenReturn("SUCCESS");

		ResultActions resultActions = mockMvc.perform(delete("/api/v1/employee/delete/{emp-id}", employee.getId()));
		resultActions.andDo(print()).andExpect(status().isOk());
	}

}
