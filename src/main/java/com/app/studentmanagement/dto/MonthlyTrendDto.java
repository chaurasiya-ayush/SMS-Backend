package com.app.studentmanagement.dto;

public class MonthlyTrendDto {

    private int month;
    private Long count;

    public MonthlyTrendDto() {}

    public MonthlyTrendDto(int month, Long count) {
        this.month = month;
        this.count = count;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
