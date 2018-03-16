/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;

/**
 *
 * @author Meluleki
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    public final String mType;
    public final String recipent;
    public final String sender;
    public final String message;
    public final Object obMessage;

//    private File filetoSend;
    public Message(String mType, String recipent, String sender, String message) {
        this.mType = mType;
        this.recipent = recipent;
        this.sender = sender;
        obMessage = null;
        this.message = message;
    }

    public Message(String mt, String recipent, String sender, Object o) {
        this.mType = mt;
        this.recipent = recipent;
        this.sender = sender;
        this.message = null;
        this.obMessage = o;
    }

    public Message(String mType, String message) {
        this.mType = mType;
        this.recipent = null;
        this.sender = null;
        obMessage = null;
        this.message = message;

    }

    @Override
    public String toString() {
       return this.mType+" "+this.recipent+" "+this.sender+" "+this.message;
    }
    

}
