package com.svm.journalapp.services;

import com.svm.journalapp.entities.JournalEntry;
import com.svm.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void addEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDate.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void putEntryById(JournalEntry journalEntry){
        journalEntry.setDate(LocalDate.now());
        journalEntryRepository.save(journalEntry);
    }

    public void deleteById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }
}
