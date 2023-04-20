package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    private Button btnCreateNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnCreateNewAccount = (Button) findViewById(R.id.btnCreateNewAccount);
    }

    public void onBtnCreateNewAccount_Clicked (View Caller){

        Intent intent = new Intent(this, CreateNewAccount.class);
        startActivity(intent);
        finish();

    }

}