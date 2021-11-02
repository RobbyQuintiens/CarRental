package be.pxl.mobdev.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import be.pxl.mobdev.R;

public class UserActivity extends AppCompatActivity {

    TextView txtUser;
    TextView txtMail;
    String user;
    String mail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        txtUser = (TextView) findViewById(R.id.textUserNameValue);
        txtMail = (TextView) findViewById(R.id.textUserMailValue);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    user = firebaseUser.getDisplayName();
                    mail = firebaseUser.getEmail();
                }
            }
        };

        txtUser.setText(user);

    }


}
