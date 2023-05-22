package be.kuleuven.gt.project_recipe;

public class Pasta extends IngredientType {
    private int id=5;
    private String imageId;
    public Pasta(){
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
