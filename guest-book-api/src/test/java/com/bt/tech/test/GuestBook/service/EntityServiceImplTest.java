package com.bt.tech.test.GuestBook.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bt.tech.test.GuestBook.entity.EntryEntity;
import com.bt.tech.test.GuestBook.exception.EntryNotFoundException;
import com.bt.tech.test.GuestBook.repository.EntryRepository;

@ExtendWith(MockitoExtension.class)
class EntityServiceImplTest {

    @Mock
    private EntryRepository entryRepository;

    @InjectMocks
    private EntityServiceImpl classUnderTest;

    @Test
    void testGetAllEntries() {
        final var expectedEntry = getEntryEntity();
        final var expectedEntryList = List.of(expectedEntry);

        when(entryRepository.findAll()).thenReturn(expectedEntryList);

        assertThat(classUnderTest.getAllEntries()).isEqualTo(expectedEntryList);
    }

    @Test
    void testGetEntryById() {
        final var expectedEntry = getEntryEntity();

        when(entryRepository.findById(expectedEntry.getId())).thenReturn(Optional.of(expectedEntry));

        assertThat(classUnderTest.getEntryById(expectedEntry.getId())).isEqualTo(expectedEntry);
    }

    @Test
    void testGetEntryByIdEntryNotFoundException() {
        final var id = UUID.randomUUID();
        when(entryRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> classUnderTest.getEntryById(id)).isInstanceOf(EntryNotFoundException.class);
    }

    @Test
    void testSaveEntry() {
        final var entry = getEntryEntity();
        entry.setId(null);
        final var savedEntry = getEntryEntity();

        when(entryRepository.save(entry)).thenReturn(savedEntry);

        assertThat(classUnderTest.saveEntry(entry)).isEqualTo(savedEntry);
    }

    @Test
    void testDeleteEntryById() {
        final var id = UUID.randomUUID();

        classUnderTest.deleteEntryById(id);

        verify(entryRepository).deleteById(id);
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
