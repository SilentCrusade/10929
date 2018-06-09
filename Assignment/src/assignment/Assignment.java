package assignment;

import java.util.*;

public class Assignment extends TimerTask {
    ArrayList<Server> SList = new ArrayList<>(); //Server List
    ArrayList<Caller> Q = new ArrayList<>(); //Queue List
    Random rand = new Random();
    
    public static int amount; //Amount of servers to be inputted by user
    int time = rand.nextInt(301) + 300; //Random duration of program execution
    boolean initial = false; //For servers to only be instantiated once
    
    public static void main(String[] args)  {
        boolean valid = false; //To be used for user validation
        
        Scanner sc = new Scanner(System.in);
        TimerTask task = new Assignment();
        Timer timer = new Timer();
        
        while (!valid){
            System.out.print("Please specify the number of servers (1 - 99): "); //Prompts user to enter a value
            
            try {
                amount = sc.nextInt(); //Accepts user input
            }
            catch (Exception e){ //If user enters a value other than integers, a message is displayed
                System.out.println("Invalid input. Try again.\n");
                sc.next();
                continue;
            }
            
            if ((amount < 100)&&(amount > 0)) //If user enters a value between 1 and 99, input is valid.
                valid = true;
            
            else
                System.out.println("Invalid amount of servers. Try again.\n");
        }
        System.out.println();
        
        timer.scheduleAtFixedRate(task, 0, 1000); //Autoruns program every 1000 millisecond or 1 second
       
    }
    
    public void ServerNo(){
        if(!initial){
            
            for(int counter = 0; counter < amount; counter++){ //Makes instances of servers
                Server s = new Server();
                SList.add(s);
            }
            initial = true;
        }
        
    }
    
    
    @Override
    public void run() {
        
        ServerNo(); //Instantialise servers
        
        Time(); //Duration program is allowed to run
        
        System.out.println("Event Log:");
        
        Arrival(); //Arrival of calls
        
        for(int counter = 0; counter < SList.size(); counter++){
            //Check if Server is empty
            if (!SList.get(counter).Server.isEmpty()){

                //Subtract Service Time for a max of 7 seconds
                if (SList.get(counter).getCall().getTimeSlice() > 0){

                    //Check if there is remainder Service Time
                    if (SList.get(counter).getCall().getServTime() > 1){

                        SList.get(counter).getCall().setServTime(); //Decrements service time of a call by 1
                        SList.get(counter).getCall().runTimeSlice(); //Decrements the time slice of a call by 1

                    }

                    //Remove Call from server when Service Time ends and retrieve new call
                    else{
                        
                        System.out.println("ID " + SList.get(counter).getCall() + " has completed in Server " + (counter+1) + ".");
                        
                        SList.get(counter).setRemoveCall(); //Removes a call from the server
                        SList.get(counter).incCallCount(); //Increments the amount of calls processed by each server by 1
                        
                        if(Q.size() > 0){
                            SList.get(counter).setAddCall(Q.get(0)); //Adds a call from queue if there is one
                            Q.remove(0); //Removes a call from the front of the queue
                            System.out.println("ID " + SList.get(counter).getCall() + " is assigned to Server " + (counter+1) + ".");
                        }
                        
                    }
                    
                    //Remove call from Server and place in queue after 7 seconds of call time slice
                    if (!SList.get(counter).Server.isEmpty()){
                        
                        if (SList.get(counter).getCall().getTimeSlice() == 0){
                            System.out.println("Time slice of ID " + SList.get(counter).getCall() + " in Server " + (counter+1) + " expired.");

                            SList.get(counter).getCall().resetTimeSlice(); //Resets time slice of the call
                            Q.add(SList.get(counter).getCall()); //Adds current call to the end of the queue
                            SList.get(counter).setRemoveCall(); //Removes call from server
                            if(Q.size() > 0){
                                SList.get(counter).setAddCall(Q.get(0));
                                Q.remove(0);

                                System.out.println("ID " + SList.get(counter).getCall() + " is assigned to Server " + (counter+1) + ".");
                            }
                        }
                        
                    }
                }
                
            }

            //Add call from Queue if Server is empty 
            else if (Q.size() > 0) {

                SList.get(counter).setAddCall(Q.get(0));
                Q.remove(0);
                
                System.out.println("ID " + SList.get(counter).getCall() + " is assigned to Server " + (counter+1) + ".");
                
            }

        }
        //Print output every interval (1 sec)
        Printout();
        
    }
    
    public void Arrival(){
        
        int chance;
       
        //Arrives call at an average of 20 calls/min
        chance = rand.nextInt(3);
       
        if (chance == 1){
            
            Caller c = new Caller(); //Instantiates a caller
      
            //Set random CallID and Service Time via Caller class
            c.setCallID();
            c.setRServTime();
            c.resetTimeSlice();
            
            System.out.println("ID " + c.getCallID() + " has been added to queue.");
            Q.add(c); //Adds call to queue
        
        }
        
    }
    
    public void Time(){
       
        //Counts down duration of program
        if (time != 0){
        
            time--;
            
        }
        else{
            //Exits program if time = 0
            System.out.println("The program has ran out of time to run.");
            
            System.out.println("Total calls proceessed by");
            for(int counter = 0; counter < SList.size(); counter++){
                System.out.println("Server " + (counter+1) + ": " + SList.get(counter).getCallCount());
            }
            
            System.exit(0);
            
        }
        
    }
    
    public void Printout (){
       
        int x = 0;
        String ServC, ServT;
       
        
        //Prints time, Call ID and Service Time that is being processed
        System.out.println("-----------------------------------");
        System.out.println("|   Time Remaining: " + String.format("%03d", time) + " seconds   |");
        System.out.println("-----------------------------------");
        System.out.println("|  Server   |  ID  | Time Left(s) |");
        System.out.println("-----------------------------------");
        
        for(int counter = 0; counter < SList.size(); counter++){
            if (SList.get(counter).Server.size() > 0){

                //Assigns variables to current Call ID and corresponding Service Time
                ServC = SList.get(counter).getCall().toString(); //CallID
                ServT = String.format("%02d", SList.get(counter).getCall().getServTime());

            }

            else{
                //Show nothing if there is no Call in Server
                ServC = "    ";
                ServT= "  ";

            }
                
            System.out.println("| Server " + String.format("%02d", counter+1) + " | " + ServC + " |      " + ServT + "      |");
                
        }
        
        
        System.out.println("-----------------------------------");
        System.out.println("Queue: ");
        
        //Print calls present in the Queue
        if (Q.size() > 0){
           
            while(x < Q.size()){

                System.out.println(Q.get(x) + " | " + String.format("%02d", Q.get(x).getServTime()) + "s");
                x++;

            }
            System.out.println();
        }
        
        else
            System.out.println("Empty\n");
    }
    
}