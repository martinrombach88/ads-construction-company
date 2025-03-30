import java.util.ArrayList;
import java.util.Arrays;

public class Playground {
    public void main() {
        //Modify your merge sort algorithm to handle object comparisons, otherwise you'll
        //add unnecessary time complexity

        //testMergeSort();
        //testSubList();
        Project test1 = new Project("id", "name1", "desc1", 300, "start", "end");
        Project test2 = new Project("id", "name1", "desc1", 200, "start", "end");
        Project test3 = new Project("id", "name1", "desc1", 800, "start", "end");
        Project test4 = new Project("id", "name1", "desc1", 200, "start", "end");
        Project test5 = new Project("id", "name1", "desc1", 100, "start", "end");
        Project test6 = new Project("id", "name1", "desc1", 400, "start", "end");
        Project test7 = new Project("id", "name1", "desc1", 700, "start", "end");
        Project test8 = new Project("id", "name1", "desc1", 600, "start", "end");
        ArrayList<Project> originalProjects1 = new ArrayList<>();
        ArrayList<Project> originalProjects2 = new ArrayList<>();

        originalProjects1.add(test1);
        originalProjects1.add(test2);
        originalProjects1.add(test3);
        originalProjects1.add(test4);

        originalProjects2.add(test5);
        originalProjects2.add(test6);
        originalProjects2.add(test7);
        originalProjects2.add(test8);

        ArrayList<Project> result = merge2(originalProjects1, originalProjects2);
        ArrayList<Project> sortedResult = mergeSortByCost(result);

        printProjects(sortedResult);

    }


    ArrayList<Project> mergeSortByCost(ArrayList<Project> list) {
        if (list.size() ==1) return list;
        int midIndex = list.size() / 2;

        ArrayList<Project> leftList = mergeSortByCost(new ArrayList<>(list.subList(0, midIndex)));
        ArrayList<Project> rightList = mergeSortByCost(new ArrayList<>(list.subList(midIndex, list.size())));

        return merge2(leftList, rightList);
    }

    ArrayList<Project> merge2(ArrayList<Project> list1, ArrayList<Project> list2) {
        ArrayList<Project> combined = new ArrayList<>();
        int index = 0;
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

    void testMergeSort() {
        //process
        //build merge, test on 2 arrays
        //build merge sort


        print("test");
        int[] test1 = {1, 2, 6, 7, 5, 8, 4, 3};
        int[] test2 = mergeSort(test1);

    }



    void testSubList() {
        //confirms that sublists can be made into new arrays therefore not modifying original array.
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            list.add(i);
        }
        ArrayList<Integer> sublist = new ArrayList<>(list.subList(0, 4));
        print(sublist.toString());
        print(list.toString());
    }

    int[] mergeSort(int[] array) {
        if (array.length == 1) return array;
        int midIndex = array.length / 2;

        int[] left = mergeSort(Arrays.copyOfRange(array, 0, midIndex));
        int[] right = mergeSort(Arrays.copyOfRange(array, midIndex, array.length));

        return merge(left, right);
    }

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
        while (j < array2.length) {
            combined[index] = array2[j];
            index++;
            j++;
        }
        return combined;
    }

    void print(String message) {
        System.out.println(message);
    }

    void printProjects(ArrayList<Project> projects) {
        print("Current projects:");
        for (Project project : projects) {
            project.printString();
        }
}

class Project {
    private String projectId;
    private String projectName;
    private String projectDescription;
    int projectCost;
    private String startDate;
    private String endDate;

    Project(String projectId) {
        this.projectId = projectId;
    }

    // Constructor
    public Project(String projectId, String projectName, String projectDescription, int projectCost, String startDate, String endDate) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectCost = projectCost;
        this.startDate = startDate;
        this.endDate = endDate;
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
}