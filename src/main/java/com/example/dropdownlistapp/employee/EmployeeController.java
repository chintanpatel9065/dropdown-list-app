package com.example.dropdownlistapp.employee;

import com.example.dropdownlistapp.department.Department;
import com.example.dropdownlistapp.department.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping("/employees/listEmployee")
    public String listEmployee(Model model) {
        List<Department> departmentList = departmentService.getAllDepartmentList();
        List<Employee> employeeList = employeeService.getAllEmployeeList();
        model.addAttribute("departmentList", departmentList);
        model.addAttribute("employeeList", employeeList);
        return "employee/employee-list";
    }

    @GetMapping("/employees/showEmployeeForm")
    public String showEmployeeForm(Model model) {
        Employee employee = new Employee();
        List<Department> departmentList = departmentService.getAllDepartmentList();
        model.addAttribute("departmentList", departmentList);
        model.addAttribute("employee", employee);
        return "employee/employee-form";
    }

    @PostMapping("/employees/insertOrUpdateEmployee")
    public String insertOrUpdateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Department> departmentList = departmentService.getAllDepartmentList();
            model.addAttribute("departmentList", departmentList);
            return "employee/employee-form";
        }
        employeeService.addEmployee(employee);
        return "redirect:/employees/listEmployee";
    }

    @GetMapping("/employees/manageEmployee/{employeeId}")
    public String manageEmployee(@PathVariable Long employeeId, Model model) {
        if (employeeId != null) {
            Employee employee = employeeService.getEmployeeById(employeeId);
            model.addAttribute("employee", employee);
            List<Department> departmentList = departmentService.getAllDepartmentList();
            model.addAttribute("departmentList", departmentList);
        }
        return "employee/employee-form";
    }

    @GetMapping("/employees/deleteEmployee/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId) {
        if (employeeId != null) {
            employeeService.deleteEmployeeById(employeeId);
        }
        return "redirect:/employees/listEmployee";
    }
}
