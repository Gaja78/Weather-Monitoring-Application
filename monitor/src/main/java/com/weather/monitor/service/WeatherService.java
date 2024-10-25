package com.weather.monitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.monitor.entity.DailySummary;
import com.weather.monitor.entity.WeatherAlert;
import com.weather.monitor.entity.WeatherData;
import com.weather.monitor.repository.DailySummaryRepository;
import com.weather.monitor.repository.WeatherAlertRepository;
import com.weather.monitor.repository.WeatherDataRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final WeatherDataRepository weatherDataRepo;
    private final DailySummaryRepository summaryRepo;
    private final WeatherAlertRepository alertRepo;
    private final RestTemplate restTemplate;

    // Map of city names to their IDs
    private final Map<String, String> cities = Map.of(
        "Delhi", "1273294",
        "Mumbai", "1275339",
        "Chennai", "1264527",
        "Bangalore", "1277333",
        "Kolkata", "1275004",
        "Hyderabad", "1269843"
    );

    private static final double ALERT_THRESHOLD = 35.0; // Example threshold, adjust as necessary
    private final Map<String, Integer> consecutiveHighTemps = new HashMap<>();

    @Autowired
    public WeatherService(WeatherDataRepository weatherDataRepo,
                          DailySummaryRepository summaryRepo,
                          WeatherAlertRepository alertRepo,
                          RestTemplate restTemplate) {
        this.weatherDataRepo = weatherDataRepo;
        this.summaryRepo = summaryRepo;
        this.alertRepo = alertRepo;
        this.restTemplate = restTemplate;
    }

    public Set<String> getCities() {
        return cities.keySet();
    }

    @Scheduled(fixedRate = 300000) // Fetch every 5 minutes
    public void fetchWeatherData() {
        cities.forEach((city, id) -> {
            WeatherData data = fetchFromOpenWeatherMap(city, id);
            if (data != null) {
                weatherDataRepo.save(data);
                checkTemperatureAlert(data);
            }
        });
    }

    private WeatherData fetchFromOpenWeatherMap(String city, String cityId) {
        String url = String.format(
            "http://api.openweathermap.org/data/2.5/weather?id=%s&appid=%s",
            cityId, apiKey
        );

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                WeatherData data = new WeatherData();
                data.setCity(city);
                // Extract temperature and other fields from the response
                data.setTemperature(kelvinToCelsius((Double) ((Map) response.get("main")).get("temp")));
                data.setFeelsLike(kelvinToCelsius((Double) ((Map) response.get("main")).get("feels_like")));
                data.setWeatherCondition((String) ((List<Map<String, Object>>) response.get("weather")).get(0).get("main"));
                data.setTimestamp(LocalDateTime.now());
                return data;
            }
        } catch (Exception e) {
            log.error("Error fetching weather data for {}: {}", city, e.getMessage());
        }
        return null;
    }

    private double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    private void checkTemperatureAlert(WeatherData data) {
        consecutiveHighTemps.put(data.getCity(), consecutiveHighTemps.getOrDefault(data.getCity(), 0)); // Default to 0 if not present
        if (data.getTemperature() > ALERT_THRESHOLD) {
            consecutiveHighTemps.merge(data.getCity(), 1, Integer::sum); // Increment the counter
            if (consecutiveHighTemps.get(data.getCity()) >= 2) { // Check if it has reached the threshold
                createAlert(data.getCity(), "High Temperature Alert",
                    String.format("Temperature above %.1f°C for %d consecutive readings",
                        ALERT_THRESHOLD, consecutiveHighTemps.get(data.getCity())));
            }
        } else {
            consecutiveHighTemps.put(data.getCity(), 0); // Reset if below threshold
        }
    }

    private void createAlert(String city, String type, String message) {
        WeatherAlert alert = new WeatherAlert();
        alert.setCity(city);
        alert.setAlertType(type);
        alert.setMessage(message);
        alert.setTimestamp(LocalDateTime.now());
        alertRepo.save(alert);
    }

    @Scheduled(cron = "0 0 0 * * *") // Run at midnight
    public void generateDailySummary() {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusDays(1);

        cities.keySet().forEach(city -> {
            List<WeatherData> dayData = weatherDataRepo.findByCityAndTimestampBetween(city, startTime, endTime);

            if (!dayData.isEmpty()) {
                DoubleSummaryStatistics stats = dayData.stream()
                    .mapToDouble(WeatherData::getTemperature)
                    .summaryStatistics();

                String dominantCondition = dayData.stream()
                    .collect(Collectors.groupingBy(
                        WeatherData::getWeatherCondition,
                        Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("Unknown");

                DailySummary summary = new DailySummary();
                summary.setCity(city);
                summary.setDate(startTime);
                summary.setAvgTemperature(stats.getAverage());
                summary.setMaxTemperature(stats.getMax());
                summary.setMinTemperature(stats.getMin());
                summary.setDominantCondition(dominantCondition);

                summaryRepo.save(summary);
            }
        });
    }
}
