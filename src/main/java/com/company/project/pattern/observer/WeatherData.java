package com.company.project.pattern.observer;

import java.util.ArrayList;

public class WeatherData implements Subject {

    public WeatherData() {
        observers = new ArrayList<>();
    }

    private ArrayList<Observer> observers;

    private float temperature;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
        tempChanged();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(it -> it.update(temperature));
    }

    private void tempChanged() {
        notifyObservers();
    }
}
