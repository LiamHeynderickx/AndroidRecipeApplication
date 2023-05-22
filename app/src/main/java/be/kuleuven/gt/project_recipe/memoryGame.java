package be.kuleuven.gt.project_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class memoryGame extends AppCompatActivity {
    private ImageButton btnMeat;
    private ImageButton btnLettuce;
    private ImageButton btnRice;
    private ImageButton btnTomato;
    private ImageButton btnPasta;
    private Button btnStart;
    private ImageView imageDisplayer;
    private Handler handler = new Handler();
    private TextView lblRound;


    private int round = 3;

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
        imageDisplayer = (ImageView) findViewById(R.id.imageDisplayer);
        lblRound = (TextView) findViewById(R.id.lblRound);
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

    public void onBtn_StartClicked(View Caller){

        btnStart.setVisibility(View.GONE);

        createRandomArray();

        try {
            long delayMillis = 1000; // Delay of 1 second
            Thread.sleep(delayMillis);
        } catch (InterruptedException e) {
            // Handle the exception if needed
        }

        displayImageSequence();

    }

    private void displayImageSequence() {


        for(int loop1 = 0; loop1 < ingredientOrder.size(); loop1++){
            int n = loop1;
            new Handler().postDelayed(() -> changeUi(n), 1000 * loop1);
        }
    }
    private void changeUi(int i)
    {
        int imageId = ingredientOrder.get(i).getImageId();
        imageDisplayer.setImageResource(imageId);
        Log.d("XXXXXXXXXXXXXXXXXXXXXX", "onFinish: "+ingredientOrder.get(i).getId());

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
    public void checkIfEqual() {

        boolean isEqual = true;

        if(ingredientOrder.size()==selectedOrder.size()) {
           for(int loop1 = 0; loop1 < ingredientOrder.size(); loop1++){
               if(ingredientOrder.get(loop1).getId() != selectedOrder.get(loop1).getId()){
                   isEqual = false;
                   Log.d("XXXXXXXXXXXXXXXXXXXX", "checkIfEqual: FALSE");
               }
           }
           if(isEqual){
               //win
               Log.d("XXXXXXXXXXXXXXXX", "checkIfEqual: GAME WON");
               round++;
               ingredientOrder.clear();

               lblRound.setText("ROUND "+(round-2));
           }
           else{
               //loose
               Log.d("XXXXXXXXXXXXXXXX", "checkIfEqual: GAME LOST");
           }
        }

    }
}