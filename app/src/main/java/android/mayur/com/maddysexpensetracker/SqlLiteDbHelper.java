package android.mayur.com.maddysexpensetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by cg-mayur on 23/4/17.
 */

public class SqlLiteDbHelper extends OrmLiteSqliteOpenHelper {
    public static final String DB_NAME = "MADDY_EXPENSE_TRACKER_DB";
    public static final int DB_VERSION = 1;

    RuntimeExceptionDao<Transaction, Integer> transactionDao = null;

    public SqlLiteDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public boolean initializeDatabase() {
        Log.e("SqliteDbHelper", "inside initialize database");
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();

        return true;
    }

    public RuntimeExceptionDao<Transaction, Integer> getTransactionDao() {
        if (transactionDao == null) {
            transactionDao = getRuntimeExceptionDao(Transaction.class);
        }
        return transactionDao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Transaction.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
