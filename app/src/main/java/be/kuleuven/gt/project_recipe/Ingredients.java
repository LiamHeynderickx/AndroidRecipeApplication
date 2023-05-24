package be.kuleuven.gt.project_recipe;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Ingredients {
    public ArrayList<String> ingredientsList = new ArrayList<>();

    public Ingredients(){
        populateList();
    }

    public void populateList()  {
        try {
                    File file = new File("ingredients.txt");
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                       ingredientsList.add(line);
                    }
                    scanner.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    public ArrayList<String> getIngredientsList()
    {
        return ingredientsList;
    }
    public void printTest()
    {
        for(int i=0; i<6;i++)
        {
            System.out.println(ingredientsList.get(i));
        }
    }

}



