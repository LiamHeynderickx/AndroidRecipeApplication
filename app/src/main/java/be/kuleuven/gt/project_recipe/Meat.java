package be.kuleuven.gt.project_recipe;

public class Meat extends IngredientType{
    private int id=1;
    private String imageId;
    public Meat(){
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getImageId() {
        return imageId;
    }
}
