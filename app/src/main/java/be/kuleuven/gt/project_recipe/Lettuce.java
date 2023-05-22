package be.kuleuven.gt.project_recipe;

public class Lettuce extends IngredientType {
    private String id;
    private String imageId;
    public Lettuce(){
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
