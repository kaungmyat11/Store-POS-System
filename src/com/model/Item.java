package com.model;

public class Item {
    private String code;
    private String name;
    private String brand;
    private int stock;
    private int retailPrice;
    private int wholesalePrice;
    private int supplierId;

    public Item(String code, String name, String brand, int stock, int retailPrice, int wholesalePrice, int supplierId) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.stock = stock;
        this.retailPrice = retailPrice;
        this.wholesalePrice = wholesalePrice;
        this.supplierId = supplierId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(int wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }


    public String getSupplierName() {
        DbConnect dbConnect = new DbConnect();
        System.out.println(this.supplierId);
        return (dbConnect.getSupplierName(this.supplierId));
    }
}
