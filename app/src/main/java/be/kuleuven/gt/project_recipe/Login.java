package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private Button btnCreateNewAccount;
    private TextView txtUsername;
    private TextView txtPassword;
    private String username;
    private String password;
    private ArrayList<String> listUsernames = new ArrayList<>();
    private ArrayList<String> listPasswords = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnCreateNewAccount = (Button) findViewById(R.id.btnCreateNewAccount);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        retrieveLoginData();
    }

    public void onBtnCreateNewAccount_Clicked (View Caller){
        Intent intent = new Intent(this, CreateNewAccount.class);
        intent.putExtra("listUsernames", listUsernames);
        startActivity(intent);
        finish();
    }
    private static final String QUEUE_URL = "https://studev.groept.be/api/a22pt409/GetEverything";
    private void retrieveLoginData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest queueRequest = new JsonArrayRequest(
                Request.Method.GET,
                QUEUE_URL,
                null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject curObject = response.getJSONObject(i);
                            String Username = curObject.getString("Username");
                            String Password = curObject.getString("Password");
                            listUsernames.add(Username);
                            listPasswords.add(Password);

                           }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Toast.makeText(
                        Login.this,
                        "Unable to communicate with the server",
                        Toast.LENGTH_LONG).show());
        requestQueue.add(queueRequest);
    }

    public void onBtnLogin_Clicked(View Caller){

        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();
        boolean correctUsername = false;
        for(int i=0;i< listPasswords.size();i++)
        {
            if(username.equals(listUsernames.get(i)) ){
                correctUsername=true;
                if(password.equals(listPasswords.get(i))){
                    Intent intent2 = new Intent(this, MainActivity.class);

                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                    editor.putString("USERNAME", username);
                    editor.apply();

                    startActivity(intent2);
                    finish();
                }
                else{
                    txtPassword.setText("");
                    txtPassword.setHint("Password Incorrect");
                }
            }
        }
        if(correctUsername==false)
        {
            txtUsername.setText("");
            txtPassword.setText("");
            txtUsername.setHint("Username Incorrect");
            txtPassword.setHint("Enter Password");
        }
    }

}