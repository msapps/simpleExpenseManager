package android.mayur.com.maddysexpensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cg-mayur on 23/4/17.
 */

public class TotalViewHolder extends AbstractViewHolder {
    TextView txtTotalAmount;

    public TotalViewHolder(View itemView) {
        super(itemView);
        txtTotalAmount = (TextView) itemView.findViewById(R.id.txt_total_amount);
    }

    public static TotalViewHolder newInstance(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_total, parent, false);
        return new TotalViewHolder(view);
    }
}
