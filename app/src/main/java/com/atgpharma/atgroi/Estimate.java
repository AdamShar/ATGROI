package com.atgpharma.atgroi; /**
 * Created by Adam on 2018-04-16.
 */

import java.io.Serializable;

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

    public Estimate(String name, String company, String email, String phone){
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
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
        this.roi_percent = Double.toString(roi_percent);
    }

    public void setPbp(double pbp) {
        this.pbp = Double.toString(pbp);
    }

    public void setRoi_dollars(double roi_dollars) {
        this.roi_dollars = Double.toString(roi_dollars);
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
        formatted += "roi_dollars+" + roi_dollars + '\n';

        return formatted;
    }
}
