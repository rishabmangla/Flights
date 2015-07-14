package com.rishab.mangla.flights.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Flight {
	private String flight;
	private String flight_class;
	private String src;
	private String dest;
	private long takeOff;
	private long landing;
	private int price;

	public Flight() {
	}

	public Flight(String name, String flight_class, String src, String dest, int takeOff, int landing, int price) {
		this.flight = name;
		this.flight_class = flight_class;
        this.src = src;
		this.dest = dest;
        this.takeOff = takeOff;
        this.landing = landing;
		this.price = price;
	}

	public String getFlight() {
		return flight;
	}

	public void setFlight(String name) {
		this.flight = name;
	}

    public String getFlightClass() {
		return flight_class;
	}

	public void setFlightClass(String flight_class) {
		this.flight_class = flight_class;
	}

    public long getTakeOffMilis() {
        return takeOff;
    }

    public String getTakeOffTime() {
		return String.valueOf(new Date(takeOff)).substring(0,20);
	}

	public void setTakeOffTime(Long takeOff) {
		this.takeOff = takeOff;
	}

    public String getLandingime() {
		return String.valueOf(new Date(landing)).substring(0, 20);
	}

	public void setLandingTime(Long landing) {
		this.landing = landing;
	}

    public String getDuration(){
        long millis = landing - takeOff;
        return(String.format("%d hr, %d min",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
        ));
    }

    public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

    public String getDest() {
		return dest;
	}

	public void setDes(String des) {
		this.dest = des;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
