package ru.academits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class PhonebookSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhonebookSpringApplication.class, args);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Runtime.getRuntime().exec("cmd /c start http://localhost:8080");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
