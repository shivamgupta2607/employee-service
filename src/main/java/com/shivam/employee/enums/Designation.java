package com.shivam.employee.enums;

public enum Designation {
    SOFTWARE_ENGINEER("Software Engineer"),
    TECHNICAL_LEAD("Technical Lead"),
    ENGINEERING_MANAGER("Engineering Manager"),
    SALES_MANAGER("Sales Manager"),
    SALES_EXECUTIVE("Sales Executive");

    public final String label;

    Designation(String label) {
        this.label = label;
    }
}
