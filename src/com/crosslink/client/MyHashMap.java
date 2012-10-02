package com.crosslink.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class MyHashMap extends HashMap<String, String> implements Serializable { 
    /**
	 * 
	 */
	private static final long serialVersionUID = 2724033765319559518L;
	ArrayList<Object> keys; 
    public MyHashMap(){ 
            super(); 
            keys = new ArrayList<Object>(); 
    } 

    public String put(String key, String value){ 
            if(!keys.contains(key)){ 
                    this.keys.add(key); 
            } 
            return super.put(key, value); 
    } 

    public String remove(Object key){ 
            this.keys.remove(key); 
            return super.remove(key); 
    } 
    public ArrayList<Object> getKeys(){ 
            return this.keys; 
    }
    
  
}