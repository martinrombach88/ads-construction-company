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
                case "e":
                    addEmployee();
                    break;
                case "f":
                    employeeTable.printEmployees();
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

    void printMenu() {
        print("View commands:");
        print("l projects by id | f employees by id");
        print("c projects by cost | a employees alphabetically");
        print("t total projects and cost ");
        print("m most expensive project");
        print("b cheapest project");

        print("Misc Commands");
        print("p add project | e add employee");
        print("h help | e exit");
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
        for (Project project : projects) {
            if (isMaxResult) {
                if (project.projectCost > resultCost) {
                    resultCost = project.projectCost;
                    resultName = project.projectName;
                    resultId = project.projectId;
                }
            }
            else {
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

    /*
    public void sortByCost() {
        ArrayList sortedProjects = mergeSort(projects);

    }

    int[] mergeSort(ArrayList list) {



        //pivot is the mid index to target
        if (list.length ==1) return list;
        int pivot = list.length / 2;

        int[] left = mergeSort(Arrays.copyOfRange(list, 0, pivot));
        int[] right = mergeSort(Arrays.copyOfRange(list, pivot, list.length));

        return merge(left, right);
    }
    */
    int[] merge(int[] array1, int[] array2) {
        int[] combined = new int[array1.length + array2.length];
        int index = 0;
        int i = 0;
        int j = 0;

        while (i < array1.length && j < array2.length) {
            if (array1[i] < array2[j]) {
                combined[index] = array1[i];
                index++;
                i++;
            } else {
                combined[index] = array2[j];
                index++;
                j++;
            }
        }
        while (i < array1.length) {
            combined[index] = array1[i];
            index++;
            i++;
        }
        while(j < array2.length) {
            combined[index] = array2[j];
            index++;
            j++;
        }
        return combined;
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
}
class Project implements Comparable<Project>{
    private String projectId;
    private String projectName;
    private String projectDescription;
    private int projectCost;
    private String startDate;
    private String endDate;

    Project(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public int compareTo(Project comparedProject) {
        //numeric representation of project comparison
        return Integer.compare(this.projectCost, comparedProject.projectCost);
    }

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



class Employee implements Comparable<Employee>{
    private String employeeId;
    private String firstName;
    private String lastName;
    private String department;
    private String role;

    public Employee(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int compareTo(Employee comparedEmployee) {
        //converting the first letter of the last name into a numeric value
        int ascLastName = Character.getNumericValue(this.lastName.charAt(0));
        int ascComparedLastName = Character.getNumericValue(comparedEmployee.lastName.charAt(0));

        //comparing the value and returning a numeric representation of result
        return Integer.compare(ascLastName, ascComparedLastName);
    }

    // Getters
    public String getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public String getRole() { return role; }

    // Setters
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setRole(String role) {
        this.role = role;
    }

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
