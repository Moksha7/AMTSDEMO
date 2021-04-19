package com.example.amtsdemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amtsdemo.Api.RetrofitClient;
import com.example.amtsdemo.R;
import com.example.amtsdemo.activity.ViewStudentData;
import com.example.amtsdemo.activity.ViewUserData;
import com.example.amtsdemo.database.MainOpenHelper;
import com.example.amtsdemo.model.PassStudent;
import com.example.amtsdemo.pojo.Collage_Select_Data;
import com.example.amtsdemo.pojo.UserListData;
import com.example.amtsdemo.pojo.User_Insert;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.StudentListViewHolder> {

    private static final String TAG = UserListAdapter.class.getSimpleName();

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_WORD = "WORD";
    public static final String EXTRA_POSITION = "POSITION";
    public static final String POS = "POS";

    private final LayoutInflater mInflater;
    /*StudentPassOpenHelper sDB;
    UserOpenHelper uDB;
    */
    MainOpenHelper mDB;
    Context mContext;
    //List<PassStudent> current;
    List<UserListData> current;
    PassStudent pass;


    /*public StudentListAdapter(Context context, List<PassStudent> userList ) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        current = userList;
        mDB = new MainOpenHelper(mContext);
    }*/

    public UserListAdapter(Context context, List<UserListData> userList ) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        current = userList;
        mDB = new MainOpenHelper(mContext);
    }

    @NonNull
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.user_item, parent, false);
        return new StudentListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListViewHolder holder, int position) {
        holder.studentName.setText(current.get(position).getUname());
        holder.studentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(mContext, ViewUserData.class);
                i.putExtra("uid",Integer.parseInt(current.get(holder.getAdapterPosition()).getUid()));
                ((Activity) mContext).startActivity(i);
            }
        });


        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int uid=Integer.parseInt(current.get(holder.getAdapterPosition()).getUid());
                Call<User_Insert> responseCall1 = RetrofitClient.getInstance()
                        .getInterPreter().deleteUser(uid);
                responseCall1.enqueue(new Callback<User_Insert>() {
                    @Override
                    public void onResponse(Call<User_Insert> call, Response<User_Insert> response) {
                        User_Insert response1 = response.body();
                        if(response1.error)
                        {
                            Toast.makeText(mContext, "Error Occurred....", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(mContext, "user deleted",Toast.LENGTH_LONG).show();
                            notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<User_Insert> call, Throwable t) {
                        Toast.makeText(mContext, "Error Occurred", Toast.LENGTH_LONG).show();
                    }
                });

                Toast.makeText(mContext,"user deleted",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return current.size();
    }

    public class StudentListViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        ImageButton delete_button;
        ImageButton confirm_button;
        public StudentListViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = (TextView) itemView.findViewById(R.id.sName);
            confirm_button = (ImageButton) itemView.findViewById(R.id.btnConfirmm);
            delete_button = (ImageButton) itemView.findViewById(R.id.btnDeletee);
        }
    }
}
