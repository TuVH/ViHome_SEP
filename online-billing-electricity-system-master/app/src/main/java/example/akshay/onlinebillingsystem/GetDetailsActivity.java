package example.akshay.onlinebillingsystem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class GetDetailsActivity extends Fragment {

    View mainView;

    LinearLayout detailsLayout;
    EditText meterNoEditText;
    Button getDetailButton, submitUnitButton, resetButton,getDetailButtonBill;
    TextView nameTextView, monoTextView, pendingTextView, phoneNumberTextView;
    User unitReader;

    String meterNo;
    int intMeterNo, waterUnit, electricUnit, homeUnit, pendingAmount;

    FirebaseDatabase database;
    DatabaseReference mCustomerRef, mBillInfo;
    ProgressDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_get_details, container, false);

        ((HomeActivity) getActivity()).setActionBarTitle("Thêm Hóa đơn");


        meterNoEditText = mainView.findViewById(R.id.get_meter_no);
        nameTextView = mainView.findViewById(R.id.dis_name);
        monoTextView = mainView.findViewById(R.id.dis_mono);
        pendingTextView = mainView.findViewById(R.id.dis_pending);
        phoneNumberTextView = mainView.findViewById(R.id.dis_phoneNumber);
        getDetailButtonBill = mainView.findViewById(R.id.find_info);

        database = FirebaseDatabase.getInstance();
        mCustomerRef = database.getReference("Users/Customer");

        detailsLayout = mainView.findViewById(R.id.details_layout);
        detailsLayout.setVisibility(View.GONE);

        getDetailButton = mainView.findViewById(R.id.get_details_button);
        getDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meterNo = meterNoEditText.getText().toString();
                if (!meterNo.equals("")) {
                    mDialog = ProgressDialog.show(mainView.getContext(), "Loading...", "Đang tìm kiếm...", true);
                    mBillInfo = database.getReference("Bill Info/" + meterNo);
                    fetchOldData();

                    Query query = FirebaseDatabase.getInstance().getReference("Users/Customer")
                            .orderByChild("mo_no").equalTo(meterNo);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                detailsLayout.setVisibility(View.VISIBLE);
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Customer customer = snapshot.getValue(Customer.class);
                                    nameTextView.setText(customer.name);
//                                    cnoTextView.setText("" + customer.c_no);
//                                    mnoTextView.setText("" + customer.meter_no);
//                                    pendingAmountTextView.setText("" + pendingAmount);
//                                    lastUnitTextView.setText("" + lastUnit);
                                    monoTextView.setText(customer.mo_no);
                                    phoneNumberTextView.setText(customer.phoneNumber);
                                    //pending
                                    mDialog.dismiss();
                                }
                            } else {
                                mDialog.dismiss();
                                meterNoEditText.requestFocus();
                                meterNoEditText.setError("Không tìm thấy khách hàng");
                                //meterNoEditText.setText("");
                                detailsLayout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                } else {
                    meterNoEditText.setError("Nhập lại số hóa đơn");
                }
            }
        });


        getDetailButtonBill.setOnClickListener(view -> {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,new GetBillActivity()).addToBackStack("tag").commit();
        });


        submitUnitButton = mainView.findViewById(R.id.submit_unit_data);
        submitUnitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    unitReader = ((HomeActivity) getActivity()).getUnitReader();

                    waterUnit = Integer.parseInt(unitReader.water_amount_new) - Integer.parseInt(unitReader.water_amount);
                    electricUnit = Integer.parseInt(unitReader.electric_amount_new) - Integer.parseInt(unitReader.electric_amount);
                    homeUnit = Integer.parseInt(unitReader.home_amount);

                        //caculator
                        final int water_amount = waterUnit * 20000;
                        final int electric_amount = electricUnit * 2500;
                        final int home_amount = homeUnit;
                        final int totalCount = water_amount + electric_amount + home_amount;

                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle("Thông báo").setMessage("Đơn vị nước hiện tại: m3"+
                                "\nĐơn vị sử dụng: " + waterUnit + "\nTổng: " + water_amount + "\nĐơn vị điện hiện tại: kwh"+
                                        "\nĐơn vị sử dụng: " + electricUnit + "\nTổng: " + electric_amount + "\nTổng tiền nhà: " +
                                        home_amount + "\nTổng cộng: " + totalCount)
                                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDialog = ProgressDialog.show(mainView.getContext(), "Loading...", "Vui lòng chờ...", true);
                                        //getBillNo();

                                        final DatabaseReference billNoRef = database.getReference("/Data");
                                        billNoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                try {
                                                    int billNo = 0;
                                                    if (dataSnapshot.child("bill_no").getValue(Integer.class) == null){
                                                        billNo = 0;
                                                    }else if (dataSnapshot.child("bill_no").getValue(Integer.class) != null){
                                                        billNo = dataSnapshot.child("bill_no").getValue(Integer.class) +1;
                                                    }
                                                    billNoRef.child("bill_no").setValue(billNo);

                                                    AddBill addBill = new AddBill(billNo, water_amount, electric_amount,home_amount, "Đang xử lý");
                                                    mBillInfo.child(labelOfBillInfo()).setValue(addBill).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                mDialog.dismiss();
                                                                alertDialog("Thành công", "Hóa đơn đã được thêm");
                                                            } else {
                                                                mDialog.dismiss();
                                                                alertDialog("lỗi", "Vui lòng thử lại");
                                                            }
                                                        }
                                                    });
                                                } catch (NullPointerException e) {
                                                    Log.d("Mã hóa đơn: ", "không có");
                                                    mDialog.dismiss();
                                                    alertDialog("Lỗi", "Vui lòng thử lại");
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                    }
            }
        });

        resetButton = mainView.findViewById(R.id.reset_unit_data);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return mainView;
    }


    private void fetchOldData() {
        mBillInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    waterUnit = dataSnapshot.child("water_amount").getValue(Integer.class);
                    electricUnit = dataSnapshot.child("electric_amount").getValue(Integer.class);
                    homeUnit = dataSnapshot.child("home_amount").getValue(Integer.class);
                } catch (NullPointerException e) {
                    waterUnit = 0;
                    electricUnit = 0;
                    homeUnit = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String labelOfBillInfo() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        String yearString = "" + year;
        String monthString = "" + (month + 1);
        if (month <= 8) {
            monthString = "0" + monthString;
        }
        return yearString + monthString;
    }

    private void alertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mainView.getContext());
        alertDialogBuilder.setTitle(title).setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        detailsLayout.setVisibility(View.GONE);
                        meterNoEditText.requestFocus();
                        //meterNoEditText.setText("");
                    }
                }).show();
    }
}
