package com.svm.journalapp.cache;

import com.svm.journalapp.entities.ConfigEntity;
import com.svm.journalapp.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }
    public Map<String, String> appCache;

    @Autowired
    private ConfigRepository configRepository;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigEntity> configEntities = configRepository.findAll();

        for(ConfigEntity configEntity : configEntities){
            appCache.put(configEntity.getKey(), configEntity.getValue());
        }
    }
}
