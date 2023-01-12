package com.example.demo.bot;

import lombok.Data;

@Data
public class Employee {
    private String name;
    private boolean isBusy;
    private String interests;
    public Employee(String name, boolean isBusy, String interests) {
        this.name = name;
        this.isBusy = isBusy;
        this.interests = interests;
    }
}
