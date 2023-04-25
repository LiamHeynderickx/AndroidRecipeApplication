package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateNewAccount extends AppCompatActivity {

    private static final String QUEUE_URL = "https://studev.groept.be/api/a22pt409/GetEverything";
    private String Que_URL = "https://studev.groept.be/api/a22pt409/insert/";
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

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
    private void requestCoffeeOrderqueue(){
        Que_URL += getUsername() + "/"+getPassword();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest queueRequest = new JsonArrayRequest(
                Request.Method.GET,
                Que_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                CreateNewAccount.this,
                                "Unable to communicate with the server",
                                Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(queueRequest);
    }

    public void onBtnCreateAccount_Clicked(View Caller){

        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();
        confirmPassword = txtConfirmPassword.getText().toString();

        //check that inputs are correct;
        boolean validUsername = checkUsername(username);
        boolean validpassword = checkPassword(password, confirmPassword);

        //if all checks are passed
        if(validUsername && validpassword){
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

            requestCoffeeOrderqueue();





        }

    }


    private boolean checkUsername(String un){

        //checks characters
        boolean speacialCharacters = false;
        for(int loop = 0; loop < un.length(); loop++){
            int asciiVal = (int)(un.charAt(loop));
            System.out.println(asciiVal);
            if(asciiVal >= 48 && asciiVal <= 57){

            }
            else if (asciiVal >= 65 && asciiVal <= 90) {

            }
            else if (asciiVal >= 97 && asciiVal <= 122) {

            } else{
                speacialCharacters = true;
                txtUsername.setText("");
                txtUsername.setHint("No Special Characters");
            }
        }

        //checks length
        boolean correctLength = false;
        if(username.length() > 0 && username.length() <= 15){
            correctLength = true;
        }
        else{
            txtUsername.setText("");
            txtUsername.setHint("Invalid Length");
        }

        //add unique check when database is added

        if(speacialCharacters == false && correctLength == true){
            return true;
        }
        else{
            return false;
        }

    }

    private boolean checkPassword(String pw, String cpw){

        //check length
        boolean correctLength = false;
        if(pw.length() > 0 && pw.length() <= 15){
            correctLength = true;
        }
        else{
            txtPassword.setText("");
            txtPassword.setHint("Invalid Length");
        }

        boolean passwordsEqual = false;
        //check passwords equal
        if(pw.equals(cpw)){
            passwordsEqual = true;
        }
        else{
            txtConfirmPassword.setText("");
            txtConfirmPassword.setHint("Passwords Don't Match");
        }

        if(passwordsEqual && correctLength){
            return true;
        }
        else{
            return false;
        }

    }


}