package com.atgpharma.atgroi; /**
 * Created by Adam on 2018-04-16.
 */

import android.util.Log;

import java.io.Serializable;
import java.text.NumberFormat;

@SuppressWarnings("serial")
public class Estimate implements Serializable{

    private String name;
    private String company;
    private String email;
    private String phone;

    private String num_operators;
    private String hourly_pay;
    private String hours_per_week;
    private String bottles_per_operator;

    private String roi_percent;
    private String pbp;
    private String roi_dollars;
    private String machine_type;

    public Estimate(String name, String company, String email, String phone){
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
    }

    public Estimate(String[] info){
        this.name = info[0];
        this.company = info[1];
        this.email = info[2];
        this.phone = info[3];

        this.num_operators = info[4];
        this.hourly_pay = info[5];
        this.hours_per_week = info[6];
        this.bottles_per_operator = info[7];

        this.roi_percent = info[8];
        this.pbp = info[9];
        this.roi_dollars = info[10];
        this.machine_type = info[11];
    }

    public void setNum_operators(double num_operators) {
        this.num_operators = Double.toString(num_operators);
    }

    public void setHourly_pay(double hourly_pay) {
        this.hourly_pay = Double.toString(hourly_pay);
    }

    public void setHours_per_week(double hours_per_week) {
        this.hours_per_week = Double.toString(hours_per_week);
    }

    public void setBottles_per_operator(double bottles_per_operator) {
        this.bottles_per_operator = Double.toString(bottles_per_operator);
    }

    public void setRoi_percent(double roi_percent) {
        this.roi_percent = String.format("%.2f%%", roi_percent);
    }

    public void setPbp(double pbp) {
        this.pbp =  String.format("%.1f", pbp);
    }

    public void setRoi_dollars(double roi_dollars) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        this.roi_dollars = formatter.format(roi_dollars);
    }

    public void setMachine_type(String machine_type) {
        this.machine_type = machine_type;
    }

    public String getEmail() {
        return email;
    }

    public String getFormattedEmail(){
        return "hey";
    }

    public String getFormattedInfo() {
        String formatted;

        formatted = "name=" + name + '\n';
        formatted += "company=" + company + '\n';
        formatted += "email=" + email + '\n';
        formatted += "phone=" + phone + '\n';

        formatted += "num_operators=" + num_operators + '\n';
        formatted += "hourly_pay=" + hourly_pay + '\n';
        formatted += "hours_per_week=" + hours_per_week + '\n';
        formatted += "bottles_per_operator=" + bottles_per_operator + '\n';

        formatted += "roi_percent=" + roi_percent + '\n';
        formatted += "pbp=" + pbp + '\n';
        formatted += "roi_dollars=" + roi_dollars + '\n';
        formatted += "machine_type=" + machine_type + '\n';

        return formatted;
    }

    public String getCSVFormattedInfo() {
        String formatted;

        formatted = name + ",";
        formatted += company + ",";
        formatted += email + ",";
        formatted += phone + ",";

        formatted += num_operators + ",";
        formatted += hourly_pay + ",";
        formatted += hours_per_week + ",";
        formatted += bottles_per_operator + ",";

        formatted += roi_percent + ",";
        formatted += pbp + ",";
        formatted += roi_dollars.replace(",", "") + ",";
        formatted += machine_type + '\n';

        Log.d("ADAMLOG", formatted);

        return formatted;
    }

    @Override
    public String toString() {
        String formatted;

        formatted = "Name: " + name + '\n';
        formatted += "Company: " + company + '\n';
        formatted += "Email: " + email + '\n';
        formatted += "Phone: " + phone + '\n';

        formatted += "Number of Operators: " + num_operators + '\n';
        formatted += "Hourly Pay: " + hourly_pay + '\n';
        formatted += "Hours Per Week: " + hours_per_week + '\n';
        formatted += "Bottles Per Operator Per Day: " + bottles_per_operator + '\n';

        formatted += "Return on Investment (%): " + roi_percent + '\n';
        formatted += "Product Buyback Period (Months): " + pbp + '\n';
        formatted += "Return on Investment ($): " + roi_dollars + '\n';
        formatted += "Machine Selected: " + machine_type + '\n';

        return formatted;
    }
}
