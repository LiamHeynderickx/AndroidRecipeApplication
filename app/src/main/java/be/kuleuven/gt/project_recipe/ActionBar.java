package be.kuleuven.gt.project_recipe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ActionBar extends AppCompatActivity {

    private ImageButton btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_action_bar_layout);

        btnHelp = (ImageButton) findViewById(R.id.btnHelp);

    }

    public void onBtnHelp_Clicked(){
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }

}
