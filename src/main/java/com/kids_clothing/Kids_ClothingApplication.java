package com.kids_clothing;


import com.kids_clothing.seeder.BathSeeder;
import com.kids_clothing.service.service.FilesSerivce;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class Kids_ClothingApplication implements CommandLineRunner {

    @Resource
    FilesSerivce storageService;

    public static void main(String[] args) {
        SpringApplication.run(Kids_ClothingApplication.class, args);
        new BathSeeder().seed();

    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.init();
    }


}
