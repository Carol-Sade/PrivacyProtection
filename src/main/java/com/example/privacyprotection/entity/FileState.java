package com.example.privacyprotection.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class FileState {
    @Id
    private Integer id;
    private Integer state;
    private String stateName;
}
