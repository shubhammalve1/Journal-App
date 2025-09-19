package com.svm.journalapp.repository;

import com.svm.journalapp.entities.ConfigEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<ConfigEntity, ObjectId> {

}
