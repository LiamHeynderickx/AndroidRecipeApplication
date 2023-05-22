package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class memoryGame extends AppCompatActivity {
    private ImageButton btnMeat;
    private ImageButton btnLettuce;
    private ImageButton btnRice;
    private ImageButton btnTomato;
    private ImageButton btnPasta;
    private Button btnStart;

    private int round = 1;

    private ArrayList<IngredientType> ingredientOrder =new ArrayList();

    private ArrayList<IngredientType> selectedOrder =new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);
        btnMeat = (ImageButton)findViewById(R.id.btnMeat);
        btnLettuce = (ImageButton)findViewById(R.id.btnLettuce);
        btnRice = (ImageButton)findViewById(R.id.btnRice);
        btnTomato = (ImageButton)findViewById(R.id.btnTomato);
        btnPasta = (ImageButton)findViewById(R.id.btnPasta);
        btnStart = (Button) findViewById(R.id.btnStart);

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

    public void onBtn_StartClicked(){

        createRandomArray();

        displayImageSequence();

        btnStart.setVisibility(View.GONE);

        round++;
    }

    private void displayImageSequence() {



    }

    private void createRandomArray() {

        for(int loop1 = 0; loop1 < round; loop1++){

            int randomNum = ThreadLocalRandom.current().nextInt(0, 4 + 1);

            switch (randomNum){
                case 0:
                    Meat m = new Meat();
                    ingredientOrder.add(m);
                    break;
                case 1:
                    Lettuce l = new Lettuce();
                    ingredientOrder.add(l);
                    break;
                case 2:
                    rice r = new rice();
                    ingredientOrder.add(r);
                    break;
                case 3:
                    Tomato t = new Tomato();
                    ingredientOrder.add(t);
                    break;
                case 4:
                    Pasta p = new Pasta();
                    ingredientOrder.add(p);
                    break;
                default:
                    Log.d("ERROR ++++++++++", randomNum+" num <--");
                    break;
            }

        }

    }

    public void onBtn_Meat_Clicked(View Caller) //0
    {
        IngredientType meat = new Meat();
        selectedOrder.add(meat);
        checkIfEqual();
    }
    public void onBtn_Lettuce_Clicked(View Caller) //1
    {
        IngredientType lettuce = new Lettuce();
        selectedOrder.add(lettuce);
        checkIfEqual();
    }
    public void onBtn_Rice_Clicked(View Caller) //2
    {
        IngredientType rice = new rice();
        selectedOrder.add(rice);
        checkIfEqual();
    }
    public void onBtn_Tomato_Clicked(View Caller) //3
    {
        IngredientType tomato = new Tomato();
        selectedOrder.add(tomato);
        checkIfEqual();
    }
    public void onBtn_Pasta_Clicked(View Caller) //4
    {
        IngredientType pasta = new Pasta();
        selectedOrder.add(pasta);
        checkIfEqual();

    }
    public void checkIfEqual()
    {
        if(ingredientOrder.size()==selectedOrder.size())
        {
           if(ingredientOrder.equals(selectedOrder))
           {
               //WIN GAME
           }
           else {
               // LOSE GAME
           }
        }
        else
        {

        }
    }
}