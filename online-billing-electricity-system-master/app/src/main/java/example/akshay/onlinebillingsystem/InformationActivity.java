package example.akshay.onlinebillingsystem;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class InformationActivity extends Fragment {

    View mainView;

    Customer customer;
    User unitReader;

    EditText editTextEmail, editTextName, editTextMono,editTextPhoneNumber;
    Button button;
    String email, name, mo_no,phoneNumber;
    DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_information, container, false);
        editTextEmail = mainView.findViewById(R.id.emailInfo);
        editTextName = mainView.findViewById(R.id.nameInfo);
        editTextMono = mainView.findViewById(R.id.mo_noInfo);
        editTextPhoneNumber = mainView.findViewById(R.id.phoneNumberInfo);
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
//
//        mRef = FirebaseDatabase.getInstance().getReference("/Contact Query");
//
//        emailT = mainView.findViewById(R.id.email);
//        emailT.setText(email);
//        subjectT = mainView.findViewById(R.id.subject);
//        descT = mainView.findViewById(R.id.description);
//        button = mainView.findViewById(R.id.contact_button);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                subject = subjectT.getText().toString().trim();
//                desc = descT.getText().toString().trim();
//
//                Map<String, String> map = new HashMap<>();
//                map.put("email", email);
//                map.put("subject", subject);
//                map.put("description", desc);
//
//                mRef.push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(getActivity().getApplicationContext(),"Query Added",Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        return mainView;
    }
}
