package com.example.amtsdemo.pojo;

import com.google.android.gms.tasks.Task;

public class StudentPassHelper {
    private String sCategory,sCollage,sDepartment,sEnrollmentNo,sYear,sSource,sDestination,sStartingDate,sEndDate,sStatus,aStatus;
    private int sId;
    private int cId;
    Task<Void> cid;
    Task<Void> sid;


    public StudentPassHelper(String not_valid, String not_valid1, String student, Task<Void> cid,  String strEndDate, String strEnrollmentNo, String strSource, String strStartDate, String strYear ,String strDepartment, String strDestination,Task<Void> sid) {
        sCategory = student;
//        this.sCollage = sCollage;
        sDepartment = strDepartment;
        sEnrollmentNo = strEnrollmentNo;
        sYear = strYear;
        sSource = strSource;
        sDestination = strDestination;
        sStartingDate = strStartDate;
        sEndDate = strEndDate;
        sStatus = not_valid1;
        aStatus = not_valid;
        this.cid = cid;
        this.sid = sid;

    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public StudentPassHelper() {
    }


    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public String getsCategory() {
        return sCategory;
    }

    public void setsCategory(String sCategory) {
        this.sCategory = sCategory;
    }

    public String getsCollage() {
        return sCollage;
    }

    public void setsCollage(String sCollage) {
        this.sCollage = sCollage;
    }

    public String getsDepartment() {
        return sDepartment;
    }

    public void setsDepartment(String sDepartment) {
        this.sDepartment = sDepartment;
    }

    public String getsEnrollmentNo() {
        return sEnrollmentNo;
    }

    public void setsEnrollmentNo(String sEnrollmentNo) {
        this.sEnrollmentNo = sEnrollmentNo;
    }

    public String getsYear() {
        return sYear;
    }

    public void setsYear(String sYear) {
        this.sYear = sYear;
    }

    public String getsSource() {
        return sSource;
    }

    public void setsSource(String sSource) {
        this.sSource = sSource;
    }

    public String getsDestination() {
        return sDestination;
    }

    public void setsDestination(String sDestination) {
        this.sDestination = sDestination;
    }

    public String getsStartingDate() {
        return sStartingDate;
    }

    public void setsStartingDate(String sStartingDate) {
        this.sStartingDate = sStartingDate;
    }

    public String getsEndDate() {
        return sEndDate;
    }

    public void setsEndDate(String sEndDate) {
        this.sEndDate = sEndDate;
    }

    public String getsStatus() {
        return sStatus;
    }

    public void setsStatus(String sStatus) {
        this.sStatus = sStatus;
    }
}
