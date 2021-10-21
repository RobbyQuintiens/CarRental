package be.pxl.mobdev.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
//import java.time.LocalDate;

public class Car implements Serializable {

    private String id;
    private String brand;
    private String model;
    private Type type;
    private int year;
    private int seats;
    private int doors;
    private int dayPrice;
    private int countRented;
    private Status status;
    private String confirmedAt;
    private String imageName;
    private String imageUrl;

    public Car() {
    }

    public Car(String brand, String model, Type type,
               int year, int seats, int doors,
               int dayPrice, String imageName, String imageUrl) {
        this.setId(id);
        this.setBrand(brand);
        this.setModel(model);
        this.setType(type);
        this.setYear(year);
        this.setSeats(seats);
        this.setDoors(doors);
        this.setDayPrice(dayPrice);
        this.setImageName(imageName);
        this.setImageUrl(imageUrl);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(int dayPrice) {
        this.dayPrice = dayPrice;
    }

    public int getCountRented() {
        return countRented;
    }

    public void setCountRented(int countRented) {
        this.countRented = countRented;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public LocalDate getLockedAt() {
//        return lockedAt;
//    }
//
//    public void setLockedAt(LocalDate lockedAt) {
//        this.lockedAt = lockedAt;
//    }
//
//    public LocalDate getConfirmedAt() {
//        return confirmedAt;
//    }
//
//    public void setConfirmedAt(LocalDate confirmedAt) {
//        this.confirmedAt = confirmedAt;
//    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(String confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public static Comparator<Car> CarBrandAZComparator = new Comparator<Car>() {
        @Override
        public int compare(Car c1, Car c2) {
            return c1.getBrand().compareTo(c2.getBrand());
        }
    };

    public static Comparator<Car> CarBrandZAComparator = new Comparator<Car>() {
        @Override
        public int compare(Car c1, Car c2) {
            return c2.getBrand().compareTo(c1.getBrand());
        }
    };

    public static Comparator<Car> CarBrandPriceAscComparator = new Comparator<Car>() {
        @Override
        public int compare(Car c1, Car c2) {
            return c1.getDayPrice() - c2.getDayPrice();
        }
    };

    public static Comparator<Car> CarBrandPriceDescComparator = new Comparator<Car>() {
        @Override
        public int compare(Car c1, Car c2) {
            return c2.getDayPrice() - c1.getDayPrice();
        }
    };

}
