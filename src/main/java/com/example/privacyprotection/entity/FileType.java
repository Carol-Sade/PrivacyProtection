package com.example.privacyprotection.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class FileType {
    @Id
    private Integer id;
    private Integer type;
    private String typeName;
}
