package com.lambda.bean;

public class Employee {

    private String name;

    private Integer age;

    private float price;

    private Status status;

    public Employee(){
    }
    public Employee(String name, Integer age, float price) {
        this.name = name;
        this.age = age;
        this.price = price;
    }

    public Employee(String name, Integer age, float price, Status status) {
        this.name = name;
        this.age = age;
        this.price = price;
        this.status = status;
    }

    public Employee(float price){
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public float getPrice() {
        return price;
    }

    public float getPrice1(float price){
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", price=" + price +
                ", status=" + status +
                '}';
    }

    public enum Status{
        FREE,
        BUSY,
        VICATION;
    }
}
