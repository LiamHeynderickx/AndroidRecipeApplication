package be.kuleuven.gt.project_recipe;

public class Pasta extends IngredientType {
    private int id=5;
    private int imageId = R.drawable.pastacartoon;
    public Pasta(){
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
