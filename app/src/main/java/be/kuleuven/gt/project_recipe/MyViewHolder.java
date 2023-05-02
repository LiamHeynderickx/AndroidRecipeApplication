package be.kuleuven.gt.project_recipe;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView lblRecipeName;
    ImageButton btnRecipeSelector;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        lblRecipeName = itemView.findViewById(R.id.lblRecipeName);
        btnRecipeSelector = itemView.findViewById(R.id.btnRecipeSelector);
    }
}
