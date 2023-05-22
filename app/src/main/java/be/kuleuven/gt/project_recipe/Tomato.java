package be.kuleuven.gt.project_recipe;

public class Tomato extends IngredientType {
    private int id=4;
    private String imageId;
    public Tomato(){
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
