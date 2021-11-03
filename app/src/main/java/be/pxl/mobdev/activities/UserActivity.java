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
    String username;
    String mail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        txtUser = (TextView) findViewById(R.id.textUserNameValue);
        txtMail = (TextView) findViewById(R.id.textUserMailValue);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            username = user.getDisplayName();
            mail = user.getEmail();
        }
        
        txtUser.setText(username);
        txtMail.setText(mail);

    }


}
