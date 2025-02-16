package com.niwaskumar.journalApplication.controller;

import com.niwaskumar.journalApplication.services.journalEntryService;
import com.niwaskumar.journalApplication.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

     @Autowired
     private journalEntryService journalEntryService;

     @GetMapping
     public List<journalEntry> getAll(){
        return journalEntryService.getAll();
     }

     @PostMapping
     public journalEntry createEntry(@RequestBody journalEntry myEntry){
          myEntry.setDate(LocalDateTime.now());
          journalEntryService.saveEntry(myEntry);
          return myEntry;
     }

     @DeleteMapping("id/{myId}")
     public Boolean deleteEntry(@PathVariable ObjectId myId){
         journalEntryService.deleteById(myId);
         return true;
     }

     @GetMapping("id/{myId}")
     public journalEntry getJournalEntryById(@PathVariable ObjectId myId){
          Optional <journalEntry> JournalEntry = journalEntryService.getEntryById(myId);
          if(journalEntry.isPresent())
          return journalEntryService.getEntryById(myId).orElse(null);
     }

     @PutMapping("id/{myId}")
     public journalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody journalEntry newEntry){
          journalEntry old = journalEntryService.getEntryById(myId).orElse(null);
          if(old != null){
               old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
               old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
          }
          journalEntryService.saveEntry(old);
          return old;
     }
}
