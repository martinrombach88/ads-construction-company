import java.util.ArrayList;
import java.util.Scanner;

public void main() {
    TableConsoleApp app = new TableConsoleApp();
    app.runUserMenu();
}

class TableConsoleApp {
    private Scanner scanner = new Scanner(System.in);
    private ProjectTable projectTable = new ProjectTable('C');
    private EmployeeTable employeeTable = new EmployeeTable('E');
    private ArrayList<ProjectAssignment> projectAssignments = new ArrayList<>();

    void runUserMenu() {
        boolean programRunning = true;
        boolean isMaxProject;
        print("Welcome to the projects and employees management system");
        printMenu();
        print("Enter command: ");
        while (programRunning) {
            String command = scanner.nextLine();
            switch (command.toLowerCase()) {
                case "p":
                    addProject();
                    break;
                case "l":
                    projectTable.printProjects();
                    break;
                case "t":
                    projectTable.totalProjectsAndCost();
                    break;
                case "m":
                    print("Highest cost project: ");
                    isMaxProject = true;
                    projectTable.minMaxCostProject(isMaxProject);
                    break;
                case "n":
                    isMaxProject = false;
                    print("Lowest cost project: ");
                    projectTable.minMaxCostProject(isMaxProject);
                    break;
                case "c":
                    print("Projects by cost, descending: ");
                    ArrayList<Project> sortedProjects = projectTable.mergeSortByCost(projectTable.projects);
                    projectTable.printProjects(sortedProjects);
                    break;
                case "e":
                    addEmployee();
                    break;
                case "f":
                    employeeTable.printEmployees();
                    break;
                case "g":
                    ArrayList<Employee> sortedEmployees = employeeTable.mergeSortByLastName(employeeTable.employees);
                    employeeTable.printEmployees(sortedEmployees);
                    break;
                case "r":
                    assignEmployeeToProject();
                    break;
                case "s":
                    if (projectAssignments.isEmpty()){
                        print("No employees assigned to projects.");
                    } else {
                        printAssignments();
                    }
                    break;
                case "h":
                    printMenu();
                    break;
                case "q":
                    programRunning = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    print("Enter h to see commands.");
                    break;
            }
        }
    }

    void addProject() {
        String projectId = projectTable.projects.isEmpty() ? createId(projectTable.idPrefix) : createId(projectTable.projects.getLast().projectId);
        Project project = new Project(projectId);

        print("Enter project name: ");
        project.projectName = scanner.nextLine();
        print("Enter project description: ");
        project.setProjectDescription(scanner.nextLine());
        print("Enter project start date: ");
        project.setStartDate(scanner.nextLine());
        print("Enter project end date: ");
        project.setEndDate(scanner.nextLine());
        print("Enter project cost: ");
        project.setProjectCost(scanner.nextInt());
        projectTable.projects.add(project);
        print("Project " + project.projectName + " added.");
    }

    private void addEmployee() {
        String employeeId = employeeTable.employees.isEmpty() ? createId(employeeTable.idPrefix) : createId(employeeTable.employees.getLast().employeeId);
        Employee employee = new Employee(employeeId);

        print("Enter employee first name: ");
        employee.firstName = scanner.nextLine();
        print("Enter employee last name: ");
        employee.lastName = scanner.nextLine();
        print("Enter employee department: ");
        employee.department = scanner.nextLine();
        print("Enter employee role: ");
        employee.role = scanner.nextLine();

        employeeTable.employees.add(employee);

        print("Employee " + employee.firstName + " " + employee.lastName + " added.");


    }

