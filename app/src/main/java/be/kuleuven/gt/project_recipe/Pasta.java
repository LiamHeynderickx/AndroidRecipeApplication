package be.kuleuven.gt.project_recipe;

public class Pasta extends IngredientType {
    private String id;
    private String imageId;
    public Pasta(){
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
