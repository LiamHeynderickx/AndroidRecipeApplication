package be.kuleuven.gt.project_recipe;

public class Lettuce extends IngredientType {
    private int id=2;
    private int imageId = R.drawable.lettuce;
    public Lettuce(){
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getImageId() {
        return imageId;
    }
}
