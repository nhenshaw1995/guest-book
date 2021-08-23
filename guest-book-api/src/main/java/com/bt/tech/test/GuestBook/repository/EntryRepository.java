package com.bt.tech.test.GuestBook.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bt.tech.test.GuestBook.entity.EntryEntity;

public interface EntryRepository extends JpaRepository<EntryEntity, UUID> {

}
