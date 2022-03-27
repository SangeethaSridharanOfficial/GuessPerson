package com.apis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helper.HTTPHelper;
import com.helper.ObjectHelper;
import com.models.Gender;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class GenderApi {

    private final String hostUrl;
    private static class GenderApiInitializer{
        private static final GenderApi genderApi = new GenderApi("https://api.genderize.io");
    }

    public static GenderApi getInstance(){
        return GenderApiInitializer.genderApi;
    }

    private GenderApi(String hostUrl){
        this.hostUrl = hostUrl;
    }

    public String getGenderByName(String name) {
        String gender = "";
        try {
            if(!ObjectHelper.isValidString(this.hostUrl) || !ObjectHelper.isValidString(name)) return "";

            // To pass the value with special characters
            name = URLEncoder.encode(name, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            InputStream respStream = HTTPHelper.makeHttpRequest(String.format("%s?name=%s", this.hostUrl, name));
            Gender ageObj = mapper.readValue(respStream, Gender.class);

            gender = ageObj.getGender();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gender;
    }

    public CompletableFuture<Object> getGenderByNameAsync(String name, ExecutorService executorService){
        return CompletableFuture.supplyAsync(() -> this.getGenderByName(name), executorService);
    }
}
