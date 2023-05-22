package be.kuleuven.gt.project_recipe;

public class rice extends IngredientType {
    private int id=3;
    private int imageId = R.drawable.rice;
    public rice(){
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
