package com.company.project.pattern.observer;

public class DisplayBoard implements Observer, DisplayElement {

    private float temperature;

    DisplayBoard(Subject weatherData) {
        weatherData.addObserver(this);
    }

    @Override
    public void display() {
        System.out.println("temperature is :" + temperature);
    }

    @Override
    public void update(float temperature) {
        this.temperature = temperature;
        display();
    }
}
