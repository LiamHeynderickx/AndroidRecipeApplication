package be.kuleuven.gt.project_recipe;

public class Meat extends IngredientType{
    private int id=1;
    private int imageId = R.drawable.meat;
    public Meat(){
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getImageId() {
        return imageId;
    }
}
