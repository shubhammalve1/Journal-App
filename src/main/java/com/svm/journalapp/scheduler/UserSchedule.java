package com.svm.journalapp.scheduler;

import com.svm.journalapp.cache.AppCache;
import com.svm.journalapp.entities.JournalEntry;
import com.svm.journalapp.entities.User;
import com.svm.journalapp.enums.Sentiment;
import com.svm.journalapp.repository.UserRepositoryImplementation;
import com.svm.journalapp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSchedule {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImplementation userRepositoryImplementation;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void fetchUsersAndSendSentimentAnalysisMail(){
        List<User> users = userRepositoryImplementation.sentimentAnalysis();

        for(User user: users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate()
                            .isAfter(ChronoLocalDate.from(LocalDateTime.now()
                            .minus(7, ChronoUnit.DAYS)
                            )))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCounts= new HashMap<>();
            for(Sentiment sentiment: sentiments){
                if(sentiment != null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment, Integer> entry: sentimentCounts.entrySet()) {
                if(entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if(mostFrequentSentiment != null){
                emailService.sendMail(
                        user.getEmail(),
                        "Sentiment for last 7 days ",
                        mostFrequentSentiment.toString()
                );
            }
        }
    }

    @Scheduled(cron = "0 0 0/1 1/1 * ?")
    public void refreshInitAppcache(){
        appCache.init();
    }
}
