package com.example.libeery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libeery.R;
import com.example.libeery.model.BeerRoom;
import com.example.libeery.viewModel.BeersViewModel;

import java.util.List;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.ViewHolder> {

    private List<BeerRoom> beers;
    private final BeersViewModel viewModel;

    public SecondAdapter(BeersViewModel viewModel, List<BeerRoom> beers) {
        this.viewModel = viewModel;
        this.beers = beers;
    }

    public void updateBeers(List<BeerRoom> b) {
        beers = b;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchbeer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondAdapter.ViewHolder holder, int position) {

        holder.display(beers.get(position));
    }

    @Override
    public int getItemCount() {
        return beers ==null?0: beers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private BeerRoom beer;
        public TextView hindi;
        public TextView english;
        public ImageView favoriteImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.hindi = itemView.findViewById(R.id.hindi);
            this.english = itemView.findViewById(R.id.english);

            this.favoriteImage = itemView.findViewById(R.id.favoriteImage);
            this.favoriteImage.setOnClickListener(v -> {
                beer.setFavorite(beer.getFavorite()!=1?1:0);
                display(beer);
                if(beer.getFavorite()==1)
                    viewModel.insert(beer);
                else
                    viewModel.delete(beer);
            });
        }

        public void display(BeerRoom beer) {
            this.beer = beer;

            hindi.setText(beer.getName());
            english.setText(beer.getAbv());


            if(beer.getFavorite()==1)
                favoriteImage.setImageResource(R.drawable.ic_lover);
            else
                favoriteImage.setImageResource(R.drawable.ic_like);
        }
    }
}