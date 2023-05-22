package be.kuleuven.gt.project_recipe;

public class rice extends IngredientType {
    private int id=3;
    private String imageId;
    public rice(){
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
