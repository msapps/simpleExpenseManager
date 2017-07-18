package android.mayur.com.maddysexpensetracker;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cg-mayur on 23/4/17.
 */

public class TransactionLoader extends AsyncTaskLoader<List<Transaction>> implements Dao.DaoObserver {
    List<Transaction> transactionList;
    Context context;
    int type;

    public TransactionLoader(Context context, int type) {
        super(context);
        this.context = context;
        this.type = type;
    }

    @Override
    public List<Transaction> loadInBackground() {
        List<Transaction> retList = null;
        switch (type) {
            case TypeTabAdapter.DAILY: {
                if (retList == null)
                    retList = new ArrayList<>();
                retList = Transaction.getTransactions(context, TypeTabAdapter.DAILY);
                break;
            }
            case TypeTabAdapter.WEEKLY: {
                if (retList == null)
                    retList = new ArrayList<>();
                retList = Transaction.getTransactions(context, TypeTabAdapter.WEEKLY);
                break;
            }
            case TypeTabAdapter.MONTHLY: {
                if (retList == null)
                    retList = new ArrayList<>();
                retList = Transaction.getTransactions(context, TypeTabAdapter.MONTHLY);
                break;
            }
        }
        return retList;
    }

    @Override
    public void onChange() {
        transactionList = null;
        forceLoad();
        deliverResult(transactionList);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (null != transactionList) {
            deliverResult(transactionList);
        } else
            forceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        Transaction.getHelper(context).getTransactionDao().unregisterObserver(this);
    }


}
