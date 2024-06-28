package ru.gb.junior.lesson4_JPA_Hibernate.hw4.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "posts")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String title;

    @Temporal(TemporalType.TIMESTAMP)
    Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
