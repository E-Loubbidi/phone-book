package com.tp4.tpannuairepro;

public class ItemModel {
    Integer id;
    String name;
    String job;
    String phone;
    String email;

    public ItemModel(Integer id, String name, String job, String phone, String email) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getPhone(){
        return phone;
    }

}
