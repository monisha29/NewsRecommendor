package com.example.admin.newsbytes;

/**
 * Created by Admin on 4/19/2018.
 */

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Admin on 4/18/2018.
 */

public class TMessage  implements Serializable{

    // private static final long serialVersionUID = 5950169519310163575L;
    private static final long serialVersionUID = 20150901L;
    public  int TYPE;
    public  int ID;
    public List<String> newsletter;
    public  String username;
    public  String  password;
}
