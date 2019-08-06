package com.satish07.firebasephonelogindemo.Profile;

import android.annotation.SuppressLint;


import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
//import android.widget.ProgressBar;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseUser;
import com.satish07.firebasephonelogindemo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Objects;

public class User_Profile extends AppCompatActivity {

    private static final String TAG ="User_Profile_Adapter" ;
    private TextView Name1,Email1,Address1;

    //add Firebase Database stuff

    DatabaseReference myRefDatabase;
    Button update_profile;
    TextView uIds1;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //declare variables

        uIds1 = findViewById(R.id.uIds1);
        Name1 = findViewById(R.id.pflName1);
        Email1 = findViewById(R.id.pflEmail1);
        Address1 = findViewById(R.id.pflAddress1);
        progressBar = findViewById(R.id.progressBar1);

        update_profile = findViewById(R.id.update_profile);

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRefDatabase = mFirebaseDatabase.getReference();

        //Retrieve User
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        updateUI(user);

        // moveNext();
          progressBar.setVisibility(View.VISIBLE);


        //Data Reteive From database
        String userId = user.getPhoneNumber();
        myRefDatabase.child(Objects.requireNonNull(userId)).child("Profile").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User_Profile_Adapter userProfile = dataSnapshot.getValue(User_Profile_Adapter.class);
                Name1.setText(userProfile != null ? userProfile.getPflName() : null);
                Email1.setText(userProfile != null ? userProfile.getPflEmail() : null);
                Address1.setText(userProfile != null ? userProfile.getPflAddress() : null);

                if (userProfile != null){

                    update_profile.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    update_profile.setVisibility(View.VISIBLE);

                    //Open Fragment
                    update_profile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            My_Dialog_Fragment dialog_fragment = new My_Dialog_Fragment();
                            dialog_fragment.show(fragmentManager, "Fragment");
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });


    }
     protected void onStart(){
        super.onStart();

   }

    //Retreive User Phone Number
    @SuppressLint("SetTextI18n")
    private void updateUI(FirebaseUser user) {
        uIds1.setText(" " + user.getPhoneNumber());

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}