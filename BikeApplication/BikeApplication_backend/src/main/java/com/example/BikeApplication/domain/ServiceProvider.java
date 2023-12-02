package com.example.BikeApplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "serviceProviders")
public class ServiceProvider {
    @Id
    private String id;
    private String name;

    private String location;


    private double rating;

    private String expertise;
    private List<ServiceCategory> serviceCategories;

    private double latitude ;
    private double longitude;

   private List<String> review;


    public ServiceProvider(String name, String location,
                           double rating, String expertise, List<ServiceCategory> serviceCategories
    , double latitude,double longitude , List<String> review) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.expertise = expertise;
        this.serviceCategories = serviceCategories;
        this.latitude=latitude;
        this.longitude=longitude;
        this.review= review;
    }

}
