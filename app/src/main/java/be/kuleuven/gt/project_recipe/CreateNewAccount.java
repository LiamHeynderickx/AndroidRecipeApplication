package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateNewAccount extends AppCompatActivity {

    private Button btnCreateAccount;
//    private ConstraintLayout constraintlayout;
    private Button btnContinue;
    private TextView lblAccountCreated;
    private TextView lblCreateNewAccount;
    private TextView lblUsername;
    private TextView lblPassword;
    private TextView txtUsername;
    private TextView txtPassword;
    private TextView txtConfirmPassword;
    private TextView lblNote;

    private String username;
    private String password;
    private String confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
//        constraintlayout = findViewById(R.id.constraintlayout);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        lblAccountCreated = (TextView) findViewById(R.id.lblAccountCreated);
        lblCreateNewAccount = (TextView) findViewById(R.id.lblCreateNewAccount);
        lblUsername = (TextView) findViewById(R.id.lblNewUsername);
        lblPassword = (TextView) findViewById(R.id.lblNewPassword);
        txtUsername = (TextView) findViewById(R.id.txtNewUsername);
        txtPassword = (TextView) findViewById(R.id.txtNewPassword);
        txtConfirmPassword = (TextView) findViewById(R.id.txtConfirmPassword);
        lblNote = (TextView) findViewById(R.id.lblNote);

    }

    public void onBtnCreateAccount_Clicked(View Caller){

        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();
        confirmPassword = txtConfirmPassword.getText().toString();

        //check for special characters in username
//        System.out.println(username);
        boolean speacialCharacters = false;
        for(int loop = 0; loop < username.length(); loop++){
            int asciiVal = (int)(username.charAt(loop));
            System.out.println(asciiVal);
            if(asciiVal >= 48 && asciiVal <= 57){

            }
            else if (asciiVal >= 65 && asciiVal <= 90) {

            }
            else if (asciiVal >= 97 && asciiVal <= 122) {

            } else{
                speacialCharacters = true;
            }
        }

        boolean correctLength = false;
        if(username.length() > 0 && username.length() <= 15){
            correctLength = true;
        }





        //if all checks are passed
        if(speacialCharacters == false && correctLength == true){
            btnContinue.setVisibility(View.VISIBLE);
            lblAccountCreated.setVisibility(View.VISIBLE);

            btnCreateAccount.setVisibility(View.GONE);
            lblCreateNewAccount.setVisibility(View.GONE);
            lblUsername.setVisibility(View.GONE);
            lblPassword.setVisibility(View.GONE);
            txtUsername.setVisibility(View.GONE);
            txtPassword.setVisibility(View.GONE);
            txtConfirmPassword.setVisibility(View.GONE);
            lblNote.setVisibility(View.GONE);
        }


    }

}