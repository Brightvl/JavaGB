package ru.gb.junior.lesson4_JPA_Hibernate.hw4.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "post_comment")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String text;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    Post post;

    @Temporal(TemporalType.TIMESTAMP)
    Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
