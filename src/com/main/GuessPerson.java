package com.main;

import com.apis.*;
import com.helper.FutureHelper;
import com.helper.ObjectHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class GuessPerson {
    private enum Type{
        AGE,
        GENDER
    }
    public static void main(String[] args) {
        initialize();
    }

    public static void initialize(){
        Boolean isEnd = false;
        do{
            System.out.println("<-------Enter 'q' to quit the program or other value to proceed------->");
            System.out.println("Enter name to continue: ");
            Scanner ipt = new Scanner(System.in);
            String name = ipt.nextLine();

            if(name.equalsIgnoreCase("q")){
                isEnd = true;
            }else if(!ObjectHelper.isValidString(name)) {
                System.out.println("Invalid Request. Please try again!!!");
            }else{
                handleGuessPerson(name);
            }
        }while(!isEnd);
        System.out.println("Thank You!!!");
    }

    public static void handleGuessPerson(String name){
        ExecutorService executorService = Executors.newCachedThreadPool();
        try{
            CompletableFuture<Object> ageFuture = AgeApi.getInstance().getAgeByNameAsync(name, executorService);
            CompletableFuture<Object> genderFuture = GenderApi.getInstance().getGenderByNameAsync(name, executorService);

            List<Object> results = FutureHelper.awaitFutureAll(Arrays.asList(ageFuture, genderFuture));

            System.out.println(String.format("Age: %d, Gender: %s",results.get(0), results.get(1)));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            executorService.shutdownNow();
        }
    }

}