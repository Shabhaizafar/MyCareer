package com.droidevils.hired.Helper.Adapter;

public class EmployerInfo {
    String name,cname,phone,city,country,vac;

    public EmployerInfo() {
    }

    public EmployerInfo(String name, String cname, String phone, String city, String country, String vac) {
        this.name = name;
        this.cname = cname;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.vac = vac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVac() {
        return vac;
    }

    public void setVac(String vac) {
        this.vac = vac;
    }
}