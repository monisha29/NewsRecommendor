package com.example.admin.newsbytes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/18/2018.
 */

public class Newsletter implements Serializable {
    //News news;
	public int userid;
    public List<News> news;
    public List<Integer> listofrecommendedindexes;
    public Newsletter()
    { news= new ArrayList<>();
    listofrecommendedindexes= new ArrayList<>();
    }




}