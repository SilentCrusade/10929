package assignment;

import java.util.ArrayList;
    
public class Server{
    int CallCount = 0;
    ArrayList<Caller> Server = new ArrayList<>();
    
    public Server() { //Server class constructor
    }
    
    public void setAddCall(Caller Caller) { //Adds call
        Server.add(Caller);    
    }
    
    public void setRemoveCall(){ //Removes call
        Server.remove(0);
    }
    
    public Caller getCall(){ //Get call
        return Server.get(0);      
    }
    
    public int getCallCount() { //Returns the amount of calls processed
        return CallCount;
    }
    
    public void incCallCount() { //Increments the amount of calls processed by 1
        this.CallCount++;
    }
    
}