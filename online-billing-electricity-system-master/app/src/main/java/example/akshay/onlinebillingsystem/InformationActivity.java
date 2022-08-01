package example.akshay.onlinebillingsystem;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class InformationActivity extends Fragment {

    View mainView;

    Customer customer;
    User unitReader;

    EditText editTextEmail, editTextName, editTextMono,editTextPhoneNumber;
    Button accept_button;
    String email, name, mo_no,phoneNumber;
    DatabaseReference mRef;
    FirebaseDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_information, container, false);
        editTextEmail = mainView.findViewById(R.id.emailInfo);
        editTextName = mainView.findViewById(R.id.nameInfo);
        editTextMono = mainView.findViewById(R.id.mo_noInfo);
        accept_button = mainView.findViewById(R.id.accept_button);
        editTextPhoneNumber = mainView.findViewById(R.id.phoneNumberInfo);
        database = FirebaseDatabase.getInstance();

        try {
            ((HomeActivity) getActivity()).setActionBarTitle("Thông tin cá nhân");
            unitReader = ((HomeActivity) getActivity()).getUnitReader();
            email = unitReader.email;
            name = unitReader.name;
            phoneNumber = unitReader.phoneNumber;

            editTextEmail.setText(email);
            editTextName.setText(name);
            editTextPhoneNumber.setText(phoneNumber);
        } catch (Exception e) {
            ((CustomerActivity) getActivity()).setActionBarTitle("Thông tin cá nhân");
            customer = ((CustomerActivity) getActivity()).getCustomer();
            email = customer.email;
            name = customer.name;
            mo_no = customer.mo_no;
            phoneNumber = customer.phoneNumber;

            editTextPhoneNumber.setText(phoneNumber);
            editTextEmail.setText(email);
            editTextName.setText(name);
            editTextMono.setText(mo_no);
        }

        accept_button.setOnClickListener(view -> {
            try {
                ((HomeActivity) getActivity()).setActionBarTitle("Thông tin cá nhân");
                unitReader = ((HomeActivity) getActivity()).getUnitReader();
                email = unitReader.email;
                name = unitReader.name;
                phoneNumber = unitReader.phoneNumber;

                unitReader.email = editTextEmail.getText().toString();
                unitReader.name = editTextName.getText().toString();
                unitReader.phoneNumber = editTextPhoneNumber.getText().toString();

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

            } catch (Exception e) {
                ((CustomerActivity) getActivity()).setActionBarTitle("Thông tin cá nhân");
                customer = ((CustomerActivity) getActivity()).getCustomer();
                email = customer.email;
                name = customer.name;
                mo_no = customer.mo_no;
                phoneNumber = customer.phoneNumber;

                customer.email = editTextEmail.getText().toString();
                customer.name = editTextName.getText().toString();
                customer.phoneNumber = editTextPhoneNumber.getText().toString();

                mRef = database.getReference("Users/Customer");
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mRef.child(customer.username).setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    alertDialog("Thành công", "Chỉnh sửa thông tin");
                                } else {
                                }
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

        });



        return mainView;
    }
    private void alertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainView.getContext());
        alertDialogBuilder.setTitle(title).setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