    private void assignEmployeeToProject() {
        String id = "";
        String role = "";
        String projectId = "";

        print("enter employee id");
        id = scanner.nextLine();
        print("enter employee role");
        role = scanner.nextLine();
        print("enter the id of the project you would like to assign them to");
        projectId = scanner.nextLine();

        if (id.isEmpty() || role.isEmpty() || projectId.isEmpty()) {
            print("invalid input");
        } else {
            Project project = projectTable.getProjectFromTable(projectId);

            if (project.projectName.isEmpty()) {
                print("project doesn't exist");
                return;
            }
            Employee employee = employeeTable.getEmployeeByIdAndRole(id, role);

            if (employee.firstName.isEmpty()) {
                print("project doesn't exist");
                return;
            }


            ProjectAssignment assignment = new ProjectAssignment();

            assignment.firstName = employee.firstName;
            assignment.lastName = employee.lastName;
            assignment.role = employee.role;
            assignment.employeeID = employee.employeeId;
            assignment.projectID = project.projectId;
            assignment.projectName = project.projectName;

            projectAssignments.add(assignment);
            print("Employee " + employee.employeeId + " added to project" + projectId);
        }

   }

   void printAssignments() {
        for (ProjectAssignment pa : projectAssignments) {
            print(pa.toString());
        }
   }

    void printMenu() {
        print("View commands:");
        print("h help | q quit");
        print("p add project | e add employee");
        print("r assign employee to project");
        print("l projects by id | f employees by id");
        print("c projects by cost | g employees alphabetically");
        print("t total projects and cost ");
        print("m most expensive project");
        print("b cheapest project");

    }

}

class ProjectTable {
    private ArrayList<Project> projects = new ArrayList<>();
    char idPrefix;

    public ProjectTable(char idPrefix) {
        this.idPrefix = idPrefix;
    }

    public void printProjects() {
        print("Current projects:");
        for (Project project : projects) {
            project.printString();
        }
    }

    public void printProjects(ArrayList<Project> projects) {
        print("Current projects:");
        for (Project project : projects) {
            project.printString();
        }
    }

    public void totalProjectsAndCost() {
        int sum = 0;
        for (Project project : projects) {
            sum += project.projectCost;
        }
        print("Number of Projects " + projects.size() + " | Total Cost " + sum);
    }

    public void minMaxCostProject(boolean isMaxResult) {
        int resultCost = isMaxResult ? 0 : 99999999;
        String resultName = "";
        String resultId = "";
        int sameCost = 0;
        //linear search
        for (Project project : projects) {
            if (isMaxResult) {
                if (project.projectCost > resultCost) {
                    resultCost = project.projectCost;
                    resultName = project.projectName;
                    resultId = project.projectId;
                }
            } else {
                if (project.projectCost < resultCost) {
                    resultCost = project.projectCost;
                    resultName = project.projectName;
                    resultId = project.projectId;
                }
            }
        }
        //let the user know about duplicates
        for (Project project : projects) {
            if (project.projectCost == resultCost) {
                sameCost++;
            }
        }
        //don't count the project being compared in duplicates
        sameCost--;

        print("Project Id: " + resultId + " Project Name: " + resultName + " Project Cost: " + resultCost);
        if (sameCost == 1) {
            print("There is one project with the same cost as this.");
        } else if (sameCost > 1) {
            print("There are " + sameCost + " projects with the same cost as this.");
        }
    }


    ArrayList<Project> mergeSortByCost(ArrayList<Project> list) {
        if (list.size() ==1) return list;
        int midIndex = list.size() / 2;

        ArrayList<Project> leftList = mergeSortByCost(new ArrayList<>(list.subList(0, midIndex)));
        ArrayList<Project> rightList = mergeSortByCost(new ArrayList<>(list.subList(midIndex, list.size())));

        return merge(leftList, rightList);
    }

    ArrayList<Project> merge(ArrayList<Project> list1, ArrayList<Project> list2) {
        ArrayList<Project> combined = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < list1.size() && j < list2.size()) {
            if(list1.get(i).projectCost > list2.get(j).projectCost) {
                combined.add(list1.get(i));
                i++;
            } else {
                combined.add(list2.get(j));
                j++;
            }
        }
        while (i < list1.size()) {
            combined.add(list1.get(i));
            i++;
        }
        while (j < list2.size()) {
            combined.add(list2.get(j));
            j++;
        }
        return combined;
    }
    Project getProjectFromTable(String projectId) {
        Project emptyProject = new Project();

        for (Project project : projects) {
            if (project.projectId.equals(projectId)) {
                return project;
            }
        }

        return emptyProject;
    }



}

