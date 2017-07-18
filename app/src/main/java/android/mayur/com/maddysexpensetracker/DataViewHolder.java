package android.mayur.com.maddysexpensetracker;

import android.databinding.DataBindingUtil;
import android.mayur.com.maddysexpensetracker.databinding.TransactionDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cg-mayur on 23/4/17.
 */

public class DataViewHolder extends AbstractViewHolder {
    TextView txtDate;
    TransactionDataBinding dataBinding;

    public DataViewHolder(View itemView) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
        txtDate = (TextView) itemView.findViewById(R.id.txt_date);
    }

    public static DataViewHolder newInstance(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_data, parent, false);
        return new DataViewHolder(view);
    }

    void setTransaction(Transaction transaction) {
        dataBinding.setTransaction(transaction);
    }
}
