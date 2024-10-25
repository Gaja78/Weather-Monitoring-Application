package com.weather.monitor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.weather.monitor.entity.DailySummary;
import com.weather.monitor.entity.WeatherAlert;
import com.weather.monitor.entity.WeatherData;
import com.weather.monitor.repository.DailySummaryRepository;
import com.weather.monitor.repository.WeatherAlertRepository;
import com.weather.monitor.repository.WeatherDataRepository;
import com.weather.monitor.service.WeatherService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherDataRepository weatherDataRepo;
    private final DailySummaryRepository summaryRepo;
    private final WeatherAlertRepository alertRepo;

    @Autowired
    public WeatherController(WeatherService weatherService,
                             WeatherDataRepository weatherDataRepo,
                             DailySummaryRepository summaryRepo,
                             WeatherAlertRepository alertRepo) {
        this.weatherService = weatherService;
        this.weatherDataRepo = weatherDataRepo;
        this.summaryRepo = summaryRepo;
        this.alertRepo = alertRepo;
    }
    @GetMapping("/current/{city}")
    public ResponseEntity<WeatherData> getCurrentWeather(@PathVariable String city) {
        WeatherData data = weatherDataRepo.findTopByCityOrderByTimestampDesc(city);
        return data != null ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
    }

    

    @GetMapping("/summary/{city}")
    public ResponseEntity<List<DailySummary>> getDailySummaries(@PathVariable String city) {
        List<DailySummary> summaries = summaryRepo.findByCity(city);
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/alerts/{city}")
    public ResponseEntity<List<WeatherAlert>> getAlerts(@PathVariable String city) {
        List<WeatherAlert> alerts = alertRepo.findByCity(city);
        return ResponseEntity.ok(alerts);
    }

    @GetMapping("/cities")
    public ResponseEntity<Set<String>> getAllCities() {
        return ResponseEntity.ok(weatherService.getCities());
    }
}
