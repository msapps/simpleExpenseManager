package android.mayur.com.maddysexpensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cg-mayur on 23/4/17.
 */

public class HeaderViewHolder extends AbstractViewHolder {
    public HeaderViewHolder(View itemView) {
        super(itemView);
    }

    public static HeaderViewHolder newInstance(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_head, parent, false);
        return new HeaderViewHolder(view);
    }
}
