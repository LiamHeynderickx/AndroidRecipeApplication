package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("319f95"));
//        getSupportActionBar().setBackgroundDrawable(colorDrawable);  //couldnt find a colour i like
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);

        setContentView(R.layout.activity_help);
    }
}