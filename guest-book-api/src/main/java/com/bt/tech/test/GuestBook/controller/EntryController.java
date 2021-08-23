package com.bt.tech.test.GuestBook.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bt.tech.test.GuestBook.entity.EntryEntity;
import com.bt.tech.test.GuestBook.service.EntityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/entries")
@RequiredArgsConstructor
public class EntryController {

    private final EntityService entryService;

    @GetMapping
    public List<EntryEntity> getAllEntries() {
        return entryService.getAllEntries();
    }

    @GetMapping("/{id}")
    public EntryEntity getEntryById(@PathVariable UUID id) {
        return entryService.getEntryById(id);
    }

    @PostMapping
    public ResponseEntity<EntryEntity> saveNewEntry(@RequestBody EntryEntity entryEntity) throws URISyntaxException {
        final var savedEntry = entryService.saveEntry(entryEntity);
        return ResponseEntity.created(new URI("/entries/" + savedEntry.getId())).body(savedEntry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntryEntity> updateEntry(@PathVariable UUID id, @RequestBody EntryEntity entryEntity) {
        final var updatedEntry = entryService.saveEntry(entryEntity);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntryEntity> deleteEntryById(@PathVariable UUID id) {
        entryService.deleteEntryById(id);
        return ResponseEntity.ok().build();
    }
}
