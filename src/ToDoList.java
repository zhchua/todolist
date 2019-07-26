import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ToDoList {
    private ArrayList<Task> taskList;
    private Scanner s;
    private int counter;

    public ToDoList() {
        this.taskList = new ArrayList<Task>();
        this.s = new Scanner(System.in);
        this.counter = 1;
        menu();
    }

    public void menu() {

        int choice = 0;

        do {
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Edit Task");
            System.out.println("4. Track Task Status");
            System.out.println("5. Change Task Status");
            System.out.println("6. Today's List");
            System.out.println("7. Search for Task");
            System.out.println("8. Exit");
            System.out.print("Enter Choice: ");
            choice = this.s.nextInt();
            this.s.nextLine();
            System.out.println();
            getChoice(choice);
            System.out.println();
        } while (choice != 8);
    }

    
    public void removeTask() {
        try{
            int id = getIDInput();
            if(!this.taskList.isEmpty()){
                for(Task t : this.taskList){
                    if(t.getId() == id){
                        this.taskList.remove(t);
                    }
                }
            }
            else{
                throw new TaskException("There are no task\n");
            }
        }
        catch(TaskException e){
            System.out.println(e.getMessage());
        }

    }
       

    public void getChoice(int choice) {
        switch (choice) {
        case 1:
            addTask();
            break;
         case 2 : removeTask();
         break;
        case 3 : modifyTask();
        break;
        case 4:
            statusTrack();
         break  ;
        case 5 : changeStatus();
            break;
          
            // case 7 : organiseTask();
            // break;
        case 6 : todayList();
            break;
        case 7:
        getTask();
        break;
        case 8 :    System.out.println("Thank you for using the app!");
            System.exit(0);
            break;
        default:
            System.out.println("Invalid Choice\n");

        }

    }

    public void addTask() {
        Task t = createTask();
        this.taskList.add(t);
    }

    public Task createTask() {
        String description = "";
        int priority = 0;
        String sStartDate = "";
        String sEndDate = "";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        String taskType = "";

        try {
            System.out.print("Enter Description: ");
            description = this.s.nextLine();
            if (description.isEmpty()) {
                throw new TaskException("Invalid Input\n");
            }

            System.out.print("Enter Priority: ");
            priority = this.s.nextInt();
            this.s.nextLine();
            if (priority < 1 || priority > 4) {
                throw new TaskException("Invalid Input\n");
            }

            System.out.print("Enter Start Date: ");
            sStartDate = this.s.nextLine();
            startDate = LocalDate.parse(sStartDate);
            if (startDate.isBefore(LocalDate.now())) {
                throw new TaskException("Start Date cannot be before today\n");
            }

            System.out.print("Enter End Date: ");
            sEndDate = this.s.nextLine();
            endDate = LocalDate.parse(sEndDate);
            if (endDate.isBefore(startDate)) {
                throw new TaskException("End Date cannot be before Start Date\n");
            }

            System.out.print("Enter Task Type: ");
            taskType = this.s.nextLine();
            if (taskType.isEmpty()) {
                throw new TaskException("Invalid Input\n");
            }

        } catch (TaskException e) {
            System.out.println(e.getMessage());
            createTask();
        } catch (DateTimeParseException dTE) {
            System.out.println("Invalid format! Format: YYYY-MM-DD\n");
            createTask();
        }

        Task task = new Task(counter, description, 1, startDate, endDate, taskType);
        this.counter++;
        return task;
    }

    public void statusTrack() {
        try{
            int id = getIDInput();
        if(this.taskList.isEmpty()){
            throw new TaskException("There are no tasks\n");
        }else{
        for(Task t : this.taskList){
            if(t.getId() == id && !t.getStatus()){
                System.out.println("Task is not completed\n");
            }
            else if(t.getId() == id && t.getStatus()){
                System.out.println("Task is completed\n");
            }
        }
    }
        }catch(TaskException e){
            System.out.println(e.getMessage());
        }
        
    }

    public void changeStatus(){
        try{
            int id = getIDInput();
            if(this.taskList.isEmpty()){
                throw new TaskException("There are no task\n");
            }else{
            for(Task t : this.taskList){
                if(t.getId() == id){
                    t.setStatus(true);
                    System.out.println("Task is now completed\n");
                }
            }
        }
    }
        catch(TaskException e){
            System.out.println(e.getMessage());
        }
    }

    public void getTask(){
        try{
            int id = getIDInput();
            if(this.taskList.isEmpty()){
                throw new TaskException("There are no task\n");
            }
            else{
                for(Task t : this.taskList){
                    if(t.getId() == id){
                        System.out.println(t);
                    }
                }
            }
        }
        catch(TaskException e){
            System.out.println(e.getMessage());
        }
    }

    public int getIDInput(){
       int id = 0;
        try{
        System.out.print("Enter ID: ");
        id = this.s.nextInt();
        this.s.nextLine();
        if(id < 1){
            throw new TaskException("Invalid Input\n");
        }

        }catch(TaskException e){
            System.out.println(e.getMessage());
        }catch(NumberFormatException nFE){
            System.out.println("Invalid Input\n");
        }
        
        return id;
    }

    public void todayList(){
        try{
            if(this.taskList.isEmpty()){
                throw new TaskException("There are no tasks");
            }
            LocalDate now = LocalDate.now();
            for(Task t : this.taskList){
                if((now.isEqual(t.getStartDate()) || now.isAfter(t.getStartDate())) && (now.isEqual(t.getEndDate()) || now.isBefore(t.getEndDate()))){
                    System.out.println(t);
                }
            }            
        }
        catch(TaskException e){
            System.out.println(e.getMessage());
        }
    }

    public void modifyTask(){
        System.out.println("Choose Task to Modify:\n");
        int chosenTaskId = -1;
        chosenTaskId = getIDInput();
       
        System.out.println("Choose Attribute to Modify:\n");
        int chosenAttr = 0;
        do{
            this.displayModifyParams();
            chosenAttr = this.s.nextInt();
            this.s.nextLine();
            modifyAttr(chosenTaskId, chosenAttr);
        }while(chosenAttr!=5);
    }

    public void displayModifyParams(){
        System.out.println("1. Start Date");
        System.out.println("2. End Date");
        System.out.println("3. Task Type");
        System.out.println("4. Exit");
        System.out.print("Enter Choice: ");
    }

    public void modifyAttr(int taskId, int attr){
        switch(attr){
        case 1 : changeStartDate(taskId);
                break;
        case 2 : changeEndDate(taskId);
                break;
        case 3 : changeTaskType(taskId);
                break;
        case 4: changePriority(taskId);
                break;
        case 5 : System.out.println("EXIT");
                break;
        default : System.out.println("Invalid Choice");
        }
    }

    public void changePriority(int taskId){
        int newPriority = 0;
        do{
            System.out.println("Current Task Priority: " + this.getTask(taskId).getPriority());
            System.out.println("Input new Task Priority: ");
            newPriority = this.s.nextInt();
        }while(newPriority > 4 || newPriority < 1);
        this.getTask(taskId).setPriority(newPriority);
    }

    public void changeTaskType(int taskId){
        String newType = new String();
        do{
            System.out.println("Current Task Type: " + this.getTask(taskId).getTaskType());
            System.out.println("Input new Task Type: ");
            newType = this.s.next();
        }while(newType == null);
        this.getTask(taskId).setTaskType(newType);
    }

    public void changeStartDate(int taskId){
        String newDate = new String();
        do{
            System.out.println("Current Start Date: " + this.getTask(taskId).getStartDate());
            System.out.println("Input new Start Date (YYYY-MM-DD): ");
            newDate = this.s.next();
        }while(!validDateString(newDate) || !LocalDate.parse(newDate).isBefore(this.getTask(taskId).getEndDate()));
        this.getTask(taskId).setStartDate(LocalDate.parse(newDate));
    }
    
    public void changeEndDate(int taskId){
        String newDate = new String();
        do{
            System.out.println("Current End Date: " + this.getTask(taskId).getEndDate());
            System.out.println("Input new End Date (YYYY-MM-DD): ");
            newDate = this.s.next();
        }while(!validDateString(newDate) || !LocalDate.parse(newDate).isAfter(this.getTask(taskId).getStartDate()));
        this.getTask(taskId).setEndDate(LocalDate.parse(newDate));
    }

    public boolean validDateString(String dateString){
        try{
            LocalDate testDate = LocalDate.parse(dateString);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public Task getTask(int taskId){
        for(int i = 0; i < this.taskList.size(); i++){
            if(this.taskList.get(i).getId() == taskId){
                return this.taskList.get(i);
            }
        }
        return null;
    }
    public static void main(String[] args) {
        ToDoList tdl = new ToDoList();
    }
}