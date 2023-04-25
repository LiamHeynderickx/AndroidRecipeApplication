package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private Button btnCreateNewAccount;
    private TextView txtUsername;
    private TextView txtPassword;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btnCreateNewAccount = (Button) findViewById(R.id.btnCreateNewAccount);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
    }

    public void onBtnCreateNewAccount_Clicked (View Caller){


        Intent intent = new Intent(this, CreateNewAccount.class);
        startActivity(intent);
        finish();

    }

    public void onBtnLogin_Clicked(View Caller){

        //Check details: (need to integrate databease)
        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();

        if(username.equals("liam") || username.equals("X")){
            if(password.equals("X")){
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                finish();
            }
            else{
                txtPassword.setText("");
                txtPassword.setHint("Password Incorrect");
            }
        }
        else{
            txtUsername.setText("");
            txtPassword.setText("");
            txtUsername.setHint("Username Incorrect");
            txtPassword.setHint("Enter Password");
        }


    }

}