package ru.gb.junior.lesson4_JPA_Hibernate.S4.entity;

import jakarta.persistence.*;

// create table book (
//   id bigint,
//   name varchar,
//   author_id foreign key references (author) id
// )
//

@Entity
@Table(name = "book")
public class Book {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

//  @ManyToMany
//  @JoinTable(
//    name = "author_book",
//    joinColumns = @JoinColumn(name = "book_id"),
//    inverseJoinColumns = @JoinColumn(name = "author_id")
//  )
//  private List<User> authors;

  public Book() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

//  public List<User> getAuthors() {
//    return authors;
//  }
//
//  public void setAuthors(List<User> authors) {
//    this.authors = authors;
//  }

  @Override
  public String toString() {
    return "Book{" +
      "id=" + id +
      ", name='" + name + '\'' +
//      ", author=" + author +
      '}';
  }
}
