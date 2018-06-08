package assignment;

import java.util.*;

public class Assignment extends TimerTask {
    ArrayList<Server> SList = new ArrayList<>();
    ArrayList<Caller> Q = new ArrayList<>();
    Random rand = new Random();
    
    public static int amount = 0;
    int //amount = 0, //amount of servers
    //time = 60,
    time = rand.nextInt(301) + 300;    //Duration of program execution   
    boolean initial = false;
    
    public static void main(String[] args)  {
        boolean valid = false;
        
        Scanner sc = new Scanner(System.in);
        TimerTask task = new Assignment();
        Timer timer = new Timer();
        
        while (!valid){
            System.out.print("Please specify the number of servers (1 - 99): ");
            
            try {
                amount = sc.nextInt();
            }
            catch (Exception e){
                System.out.println("Invalid input. Try again.");
                sc.next();
                continue;
            }
            
            if ((amount < 100)&&(amount > 0))
                valid = true;
            
            else
                System.out.println("Invalid amount of servers. Try again.");
        }
        //Change back to 1000 later
        timer.scheduleAtFixedRate(task,0,1000);
       
    }
    
    public void ServerNo(){
        if(!initial){
            
            //System.out.print("Specify amount of server: ");
            //amount = sc.nextInt();
        
            for(int k = 0; k < amount; k++){
                Server s = new Server();
                SList.add(s);
            }
            initial = true;
        }
        
    }
    
    
    @Override
    public void run() {
        //Add Servers
        ServerNo();
        
        //Duration program is allowed to run
        Time();
        Arrival();
        for(int n = 0; n < SList.size(); n++){
            //Check if Server is empty
            if (SList.get(n).Server.isEmpty() == false){

                //Subtract Service Time for a max of 7 seconds
                if (SList.get(n).getCall().getTimeSlice() > 0){

                    //Check if there is remainder Service Time
                    if(SList.get(n).getCall().getServTime() > 1){

                        SList.get(n).getCall().setServTime();
                        
                        SList.get(n).getCall().runTimeSlice();

                    }

                    //Remove Call from server when Service Time ends and retrieve new call
                    else{

                        SList.get(n).setRemoveCall();

                        if(Q.size() > 0){
                            SList.get(n).setAddCall(Q.get(0));
                            Q.remove(0);
                        }

                    }

                }

                //Remove call from Server and place in queue after 7 seconds
                else{

                    Q.add(SList.get(n).getCall());
                    SList.get(n).setRemoveCall();
                    if(Q.size() > 0){
                        SList.get(n).setAddCall(Q.get(0));
                        Q.remove(0);
                    }
                    SList.get(n).getCall().resetTimeSlice();

                }

            }

            //Add call from Queue if Server is empty 
            else if (Q.size() > 0) {

                SList.get(n).setAddCall(Q.get(0));
                Q.remove(0);

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
        c.resetTimeSlice();
       
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
        System.out.println("|   Name    |  ID  | Time Left(s) |");
        
        
        for(int i = 0; i < SList.size(); i++){
            if (SList.get(i).Server.size() > 0){

                //Assigns variables to current Call ID and corresponding Service Time
                ServC = SList.get(i).getCall().toString(); //CallID
                ServT = String.format("%02d", SList.get(i).getCall().getServTime());

            }

            else{
                //Show nothing if there is no Call in Server
                ServC = "    ";
                ServT= "  ";

            }
                
            System.out.println("| Server " + String.format("%02d", i+1) + " | " + ServC + " |      " + ServT + "      |");
                
        }
        
        
        System.out.println("----------------------------------");
        System.out.println("Queue: ");
        
        //Print calls present in the Queue
        if (Q.size() > 0){
           
            while(x < Q.size()){

                System.out.println(Q.get(x) + " | " + Q.get(x).getServTime());
                x++;

            }
        }
        
        else
            System.out.println("Empty");
    }
    
}