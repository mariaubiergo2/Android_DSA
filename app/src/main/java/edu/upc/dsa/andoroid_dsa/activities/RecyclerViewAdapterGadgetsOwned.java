package edu.upc.dsa.andoroid_dsa.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.upc.dsa.andoroid_dsa.R;
import edu.upc.dsa.andoroid_dsa.models.Gadget;

public class RecyclerViewAdapterGadgetsOwned extends RecyclerView.Adapter<RecyclerViewAdapterGadgetsOwned.ViewHolder>{
    private static RecycleClickViewListener listener;
    public List<Gadget> gadgets;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView id,cost,description;
        ImageView fotoGadget;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=(TextView)itemView.findViewById(R.id.idGadgetOwned);
            cost=(TextView)itemView.findViewById(R.id.costGadgetOwned);
            description=(TextView)itemView.findViewById(R.id.descriptionGadgetOwned);
            fotoGadget=(ImageView) itemView.findViewById(R.id.imgGadgetOwned);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            listener.recyclerViewListClicked(this.getLayoutPosition());
        }
    }

    public RecyclerViewAdapterGadgetsOwned(List<Gadget> gadgets, RecycleClickViewListener listener) {
        this.gadgets = gadgets;
        this.listener =listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_gadget_owned,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(gadgets.get(position).getIdGadget());
        holder.description.setText(gadgets.get(position).getDescription());
        holder.cost.setText(Integer.toString(gadgets.get(position).getCost()));
        Picasso.get().load(gadgets.get(position).getUnityShape()).into(holder.fotoGadget);
    }

    @Override
    public int getItemCount() {
        return gadgets.size();
    }

}
