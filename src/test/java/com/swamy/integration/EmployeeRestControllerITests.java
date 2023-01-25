package com.swamy.integration;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swamy.entity.Employee;
import com.swamy.service.IEmployeeService;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeRestControllerITests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
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

		//when(employeeService.saveEmployee(employee)).thenReturn(employee);

		Employee savedEmployee = employeeService.saveEmployee(employee);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/employee/save")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(savedEmployee));

//		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andDo(print());
		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andDo(print())
				.andExpect(jsonPath("$.employeeName", is(savedEmployee.getEmployeeName())))
				.andExpect(jsonPath("$.employeeAddress", is(savedEmployee.getEmployeeAddress())));
	}

	@DisplayName("JUnit Test For Save Employee Operation (Negative Scenario)")
	@Test
	public void givenEmployeeObject_whenSaveEmployee_thenReturnSavedEmployeeNS() throws Exception {

		//when(employeeService.saveEmployee(employee)).thenReturn(employee);
		employeeService.saveEmployee(employee);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/employee/save")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null));

//		mockMvc.perform(requestBuilder).andExpect(status().isCreated()).andDo(print());
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andDo(print());
	}

	@DisplayName("JUnit Test For Get All Employees Operation")
	@Test
	public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {
		Employee savedEmployee = employeeService.saveEmployee(employee);
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(savedEmployee);

		//when(employeeService.getAllEmployees()).thenReturn(employeeList);
		employeeList = employeeService.getAllEmployees();
		mockMvc.perform(get("/api/v1/employee/list")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(employeeList.size())));
	}

	@DisplayName("JUnit Test For Get One Employee By Id Operation")
	@Test
	public void givenEmployeeObject_whenGetOneEmployee_thenReturnEmployee() throws Exception {

		Employee savedEmployee = employeeService.saveEmployee(employee);
		//when(employeeService.getOneEmployee(employee.getId())).thenReturn(employee);
		employeeService.getOneEmployee(savedEmployee.getId());
		ResultActions resultActions = mockMvc.perform(get("/api/v1/employee/emp/" + savedEmployee.getId()));
		resultActions.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.employeeName", is(savedEmployee.getEmployeeName())))
				.andExpect(jsonPath("$.employeeAddress", is(savedEmployee.getEmployeeAddress())));
	}

	@DisplayName("JUnit Test For Update Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {

		Employee savedEmployee = employeeService.saveEmployee(employee);
		//when(employeeService.updateEmployee(employee.getId(), employee)).thenReturn(employee);

		ResultActions resultActions = mockMvc.perform(put("/api/v1/employee/update/" + savedEmployee.getId())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(savedEmployee)));

		resultActions.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.employeeName", is(savedEmployee.getEmployeeName())))
				.andExpect(jsonPath("$.employeeAddress", is(savedEmployee.getEmployeeAddress())));

	}

	@DisplayName("JUnit Test For Update Employee By Id Operation [Negative Scenario]")
	@Test
	public void givenEmployeeId_whenUpdateEmployee_thenReturnUpdatedEmployeeNS() throws Exception {

		employeeService.saveEmployee(employee);
		//when(employeeService.updateEmployee(employee.getId(), employee)).thenReturn(employee);

		ResultActions resultActions = mockMvc.perform(put("/api/v1/employee/update/" + employee.getId())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null)));

		resultActions.andDo(print()).andExpect(status().isBadRequest());

	}

	@DisplayName("JUnit Test For Delete Employee By Id Operation")
	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenReturnDeleteSuccessResponse() throws Exception {

		Employee savedEmployee = employeeService.saveEmployee(employee);
		//when(employeeService.deleteEmployee(employee.getId())).thenReturn("SUCCESS");
//		employeeService.deleteEmployee(employee.getId());
		
		ResultActions resultActions = mockMvc.perform(delete("/api/v1/employee/delete/{emp-id}", savedEmployee.getId()));
		resultActions.andDo(print()).andExpect(status().isOk());
	}

}
