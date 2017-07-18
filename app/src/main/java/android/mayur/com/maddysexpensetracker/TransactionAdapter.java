package android.mayur.com.maddysexpensetracker;

import android.app.AlarmManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cg-mayur on 23/4/17.
 */

public class TransactionAdapter extends RecyclerView.Adapter<AbstractViewHolder> {
    List<Transaction> transactionList = new ArrayList<>();
    static final int HEADER = 0;
    static final int DATA = 1;
    static final int TOTAL = 2;
    Context context;
    int type;
    float totalAmount = 0;
    SimpleDateFormat dailyFormat = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat weeklyFormat = new SimpleDateFormat("dd MMM");
    SimpleDateFormat monthlyFormat = new SimpleDateFormat("MMM yyyy");

    public TransactionAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return HeaderViewHolder.newInstance(parent);
        } else if (viewType == DATA) {
            return DataViewHolder.newInstance(parent);
        } else {
            return TotalViewHolder.newInstance(parent);
        }
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof DataViewHolder) {
            ((DataViewHolder) holder).setTransaction(transactionList.get(position - 1));
            ((DataViewHolder) holder).txtDate.setText(getDate(transactionList.get(position - 1).timestamp));
        } else if (holder instanceof TotalViewHolder) {
            ((TotalViewHolder) holder).txtTotalAmount.setText(String.valueOf(totalAmount));
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        int ret;
        if (position == 0)
            ret = HEADER;
        else if (position <= transactionList.size())
            ret = DATA;
        else
            ret = TOTAL;
        return ret;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
        totalAmount = 0;
        for (Transaction transaction : transactionList) {
            totalAmount = totalAmount + transaction.amount;
        }
    }

    String getDate(long timeStamp) {
        String retString = null;
        if (type == TypeTabAdapter.DAILY) {
            retString = dailyFormat.format(new Date(timeStamp));
        } else if (type == TypeTabAdapter.WEEKLY) {
            StringBuilder builder = new StringBuilder();
            builder.append(weeklyFormat.format(new Date(timeStamp)));
            builder.append(" - ");
            builder.append(weeklyFormat.format(new Date(timeStamp + (AlarmManager.INTERVAL_DAY * 6))));
            retString = builder.toString();
        } else {
            retString = monthlyFormat.format(new Date(timeStamp));
        }
        return retString;
    }
}
