package com.TimothyJmartKD.jmart_android.model;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * Class Shipment
 *
 * Timothy Christian Panggabean
 * 1906355705
 */
public class Shipment
{
    public static final SimpleDateFormat ESTIMATION_FORMAT = new SimpleDateFormat("EEE MMMM dd yyyy");
    public static final Plan INSTANT = new Plan((byte)(1 << 0));
    public static final Plan SAME_DAY = new Plan((byte)(1 << 1));
    public static final Plan NEXT_DAY = new Plan((byte)(1 << 2));
    public static final Plan REGULER = new Plan((byte)(1 << 3));
    public static final Plan KARGO = new Plan((byte)(1 << 4));
    public String address;
    public int cost;
    public byte plan;
    public String receipt;

    public Shipment(String address, int cost, byte plan, String receipt)
    {
        this.address = address;
        this.cost = cost;
        this.plan = plan;
        this.receipt = receipt;
    }

    public boolean isDuration(Plan reference)
    {
        if((this.plan & reference.bit) != 0)
            return true;
        else
            return false;
    }

    public boolean isDuration(byte object, Plan reference)
    {
        if((this.plan & reference.bit) != 0)
            return true;
        else
            return false;
    }


    public String getEstimatedArrival(Date reference)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(reference);
        if(this.plan == INSTANT.bit || this.plan == SAME_DAY.bit) return ESTIMATION_FORMAT.format(reference);
        else if(this.plan == NEXT_DAY.bit)
        {
            cal.add(Calendar.DATE,1);
            return ESTIMATION_FORMAT.format(cal.getTime());
        }
        else if(this.plan == REGULER.bit)
        {
            cal.add(Calendar.DATE,2);
            return ESTIMATION_FORMAT.format(cal.getTime());
        }
        else if(this.plan == KARGO.bit)
        {
            cal.add(Calendar.DATE,5);
            return ESTIMATION_FORMAT.format(cal.getTime());
        }
        else return ESTIMATION_FORMAT.format(reference);
    }

    public static class Plan
    {
        public byte bit;

        private Plan(byte bit)
        {
            this.bit = bit;
        }
    }

}