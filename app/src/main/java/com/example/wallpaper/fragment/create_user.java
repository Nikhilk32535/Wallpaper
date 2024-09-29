package com.example.wallpaper.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wallpaper.R;
import com.example.wallpaper.utility.utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class create_user extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView signinpage;
    EditText username,emailorphone,password;
    Button signupbtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_create_user, container, false);

        signinpage=view.findViewById(R.id.signinpage);
        username=view.findViewById(R.id.username);
        emailorphone=view.findViewById(R.id.emailorphone);
        password=view.findViewById(R.id.password);
        signupbtn=view.findViewById(R.id.signupbtn);

        signinpage.setOnClickListener(v->{
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentview,new Userauthentication())
                    .addToBackStack(null).commit();
        });

        signupbtn.setOnClickListener(v -> {
            String email = emailorphone.getText().toString().trim();
            String pass = password.getText().toString().trim();
            String name = username.getText().toString().trim();

            if (email.isEmpty()) {
                emailorphone.setError("Email is required");
                emailorphone.requestFocus();
                return;
            }

            if (pass.isEmpty()) {
                password.setError("Password is required");
                password.requestFocus();
                return;
            }

            if (name.isEmpty()) {
                username.setError("Name is required");
                username.requestFocus();
                return;
            }

            createAccount(email, pass, name);
            utility.log("signup worked");
        });



        return view;
    }

    private void createAccount(String email, String password,String name) {
        utility.log("createAccount:worked");
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            utility.toast(getActivity(), "Create Account Successful");
                          utility.log( "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user!=null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                   utility.toast(getContext(),"Verification email sent to " + user.getEmail());
                                                    UpdateUi(user,email,name);
                                                } else {
                                                   utility.log( "sendEmailVerification "+ task.getException());
                                                    utility.toast(getActivity(),"Failed to send verification email.");
                                                }
                                            }
                                        });
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                           utility.log("createUserWithEmail:failure"+ task.getException());
                            utility.toast(getActivity(), "Authentication failed.");
                        }
                    }
                });
        // [END create_user_with_email]

    }

    private void UpdateUi(FirebaseUser user, String email, String name) {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        utility.log("UpdateUi worked");
        HashMap<String, Object>userinfo=new HashMap<>();
        userinfo.put("name",name);
        userinfo.put("email",email);
        userinfo.put("uid",user.getUid());

        db.collection("USERS").document(user.getUid())
                .set(userinfo)
                .addOnSuccessListener(aVoid -> {
                    utility.log("User created successfully");
                }).addOnFailureListener(aVoid -> {
                    utility.log("Failed to create user"+aVoid);
                });

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentview,new Userauthentication())
                .addToBackStack(null).commit();

    };
}