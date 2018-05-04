package com.example.admin.newsbytes;

import java.io.Serializable;

/**
 * Created by Admin on 4/20/2018.
 */

public class Messenger implements Serializable{
    public String header;
    public String type;
    public Object object;
    public Messenger()
    {
        object = new Object();
    }
}
