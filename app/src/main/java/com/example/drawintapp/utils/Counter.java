package com.example.drawintapp.utils;

public class Counter {
    private int count;

    public Counter() {
        count = 0;
    }

    public Counter(int initCount) {
        count = initCount;
    }

    public void increase() {
        count++;
    }

    public void decrease() {
        count--;
    }

    @Override
    public String toString() {
        return String.valueOf(count);
    }
}
