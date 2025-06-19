package com.example.dropdownlistapp.integration;

import com.example.dropdownlistapp.department.Department;
import com.example.dropdownlistapp.department.DepartmentRepository;
import com.example.dropdownlistapp.employee.Employee;
import com.example.dropdownlistapp.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testDepartmentWorkflow() throws Exception {
        // Test department list page
        mockMvc.perform(get("/departments/listDepartment"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-list"))
                .andExpect(model().attributeExists("departmentList"));

        // Test department form page
        mockMvc.perform(get("/departments/showDepartmentForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"))
                .andExpect(model().attributeExists("department"));

        // Test create department
        mockMvc.perform(post("/departments/insertOrUpdateDepartment")
                .param("departmentName", "Engineering"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments/listDepartment"));

        // Test department list after creation
        mockMvc.perform(get("/departments/listDepartment"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-list"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attribute("departmentList", hasItem(
                        hasProperty("departmentName", is("Engineering"))
                )));
    }

    @Test
    void testEmployeeWorkflow() throws Exception {
        // Create a department first
        Department department = new Department();
        department.setDepartmentName("IT");
        department = departmentRepository.save(department);

        // Test employee list page
        mockMvc.perform(get("/employees/listEmployee"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-list"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attributeExists("employeeList"));

        // Test employee form page
        mockMvc.perform(get("/employees/showEmployeeForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attributeExists("employee"));

        // Test create employee
        mockMvc.perform(post("/employees/insertOrUpdateEmployee")
                .param("firstName", "John")
                .param("middleName", "A")
                .param("lastName", "Doe")
                .param("address", "123 Main St")
                .param("email", "john.doe@example.com")
                .param("mobile", "1234567890")
                .param("birthDate", "1990-01-01")
                .param("userName", "johndoe")
                .param("password", "password123")
                .param("department.departmentId", department.getDepartmentId().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/listEmployee"));

        // Test employee list after creation
        mockMvc.perform(get("/employees/listEmployee"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-list"))
                .andExpect(model().attributeExists("employeeList"))
                .andExpect(model().attribute("employeeList", hasItem(
                        allOf(
                                hasProperty("firstName", is("John")),
                                hasProperty("lastName", is("Doe")),
                                hasProperty("email", is("john.doe@example.com"))
                        )
                )));
    }

    @Test
    void testEmployeeManagement() throws Exception {
        // Create a department
        Department department = new Department();
        department.setDepartmentName("IT");
        department = departmentRepository.save(department);

        // Create an employee
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setMiddleName("A");
        employee.setLastName("Doe");
        employee.setAddress("123 Main St");
        employee.setEmail("john.doe@example.com");
        employee.setMobile("1234567890");
        employee.setBirthDate(LocalDate.of(1990, 1, 1));
        employee.setUserName("johndoe");
        employee.setPassword("password123");
        employee.setDepartment(department);
        employee = employeeRepository.save(employee);

        // Test manage employee
        mockMvc.perform(get("/employees/manageEmployee/" + employee.getEmployeeId()))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attribute("employee", 
                        allOf(
                                hasProperty("firstName", is("John")),
                                hasProperty("lastName", is("Doe")),
                                hasProperty("email", is("john.doe@example.com"))
                        )
                ));

        // Test delete employee
        mockMvc.perform(get("/employees/deleteEmployee/" + employee.getEmployeeId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/listEmployee"));
    }
}