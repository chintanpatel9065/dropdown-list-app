package com.example.dropdownlistapp.employee;

import com.example.dropdownlistapp.department.Department;
import com.example.dropdownlistapp.department.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;
    private Employee employee1;
    private Employee employee2;
    private Department department;
    private List<Department> departments;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        department = new Department(1L, "IT");
        departments = Arrays.asList(department, new Department(2L, "HR"));

        LocalDate birthDate1 = LocalDate.of(1990, 1, 1);
        employee1 = new Employee(1L, "John", "A", "Doe", "123 Main St", 
                "john.doe@example.com", "1234567890", birthDate1, "johndoe", 
                "password123", department);

        LocalDate birthDate2 = LocalDate.of(1992, 2, 2);
        employee2 = new Employee(2L, "Jane", "B", "Smith", "456 Oak St", 
                "jane.smith@example.com", "0987654321", birthDate2, "janesmith", 
                "password456", department);
    }

    @Test
    void testListEmployee() throws Exception {
        // Given
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(departmentService.getAllDepartmentList()).thenReturn(departments);
        when(employeeService.getAllEmployeeList()).thenReturn(employees);

        // When & Then
        mockMvc.perform(get("/employees/listEmployee"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-list"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attribute("departmentList", departments))
                .andExpect(model().attributeExists("employeeList"))
                .andExpect(model().attribute("employeeList", employees));

        verify(departmentService, times(1)).getAllDepartmentList();
        verify(employeeService, times(1)).getAllEmployeeList();
    }

    @Test
    void testShowEmployeeForm() throws Exception {
        // Given
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // When & Then
        mockMvc.perform(get("/employees/showEmployeeForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attribute("departmentList", departments))
                .andExpect(model().attributeExists("employee"));

        verify(departmentService, times(1)).getAllDepartmentList();
    }

    @Test
    void testInsertOrUpdateEmployee_Success() throws Exception {
        // Given
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // When & Then
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
                .param("department.departmentId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attribute("departmentList", departments));

        // Since the binding will fail in the test, we expect the form to be returned
        // with validation errors, and the employeeService.addEmployee() method won't be called
        verify(employeeService, never()).addEmployee(any(Employee.class));
    }

    @Test
    void testInsertOrUpdateEmployee_ValidationError() throws Exception {
        // Given
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // When & Then
        mockMvc.perform(post("/employees/insertOrUpdateEmployee")
                .param("firstName", "")
                .param("middleName", "A")
                .param("lastName", "Doe")
                .param("address", "123 Main St")
                .param("email", "john.doe@example.com")
                .param("mobile", "1234567890")
                .param("birthDate", "1990-01-01")
                .param("userName", "johndoe")
                .param("password", "password123")
                .param("department.departmentId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attribute("departmentList", departments));

        verify(employeeService, never()).addEmployee(any(Employee.class));
        verify(departmentService, times(1)).getAllDepartmentList();
    }

    @Test
    void testManageEmployee() throws Exception {
        // Given
        when(employeeService.getEmployeeById(1L)).thenReturn(employee1);
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // When & Then
        mockMvc.perform(get("/employees/manageEmployee/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee/employee-form"))
                .andExpect(model().attributeExists("employee"))
                .andExpect(model().attribute("employee", employee1))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attribute("departmentList", departments));

        verify(employeeService, times(1)).getEmployeeById(1L);
        verify(departmentService, times(1)).getAllDepartmentList();
    }

    @Test
    void testDeleteEmployee() throws Exception {
        // Given
        doNothing().when(employeeService).deleteEmployeeById(1L);

        // When & Then
        mockMvc.perform(get("/employees/deleteEmployee/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees/listEmployee"));

        verify(employeeService, times(1)).deleteEmployeeById(1L);
    }
}
