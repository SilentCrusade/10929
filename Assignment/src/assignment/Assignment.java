package assignment;

import java.util.*;

public class Assignment extends TimerTask {
    Scanner sc = new Scanner(System.in);
    ArrayList<Server> SList = new ArrayList<>();
    ArrayList<Caller> Q = new ArrayList<>();
    Random rand = new Random();
    
    int counter = 7,
    amount = 0, //amount of servers
    //time = 60,        
    time = rand.nextInt(301) + 300,    //Duration of program execution   
    initial = 0;
    
    public static void main(String[] args)  {
      
        TimerTask task = new Assignment();
        Timer timer = new Timer();

        //Change back to 1000 later
        timer.scheduleAtFixedRate(task,0,1000);
       
      
    }
    
    public void ServerNo(){
        if(initial == 0){
            
            System.out.print("Specify amount of server(s): ");
            amount = sc.nextInt();
        
            for(int k = 0; k < amount; k++){
                Server s = new Server();
                SList.add(s);
            }
            initial++;
        }
        
    }
    
    
    @Override
    public void run() {
        //Add Servers
        ServerNo();
        
        //Duration program is allowed to run
        Time();
        Arrival();
        for(int l = 0; l < SList.size(); l++){
            //Check if Server is empty
            if (SList.get(l).Server.isEmpty() == false){

                //Arrival(l);

                //Subtract Service Time for a max of 7 seconds
                if (counter > 0){

                    //Check if there is remainder Service Time
                    if(SList.get(l).getCall().getServTime() > 1){

                        SList.get(l).getCall().setServTime();
                        
                        System.out.println(counter); //Remove
                        counter--;

                    }

                    //Remove Call from server when Service Time ends and retrieve new call
                    else{

                        counter = 7;
                        SList.get(l).setRemoveCall();

                        if(Q.size() > 0){
                            SList.get(l).setAddCall(Q.get(0));
                            Q.remove(0);
                        }

                    }

                }

                //Remove call from Server and place in queue after 7 seconds
                else{

                    Q.add(SList.get(l).getCall());
                    SList.get(l).setRemoveCall();
                    if(Q.size() > 0){
                        SList.get(l).setAddCall(Q.get(0));
                        Q.remove(0);
                    }
                    counter = 7;

                }

            }

            //Add call from Queue if Server is empty 
            else if (Q.size() > 0) {

              SList.get(l).setAddCall(Q.get(0));
              Q.remove(0);

            }

            //Add call directly into Server if Q and Server is empty (for the first run)
            else {

                //Arrival(l);

            }
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
       
        //"Arrives call at an average of 20 calls/min
       chance = rand.nextInt(3);
       //chance = 1;
       
       if (chance == 1){
          
            for(int m = 0; m < SList.size(); m++){
                if(SList.get(m).Server.isEmpty() == false){

                    Q.add(c);
                    break;

                }

                else {

                    SList.get(m).setAddCall(c);
                    break;

                }
            }
          
       }
        
    }
    
    public void Time(){
       
        //Counts down duration of program
        if (time != 0){
        
            time--;
            
        }
        else{
            //Exits program if time = 0
            System.out.println(":( The program has ran out of time to run");
            System.exit(0);
            
        }
        
    }
    
    public void Printout (){
       
       int x = 0;
       String ServC, ServT;
       
        
        
        //Prints time, Call ID and Service Time that is being processed
        System.out.println("\nTime Remaining: " + time + " seconds");
        System.out.println("----------------------------------");
        System.out.println("|   Name   |  ID  | Time Left(s) |");
        
        
        for(int i = 0; i < SList.size(); i++){
            if (SList.get(i).Server.size() > 0){

                //Assigns variables to current Call ID and corresponding Service Time
                ServC = SList.get(i).getCall().toString(); //CallID
                
                if (SList.get(i).getCall().getServTime() < 10){
                    ServT = " " + Integer.toString(SList.get(i).getCall().getServTime());
                }
                else{
                    ServT = Integer.toString(SList.get(i).getCall().getServTime()); //Call Service Time
                }

            }

            else{
                //Show nothing if there is no Call in Server
                ServC = "    ";
                ServT= "  ";

            }
                
            System.out.println("| Server " + (i+1) + " | " + ServC + " |      " + ServT + "      |");
                
        }
        
        
        System.out.println("----------------------------------");
        System.out.println("Queue: ");
        
        //Print calls present in the Queue
        if (Q.size() > 0){
           
            while(x < Q.size()){

                System.out.println(Q.get(x));
                x++;

            }
        }
        
    }
    
}
