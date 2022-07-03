package example.akshay.onlinebillingsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {

    private Context context;
    private List<AddBill> billList;

    public DetailAdapter(Context context, List<AddBill> billList) {
        this.context = context;
        this.billList = billList;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_card, parent, false);
        return new DetailViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        AddBill addBill = billList.get(position);

        holder.water_unit_total.setText("₹ " + addBill.water_amount);
        holder.electric_unit_total.setText("" + addBill.electric_amount);
        holder.home_unit_total.setText("" + addBill.home_amount);
        int total = addBill.water_amount + addBill.electric_amount +  addBill.home_amount;
        holder.totalAll_unit_total.setText("" + total);
        holder.bill_no.setText("" + addBill.bill_no);
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {

        TextView water_unit_total, electric_unit_total, home_unit_total, totalAll_unit_total, bill_no;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            bill_no = itemView.findViewById(R.id.bill_no);
            water_unit_total = itemView.findViewById(R.id.water_unit_total);
            electric_unit_total = itemView.findViewById(R.id.electric_unit_total);
            home_unit_total = itemView.findViewById(R.id.home_unit_total);
            totalAll_unit_total = itemView.findViewById(R.id.totalAll_unit_total);
        }
    }
}
