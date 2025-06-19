package com.example.dropdownlistapp.department;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testDepartmentCreation() {
        // Given
        Department department = new Department();
        department.setDepartmentId(1L);
        department.setDepartmentName("IT");

        // Then
        assertEquals(1L, department.getDepartmentId());
        assertEquals("IT", department.getDepartmentName());
    }

    @Test
    void testDepartmentConstructor() {
        // Given
        Department department = new Department(1L, "IT");

        // Then
        assertEquals(1L, department.getDepartmentId());
        assertEquals("IT", department.getDepartmentName());
    }

    @Test
    void testDepartmentNameValidation() {
        // Given
        Department department = new Department();
        department.setDepartmentId(1L);
        department.setDepartmentName("");

        // When
        Set<ConstraintViolation<Department>> violations = validator.validate(department);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Department> violation = violations.iterator().next();
        assertEquals("Please Provide Department Name", violation.getMessage());
        assertEquals("departmentName", violation.getPropertyPath().toString());
    }

    @Test
    void testDepartmentNameNull() {
        // Given
        Department department = new Department();
        department.setDepartmentId(1L);
        department.setDepartmentName(null);

        // When
        Set<ConstraintViolation<Department>> violations = validator.validate(department);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<Department> violation = violations.iterator().next();
        assertEquals("Please Provide Department Name", violation.getMessage());
        assertEquals("departmentName", violation.getPropertyPath().toString());
    }
}