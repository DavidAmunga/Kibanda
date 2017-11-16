package com.labs.tatu.kibanda.model;

import java.util.List;

/**
 * Created by amush on 07-Oct-17.
 */

public class Request {
    private String phone,address,total,name,status;
    private List<Order> foods;

    public Request(String phone,String name,String address, String total, List<Order> foods) {
        this.phone = phone;
        this.address = address;
        this.name=name;
        this.total = total;
        this.foods = foods;
        this.status="0"; //Default is 0; Placed 0; Shipping 1,Shipping 2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Request()
    {

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
