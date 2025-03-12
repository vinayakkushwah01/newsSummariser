package com.newsSummeriser.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsSummeriser.model.City;
import com.newsSummeriser.model.State;
import com.newsSummeriser.repository.CityRepository;
import com.newsSummeriser.repository.StateRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class StateCityService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    public void importStateCityData(String jsonFilePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(new File(jsonFilePath));

            root.fields().forEachRemaining(entry -> {
                String stateName = entry.getKey();

                // Check if state exists
                State state = stateRepository.findByName(stateName).orElse(null);
                if (state == null) {
                    state = new State(stateName);
                    state = stateRepository.save(state); // Save new state
                }

                List<City> cityList = new ArrayList<>();
                for (JsonNode cityNode : entry.getValue()) {
                    String cityName = cityNode.get("city").asText();
                    String cityUrl = cityNode.get("url").asText();

                    // Check if city exists in the given state
                    boolean cityExists = cityRepository.existsByNameAndState(cityName, state);
                    if (!cityExists) {
                        cityList.add(new City(cityName, cityUrl, state));
                    }
                }

                // Save only new cities
                if (!cityList.isEmpty()) {
                    cityRepository.saveAll(cityList);
                }
            });

            System.out.println("State and City data imported successfully, skipping existing ones!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // public void importStateCityData(String jsonFilePath) {
    //     System.out.println("Importing state and city data from: " + jsonFilePath);
    //     // Your logic to read and store data
    // }
   
    @EventListener(ApplicationReadyEvent.class)  //Automatic runs after server Setup 
    public void importDataOnStartup() {
        try {
            File file = new ClassPathResource("static/state_city_list.json").getFile();
            String jsonFilePath = file.getAbsolutePath();
            importStateCityData(jsonFilePath);
            System.out.println("Data import completed on startup!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
