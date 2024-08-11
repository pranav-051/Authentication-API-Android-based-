package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class activity_Register extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterDOB, editTextRegisterMobile, editTextRegisterPWD, editTextRegisterConfgOWD;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private Button buttonRegister;
    private DatePickerDialog picker;
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.getElevation();
        actionBar.setTitle("                     Register Now");
        actionBar.setElevation(5);
        ColorDrawable colorDrawable= new ColorDrawable(Color.parseColor("#ff9817"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //getSupportActionBar().setTitle("Register");
        Toast.makeText(activity_Register.this, "You can register now ", Toast.LENGTH_LONG).show();

        progressBar = findViewById(R.id.progressBar);
        editTextRegisterFullName = findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterDOB = findViewById(R.id.editText_register_dob);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPWD = findViewById(R.id.editText_register_password);
        editTextRegisterConfgOWD = findViewById(R.id.editText_register_conform_password);

        //RadioBUtton for gender
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        //Setting up datePicker on EditText
        editTextRegisterDOB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar calender = Calendar.getInstance();
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int month = calender.get(Calendar.MONTH);
                int year = calender.get(Calendar.YEAR);

                //Date picker dialog
                picker = new DatePickerDialog(activity_Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDOB.setText(dayOfMonth + "/" + (month+1)+"/"+year);
                    }
                },year, month, day);
                picker.show();
            }
        });

        buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                int selectedGenderID = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderID);

                //Obtain the entered data
                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDOB = editTextRegisterDOB.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textPWD = editTextRegisterPWD.getText().toString();
                String textConfirmPass = editTextRegisterConfgOWD.getText().toString();
                String textGender; //Cannot obtain the value before verifying if any button was selected or not

                //Validate mobile number using matcher and pattern (regular expression)
                String mobileRegex = "[6-9][0-9]{9}"; //first no. can be {6,8,9} and rest 9 nos, can be any no.
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePattern.matcher(textMobile);

                if(TextUtils.isEmpty(textFullName))
                {
                    Toast.makeText(activity_Register.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Full name is required");
                    editTextRegisterFullName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(activity_Register.this, "Please enter your valid email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("E-mail is required");
                    editTextRegisterEmail.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches())
                {
                    Toast.makeText(activity_Register.this, "Please re-enter your valid email ", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid Email is required");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDOB)){
                    Toast.makeText(activity_Register.this, "Please enter your Date Of Birth", Toast.LENGTH_LONG).show();
                    editTextRegisterDOB.setError("Date of Birth is required");
                    editTextRegisterDOB.requestFocus();
                } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(activity_Register.this, "Please select your gender", Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioGroupRegisterGender.requestFocus();
                } else if (TextUtils.isEmpty(textMobile)){
                    Toast.makeText(activity_Register.this, "Please enter your mobile number", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile number is required");
                    editTextRegisterMobile.requestFocus();
                }else if (textMobile.length() != 10){
                    Toast.makeText(activity_Register.this, "Please re-enter your mobile number.", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile number must be of 10 digits long.");
                    editTextRegisterMobile.requestFocus();
                } else if (!mobileMatcher.find()){
                    Toast.makeText(activity_Register.this, "Please re-enter your mobile no.", Toast.LENGTH_LONG).show();
                    editTextRegisterMobile.setError("Mobile number is not valid");
                    editTextRegisterMobile.requestFocus();
                }else if (TextUtils.isEmpty(textPWD)) {
                    Toast.makeText(activity_Register.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                    editTextRegisterPWD.setError("Password is required");
                    editTextRegisterPWD.requestFocus();
                }else if (textPWD.length() < 6 ){
                    Toast.makeText(activity_Register.this, "Password should at least 6 digits, ", Toast.LENGTH_LONG).show();
                    editTextRegisterPWD.setError("Password is too weak");
                    editTextRegisterPWD.requestFocus();
                }else if(TextUtils.isEmpty(textConfirmPass)){
                    Toast.makeText(activity_Register.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    editTextRegisterConfgOWD.setError("Password confirmation is required");
                    editTextRegisterConfgOWD.requestFocus();
                }else if(!textPWD.equals(textConfirmPass)) {
                    Toast.makeText(activity_Register.this, "Please conform your password", Toast.LENGTH_LONG).show();
                    editTextRegisterConfgOWD.setError("Password confirmation is required");
                    editTextRegisterConfgOWD.requestFocus();
                    //Clear the entered password
                    editTextRegisterPWD.clearComposingText();
                    editTextRegisterConfgOWD.clearComposingText();
                }
                else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility((View.VISIBLE));
                    registerUser(textFullName, textEmail, textDOB, textGender, textMobile, textPWD);

                }
            }
        });

    }
    //Register user using the credentials given
    private void registerUser(String textFullName, String textEmail, String textDOB, String textGender, String textMobile, String textPWD) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPWD).addOnCompleteListener(activity_Register.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //after authentication we can add something here
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //Update display name of user
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    //Enter user data into firebase realtime database.
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textDOB, textGender, textMobile, textEmail);

                    //Extracting user reference from database for "Registerd users"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){Toast.makeText(activity_Register.this, "User registration failed. Please try again", Toast.LENGTH_LONG).show();
                              /*  //Send verification email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(activity_Register.this, "User registered successfully. Please verify your email", Toast.LENGTH_LONG).show();

                             //OPen user profile after successful registeration
                            Intent intent = new Intent(activity_Register.this, UserProfileActivity.class);
                            //To prevent user form returning back to register activity on pressing back button after registration
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);  //it will delete previous steps after success reg of user
                            startActivity(intent);
                            finish(); //to close registration activity*/
                       }else {
                                //Send verification email
                                firebaseUser.sendEmailVerification();

                                Toast.makeText(activity_Register.this, "User registered successfully. Please verify your email", Toast.LENGTH_LONG).show();

                                //OPen user profile after successful registeration
                                Intent intent = new Intent(activity_Register.this, HomePage.class);
                                //To prevent user form returning back to register activity on pressing back button after registration
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);  //it will delete previous steps after success reg of user
                                startActivity(intent);
                                finish(); //to close registration activity
                               // Toast.makeText(activity_Register.this, "User registration failed. Please try again", Toast.LENGTH_LONG).show();
                            }
                            //hide progress bar weather user creation is successful or failed
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                else
                {
                    try{
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e){
                        editTextRegisterPWD.setError("Your password is too weak. Enter again");
                        editTextRegisterPWD.requestFocus();
                    } catch(FirebaseAuthInvalidCredentialsException e) {
                        editTextRegisterPWD.setError("Invalid Email. Enter again");
                        editTextRegisterPWD.requestFocus();
                    }catch(FirebaseAuthUserCollisionException e){
                        editTextRegisterPWD.setError("User already exists.");
                        editTextRegisterPWD.requestFocus();
                    }catch(Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(activity_Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    //hide progress bar weather user creation is succesfull or failed
                    progressBar.setVisibility(View.GONE);
            }
        }
        });
      }
    }