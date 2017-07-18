package android.mayur.com.maddysexpensetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Transaction>> {
    View view;
    RecyclerView recyclerView;
    public static TransactionAdapter transactionAdapter;
    int type;
    TextView txtNoData;

    public StatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stats, container, false);
        getLoaderManager().initLoader(1, null, this);
        txtNoData = (TextView) view.findViewById(R.id.txt_no_data);
        if (getArguments() != null && getArguments().containsKey("SELECTION"))
            type = getArguments().getInt("SELECTION");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_stats);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public Loader<List<Transaction>> onCreateLoader(int id, Bundle args) {
        return new TransactionLoader(getActivity(), type);
    }

    @Override
    public void onLoadFinished(Loader<List<Transaction>> loader, List<Transaction> data) {
        if (data.size() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            transactionAdapter = new TransactionAdapter(getActivity(), type);
            transactionAdapter.setTransactionList(data);
            recyclerView.setAdapter(transactionAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Transaction>> loader) {

    }


}
