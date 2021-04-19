package com.example.amtsdemo.model;

public class PassStudent {
    int uid,sid,cid;
    String Name,Email,BirthDate,MobileNo,CollageName,Department,year,Source,Destination,StartDate,EndDate,status,Enrollment;

    public PassStudent(int uid, int sid, int cid, String name, String email, String birthDate, String mobileNo, String collageName, String department, String Enrollment ,String year, String source, String destination, String startDate, String endDate) {
        this.uid = uid;
        this.sid = sid;
        this.cid = cid;
        Name = name;
        Email = email;
        BirthDate = birthDate;
        MobileNo = mobileNo;
        CollageName = collageName;
        Department = department;
        Enrollment = this.Enrollment;
        this.year = year;
        Source = source;
        Destination = destination;
        StartDate = startDate;
        EndDate = endDate;

    }

    public String getEnrollment() {
        return Enrollment;
    }

    public void setEnrollment(String enrollment) {
        Enrollment = enrollment;
    }

    public PassStudent() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getCollageName() {
        return CollageName;
    }

    public void setCollageName(String collageName) {
        CollageName = collageName;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
