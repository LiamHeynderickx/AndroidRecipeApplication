package be.kuleuven.gt.project_recipe;

public class rice extends IngredientType {
    private String id;
    private String imageId;
    public rice(){
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
