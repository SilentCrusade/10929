package assignment;

import java.util.ArrayList;

public class Server {
    ArrayList<Caller> Server = new ArrayList();

    public Server() {
    }

    public void setAddCall(Caller Caller) {
        this.Server.add(Caller);
    }

    public void setRemoveCall() {
        this.Server.remove(0);
    }

    public Caller getCall() {
        return (Caller)this.Server.get(0);
    }
}
