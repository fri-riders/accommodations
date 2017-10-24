package com.fri.rso.fririders.accommodations.data;

public class Accommodation {
    private long id;
    private String name;
    private String location;
    private String description;
    private Double value;
    private Double pricePerDay;

    public Accommodation(long id, String name, String location, Double value, Double pricePerDay) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.value = value;
        this.pricePerDay = pricePerDay;
    }

    public Accommodation(long id, String name, String location, String description, Double value, Double pricePerDay) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.value = value;
        this.pricePerDay = pricePerDay;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Double getValue() {
        return value;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
