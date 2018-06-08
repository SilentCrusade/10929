package assignment;

import java.util.Random;

public class Caller {
    Random rand = new Random();
    int CallID;
    int ServTime;
    int TimeSlice;
    String Callname;

    public Caller() {
        this.CallID = CallID;
    }
    
    //Overrides name of object to CallID
    @Override
    public String toString() {
        Callname = Integer.toString(CallID);
        return Callname;
     }
    
    //Assigns random CallID
    public void setCallID() {
        this.CallID = rand.nextInt(5000) + 1000;
    }

    //Get CallID
    public int getCallID() {
        return CallID;
    }

    //Get Service Time
    public int getServTime() {
        return ServTime;
    }

    //Assigns random Service Time
    public void setRServTime() {
        this.ServTime = rand.nextInt(14) + 3;
    }
    
    //Decrements Service Time (When call is being processed)
    public void setServTime() {  
        this.ServTime = ServTime - 1;  
    }
    
     //
    public int getTimeSlice() {
        return TimeSlice;
    }
    
    //
    public void runTimeSlice() {
        this.TimeSlice = TimeSlice - 1;
    }

    //
    public void resetTimeSlice() {
        this.TimeSlice = 7; 
    }

    
}