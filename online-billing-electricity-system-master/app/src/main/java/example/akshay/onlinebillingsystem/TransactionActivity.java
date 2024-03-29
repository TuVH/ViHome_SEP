package example.akshay.onlinebillingsystem;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TransactionActivity extends Fragment {

    View mainView;
    Customer mCustomer;

    private RecyclerView recyclerView;
    private DetailAdapter adapter;
    private List<AddBill> billList;

    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_transaction, container, false);

        ((CustomerActivity) getActivity()).setActionBarTitle("Chi tiết hóa đơn");
        mCustomer = ((CustomerActivity) getActivity()).getCustomer();

        recyclerView = mainView.findViewById(R.id.transactionRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        billList = new ArrayList<>();
        adapter = new DetailAdapter(getActivity().getApplicationContext(), billList);
        recyclerView.setAdapter(adapter);

        mRef = FirebaseDatabase.getInstance().getReference("/Bill Info/");

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                billList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        try {
                            if (Objects.equals(mCustomer.mo_no, snapshot.getKey())){
                                Iterable<DataSnapshot> dataSnapshots =  snapshot.getChildren();
                                for (DataSnapshot t: dataSnapshots) {
                                    AddBill addBill = t.getValue(AddBill.class);
                                    addBill.month = t.getKey();
                                    if (addBill != null){
                                        billList.add(addBill);
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mainView;
    }

}
