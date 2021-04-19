package com.example.amtsdemo.pojo;

public class UserHelper {
    private String Name,Email,Mobile,Gender,BirthDate,PassWord,Category;
    private int Id;

    public UserHelper() {
    }

    public UserHelper(String name, String email, String mobile) {
        Name = name;
        Email = email;
        Mobile = mobile;
    }

    public  UserHelper(String birthDate,String category,String email,String gender,String mobile,String name,String passWord){
        Name = name;
        Email = email;
        Mobile = mobile;
        Gender = gender;
        BirthDate = birthDate;
        PassWord = passWord;
        Category = category;
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

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
