package com.example.privacyprotection.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class User {

    @Id
    private Integer id;
    private String username;
    private String avatar;
    private String session_key;

    private Timestamp create_time;
    private Timestamp update_time;
}
