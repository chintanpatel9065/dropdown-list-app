# Dropdown List App

A Spring Boot web application for managing employees and departments with dropdown list functionality.

## Description

Dropdown List App is a web-based application built with Spring Boot that allows users to manage employees and departments. The application demonstrates the use of dropdown lists in forms, specifically for selecting departments when creating or editing employees.

## Technologies Used

- Java 24
- Spring Boot 3.5.0
- Spring Data JPA
- Spring MVC
- Thymeleaf
- Bootstrap 5.3.6
- PostgreSQL
- Maven
- Hibernate
- Jakarta Validation

## Features

- **Department Management**
  - List all departments
  - Add new departments
  - Edit existing departments
  - Delete departments

- **Employee Management**
  - List all employees
  - Add new employees with department selection via dropdown
  - Edit existing employees
  - Delete employees
  - Form validation for employee data

## Prerequisites

- Java 24 or higher
- Maven
- PostgreSQL

## Setup and Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd DropdownListApp
   ```

2. **Configure the database**
   
   Update the `src/main/resources/application.properties` file with your PostgreSQL credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/dropdown
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the application**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   
   Open your browser and navigate to `http://localhost:8080`

## Usage

### Department Management

1. Click on the "Department" link on the home page
2. To add a new department, click "Add New Department"
3. To edit a department, click "Edit" next to the department
4. To delete a department, click "Delete" next to the department

### Employee Management

1. Click on the "Employee" link on the home page
2. To add a new employee, click "Add New Employee"
3. Fill in the employee details and select a department from the dropdown list
4. To edit an employee, click "Edit" next to the employee
5. To delete an employee, click "Delete" next to the employee

## API Endpoints

### Department Endpoints

- `GET /departments/listDepartment` - List all departments
- `GET /departments/showDepartmentForm` - Show form to add a new department
- `POST /departments/insertOrUpdateDepartment` - Add or update a department
- `GET /departments/manageDepartment/{departmentId}` - Show form to edit a department
- `GET /departments/deleteDepartment/{departmentId}` - Delete a department

### Employee Endpoints

- `GET /employees/listEmployee` - List all employees
- `GET /employees/showEmployeeForm` - Show form to add a new employee
- `POST /employees/insertOrUpdateEmployee` - Add or update an employee
- `GET /employees/manageEmployee/{employeeId}` - Show form to edit an employee
- `GET /employees/deleteEmployee/{employeeId}` - Delete an employee

## Project Structure

```
DropdownListApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── dropdownlistapp/
│   │   │               ├── department/
│   │   │               │   ├── Department.java
│   │   │               │   ├── DepartmentController.java
│   │   │               │   ├── DepartmentRepository.java
│   │   │               │   └── DepartmentService.java
│   │   │               ├── employee/
│   │   │               │   ├── Employee.java
│   │   │               │   ├── EmployeeController.java
│   │   │               │   ├── EmployeeRepository.java
│   │   │               │   └── EmployeeService.java
│   │   │               └── DropdownListAppApplication.java
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── department/
│   │       │   │   ├── department-form.html
│   │       │   │   └── department-list.html
│   │       │   ├── employee/
│   │       │   │   ├── employee-form.html
│   │       │   │   └── employee-list.html
│   │       │   └── index.html
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── dropdownlistapp/
│                       ├── department/
│                       │   ├── DepartmentControllerTest.java
│                       │   ├── DepartmentRepositoryTest.java
│                       │   ├── DepartmentServiceTest.java
│                       │   └── DepartmentTest.java
│                       ├── employee/
│                       │   ├── EmployeeControllerTest.java
│                       │   ├── EmployeeRepositoryTest.java
│                       │   ├── EmployeeServiceTest.java
│                       │   └── EmployeeTest.java
│                       ├── integration/
│                       │   └── ApplicationIntegrationTest.java
│                       └── DropdownListAppApplicationTests.java
└── pom.xml
```

## Testing

The application includes unit tests and integration tests. To run the tests:

```bash
mvn test
```

## License

This project is licensed under the MIT License—see the LICENSE file for details.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request