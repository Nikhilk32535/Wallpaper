package com.example.wallpaper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wallpaper.R;
import com.example.wallpaper.activity.MainActivity;
import com.example.wallpaper.utility.utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Userauthentication extends Fragment {

    // UI Elements
    private TextView forgetPassword, createNewAccount, forgetSignIn;
    private EditText userEmail, userPassword, forgetEmail;
    private Button signIn, forgetSentMail;
    private RelativeLayout signInLayout, forgetLayout;

    // Firebase
    private FirebaseAuth mAuth;
    private boolean isButtonClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Handle back button press
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finishAffinity();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_userauthentication, container, false);

        // Initialize UI elements
        initViews(view);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Set up click listeners
        setUpClickListeners();

        return view;
    }

    /**
     * Initialize all UI components
     */
    private void initViews(View view) {
        userEmail = view.findViewById(R.id.useremail);
        userPassword = view.findViewById(R.id.inputPassword);
        signIn = view.findViewById(R.id.btnSignIn);
        forgetPassword = view.findViewById(R.id.txtForgotPassword);
        createNewAccount = view.findViewById(R.id.txtSignUp);
        signInLayout = view.findViewById(R.id.signinlayout);
        forgetLayout = view.findViewById(R.id.forgetlayout);
        forgetSignIn = view.findViewById(R.id.signinpage);
        forgetEmail = view.findViewById(R.id.forgetemail);
        forgetSentMail = view.findViewById(R.id.sentemail);
    }

    /**
     * Set up button click listeners for the various actions
     */
    private void setUpClickListeners() {
        signIn.setOnClickListener(v -> handleSignInClick());
        forgetPassword.setOnClickListener(v -> switchToForgetPasswordView());
        forgetSentMail.setOnClickListener(v -> handleForgetPasswordClick());
        forgetSignIn.setOnClickListener(v -> switchToSignInView());
        createNewAccount.setOnClickListener(v ->{
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentview,new create_user())
                    .addToBackStack(null).commit();
            utility.log("create user start");
        });
    }

    /**
     * Handle sign-in button click
     */
    private void handleSignInClick() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if (isInputValid(email, password)) {
            verifyUser(email, password);
        } if (email.isEmpty()){
           userEmail.setError("Email is required");
        } if (password.isEmpty()){
           userPassword.setError("Password is required");
        }
    }

    /**
     * Switch to the forget password view
     */
    private void switchToForgetPasswordView() {
        signInLayout.setVisibility(View.GONE);
        forgetLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Handle forget password button click
     */
    private void handleForgetPasswordClick() {
        if (!isButtonClicked) {
            String email = forgetEmail.getText().toString().trim();

            if (isEmailValid(email)) {
                sendPasswordReset(email);
                resetForgetPasswordUI();
                isButtonClicked = true;
            } else {
                utility.toast(getActivity(), "Please fill Email Address Correctly");
            }
        } else {
            utility.log("Button already clicked.");
        }
    }

    /**
     * Switch back to sign-in view
     */
    private void switchToSignInView() {
        forgetSentMail.setText("Sent Email");
        forgetSentMail.setBackgroundColor(getResources().getColor(R.color.Button));
        signInLayout.setVisibility(View.VISIBLE);
        forgetLayout.setVisibility(View.GONE);
        isButtonClicked = false;
    }

    /**
     * Verify user credentials and sign in
     */
    private void verifyUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        utility.toast(getActivity(), "Authentication Successful");
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    } else {
                        utility.log("Auth Error: " + task.getException());
                        utility.toast(getActivity(), "Authentication Failed");
                    }
                });
    }

    /**
     * Send a password reset email
     */
    public void sendPasswordReset(String emailAddress) {
        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        utility.toast(getActivity(), "Email sent.");
                    } else {
                        utility.toast(getActivity(), "Failed to send email.");
                    }
                });
    }

    /**
     * Reset the forget password UI
     */
    private void resetForgetPasswordUI() {
        forgetEmail.setText("");
        forgetSentMail.setText("Email sent successfully");
        forgetSentMail.setBackgroundColor(getResources().getColor(R.color.Accent));
    }

    /**
     * Validate user input
     */
    private boolean isInputValid(String email, String password) {
        return !email.isEmpty() && email.contains("@") && email.endsWith(".com") && !password.isEmpty();
    }

    /**
     * Validate email address format
     */
    private boolean isEmailValid(String email) {
        return !email.isEmpty() && email.contains("@") && email.endsWith(".com");
    }

}
