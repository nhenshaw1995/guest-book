package com.bt.tech.test.GuestBook.service;

import java.util.List;
import java.util.UUID;

import com.bt.tech.test.GuestBook.entity.EntryEntity;

public interface EntityService {
    public List<EntryEntity> getAllEntries();

    public EntryEntity getEntryById(UUID id);

    EntryEntity saveEntry(EntryEntity entryEntity);

    public void deleteEntryById(UUID id);

}
