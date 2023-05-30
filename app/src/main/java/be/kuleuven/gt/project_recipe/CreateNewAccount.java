package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import java.util.ArrayList;


public class CreateNewAccount extends AppCompatActivity {
    private String Que_URL = "https://studev.groept.be/api/a22pt409/insert/";
    private Button btnCreateAccount;

    private Button getBtnContinue;
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
    private ArrayList<String>usernamesFromDatabase = new ArrayList<>();

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
        Intent intent = getIntent();
        usernamesFromDatabase= intent.getStringArrayListExtra("listUsernames");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
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
    @Override //copy to each new activity
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_create_account, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private Intent menuOption; // copy to each activity
    @Override // copy to each activity
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_back:
                menuOption = new Intent(this, Login.class);
                startActivity(menuOption);
                finish();
                return true;
            case android.R.id.home:
                menuOption = new Intent(this,Login.class);
                startActivity(menuOption);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addUserDetails(){
        Que_URL += getUsername() + "/"+getPassword();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest queueRequest = new JsonArrayRequest(
                Request.Method.GET,
                Que_URL,
                null,
                response -> Toast.makeText(
                        CreateNewAccount.this,
                        "Account Created",
                        Toast.LENGTH_LONG).show(),
                error -> Toast.makeText(
                        CreateNewAccount.this,
                        "Unable to communicate with the server",
                        Toast.LENGTH_LONG).show());
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
            username = txtUsername.getText().toString();
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

            addUserDetails();

        }

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public void onBtnContinue_Clicked(View Caller){
        Intent intent2 = new Intent(this, MainActivity.class);

        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
        editor.putString("USERNAME", username);
        editor.apply();

        startActivity(intent2);
        finish();
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
        boolean uniqueUsername = true;
        for(int i=0;i<usernamesFromDatabase.size();i++)
        {
         if(usernamesFromDatabase.get(i).contains(username))
         {
             uniqueUsername = false;
             txtUsername.setText("");
             txtUsername.setHint("Username Not unique");
             txtPassword.setText("");
             txtConfirmPassword.setText("");

         }
        }

        if(speacialCharacters == false && correctLength == true && uniqueUsername == true){
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