package assignment;

import java.util.Random;

public class Caller {
    Random rand = new Random();
    int CallID;
    int ServTime;
    int TimeSlice;
    String Callname;
    int attempts = 0;

    public Caller() { //Caller class constructor
    }
    
    //Overrides name of object to CallID
    @Override
    public String toString() {
        this.Callname = Integer.toString(CallID);
        return this.Callname;
     }
    
    //Assigns random CallID
    public void setCallID() {
        this.CallID = rand.nextInt(5000) + 1000;
    }

    //Get CallID
    public int getCallID() {
        return this.CallID;
    }

    //Get Service Time
    public int getServTime() {
        return this.ServTime;
    }

    //Assigns random Service Time
    public void setRServTime() {
        this.ServTime = rand.nextInt(14) + 3;
    }
    
    //Decrements Service Time (When call is being processed)
    public void setServTime() {  
        this.ServTime--;  
    }
    
    //Returns the time slice of a call
    public int getTimeSlice() {
        return this.TimeSlice;
    }
    
    //Decrements the time slice
    public void runTimeSlice() {
        this.TimeSlice--;
    }

    //Resets the time slice of a call
    public void resetTimeSlice() {
        this.TimeSlice = 7; 
    }

    //Increments the number of attempts the call takes.
    public void setAttempts(){
        this.attempts++;
    }

    //getter method to get the number of attempts of the call.
    public int getAttempts(){
        return this.attempts;
    }
}
