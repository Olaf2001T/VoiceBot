package com.auth0.example.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "auth0_id")
    private String authId;
    @Column(name = "last_send_messege")
    private String lastSendMessege;
    @Column(name = "last_received_messege")
    private String lastReceivedMessege;


    public User(String authId, String lastSendMessege, String lastReceivedMessege) {
        this.authId = authId;
        this.lastSendMessege = lastSendMessege;
        this.lastReceivedMessege = lastReceivedMessege;
    }

    public User() {

    }


    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getLastSendMessege() {
        return lastSendMessege;
    }

    public void setLastSendMessege(String lastSendMessege) {
        this.lastSendMessege = lastSendMessege;
    }

    public String getLastReceivedMessege() {
        return lastReceivedMessege;
    }

    public void setLastReceivedMessege(String lastReceivedMessege) {
        this.lastReceivedMessege = lastReceivedMessege;
    }
}
