
package assignment;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Assignment extends TimerTask {
    
    ArrayList<Server> SList = new ArrayList<>();
    ArrayList<Caller> Q = new ArrayList<>();
    Random rand = new Random();
    
    int counter = 7,
    //time = 60,        
    time = rand.nextInt(301) + 300,       
    initial = 0;
    
    public static void main(String[] args)  {
      
        
       TimerTask task = new Assignment();
       Timer timer = new Timer();
       
       //Change back to 1000 later
       timer.scheduleAtFixedRate(task,0,100);
       
      
    }
    
    public void ServerNo(){
        
        if(initial == 0){
            
            Server s = new Server();
            SList.add(s);
           
        }
        
        
    }
    
    
    @Override
    public void run() {
        
        //Add Servers
        ServerNo();
        
        //Duration program is allowed to run
        Time();
        
        //Check if Server is empty
        if (SList.get(0).Server.isEmpty() == false){
            
            Arrival();
            
            //Subtract Service Time for a max of 7 seconds
            if (counter > 0){
               
                //Check if there is remainder Service Time
                if(SList.get(0).getCall().getServTime() > 1){
                    
                    SList.get(0).getCall().setServTime();
                    counter--;
                    
                }
                
                //Remove Call from server when Service Time ends and retrieve new call
                else{
                   
                    counter = 7;
                    SList.get(0).setRemoveCall();
                    
                    if(Q.size() > 0){
                        SList.get(0).setAddCall(Q.get(0));
                        Q.remove(0);
                    }
                    
                }
               
            }
            
            //Remove call from Server and place in queue after 7 seconds
            else{
                
                Q.add(SList.get(0).getCall());
                SList.get(0).setRemoveCall();
                if(Q.size() > 0){
                    SList.get(0).setAddCall(Q.get(0));
                    Q.remove(0);
                }
                counter = 7;
                
            }
           
        }
       
        //Add call from Queue if Server is empty 
        else if (Q.size() > 0) {
          
          SList.get(0).setAddCall(Q.get(0));
          Q.remove(0);
           
        }
        
        //Add call directly into Server if Q and Server is empty (for the first run)
        else {
      
            Arrival();
                  
        }
        
        //Print output every interval (1 sec)
        Printout();
        
    }
    
    public void Arrival(){
        
       int chance;
       Caller c = new Caller();
      
       //Set random CallID and Service Time via Caller class
        c.setCallID();
        c.setRServTime();
        
       chance = rand.nextInt(2);
       //chance = 1;
       
       if (chance == 1){
          
          if(SList.get(0).Server.isEmpty() == false){
              
              Q.add(c);
              
          }
          
          else {
              
              SList.get(0).setAddCall(c);
              
          }
          
       }
       
        
    }
    
    public void Time(){
       
        if (time != 0){
        
            time --;
            
        }
        else{
            
            System.out.println(":( The program has ran out of time to run");
            System.exit(0);
            
        }
        
    }
    
    public void Printout (){
       
       int x = 0;
       String ServC, ServT;
       
        if (SList.get(0).Server.size() > 0){
          
           ServC = SList.get(0).getCall().toString(); //CallID
           ServT = Integer.toString(SList.get(0).getCall().getServTime()); //Call Service Time
           
        }
       
        else{
           //Show nothing if there is no Call in Server
           ServC = "";
           ServT= "";
           
        }
       
        System.out.println("Time Remaining: " + time + " seconds");
        System.out.println("--------------------");
        System.out.println("Server: \n" + ServC + " | " + ServT);
        System.out.println("----------");
        System.out.println("Queue: ");
       
        if (Q.size() > 0){
           
            while(x < Q.size()){

                System.out.println(Q.get(x));
                x++;

            }
        }
        
    }
    
}
