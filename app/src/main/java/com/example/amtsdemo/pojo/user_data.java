package com.example.amtsdemo.pojo;

import java.util.List;

    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString), Root.class); */

public class user_data{
    public boolean error;
    public String message;
    public List<Datum> data;
   // public List<user_login> uLogin;
}





