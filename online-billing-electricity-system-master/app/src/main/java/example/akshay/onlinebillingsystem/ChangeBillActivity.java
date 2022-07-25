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

public class ChangeBillActivity extends Fragment {

    View mainView;

    Customer customer;
    User unitReader;

    EditText electric_amount_old, water_amount_old, home_amount,electric_amount_new, water_amount_new;
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

        database = FirebaseDatabase.getInstance();
        dataSubmit = mainView.findViewById(R.id.submit_unit_data);
        electric_amount_old = mainView.findViewById(R.id.enter_electric_unit_old);
        water_amount_old = mainView.findViewById(R.id.enter_water_unit);
        home_amount = mainView.findViewById(R.id.enter_home_unit);
        electric_amount_new = mainView.findViewById(R.id.enter_electric_unit_new);
        electric_amount_new = mainView.findViewById(R.id.enter_water_unit_new);
        unitReader = ((HomeActivity) getActivity()).getUnitReader();



        dataSubmit.setOnClickListener(view -> {


            if (electric_amount_new.getText().toString() == null || electric_amount_new.getText().toString() == "" || electric_amount_new.getText().toString() == null || electric_amount_new.getText().toString() == "" || electric_amount_new.getText().toString() == null || electric_amount_new.getText().toString() == ""){

            }
            int electricamount =Integer.parseInt(electric_amount_new.getText().toString()) - Integer.parseInt(electric_amount_old.getText().toString());
            int wateramount = Integer.parseInt(electric_amount_new.getText().toString()) - Integer.parseInt(water_amount_old.getText().toString());
            int homeamount = Integer.parseInt(home_amount.getText().toString());

            unitReader.electric_amount = String.valueOf(electricamount);
            unitReader.water_amount = String.valueOf(wateramount);
            unitReader.home_amount = String.valueOf(homeamount);

            mRef = database.getReference("Users/Unit Reader");
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mRef.child(unitReader.username).setValue(unitReader).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                            } else {
                            }
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        });
        return mainView;
    }
}
