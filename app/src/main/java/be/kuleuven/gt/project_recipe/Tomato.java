package be.kuleuven.gt.project_recipe;

public class Tomato extends IngredientType {
    private String id;
    private String imageId;
    public Tomato(){
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
