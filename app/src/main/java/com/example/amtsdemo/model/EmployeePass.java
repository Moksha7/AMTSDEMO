package com.example.amtsdemo.model;

public class EmployeePass {
    int eid;
    private String ename,eEmail,eMobile,eBirthdate,eGender,eJobProfile,eJobAddress,eSource,eDestination,eStartDate,eEndDate;

    public EmployeePass() {
    }

    public EmployeePass(int eid, String ename, String eEmail, String eMobile, String eBirthdate, String eGender, String eJobProfile, String eJobAddress, String eSource, String eDestination, String eStartDate, String eEndDate) {
        this.eid = eid;
        this.ename = ename;
        this.eEmail = eEmail;
        this.eMobile = eMobile;
        this.eBirthdate = eBirthdate;
        this.eGender = eGender;
        this.eJobProfile = eJobProfile;
        this.eJobAddress = eJobAddress;
        this.eSource = eSource;
        this.eDestination = eDestination;
        this.eStartDate = eStartDate;
        this.eEndDate = eEndDate;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String geteEmail() {
        return eEmail;
    }

    public void seteEmail(String eEmail) {
        this.eEmail = eEmail;
    }

    public String geteMobile() {
        return eMobile;
    }

    public void seteMobile(String eMobile) {
        this.eMobile = eMobile;
    }

    public String geteBirthdate() {
        return eBirthdate;
    }

    public void seteBirthdate(String eBirthdate) {
        this.eBirthdate = eBirthdate;
    }

    public String geteGender() {
        return eGender;
    }

    public void seteGender(String eGender) {
        this.eGender = eGender;
    }

    public String geteJobProfile() {
        return eJobProfile;
    }

    public void seteJobProfile(String eJobProfile) {
        this.eJobProfile = eJobProfile;
    }

    public String geteJobAddress() {
        return eJobAddress;
    }

    public void seteJobAddress(String eJobAddress) {
        this.eJobAddress = eJobAddress;
    }

    public String geteSource() {
        return eSource;
    }

    public void seteSource(String eSource) {
        this.eSource = eSource;
    }

    public String geteDestination() {
        return eDestination;
    }

    public void seteDestination(String eDestination) {
        this.eDestination = eDestination;
    }

    public String geteStartDate() {
        return eStartDate;
    }

    public void seteStartDate(String eStartDate) {
        this.eStartDate = eStartDate;
    }

    public String geteEndDate() {
        return eEndDate;
    }

    public void seteEndDate(String eEndDate) {
        this.eEndDate = eEndDate;
    }
}
