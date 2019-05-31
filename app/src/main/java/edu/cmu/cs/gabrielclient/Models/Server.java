package edu.cmu.cs.gabrielclient.Models;

import java.io.Serializable;

public class Server implements Serializable {
    private String servername;
    private String serverAddresse;

    public Server(String servername, String serverAddresse) {
        this.servername = servername;
        this.serverAddresse = serverAddresse;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getServerAddresse() {
        return serverAddresse;
    }

    public void setServerAddresse(String serverAddresse) {
        this.serverAddresse = serverAddresse;
    }
}
