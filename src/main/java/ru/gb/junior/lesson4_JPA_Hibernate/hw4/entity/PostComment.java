package ru.gb.junior.lesson4_JPA_Hibernate.hw4.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Entity
@Table
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @Column
    String text;

    @Column
    Long post_id;

    @Temporal(TemporalType.TIMESTAMP)
    Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
