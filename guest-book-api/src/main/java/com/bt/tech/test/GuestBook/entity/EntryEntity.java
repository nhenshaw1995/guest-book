package com.bt.tech.test.GuestBook.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Entity
@Table(name = "entry")
@NoArgsConstructor
public class EntryEntity {

    @Id
    @Getter
    @GeneratedValue
    private UUID id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("isImage")
    private boolean isImage;

    @JsonProperty("postedBy")
    private String postedBy;

    @JsonProperty("approved")
    private boolean approved;
}
