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
                    // Create a new File object with the path to the file
                    File file = new File("ingredients.txt");

                    // Create a new Scanner object to read from the file
                    Scanner scanner = new Scanner(file);

                    // Read each line of the file
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        // Do something with the line (e.g. print it)
                       ingredientsList.add(line);
                    }

                    // Close the scanner
                    scanner.close();
                } catch (FileNotFoundException e) {
                    // Handle the FileNotFoundException
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



