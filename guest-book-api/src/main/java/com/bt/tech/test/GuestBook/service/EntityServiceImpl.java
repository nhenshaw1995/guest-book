package com.bt.tech.test.GuestBook.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bt.tech.test.GuestBook.entity.EntryEntity;
import com.bt.tech.test.GuestBook.exception.EntryNotFoundException;
import com.bt.tech.test.GuestBook.repository.EntryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntityServiceImpl implements EntityService {

    private final EntryRepository entryRepository;

    @Override
    public List<EntryEntity> getAllEntries() {
        return entryRepository.findAll();
    }

    @Override
    public EntryEntity getEntryById(UUID id) {
        return entryRepository.findById(id).orElseThrow(EntryNotFoundException::new);
    }

    @Override
    public EntryEntity saveEntry(EntryEntity entryEntity) {
        return entryRepository.save(entryEntity);
    }

    @Override
    public void deleteEntryById(UUID id) {
        entryRepository.deleteById(id);
    }

}
