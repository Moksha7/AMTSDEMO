package com.example.amtsdemo.Api;


import com.example.amtsdemo.pojo.Collage_Select;
import com.example.amtsdemo.pojo.Employee_Select;
import com.example.amtsdemo.pojo.Employee_Show;
import com.example.amtsdemo.pojo.Employee_Status;
import com.example.amtsdemo.pojo.FindCollage;
import com.example.amtsdemo.pojo.Image;
import com.example.amtsdemo.pojo.Pass_Insert;
import com.example.amtsdemo.pojo.Root;
import com.example.amtsdemo.pojo.SeniorCitizen_Select;
import com.example.amtsdemo.pojo.SeniorCitizen_Show;
import com.example.amtsdemo.pojo.SeniorCitizen_Status;
import com.example.amtsdemo.pojo.Student_Show;
import com.example.amtsdemo.pojo.Student_Status;
import com.example.amtsdemo.pojo.User_Insert;
import com.example.amtsdemo.pojo.User_List;
import com.example.amtsdemo.pojo.User_Show;
import com.example.amtsdemo.pojo.ViewImageData;
import com.example.amtsdemo.pojo.user_data;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface InterPrater {

    @GET("/AMTSDB/getuser.php")
    Call<user_data> getUser();

    @FormUrlEncoded
    @POST("/AMTS/Loginuser.php")
    Call<Root> StudentLogin(
            @Field("email") String uemail,
            @Field("pass") String upassword

    );

    @FormUrlEncoded
    @POST("/AMTS/SeniorCitizenLogin.php")
    Call<Root> SeniorCitizenLogin(
            @Field("email") String uemail,
            @Field("pass") String upassword

    );

    @FormUrlEncoded
    @POST("/AMTS/EmployeeLogin.php")
    Call<Root> EmployeeLogin(
            @Field("email") String uemail,
            @Field("pass") String upassword

    );

    @FormUrlEncoded
    @POST("/AMTS/CollageAuthorityLogin.php")
    Call<Root> CollageAuthorityLogin(
            @Field("email") String uemail,
            @Field("pass") String upassword

    );

    @FormUrlEncoded
    @POST("/AMTS/AMTSManagerLogin.php")
    Call<Root> AMTSManagerLogin(
            @Field("email") String uemail,
            @Field("pass") String upassword
    );

    @FormUrlEncoded
    @POST("/AMTS/collage_select.php")
    Call<FindCollage> FindCollageId(
            @Field("cname") String cname
    );

    @FormUrlEncoded
    @POST("/AMTS/select_collage.php")
    Call<Collage_Select> FindCollageStudentList(
            @Field("cname") String cname
    );

    @FormUrlEncoded
    @POST("/AMTS/StudentList.php")
    Call<Collage_Select> FindStudentList(
        @Field("astatus") String astatus
    );

    @FormUrlEncoded
    @POST("/AMTS/Student_List1.php")
    Call<Collage_Select> FindValidStudentList(
            @Field("astatus") String astatus
    );

    @FormUrlEncoded
    @POST("/AMTS/CollageStudentData.php")
    Call<Student_Show> FindCollageStudentData(
            @Field("sid") int sid
    );

    @FormUrlEncoded
    @POST("/AMTS/Student_Cstatus.php")
    Call<User_Insert> EditStudentCstatus(
            @Field("sid") int sid
    );

    @FormUrlEncoded
    @POST("/AMTS/EmployeeList.php")
    Call<Employee_Select> FindEmployeeList(
            @Field("astatus") String astatus
    );

    @FormUrlEncoded
    @POST("/AMTS/generate_student_pass.php")
    Call<Student_Status> FindStudentPassStatus(
            @Field("uid") int uid
    );

    @FormUrlEncoded
    @POST("/AMTS/generate_employee_pass.php")
    Call<Employee_Status> FindEmployeePassStatus(
            @Field("uid") int uid
    );

    @FormUrlEncoded
    @POST("/AMTS/generate_seniorcitizen_pass.php")
    Call<SeniorCitizen_Status> FindSeniorcitizenPassStatus(
            @Field("uid") int uid
    );

    @FormUrlEncoded
    @POST("/AMTS/EmployeeData.php")
    Call<Employee_Show> FindEmployeeData(
            @Field("eid") int eid
    );


    @FormUrlEncoded
    @POST("/AMTS/SeniorCitizenList.php")
    Call<SeniorCitizen_Select> FindSeniorCitizenList(
            @Field("astatus") String astatus
    );

    @FormUrlEncoded
    @POST("/AMTS/SeniorCitizenData.php")
    Call<SeniorCitizen_Show> FindSeniorCitizenData(
            @Field("scid") int scid
    );

    @Multipart
    @POST("/AMTS/uploadImage.php")
    Call<Image> uploadImage(
            //@Part("image") RequestBody file,
            @Part("id") RequestBody id,
            @Part MultipartBody.Part image,
            @Part("uid") RequestBody uid
    );

    @FormUrlEncoded
    @POST("/AMTS/user_insert.php")
    Call<User_Insert> userInsert(
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("gender") String gender,
            @Field("birthdate") String birthdate,
            @Field("category") String category,
            @Field("password") String password,
            @Field("age") int age
    );

    @FormUrlEncoded
    @POST("/AMTS/student_pass_insert.php")
    Call<Pass_Insert> studentPassInsert(
            @Field("scollage") String collage,
            @Field("scategory") String scategory,
            @Field("sdepartment") String sdepartment,
            @Field("senrollment") String senrollment,
            @Field("syear") String syear,
            @Field("ssource") String ssource,
            @Field("sdestination") String sdestination,
            @Field("sstartdate") String sstartdate,
            @Field("senddate") String senddate,
            @Field("stotaldays") String stotaldays,
            @Field("astatus") String astatus,
            @Field("cstatus") String cstatus,
            @Field("uid") int uid
    );

    @FormUrlEncoded
    @POST("/AMTS/employee_pass_insert.php")
    Call<Pass_Insert> employeePassInsert(
            @Field("ecategory") String ecategory,
            @Field("ejobprofile") String edepartment,
            @Field("ejobaddress") String eenrollment,
            @Field("esource") String esource,
            @Field("edestination") String edestination,
            @Field("estartdate") String estartdate,
            @Field("eenddate") String eenddate,
            @Field("etotaldays") String etotaldays,
            @Field("astatus") String estatus,
            @Field("uid") int uid
    );

    @FormUrlEncoded
    @POST("/AMTS/collage_insert.php")
    Call<User_Insert> collageDataInsert(
            @Field("cname") String cname
            );

    @FormUrlEncoded
    @POST("/AMTS/seniorcitizen_pass_insert.php")
    Call<Pass_Insert> seniorCitizenPassInsert(
            @Field("sccategory") String sccategory,
            @Field("scsource") String scsource,
            @Field("scdestination") String scdestination,
            @Field("astatus") String estatus,
            @Field("uid") int uid
    );

    @FormUrlEncoded
    @POST("/AMTS/Student_Astatus.php")
    Call<User_Insert> EditStudentAstatus(
            @Field("sid") int sid
    );

    @FormUrlEncoded
    @POST("/AMTS/Employee_Astatus.php")
    Call<User_Insert> EditEmployeeAstatus(
            @Field("eid") int eid
    );

    @FormUrlEncoded
    @POST("/AMTS/SeniorCitizen_Astatus.php")
    Call<User_Insert> EditSeniorCitizenAstatus(
            @Field("scid") int scid
    );

    @FormUrlEncoded
    @POST("/AMTS/delete_seniorcitizen.php")
    Call<User_Insert> deleteSeniorCitizen(
            @Field("scid") int scid
    );

    @FormUrlEncoded
    @POST("/AMTS/delete_employee.php")
    Call<User_Insert> deleteEmployee(
            @Field("eid") int eid
    );

    @FormUrlEncoded
    @POST("/AMTS/delete_student.php")
    Call<User_Insert> deleteStudent(
            @Field("sid") int sid
    );


    @FormUrlEncoded
    @POST("/AMTS/map_request.php")
    Call<User_Insert> mapUploadId(
            @Field("uid") int uid,
            @Field("passid") int passid,
            @Field("aadharcardid") String aadharcardid,
            @Field("uploadid") int uploadid,
            @Field("imgpath") String imgpath
    );

    @FormUrlEncoded
    @POST("/AMTS/payment_insert.php")
    Call<User_Insert> insertPayment(
            @Field("uid") int uid,
            @Field("passid") int passid,
            @Field("orderid") String orderid,
            @Field("paymentid") String paymentid,
            @Field("pstatus") String pstatus,
            @Field("amount") int amount
    );

    @FormUrlEncoded
    @POST("/AMTS/Update_UserData.php")
    Call<User_Insert> userUpdate(
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("gender") String gender,
            @Field("birthdate") String birthdate,
            @Field("age") int age,
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/AMTS/UserList.php")
    Call<User_List> userList(
            @Field("user") String user
    );

    @FormUrlEncoded
    @POST("/AMTS/UserListData.php")
    Call<User_Show> findUserData(
            @Field("uid") int uid
    );

    @FormUrlEncoded
    @POST("/AMTS/user_delete.php")
    Call<User_Insert> deleteUser(
            @Field("uid") int uid
    );


    @FormUrlEncoded
    @POST("/AMTS/view_image.php")
    Call<ViewImageData> viewImage(
            @Field("uid") int uid
    );


    //user login
    /*@FormUrlEncoded
    @POST("/android/getUser.php")
    Call<LoginRes> Login(
            @Field("uname") String uname,
            @Field("pass") String pass

    );

    //image upload
    @Multipart
    @POST("/android/uploadImage2.php")
    Call<DefaultRes> uploadImage(
            @Part MultipartBody.Part file,
            @Part("id") RequestBody id
    );

    @GET("/Donation/getCategory.php")
    Call<CategoryRes> getCategory();

    @GET("/Donation/getCategory2.php")
    Call<CategoryRes> getCategory2();

    @GET("/Donation/getColl.php")
    Call<CollRes> getcoll();

    @GET("/Donation/getNgoAll.php")
    Call<NGORes> getNgoALl();

    //pincode API calling
    Call<DefaultRes> getPinAddr(String s_pin);

    @FormUrlEncoded
    @POST("/Donation/getNgo.php")
    Call<NGORes> getNGO(
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("/Donation/getCollDetails.php")
    Call<CollDetailsRes> getCollDetails(
            @Field("tag") String tag
    );*/
}
