package com.labs.tatu.kibanda.model;

/**
 * Created by amush on 06-Oct-17.
 */

public class Food {
    private String Description,Discount,Image,Name,Price,MenuId;

    public Food()
    {

    }

    public Food(String description, String discount, String image, String name, String price, String menuId) {
        Description = description;
        Discount = discount;
        Image = image;
        Name = name;
        Price = price;
        MenuId = menuId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
