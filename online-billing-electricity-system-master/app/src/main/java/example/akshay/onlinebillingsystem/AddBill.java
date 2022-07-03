package example.akshay.onlinebillingsystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddBill {
    public int bill_no;
    public int water_amount;
    public int electric_amount;
    public int home_amount;
    public String status;

    AddBill(){}

    AddBill(int bill_no, int water_amount, int electric_amount,int home_amount, String status){
        this.bill_no = bill_no;
        this.water_amount= water_amount;
        this.electric_amount= electric_amount;
        this.home_amount= home_amount;
        this.status = status;

    }
}
