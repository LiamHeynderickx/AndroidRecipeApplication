package be.kuleuven.gt.project_recipe;

public class Lettuce extends IngredientType {
    private int id=2;
    private String imageId;
    public Lettuce(){
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
