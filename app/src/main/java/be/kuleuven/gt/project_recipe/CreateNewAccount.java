package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.view.MotionEvent;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class CreateNewAccount extends AppCompatActivity {

    private Button btnCreateAccount;
//    private ConstraintLayout constraintlayout;
    private Button btnContinue;
    private TextView lblAccountCreated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
//        constraintlayout = findViewById(R.id.constraintlayout);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        lblAccountCreated = (TextView) findViewById(R.id.lblAccountCreated);
    }

    public void onBtnCreateAccount_Clicked(View Caller){



//        Intent intent = new Intent(this, );
        btnContinue.setVisibility(View.VISIBLE);
        lblAccountCreated.setVisibility(View.VISIBLE);

    }

}