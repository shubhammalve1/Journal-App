package com.svm.journalapp.repository;

import com.svm.journalapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImplementation {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> sentimentAnalysis(){
        Query query = new Query();
        //query.addCriteria(Criteria.where("username").is("aniket"));
        //query.addCriteria(Criteria.where("email").exists(true));

        //query.addCriteria(Criteria.where("email").ne(null).ne("").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));

        Criteria emailCriteria = new Criteria().andOperator(
                Criteria.where("email").ne(null),           // not null
                Criteria.where("email").ne(""),             // not empty
                Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        );

        query.addCriteria(emailCriteria);
        query.addCriteria(Criteria.where("sentimentAnalysis").exists(true));

        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
