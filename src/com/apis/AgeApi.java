package com.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helper.HTTPHelper;
import com.helper.ObjectHelper;
import com.models.Age;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class AgeApi {

    private final String hostUrl;
    private static class AgeApiInitializer{
        private static final AgeApi ageApi = new AgeApi("https://api.agify.io");
    }

    public static AgeApi getInstance(){
        return AgeApiInitializer.ageApi;
    }

    private AgeApi(String hostUrl){
        this.hostUrl = hostUrl;
    }

    public int getAgeByName(String name) {
        try {
            if(!ObjectHelper.isValidString(this.hostUrl) || !ObjectHelper.isValidString(name)) return 0;

            // To pass the value with special characters
            name = URLEncoder.encode(name, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            InputStream respStream = HTTPHelper.makeHttpRequest(String.format("%s?name=%s", this.hostUrl, name));
            Age ageObj = mapper.readValue(respStream, Age.class);

            return ageObj.getAge();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    public CompletableFuture<Object> getAgeByNameAsync(String name, ExecutorService executorService){
        return CompletableFuture.supplyAsync(() -> this.getAgeByName(name), executorService);
    }
}
