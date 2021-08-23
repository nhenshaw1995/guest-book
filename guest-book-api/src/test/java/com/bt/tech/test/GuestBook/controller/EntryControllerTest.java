package com.bt.tech.test.GuestBook.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.bt.tech.test.GuestBook.entity.EntryEntity;
import com.bt.tech.test.GuestBook.service.EntityService;

@ExtendWith(MockitoExtension.class)
class EntryControllerTest {

    @Mock
    private EntityService entryService;

    @InjectMocks
    private EntryController classUnderTest;

    @Test
    void testGetAllEntries() {
        final var expectedEntry = getEntryEntity();
        final var expectedEntryList = List.of(expectedEntry);

        when(entryService.getAllEntries()).thenReturn(expectedEntryList);

        assertThat(classUnderTest.getAllEntries()).isEqualTo(expectedEntryList);
    }

    @Test
    void testGetEntryById() {
        final var expectedEntry = getEntryEntity();

        when(entryService.getEntryById(expectedEntry.getId())).thenReturn(expectedEntry);

        assertThat(classUnderTest.getEntryById(expectedEntry.getId())).isEqualTo(expectedEntry);
    }

    @Test
    void testSaveNewEntry() throws URISyntaxException {
        final var entry = getEntryEntity();
        entry.setId(null);
        final var savedEntry = getEntryEntity();

        when(entryService.saveEntry(entry)).thenReturn(savedEntry);

        assertThat(classUnderTest.saveNewEntry(entry))
                .isEqualTo(ResponseEntity.created(new URI("/entries/" + savedEntry.getId())).body(savedEntry));
    }

    @Test
    void testUpdateEntry() {
        final var entry = getEntryEntity();
        final var updatedEntry = getEntryEntity();
        updatedEntry.setId(entry.getId());
        updatedEntry.setContent("testing");

        when(entryService.saveEntry(entry)).thenReturn(updatedEntry);

        assertThat(classUnderTest.updateEntry(entry.getId(), entry)).isEqualTo(ResponseEntity.ok(updatedEntry));
    }

    @Test
    void testDeleteEntryById() {
        final var id = UUID.randomUUID();

        classUnderTest.deleteEntryById(id);

        verify(entryService).deleteEntryById(id);
    }

    private EntryEntity getEntryEntity() {
        final var entry = new EntryEntity();
        entry.setId(UUID.randomUUID());
        entry.setContent("test");
        entry.setImage(false);
        entry.setPostedBy("user");
        entry.setApproved(true);

        return entry;
    }

}
