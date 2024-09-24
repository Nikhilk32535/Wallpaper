package com.example.wallpaper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.wallpaper.R;
import com.example.wallpaper.activity.MainActivity;
import com.example.wallpaper.utility.utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Userauthentication extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView forgetpassword,createnewaccount;
    EditText useremail,userpassword;
    Button signin;
    private FirebaseAuth mAuth;
    public static String UserEmail,UserPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_userauthentication, container, false);

        useremail=view.findViewById(R.id.useremail);
        userpassword=view.findViewById(R.id.inputPassword);
        signin=view.findViewById(R.id.btnSignIn);
        forgetpassword=view.findViewById(R.id.txtForgotPassword);
        createnewaccount=view.findViewById(R.id.txtSignUp);

        mAuth= FirebaseAuth.getInstance();
        signin.setOnClickListener(v->{
            String email=useremail.getText().toString();
            String password=userpassword.getText().toString();
            UserEmail=email;
            UserPassword=password;
            varifyuser(UserEmail,UserPassword);
        });

        return  view;
    }

    private void varifyuser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((getActivity()), task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user=mAuth.getCurrentUser();
                        utility.toast(getActivity(),"Authentication Succesfull");
                        utility.log("Authentication Succesfull");
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }else {
                        utility.log("Auth :-"+task.getException());
                        utility.toast(getActivity(),"Failed");
                    }
                });

    }
}