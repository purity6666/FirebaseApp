package com.techta.firebaseapp1;

public class UserModel {
    private int age;
    private String name;
    private String address;
    private long zip_code;

    public UserModel(int age, String name, String address, long zipCode) {
        this.age = age;
        this.name = name;
        this.address = address;
        this.zip_code = zipCode;
    }

    public UserModel() {
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zip_code + '\'' +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getZip_code() {
        return zip_code;
    }

    public void setZip_code(long zip_code) {
        this.zip_code = zip_code;
    }
}
