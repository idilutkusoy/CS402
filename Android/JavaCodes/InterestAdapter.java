package tr.edu.ozu.ozunaviclient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by idilutkusoy on 13.11.2017.
 */

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.MyViewHolder> {

    private List<Interest> interestList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public RatingBar rate;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.interestName);
            rate = (RatingBar) view.findViewById(R.id.interestRate);
        }
    }


    public InterestAdapter(List<Interest> interestList) {
        this.interestList = interestList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_interest, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Interest interest = interestList.get(position);
        holder.name.setText(interest.getName());
        holder.rate.setRating(interest.getRate());
        holder.rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                interest.setRate(ratingBar.getNumStars());
            }
        });
    }

    @Override
    public int getItemCount() {
        return interestList.size();

    }
}
