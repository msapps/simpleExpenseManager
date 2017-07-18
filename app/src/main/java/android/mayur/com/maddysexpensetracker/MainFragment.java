package android.mayur.com.maddysexpensetracker;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static final String TAG = MainFragment.class.getSimpleName();
    View view;
    TabLayout mainTabLayout;
    ViewPager typeViewPager;
    TypeTabAdapter tabAdapter;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeControls();
        tabAdapter = new TypeTabAdapter(getActivity().getSupportFragmentManager());
        typeViewPager.setAdapter(tabAdapter);
        mainTabLayout.setupWithViewPager(typeViewPager);
        typeViewPager.setCurrentItem(0);
        typeViewPager.setOffscreenPageLimit(2);


        MainActivity.addEntryFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddExpenseDialog(container);
            }
        });
        return view;
    }

    void initializeControls() {
        mainTabLayout = (TabLayout) view.findViewById(R.id.type_tablayout);
        typeViewPager = (ViewPager) view.findViewById(R.id.type_pager);
    }

    void showAddExpenseDialog(ViewGroup container) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(R.string.string_add_expense);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_add_expense, null);
        //alertDialog.setView(R.layout.layout_add_expense);
        alertDialog.setView(dialogView);
        final EditText amountEtxt = (EditText) dialogView.findViewById(R.id.etxt_amount);
        final EditText descriptionEtxt = (EditText) dialogView.findViewById(R.id.etxt_description);
        alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(amountEtxt.getText().toString().trim()) || TextUtils.isEmpty(descriptionEtxt.getText().toString().trim()))
                    Snackbar.make(view, "All fields mandatory", Snackbar.LENGTH_SHORT).show();
                else {
                    addExpenseEntry(Float.parseFloat(amountEtxt.getText().toString().trim()), descriptionEtxt.getText().toString().trim());
                    Snackbar.make(view, "Transaction Saved Successfully!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    void addExpenseEntry(float amount, String description) {
        Log.e(TAG, amount + " " + description);
        Transaction transaction = new Transaction();
        transaction.amount = amount;
        transaction.description = description;
        transaction.timestamp = System.currentTimeMillis();
        Transaction.saveTransaction(getActivity(), transaction);
        StatsFragment.transactionAdapter.notifyDataSetChanged();
    }
}
