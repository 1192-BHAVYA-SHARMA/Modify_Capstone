package com.example.BikeApplication.repository;

import com.example.BikeApplication.domain.ServiceProvider;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceProviderRepo extends MongoRepository<ServiceProvider, String> {

    List<ServiceProvider> findByServiceCategories_Category(String category);

    ServiceProvider findServiceProviderById(String id);

}
