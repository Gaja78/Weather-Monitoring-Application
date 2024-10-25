package com.weather.monitor.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class DailySummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private LocalDateTime date;
    private Double avgTemperature;
    private Double maxTemperature;
    private Double minTemperature;
    private String dominantCondition;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Double getAvgTemperature() {
		return avgTemperature;
	}
	public void setAvgTemperature(Double avgTemperature) {
		this.avgTemperature = avgTemperature;
	}
	public Double getMaxTemperature() {
		return maxTemperature;
	}
	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	public Double getMinTemperature() {
		return minTemperature;
	}
	public void setMinTemperature(Double minTemperature) {
		this.minTemperature = minTemperature;
	}
	public String getDominantCondition() {
		return dominantCondition;
	}
	public void setDominantCondition(String dominantCondition) {
		this.dominantCondition = dominantCondition;
	}
    
    
}


