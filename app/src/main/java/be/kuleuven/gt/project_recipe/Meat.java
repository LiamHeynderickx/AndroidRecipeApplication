package be.kuleuven.gt.project_recipe;

public class Meat extends IngredientType{
    private String id;
    private String imageId;
    public Meat(){
    }
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getImageId() {
        return imageId;
    }
}
