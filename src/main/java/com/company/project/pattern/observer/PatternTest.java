package com.company.project.pattern.observer;

public class PatternTest {
    public static void main(String[] args) {
        // 创建新主题
        WeatherData weatherData = new WeatherData();
        // 增加订阅者
        DisplayBoard displayBoard = new DisplayBoard(weatherData);

        // 主题进行更新
        System.out.println("========1111111=======");
        weatherData.setTemperature(100);

        System.out.println("========2222222=======");
        weatherData.setTemperature(200);

        System.out.println("========3333333=======");
        weatherData.setTemperature(300);

        // 移除订阅者
        weatherData.removeObserver(displayBoard);
        System.out.println("========4444444=======");
        weatherData.setTemperature(400);
    }
}
