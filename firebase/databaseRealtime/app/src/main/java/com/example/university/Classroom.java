package com.example.university;

public class Classroom {
    protected String tenlop, tenkhoa;

    public Classroom() {
    }

    public Classroom(String tenlop, String tenkhoa) {
        this.tenlop = tenlop;
        this.tenkhoa = tenkhoa;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    public String getTenkhoa() {
        return tenkhoa;
    }

    public void setTenkhoa(String tenkhoa) {
        this.tenkhoa = tenkhoa;
    }

}
