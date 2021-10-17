package be.pxl.mobdev.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.appcompat.app.AppCompatActivity;

import be.pxl.mobdev.R;
import be.pxl.mobdev.util.FirebaseUtil;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_activity_menu, menu);
        if (FirebaseUtil.isAdmin) {
            menu.findItem(R.id.edit_menu).setVisible(true);
            menu.findItem(R.id.edit_menu).setEnabled(true);
        } else {
            menu.findItem(R.id.edit_menu).setVisible(false);
            menu.findItem(R.id.edit_menu).setEnabled(false);
        }
        return true;
    }

}
