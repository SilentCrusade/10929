package assignment;

import java.util.Random;

public class Caller {
    Random rand = new Random();
    int CallID;
    int ServTime;
    int TimeSlice;
    String Callname;
    int attempts = 0;

    public Caller() {
        this.CallID = this.CallID;
    }

    public String toString() {
        this.Callname = Integer.toString(this.CallID);
        return this.Callname;
    }

    public void setCallID() {
        this.CallID = this.rand.nextInt(5000) + 1000;
    }

    public int getCallID() {
        return this.CallID;
    }

    public int getServTime() {
        return this.ServTime;
    }

    public void setRServTime() {
        this.ServTime = this.rand.nextInt(14) + 3;
    }

    public void setServTime() {
        --this.ServTime;
    }

    public int getTimeSlice() {
        return this.TimeSlice;
    }

    public void runTimeSlice() {
        --this.TimeSlice;
    }

    public void resetTimeSlice() {
        this.TimeSlice = 7;
    }

    public void setAttempts(){
        this.attempts++;
    }

    public int getAttempts(){
        return attempts;
    }
}
