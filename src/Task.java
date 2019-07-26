import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Task {
    private int id;
    private String description;
    private int priority;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean status;
    private String taskType;

    public Task(int id, String description, int priority, LocalDate startDate, LocalDate endDate, String taskType) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = false;
        this.taskType = taskType;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public int getPriority(){
        return this.priority;
    }

    public LocalDate getStartDate(){
        return this.startDate;
    }

    public LocalDate getEndDate(){
        return this.endDate;
    }

    public boolean getStatus(){
        return this.status;
    }

    public String getTaskType(){
        return this.taskType;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public void setTaskType(String taskType){
        this.taskType = taskType;
    }

    public String toString(){
        String status = "";
        if(this.status){
            status = "Completed";
        }
        else{
            status = "Ongoing";
        }
        return "ID: " + this.id + "\nDescription: " + this.description + "\nPriority: " + this.priority + "\nStart Date: "
        + this.startDate.toString() + "\nEnd Date: " + this.endDate.toString() + "\nStatus: " + status + "\nTask Type: " + this.taskType +"\n";
    }
}