class EmployeeTable {
    private ArrayList<Employee> employees = new ArrayList<>();
    char idPrefix;
    public EmployeeTable(char idPrefix) {
        this.idPrefix = idPrefix;
    }
    public String getLastId () {
        return employees.get(-1).employeeId;
    }

    public void printEmployees() {
        print("Current employees:");
        for (Employee employee : employees) {
            employee.printString();
        }
    }

    public void printEmployees(ArrayList<Employee> employees ) {
        print("Current employees:");
        for (Employee employee : employees) {
            employee.printString();
        }
    }

    ArrayList<Employee> mergeSortByLastName(ArrayList<Employee> list) {
        if (list.size() ==1) return list;
        int midIndex = list.size() / 2;

        ArrayList<Employee> leftList = mergeSortByLastName(new ArrayList<>(list.subList(0, midIndex)));
        ArrayList<Employee> rightList = mergeSortByLastName(new ArrayList<>(list.subList(midIndex, list.size())));

        return mergeByLastName(leftList, rightList);
    }

    ArrayList<Employee> mergeByLastName(ArrayList<Employee> list1, ArrayList<Employee> list2) {
        ArrayList<Employee> combined = new ArrayList<>();
        int i = 0;
        int j = 0;


        while (i < list1.size() && j < list2.size()) {
            if(Character.getNumericValue(list1.get(i).lastName.charAt(0)) < Character.getNumericValue(list2.get(j).lastName.charAt(0))) {
                combined.add(list1.get(i));
                i++;
            } else {
                combined.add(list2.get(j));
                j++;
            }
        }
        while (i < list1.size()) {
            combined.add(list1.get(i));
            i++;
        }
        while (j < list2.size()) {
            combined.add(list2.get(j));
            j++;
        }
        return combined;
    }

    Employee getEmployeeByIdAndRole(String id, String role) {
        Employee result = new Employee();
        for (Employee employee : employees) {
            if (employee.employeeId.equals(id) && employee.role.equals(role)) {
                result = employee;
            }
        }

        return result;
    }
}

class Project {
    private String projectId;
    private String projectName;
    private String projectDescription;
    private int projectCost;
    private String startDate;
    private String endDate;

    Project(String projectId) {
        this.projectId = projectId;
    }

    public Project() {}

    // Setters
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public void setProjectCost(int projectCost) {
        this.projectCost = projectCost;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    void printString() {
        System.out.println(STR."""
            Project Details:
            ID: \{projectId}
            Name: \{projectName}
            Description: \{projectDescription}
            Cost: $\{projectCost}
            Start Date: \{startDate}
            End Date: \{endDate}""");
    }
}

class Employee{
    private String employeeId;
    private String firstName;
    private String lastName;
    private String department;
    private String role;


    public Employee(String employeeId) {
        this.employeeId = employeeId;
    }

    public Employee(){}

    // Getters
    public String getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public String getRole() { return role; }

    void printString() {
        System.out.println(STR."""
            Employee Details:
            ID: \{employeeId}
            First Name: \{firstName}
            Last Name: \{lastName}
            Department: \{department}
            Role: \{role}""");
    }

}

public class ProjectAssignment {
    private String projectID;
    private String projectName;
    private String employeeID;
    private String role;
    private String firstName;
    private String lastName;

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "ProjectAssignment{" +
                "projectID='" + projectID + '\'' +
                ", projectName='" + projectName + '\'' +
                ", employeeID='" + employeeID + '\'' +
                ", role='" + role + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}




public String createId(String previousEntry) {
    char prefix = previousEntry.charAt(0);
    int entry = Integer.parseInt(previousEntry.substring(1));
    entry++;

    String idEntry = String.format("%03d", entry);
    return prefix + idEntry;
}

public String createId(char prefix) {
    return prefix + "001";
}

void print(String message){
    System.out.println(message);
}

