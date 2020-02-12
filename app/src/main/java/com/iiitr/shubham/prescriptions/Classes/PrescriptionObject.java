package com.iiitr.shubham.prescriptions.Classes;

import java.util.ArrayList;

public class PrescriptionObject {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public PrescriptionObject(String id, String email, String phone, String age, String gender, String symptoms, String diagnosis, String advice, String name, ArrayList<String> prescription) {
        this.id=id;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.advice = advice;
        this.name = name;
        this.prescription = prescription;
    }

    String email,phone,age,gender,symptoms,diagnosis,advice,name;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrescription(ArrayList<String> prescription) {
        this.prescription = prescription;
    }

    ArrayList<String> prescription;

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getAdvice() {
        return advice;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPrescription() {
        return prescription;
    }

    public PrescriptionObject()
    {

    }
}
