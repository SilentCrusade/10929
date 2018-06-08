package assignment;

import java.util.ArrayList;
    
public class Server{
   
    ArrayList<Caller> Server = new ArrayList<>();
    
    public Server() {
       
    }
   
    
    public void setAddCall(Caller Caller) {
       
       Server.add(Caller);
               
    }
    
    public void setRemoveCall(){
        
        Server.remove(0);
        
    }
    
    public Caller getCall(){
        
        return Server.get(0);
                
    }
   
}
