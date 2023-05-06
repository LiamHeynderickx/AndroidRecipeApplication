package be.kuleuven.gt.project_recipe;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{


    Context context;


    List<RecipeInformation> recipeInformation;

    public MyAdapter(Context context, List<RecipeInformation> recipeInformation) {
        this.context = context;
        this.recipeInformation = recipeInformation;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recipes_recycler_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.lblRecipeName.setText(recipeInformation.get(position).getRecipeName());
    }

    @Override
    public int getItemCount() {
        return recipeInformation.size();
    }




}
