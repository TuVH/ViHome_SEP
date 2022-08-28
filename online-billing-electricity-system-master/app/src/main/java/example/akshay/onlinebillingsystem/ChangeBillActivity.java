package example.akshay.onlinebillingsystem;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ChangeBillActivity extends Fragment {

    View mainView;

    Customer customer;
    User unitReader;

    EditText electric_amount_old;
    EditText water_amount_old;
    EditText home_amount;
    EditText electric_amount_new;
    EditText water_amount_new;
    Button button;
    String email, name, mo_no,phoneNumber;
    DatabaseReference mRef;
    FirebaseDatabase database;
    ProgressDialog mDialog;

    Button dataSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_change_bill, container, false);

        ((HomeActivity) getActivity()).setActionBarTitle("Chi tiết điện nước");

        database = FirebaseDatabase.getInstance();
        dataSubmit = mainView.findViewById(R.id.submit_unit_data);
        electric_amount_old = mainView.findViewById(R.id.enter_electric_unit_old);
        water_amount_old = mainView.findViewById(R.id.enter_water_unit);
        home_amount = mainView.findViewById(R.id.enter_home_unit);
        electric_amount_new = mainView.findViewById(R.id.enter_electric_unit_new);
        water_amount_new = mainView.findViewById(R.id.enter_water_unit_new);
        unitReader = ((HomeActivity) getActivity()).getUnitReader();

        mRef = database.getReference("Users/Host");

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    DataSnapshot user = dataSnapshot.child(unitReader.username);
                    String a = user.child("water_amount_new").getValue(String.class);
                    String b = user.child("electric_amount_new").getValue(String.class);
                    if(a == null)
                    {
                        water_amount_old.setText("0");
                    }else {
                        water_amount_old.setText(user.child("water_amount_new").getValue(String.class));
                    }
                    if (b == null){
                        electric_amount_old.setText("0");
                    }else{
                        electric_amount_old.setText(user.child("electric_amount_new").getValue(String.class));
                    }
                    water_amount_new.setText("");
                    electric_amount_new.setText("");
                    home_amount.setText(user.child("home_amount").getValue(String.class));
                } catch (NullPointerException e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        dataSubmit.setOnClickListener(view -> {
                    unitReader.electric_amount_new = electric_amount_new.getText().toString();
                    unitReader.electric_amount = electric_amount_old.getText().toString();
                    unitReader.water_amount = water_amount_old.getText().toString();
                    unitReader.water_amount_new = water_amount_new.getText().toString();
                    unitReader.home_amount = home_amount.getText().toString();


                    mRef = database.getReference("Users/Host");
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mRef.child(unitReader.username).setValue(unitReader);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });



                }
        );
        return mainView;
    }
}
