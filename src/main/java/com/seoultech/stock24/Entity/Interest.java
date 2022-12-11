package com.seoultech.stock24.Entity;

import java.time.LocalDate;

public class Interest {
    // MyPage 의 관심 목록

    private String stockName;
    private String stockClass;
    private String date;

    public Interest() { }

    public Interest(String stockName, String stockClass, String date) {
        this.stockName = stockName;
        this.stockClass = stockClass;
        this.date = date;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockClass() {
        return stockClass;
    }

    public void setStockClass(String stockClass) {
        this.stockClass = stockClass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
