package com.example.amtsdemo.pojo;

public class CollageHelper {
    int id;
    String cName;

    public CollageHelper() {
    }

    public CollageHelper(String cName) {
        //this.id = id;
        this.cName = cName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
}
