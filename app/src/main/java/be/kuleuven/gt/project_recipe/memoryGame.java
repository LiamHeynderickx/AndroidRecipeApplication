package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class memoryGame extends AppCompatActivity {
    private ImageButton btnMeat;
    private ImageButton btnLettuce;
    private ImageButton btnRice;
    private ImageButton btnTomato;
    private ImageButton btnPasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        btnMeat = (ImageButton)findViewById(R.id.btnMeat);
        btnLettuce = (ImageButton)findViewById(R.id.btnLettuce);
        btnRice = (ImageButton)findViewById(R.id.btnRice);
        btnTomato = (ImageButton)findViewById(R.id.btnTomato);
        btnPasta = (ImageButton)findViewById(R.id.btnPasta);

    }

    @Override //copy to each new activity
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menue_search_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private Intent menuOption; // copy to each activity
    @Override // copy to each activity
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuSettings:
                menuOption = new Intent(this, Settings.class);
                startActivity(menuOption);
                return true;
            case R.id.menuHelp:
                menuOption = new Intent(this, Help.class);
                startActivity(menuOption);
                return true;
            case R.id.menuLogout:
                menuOption = new Intent(this, Login.class);
                startActivity(menuOption);
                finish();
            case R.id.menuHome:
                menuOption = new Intent(this, MainActivity.class);
                startActivity(menuOption);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBtn_Meat_Clicked(View Caller)
    {

    }
    public void onBtn_Lettuce_Clicked(View Caller)
    {

    }
    public void onBtn_Rice_Clicked(View Caller)
    {

    }
    public void onBtn_Tomato_Clicked(View Caller)
    {

    }
    public void onBtn_Pasta_Clicked(View Caller)
    {

    }

}