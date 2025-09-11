package com.svm.journalapp.controllers;

import com.svm.journalapp.entities.JournalEntry;
import com.svm.journalapp.entities.User;
import com.svm.journalapp.services.JournalEntryService;
import com.svm.journalapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Controller -> Service -> Repository

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<JournalEntry> createEntryOfUser(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.addEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e){
            log.info("Exception in createEntryForUser");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUsername(username);
        List<JournalEntry> allEntries = user.getJournalEntries();

        if(allEntries != null && !allEntries.equals("")){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable String myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUsername(username);
        List<JournalEntry> entry = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!entry.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateEntryById(@PathVariable String myId, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findUserByUsername(username);
        List<JournalEntry> entry = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!entry.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);

            if(journalEntry.isPresent()){
                JournalEntry oldEntry = journalEntry.get();

                oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
                journalEntryService.addEntry(oldEntry);

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

         return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean isRemoved = journalEntryService.deleteById(id, username);
        if(isRemoved){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
