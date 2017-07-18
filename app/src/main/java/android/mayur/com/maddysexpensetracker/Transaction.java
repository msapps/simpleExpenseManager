package android.mayur.com.maddysexpensetracker;

import android.app.AlarmManager;
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by cg-mayur on 23/4/17.
 */
@DatabaseTable(tableName = "transaction")
public class Transaction {

    static SqlLiteDbHelper dbHelper = null;

    @DatabaseField(columnName = "id", generatedId = true)
    public int id;

    @DatabaseField(columnName = "amount")
    public float amount;

    @DatabaseField(columnName = "description")
    public String description;

    @DatabaseField(columnName = "timestamp")
    public long timestamp;

    public Transaction() {
    }

    public static void saveTransaction(Context context, Transaction transaction) {
        RuntimeExceptionDao<Transaction, Integer> transactionDao = getHelper(context).getTransactionDao();
        transactionDao.createOrUpdate(transaction);
    }

    public static List<Transaction> getAllTransactions(Context context) {
        RuntimeExceptionDao<Transaction, Integer> transactionDao = getHelper(context).getTransactionDao();
        QueryBuilder<Transaction, Integer> queryBuilder = transactionDao.queryBuilder();
        List<Transaction> allTransactions = new ArrayList<>();
        try {
            allTransactions = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTransactions;
    }

    public static List<Transaction> getTransactions(Context context, int type) {
        List<Transaction> allTransactions = new ArrayList<>();
        List<Transaction> retList = new ArrayList<>();
        RuntimeExceptionDao<Transaction, Integer> transactionDao = getHelper(context).getTransactionDao();
        allTransactions = getAllTransactions(context);

        if (allTransactions.size() > 0) {
            Transaction firstTransaction = allTransactions.get(0);
            long date = firstTransaction.timestamp;
            Calendar calendar = Calendar.getInstance();
            long startDate = getMidnightTimeStamp(date);
            retList = getInitialRetList(startDate, type);
            for (Transaction transaction : allTransactions) {
                for (int i = 0; i < retList.size(); i++) {
                    if (retList.get(i).timestamp < transaction.timestamp) {
                        retList.get(i).amount = retList.get(i).amount + transaction.amount;
                        //retList.add(transaction);
                        break;
                    }
                }
            }
        }
        return retList;
    }

    static List<Transaction> getInitialRetList(long startDate, int type) {
        List<Transaction> retList = new ArrayList<>();
        switch (type) {
            case TypeTabAdapter.DAILY: {
                while (startDate < System.currentTimeMillis()) {
                    Transaction transaction = new Transaction();
                    transaction.timestamp = startDate;
                    transaction.amount = 0;
                    retList.add(transaction);
                    startDate = startDate + AlarmManager.INTERVAL_DAY;
                }
                break;
            }
            case TypeTabAdapter.WEEKLY: {
                while (startDate < System.currentTimeMillis()) {
                    Transaction transaction = new Transaction();
                    transaction.timestamp = startDate;
                    transaction.amount = 0;
                    retList.add(transaction);
                    startDate = startDate + (AlarmManager.INTERVAL_DAY * 7);
                }
                break;
            }
            case TypeTabAdapter.MONTHLY: {
                while (startDate < System.currentTimeMillis()) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(startDate);
                    Transaction transaction = new Transaction();
                    transaction.timestamp = cal.getTimeInMillis();
                    transaction.amount = 0;
                    retList.add(transaction);
                    cal.add(Calendar.MONTH, 1);
                    startDate = cal.getTimeInMillis();
                    //startDate = startDate + (AlarmManager.INTERVAL_DAY * 30);
                }
                break;
            }
        }
        return retList;
    }

    static long getMidnightTimeStamp(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }


    public static SqlLiteDbHelper getHelper(Context context) {
        if (dbHelper == null) {
            dbHelper = OpenHelperManager.getHelper(context,
                    SqlLiteDbHelper.class);
        }
        return dbHelper;
    }


}
