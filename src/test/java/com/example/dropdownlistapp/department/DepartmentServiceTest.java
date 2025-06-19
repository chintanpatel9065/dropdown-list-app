package com.example.dropdownlistapp.department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        department1 = new Department(1L, "IT");
        department2 = new Department(2L, "HR");
    }

    @Test
    void testAddDepartment() {
        // Given
        when(departmentRepository.save(department1)).thenReturn(department1);

        // When
        departmentService.addDepartment(department1);

        // Then
        verify(departmentRepository, times(1)).save(department1);
    }

    @Test
    void testGetAllDepartmentList() {
        // Given
        List<Department> departments = Arrays.asList(department1, department2);
        when(departmentRepository.findAll()).thenReturn(departments);

        // When
        List<Department> result = departmentService.getAllDepartmentList();

        // Then
        assertEquals(2, result.size());
        assertEquals(departments, result);
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartmentById_ExistingId() {
        // Given
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department1));

        // When
        Department result = departmentService.getDepartmentById(1L);

        // Then
        assertNotNull(result);
        assertEquals(department1, result);
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDepartmentById_NonExistingId() {
        // Given
        when(departmentRepository.findById(3L)).thenReturn(Optional.empty());

        // When
        Department result = departmentService.getDepartmentById(3L);

        // Then
        assertNull(result);
        verify(departmentRepository, times(1)).findById(3L);
    }

    @Test
    void testDeleteDepartmentById() {
        // Given
        doNothing().when(departmentRepository).deleteById(1L);

        // When
        departmentService.deleteDepartmentById(1L);

        // Then
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}