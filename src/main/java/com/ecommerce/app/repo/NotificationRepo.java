package com.ecommerce.app.repo;


import com.ecommerce.app.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepo extends MongoRepository<Notification, String> {

}
