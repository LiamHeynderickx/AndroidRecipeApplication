package be.kuleuven.gt.project_recipe;

public class Tomato extends IngredientType {
    private int id=4;
    private int imageId = R.drawable.tomatocartoon;
    public Tomato(){
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
