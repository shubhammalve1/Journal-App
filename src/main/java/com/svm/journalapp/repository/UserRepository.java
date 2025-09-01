package com.svm.journalapp.repository;

import com.svm.journalapp.entities.JournalEntry;
import com.svm.journalapp.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findUserByUserName(String userName);
}
