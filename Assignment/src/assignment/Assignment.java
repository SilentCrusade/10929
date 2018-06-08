package assignment;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Assignment extends TimerTask {
    ArrayList<Server> serverList = new ArrayList();
    ArrayList<Caller> queue = new ArrayList();
    Random rand = new Random();
    public static int amount = 0;
    int time;
    boolean initial;
    public static int calls_processed = 0, calls_count = 0, first_attempt = 0, second_attempt = 0, third_attempt = 0, attempt;


    public Assignment() {
        this.time = this.rand.nextInt(301) /*+ 300*/;
        this.initial = false;
    }

    public static void main(String[] args) {
        boolean valid = false;
        Scanner sc = new Scanner(System.in);
        TimerTask task = new Assignment();
        Timer timer = new Timer();

        while(!valid) {
            System.out.print("Please specify the number of servers (1 - 99): ");

            try {
                amount = sc.nextInt();
            } catch (Exception var6) {
                System.out.println("Invalid input. Try again.");
                sc.next();
                continue;
            }

            if (amount < 100 && amount > 0) {
                valid = true;
            } else {
                System.out.println("Invalid amount of servers. Try again.");
            }
        }

        timer.scheduleAtFixedRate(task, 0L, 100L);
    }

    public void ServerManagement() {
        if (!this.initial) {
            for(int k = 0; k < amount; ++k) {
                Server s = new Server();
                this.serverList.add(s);
            }

            this.initial = true;
        }

    }

    public void run() {
        this.ServerManagement();
        this.Time();
        this.Generator();
        calls_count++;
        for(int n = 0; n < this.serverList.size(); ++n) {
            if (!((Server)this.serverList.get(n)).Server.isEmpty()) {
                if (((Server)this.serverList.get(n)).getCall().getTimeSlice() > 0) {
                    if (((Server)this.serverList.get(n)).getCall().getServTime() > 1) {
                        ((Server)this.serverList.get(n)).getCall().setServTime();
                        ((Server)this.serverList.get(n)).getCall().runTimeSlice();
                    } else {
                        ((Server)this.serverList.get(n)).getCall().setAttempts();      //<-- added by J.
                        ((Server)this.serverList.get(n)).setRemoveCall();
                        if (this.queue.size() > 0) {
                            ((Server)this.serverList.get(n)).setAddCall((Caller)this.queue.get(0));
                            calls_processed++;      //<-- added by J.
                            this.queue.remove(0);
                        }
                    }
                } else {
                    attempt = ((Server)this.serverList.get(n)).getCall().getAttempts();
                    this.queue.add(((Server)this.serverList.get(n)).getCall());
                    ((Server)this.serverList.get(n)).setRemoveCall();
                    if(attempt == 1){
                        first_attempt++;
                    }
                    else if(attempt == 2){
                        second_attempt++;
                    }
                    else if(attempt == 3){
                        third_attempt++;
                    }

                    if (this.queue.size() > 0) {
                        ((Server)this.serverList.get(n)).setAddCall((Caller)this.queue.get(0));
                        calls_processed++;
                        this.queue.remove(0);
                    }

                    ((Server)this.serverList.get(n)).getCall().resetTimeSlice();
                }
            } else if (this.queue.size() > 0) {
                ((Server)this.serverList.get(n)).setAddCall((Caller)this.queue.get(0));
                this.queue.remove(0);
                ((Server)this.serverList.get(n)).getCall().setAttempts();
                calls_processed++;
            }
        }

        this.Output();
    }

    public void Generator() {
        Caller c = new Caller();
        c.setCallID();
        c.setRServTime();
        c.resetTimeSlice();
        int chance = this.rand.nextInt(3);
        if (chance == 1) {
            int m = 0;
            if (m < this.serverList.size()) {
                if (!((Server)this.serverList.get(m)).Server.isEmpty()) {
                    this.queue.add(c);
                } else {
                    ((Server)this.serverList.get(m)).setAddCall(c);
                }
            }
        }

    }

    public void Time() {
        if (this.time != 0) {
            --this.time;
        } else {
            System.out.println(":( The program has ran out of time to run");
            Details();
            System.exit(0);
        }

    }

    public void Output() {
        int x = 0;
        System.out.println("\nTime Remaining: " + this.time + " seconds");
        System.out.println("----------------------------------");
        System.out.println("|   Name    |  ID  | Time Left(s) |");

        for(int i = 0; i < this.serverList.size(); ++i) {
            String ServC;
            String ServT;
            if (((Server)this.serverList.get(i)).Server.size() > 0) {
                ServC = ((Server)this.serverList.get(i)).getCall().toString();
                ServT = String.format("%02d", ((Server)this.serverList.get(i)).getCall().getServTime());
            } else {
                ServC = "    ";
                ServT = "  ";
            }

            System.out.println("| Server " + String.format("%02d", i + 1) + " | " + ServC + " |      " + ServT + "      |");
        }

        System.out.println("----------------------------------");
        System.out.println("Queue: ");
        if (this.queue.size() > 0) {
            while(x < this.queue.size()) {
                System.out.println(this.queue.get(x) + " | " + ((Caller)this.queue.get(x)).getServTime());
                ++x;
            }
        } else {
            System.out.println("Empty");
        }

    }

    public void Details() {
        if (time == 0) {
            System.out.print("------------------------------------------------------------------");
            System.out.println("\nDetails ");
            System.out.println("The total number of calls processed: " + calls_processed);
            System.out.println("Average number of calls processed per minute: " + (calls_processed / 60.0));
            System.out.println("The average arrival rate per minute: " + (double) (calls_count / 60));
            System.out.println("The number of calls processed on the first attempt (one time): " + first_attempt);
            System.out.println("The number of calls had to be requeued once (require 2 times) " + second_attempt);
            System.out.println("The number of calls had to be requeued twice (3 times): " + third_attempt);
            System.out.print("------------------------------------------------------------------");
        }
    }
}
