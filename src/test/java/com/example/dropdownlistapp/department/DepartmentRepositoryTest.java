package com.example.dropdownlistapp.department;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void testSaveDepartment() {
        // Given
        Department department = new Department();
        department.setDepartmentName("IT");

        // When
        Department savedDepartment = departmentRepository.save(department);

        // Then
        assertNotNull(savedDepartment.getDepartmentId());
        assertEquals("IT", savedDepartment.getDepartmentName());
    }

    @Test
    void testFindAllDepartments() {
        // Given
        Department department1 = new Department();
        department1.setDepartmentName("IT");
        departmentRepository.save(department1);

        Department department2 = new Department();
        department2.setDepartmentName("HR");
        departmentRepository.save(department2);

        // When
        List<Department> departments = departmentRepository.findAll();

        // Then
        assertEquals(2, departments.size());
    }

    @Test
    void testFindDepartmentById() {
        // Given
        Department department = new Department();
        department.setDepartmentName("IT");
        Department savedDepartment = departmentRepository.save(department);

        // When
        Optional<Department> foundDepartment = departmentRepository.findById(savedDepartment.getDepartmentId());

        // Then
        assertTrue(foundDepartment.isPresent());
        assertEquals("IT", foundDepartment.get().getDepartmentName());
    }

    @Test
    void testUpdateDepartment() {
        // Given
        Department department = new Department();
        department.setDepartmentName("IT");
        Department savedDepartment = departmentRepository.save(department);

        // When
        savedDepartment.setDepartmentName("Information Technology");
        Department updatedDepartment = departmentRepository.save(savedDepartment);

        // Then
        assertEquals("Information Technology", updatedDepartment.getDepartmentName());
        assertEquals(savedDepartment.getDepartmentId(), updatedDepartment.getDepartmentId());
    }

    @Test
    void testDeleteDepartment() {
        // Given
        Department department = new Department();
        department.setDepartmentName("IT");
        Department savedDepartment = departmentRepository.save(department);

        // When
        departmentRepository.deleteById(savedDepartment.getDepartmentId());
        Optional<Department> deletedDepartment = departmentRepository.findById(savedDepartment.getDepartmentId());

        // Then
        assertFalse(deletedDepartment.isPresent());
    }
}