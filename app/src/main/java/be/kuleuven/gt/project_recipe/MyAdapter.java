package be.kuleuven.gt.project_recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


    Context context;

    private final RecyclerViewInterface recyclerViewInterface;


    List<RecipeInformation> recipeInformation;

    public MyAdapter(Context context, List<RecipeInformation> recipeInformation,
                     RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recipeInformation = recipeInformation;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(context);
        View view = inflator.inflate(R.layout.recipes_recycler_view, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.lblRecipeName.setText(recipeInformation.get(position).getRecipeName());
    }

    @Override
    public int getItemCount() {
        return recipeInformation.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageButton btnRecipeSelector;
        TextView lblRecipeName;


        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

//            btnRecipeSelector = itemView.findViewById(R.id.btnRecipeSelector);
            lblRecipeName = itemView.findViewById(R.id.lblRecipeName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onRecyclerViewItemClick(pos);
                        }
                    }
                }
            });

        }
    }


}
