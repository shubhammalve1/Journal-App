package com.svm.journalapp.services;

import com.svm.journalapp.entities.JournalEntry;
import com.svm.journalapp.entities.User;
import com.svm.journalapp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void addEntry(JournalEntry journalEntry, String username){
        try {
            User user = userService.findUserByUsername(username);
            journalEntry.setDate(LocalDate.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e){
            log.info("Error in addEntry for {}: ", username);
            //throw new RuntimeException("Entry is not saved!", e);
        }
    }

    public void addEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(String id){
        return journalEntryRepository.findById(id);
    }

    public void putEntryById(JournalEntry journalEntry){
        journalEntry.setDate(LocalDate.now());
        journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public boolean deleteById(String id, String username){
        boolean removed;
        try {
            User user = userService.findUserByUsername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }

            return removed;

        } catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurred while deleting the user's entry!!!", e);
        }
    }
}
