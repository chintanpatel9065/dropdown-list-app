package com.example.dropdownlistapp.employee;

import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeService")
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public List<Employee>getAllEmployeeList() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public void deleteEmployeeById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
