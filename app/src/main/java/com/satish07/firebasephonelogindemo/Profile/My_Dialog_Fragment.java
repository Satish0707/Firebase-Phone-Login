package com.satish07.firebasephonelogindemo.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.satish07.firebasephonelogindemo.R;

import java.util.Objects;

public class My_Dialog_Fragment extends DialogFragment {

    private EditText Name, Email,Address;
    private ProgressBar progressBar;

    //add Firebase Database stuff

    private DatabaseReference myRefDatabase;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment, container, false);
        getDialog().setTitle("Update Profile");


        Button buttonSave = rootView.findViewById(R.id.buttonSave);

        Name = rootView.findViewById(R.id.pflName);
        Email = rootView.findViewById(R.id.pflEmail);
        Address = rootView.findViewById(R.id.pflAddress);

        progressBar = rootView.findViewById(R.id.progressBar);

//Database reference

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRefDatabase = mFirebaseDatabase.getReference();


        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // dismiss();

                    String name = Name.getText().toString().trim();
                    String email = Email.getText().toString();
                    String address = Address.getText().toString().trim();

                    if (name.isEmpty() || name.length() < 2) {
                        Name.setError("Please Enter Full Name");
                        Name.requestFocus();
                        return;
                    }

                    if (email.isEmpty() || email.length()<10) {
                        Email.setError("Please Enter Valid Email Id");

                        if (Email.getText().toString().matches(emailPattern))
                        {
                            Toast.makeText(getActivity(),"Valid Email Id",Toast.LENGTH_SHORT).show();

                        } else
                        {
                            Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        }
                        Email.requestFocus();
                        return;
                    }
                    if (address.isEmpty() || address.length() < 10) {
                        Address.setError("Please Enter Full Address with Pin Code");
                        Address.requestFocus();
                    }
                    else {

                        User_Profile_Adapter userProfile = new User_Profile_Adapter(name, email, address);
                        progressBar.setVisibility(View.VISIBLE);
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        String  userId = Objects.requireNonNull(user).getPhoneNumber();
                        myRefDatabase.child(Objects.requireNonNull(userId)).child("Profile").setValue(userProfile);
                        Toast.makeText(getActivity(),"Your Details Saved  ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),User_Profile.class);
                        startActivity(intent);
                        dismiss();
                    }
                }


            });

      return rootView;

    }
}
