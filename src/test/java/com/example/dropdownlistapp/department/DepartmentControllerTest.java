package com.example.dropdownlistapp.department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private MockMvc mockMvc;
    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
        
        department1 = new Department(1L, "IT");
        department2 = new Department(2L, "HR");
    }

    @Test
    void testListDepartment() throws Exception {
        // Given
        List<Department> departments = Arrays.asList(department1, department2);
        when(departmentService.getAllDepartmentList()).thenReturn(departments);

        // When & Then
        mockMvc.perform(get("/departments/listDepartment"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-list"))
                .andExpect(model().attributeExists("departmentList"))
                .andExpect(model().attribute("departmentList", departments));
        
        verify(departmentService, times(1)).getAllDepartmentList();
    }

    @Test
    void testShowDepartmentForm() throws Exception {
        // When & Then
        mockMvc.perform(get("/departments/showDepartmentForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"))
                .andExpect(model().attributeExists("department"));
    }

    @Test
    void testInsertOrUpdateDepartment_Success() throws Exception {
        // Given
        doNothing().when(departmentService).addDepartment(any(Department.class));

        // When & Then
        mockMvc.perform(post("/departments/insertOrUpdateDepartment")
                .param("departmentName", "IT"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments/listDepartment"));
        
        verify(departmentService, times(1)).addDepartment(any(Department.class));
    }

    @Test
    void testInsertOrUpdateDepartment_ValidationError() throws Exception {
        // When & Then
        mockMvc.perform(post("/departments/insertOrUpdateDepartment")
                .param("departmentName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"));
        
        verify(departmentService, never()).addDepartment(any(Department.class));
    }

    @Test
    void testManageDepartment() throws Exception {
        // Given
        when(departmentService.getDepartmentById(1L)).thenReturn(department1);

        // When & Then
        mockMvc.perform(get("/departments/manageDepartment/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("department/department-form"))
                .andExpect(model().attributeExists("department"))
                .andExpect(model().attribute("department", department1));
        
        verify(departmentService, times(1)).getDepartmentById(1L);
    }

    @Test
    void testDeleteDepartment() throws Exception {
        // Given
        doNothing().when(departmentService).deleteDepartmentById(1L);

        // When & Then
        mockMvc.perform(get("/departments/deleteDepartment/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/departments/listDepartment"));
        
        verify(departmentService, times(1)).deleteDepartmentById(1L);
    }
}