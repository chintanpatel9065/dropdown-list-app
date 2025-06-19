package com.example.dropdownlistapp.employee;

import com.example.dropdownlistapp.department.Department;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Validator validator;
    private Department department;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        department = new Department(1L, "IT");
    }

    @Test
    void testEmployeeCreation() {
        // Given
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
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

        // Then
        assertEquals(1L, employee.getEmployeeId());
        assertEquals("John", employee.getFirstName());
        assertEquals("A", employee.getMiddleName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("123 Main St", employee.getAddress());
        assertEquals("john.doe@example.com", employee.getEmail());
        assertEquals("1234567890", employee.getMobile());
        assertEquals(LocalDate.of(1990, 1, 1), employee.getBirthDate());
        assertEquals("johndoe", employee.getUserName());
        assertEquals("password123", employee.getPassword());
        assertEquals(department, employee.getDepartment());
    }

    @Test
    void testEmployeeConstructor() {
        // Given
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Employee employee = new Employee(1L, "John", "A", "Doe", "123 Main St", 
                "john.doe@example.com", "1234567890", birthDate, "johndoe", 
                "password123", department);

        // Then
        assertEquals(1L, employee.getEmployeeId());
        assertEquals("John", employee.getFirstName());
        assertEquals("A", employee.getMiddleName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("123 Main St", employee.getAddress());
        assertEquals("john.doe@example.com", employee.getEmail());
        assertEquals("1234567890", employee.getMobile());
        assertEquals(birthDate, employee.getBirthDate());
        assertEquals("johndoe", employee.getUserName());
        assertEquals("password123", employee.getPassword());
        assertEquals(department, employee.getDepartment());
    }

    @Test
    void testFirstNameValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setFirstName("");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Provide First Name", violation.getMessage());
        assertEquals("firstName", violation.getPropertyPath().toString());
    }

    @Test
    void testMiddleNameValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setMiddleName("");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Provide Middle Name", violation.getMessage());
        assertEquals("middleName", violation.getPropertyPath().toString());
    }

    @Test
    void testLastNameValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setLastName("");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Provide Last Name", violation.getMessage());
        assertEquals("lastName", violation.getPropertyPath().toString());
    }

    @Test
    void testAddressValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setAddress("");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Provide Address", violation.getMessage());
        assertEquals("address", violation.getPropertyPath().toString());
    }

    @Test
    void testEmailValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setEmail("");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Provide Email", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    void testInvalidEmailFormat() {
        // Given
        Employee employee = createValidEmployee();
        employee.setEmail("invalid-email");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Provide Valid Email", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    void testMobileValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setMobile("");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Provide Mobile Number", violation.getMessage());
        assertEquals("mobile", violation.getPropertyPath().toString());
    }

    @Test
    void testBirthDateValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setBirthDate(null);

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Select BirthDate", violation.getMessage());
        assertEquals("birthDate", violation.getPropertyPath().toString());
    }

    @Test
    void testUserNameValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setUserName("");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Provide UserName", violation.getMessage());
        assertEquals("userName", violation.getPropertyPath().toString());
    }

    @Test
    void testPasswordValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setPassword("");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(2, violations.size());
        // Both @NotEmpty and @Size validations are triggered for empty password
        boolean hasNotEmptyViolation = false;
        boolean hasSizeViolation = false;

        for (ConstraintViolation<Employee> violation : violations) {
            if (violation.getMessage().equals("Please Provide Password")) {
                hasNotEmptyViolation = true;
                assertEquals("password", violation.getPropertyPath().toString());
            } else if (violation.getMessage().equals("Password must be between 8 and 16 characters")) {
                hasSizeViolation = true;
                assertEquals("password", violation.getPropertyPath().toString());
            }
        }

        assertTrue(hasNotEmptyViolation, "Should have @NotEmpty violation");
        assertTrue(hasSizeViolation, "Should have @Size violation");
    }

    @Test
    void testPasswordTooShort() {
        // Given
        Employee employee = createValidEmployee();
        employee.setPassword("short");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Password must be between 8 and 16 characters", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
    }

    @Test
    void testPasswordTooLong() {
        // Given
        Employee employee = createValidEmployee();
        employee.setPassword("thisisaverylongpasswordthatexceedsthemaximumlength");

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Password must be between 8 and 16 characters", violation.getMessage());
        assertEquals("password", violation.getPropertyPath().toString());
    }

    @Test
    void testDepartmentValidation() {
        // Given
        Employee employee = createValidEmployee();
        employee.setDepartment(null);

        // When
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Employee> violation = violations.iterator().next();
        assertEquals("Please Select Department", violation.getMessage());
        assertEquals("department", violation.getPropertyPath().toString());
    }

    private Employee createValidEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
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
        return employee;
    }
}
