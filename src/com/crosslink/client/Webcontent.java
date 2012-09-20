package com.crosslink.client;

import java.io.Serializable;

public class Webcontent implements Serializable {
 
   private static final long serialVersionUID = 1L;
   private String webcontent;
   public Webcontent(){};

   public void setwebContent(String webcontent) {
      this.webcontent = webcontent;
   }

   public String getwebContent() {
      return webcontent;
   }
}