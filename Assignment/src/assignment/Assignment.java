
package assignment;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Assignment extends TimerTask {
    
    ArrayList<Caller> Server = new ArrayList<>();
    ArrayList<Caller> Q = new ArrayList<>();
    Random rand = new Random();
    
    int counter = 7, 
    time = rand.nextInt(301) + 300,       
    initial = 0;
    
    public static void main(String[] args)  {
      
        
       TimerTask task = new Assignment();
       Timer timer = new Timer();
       
       
       //Change back to 1000 later
       timer.scheduleAtFixedRate(task,0,100);
       
      
    }
    
    @Override
    public void run() {
       
        //Duration program is allowed to run
        Time();
        
        //Caller c = new Caller();
        
        /*//Set random CallID and Service Time via Caller class
        c.setCallID();
        c.setRServTime();*/
        
        //Check if Server is empty
        if (Server.isEmpty() == false){
                     
            /*if (y>0){
                Q.add(c);
                y--;
            }*/
            
            Arrival();
           
            //Subtract Service Time for a max of 7 seconds
            if (counter > 0){
               
                //Check if there is remainder Service Time
                if(Server.get(0).getServTime() > 1){
                    
                    Server.get(0).setServTime();
                    counter--;
                    
                }
                
                //Remove Call from server when Service Time ends and retrieve new call
                else{
                   
                    counter = 7;
                    Server.remove(0);
                    
                    if(Q.size() > 0){
                        Server.add(Q.get(0));
                        Q.remove(0);
                    }
                    
                }
               
            }
            
            //Remove call from Server and place in queue after 7 seconds
            else{
                
                Q.add(Server.get(0));
                Server.remove(0);
                if(Q.size() > 0){
                    Server.add(Q.get(0));
                    Q.remove(0);
                }
                counter = 7;
                
            }
           
        }
       
        //Add call from Queue if Server is empty 
        else if (Q.size() > 0) {
          
          Server.add(Q.get(0));
          Q.remove(0);
           
        }
        
        //Add call directly into Server if Q and Server is empty (for the first run)
        else {
      
            /*if(z > 0){
                Server.add(c);
                z--;
            }*/
            Arrival();
                  
        }
        
        Printout();
        /*//Print if there is any calls in the Server or Queue
        if(Server.size() + Q.size() > 0){
            printout();
        }
        
        else{
            
            System.out.println("Done!");
            System.exit(0);
            
        }*/
        
    }
    
    
    //int y = 3, z = 1;
    public void Arrival(){
        
       int chance;
       Caller c = new Caller();
      
       //Set random CallID and Service Time via Caller class
        c.setCallID();
        c.setRServTime();
        
       chance = rand.nextInt(2);
       //chance = 1;
       
       if (chance == 1){
          
          if(Server.isEmpty() == false){
              
              /*if (y>0){
                Q.add(c);
                y--;
              }*/
              Q.add(c);
              
          }
          
          else {
              
              /*if(z > 0){
                Server.add(c);
                z--;
              }*/
              Server.add(c);
              
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
       
        if (Server.size() > 0){
          
           ServC = Server.get(0).toString();
           ServT = Integer.toString(Server.get(0).getServTime());
           
        }
       
        else{
           
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
