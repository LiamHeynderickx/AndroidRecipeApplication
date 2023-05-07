package be.kuleuven.gt.project_recipe; // currently not used

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolderExtra extends RecyclerView.ViewHolder {

    TextView lblRecipeName;
    ImageButton btnRecipeSelector;


    public MyViewHolderExtra(@NonNull View itemView) {
        super(itemView);
        lblRecipeName = itemView.findViewById(R.id.lblRecipeName);
//        btnRecipeSelector = itemView.findViewById(R.id.btnRecipeSelector);
    }
}
