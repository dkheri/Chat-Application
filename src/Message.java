<<<<<<< HEAD
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
public class Message implements Serializable{
    public final String mType;
    public final String recipent;
    public final String destFor;
    public final String message;
    
    
//    private File filetoSend;
    
    public Message(String mType, String recipent, String destFor, String message) {
        this.mType = mType;
        this.recipent = recipent;
        this.destFor = destFor;
        this.message = message;
    }

    
}
=======
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
public class Message implements Serializable{
    public final String mType;
    public final String recipent;
    public final String destFor;
    public final String message;
    
    
//    private File filetoSend;

    public Message(String mType, String recipent, String sender, String message) {
        this.mType = mType;
        this.recipent = recipent;
        this.destFor = destFor;
        this.message = message;
    }

    public Message(String mType,String message)
    {
        this.mType = mType;
        this.recipent = null;
        this.destFor = null
        this.message = message;

    }

    
}
>>>>>>> de880564ea2d66c713dbd7b12e30d171ded7484b
