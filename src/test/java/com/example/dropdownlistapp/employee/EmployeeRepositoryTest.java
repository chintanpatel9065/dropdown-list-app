package com.example.dropdownlistapp.employee;

import com.example.dropdownlistapp.department.Department;
import com.example.dropdownlistapp.department.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;

    @BeforeEach
    void setUp() {
        // Create and save a department first
        department = new Department();
        department.setDepartmentName("IT");
        department = departmentRepository.save(department);
    }

    @Test
    void testSaveEmployee() {
        // Given
        Employee employee = createEmployee("John", "A", "Doe");

        // When
        Employee savedEmployee = employeeRepository.save(employee);

        // Then
        assertNotNull(savedEmployee.getEmployeeId());
        assertEquals("John", savedEmployee.getFirstName());
        assertEquals("A", savedEmployee.getMiddleName());
        assertEquals("Doe", savedEmployee.getLastName());
        assertEquals("123 Main St", savedEmployee.getAddress());
        assertEquals("john.doe@example.com", savedEmployee.getEmail());
        assertEquals("1234567890", savedEmployee.getMobile());
        assertEquals(LocalDate.of(1990, 1, 1), savedEmployee.getBirthDate());
        assertEquals("johndoe", savedEmployee.getUserName());
        assertEquals("password123", savedEmployee.getPassword());
        assertEquals(department.getDepartmentId(), savedEmployee.getDepartment().getDepartmentId());
    }

    @Test
    void testFindAllEmployees() {
        // Given
        Employee employee1 = createEmployee("John", "A", "Doe");
        employeeRepository.save(employee1);

        Employee employee2 = createEmployee("Jane", "B", "Smith");
        employee2.setEmail("jane.smith@example.com");
        employee2.setUserName("janesmith");
        employeeRepository.save(employee2);

        // When
        List<Employee> employees = employeeRepository.findAll();

        // Then
        assertEquals(2, employees.size());
    }

    @Test
    void testFindEmployeeById() {
        // Given
        Employee employee = createEmployee("John", "A", "Doe");
        Employee savedEmployee = employeeRepository.save(employee);

        // When
        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getEmployeeId());

        // Then
        assertTrue(foundEmployee.isPresent());
        assertEquals("John", foundEmployee.get().getFirstName());
        assertEquals("A", foundEmployee.get().getMiddleName());
        assertEquals("Doe", foundEmployee.get().getLastName());
    }

    @Test
    void testUpdateEmployee() {
        // Given
        Employee employee = createEmployee("John", "A", "Doe");
        Employee savedEmployee = employeeRepository.save(employee);

        // When
        savedEmployee.setFirstName("Jonathan");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // Then
        assertEquals("Jonathan", updatedEmployee.getFirstName());
        assertEquals(savedEmployee.getEmployeeId(), updatedEmployee.getEmployeeId());
    }

    @Test
    void testDeleteEmployee() {
        // Given
        Employee employee = createEmployee("John", "A", "Doe");
        Employee savedEmployee = employeeRepository.save(employee);

        // When
        employeeRepository.deleteById(savedEmployee.getEmployeeId());
        Optional<Employee> deletedEmployee = employeeRepository.findById(savedEmployee.getEmployeeId());

        // Then
        assertFalse(deletedEmployee.isPresent());
    }

    private Employee createEmployee(String firstName, String middleName, String lastName) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setLastName(lastName);
        employee.setAddress("123 Main St");
        employee.setEmail("john.doe@example.com");
        employee.setMobile("1234567890");
        employee.setBirthDate(LocalDate.of(1990, 1, 1));
        employee.setUserName("johndoe");
        employee.setPassword("password123");
        employee.setDepartment(department);
        return employee;
    }
}