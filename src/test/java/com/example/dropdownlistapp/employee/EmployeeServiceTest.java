package com.example.dropdownlistapp.employee;

import com.example.dropdownlistapp.department.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;
    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department(1L, "IT");
        
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
    void testAddEmployee() {
        // Given
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        // When
        employeeService.addEmployee(employee1);

        // Then
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    void testGetAllEmployeeList() {
        // Given
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        // When
        List<Employee> result = employeeService.getAllEmployeeList();

        // Then
        assertEquals(2, result.size());
        assertEquals(employees, result);
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById_ExistingId() {
        // Given
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));

        // When
        Employee result = employeeService.getEmployeeById(1L);

        // Then
        assertNotNull(result);
        assertEquals(employee1, result);
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NonExistingId() {
        // Given
        when(employeeRepository.findById(3L)).thenReturn(Optional.empty());

        // When
        Employee result = employeeService.getEmployeeById(3L);

        // Then
        assertNull(result);
        verify(employeeRepository, times(1)).findById(3L);
    }

    @Test
    void testDeleteEmployeeById() {
        // Given
        doNothing().when(employeeRepository).deleteById(1L);

        // When
        employeeService.deleteEmployeeById(1L);

        // Then
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}