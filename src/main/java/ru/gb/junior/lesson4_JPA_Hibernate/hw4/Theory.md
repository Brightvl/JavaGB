# Hibernate

## Основные аннотации Hibernate

`@Entity`
Эта аннотация используется для указания, что данный класс является сущностью и должен быть отображен на таблицу в базе
данных.

```java

@Entity
@Table(name = "posts")
public class Post {
// ...
}
```

`@Table`
Эта аннотация используется для указания имени таблицы, к которой будет отображаться данная сущность. Если имя таблицы
совпадает с именем класса, эту аннотацию можно опустить.

```java
@Table(name = "posts")
```

`@Id`
Эта аннотация указывает на поле, которое является первичным ключом в таблице.

```java

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

`@GeneratedValue`
Эта аннотация указывает, что значение первичного ключа будет автоматически сгенерировано. GenerationType.IDENTITY
используется для автоинкрементных полей.

```java
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@GeneratedValue(strategy = GenerationType.IDENTITY)
```

`@Column`
Эта аннотация используется для указания дополнительных свойств столбца (например, имя столбца, nullable, уникальность
и т.д.). В нашем примере мы не использовали эту аннотацию, так как имена полей совпадают с именами столбцов.

`@Temporal`
Эта аннотация используется для указания типа данных времени. В нашем примере используется TemporalType.TIMESTAMP для
хранения даты и времени.

```java

@Temporal(TemporalType.TIMESTAMP)
private Date timestamp;
```

`@ManyToOne`
Эта аннотация используется для указания отношения "многие к одному". В нашем примере посты и комментарии связаны с
пользователем, а комментарии связаны с постами.

```java

@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```

`@OneToMany`
Эта аннотация используется для указания отношения "один ко многим". В нашем примере посты имеют множество
комментариев, а пользователи имеют множество постов и комментариев.

```java

@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
private List<PostComment> comments;
```

`@JoinColumn`
Эта аннотация используется для указания имени столбца, который будет хранить внешний ключ.

```java
@JoinColumn(name = "user_id")
```

`@Cascade`
Эта аннотация указывает каскадные операции, которые должны быть выполнены при выполнении операций над родительской
сущностью. В нашем примере мы используем CascadeType.ALL, чтобы все операции (сохранение, обновление, удаление) на
сущности Post применялись к связанным комментариям.

```java
cascade =CascadeType.ALL
```

`@OrphanRemoval`    
Эта аннотация указывает, что связанные дочерние сущности должны быть удалены, если они больше не связаны с
родительской сущностью.

```java
orphanRemoval =true
```

# PostgresSQL
## Что нужно сделать чтобы работать с PostgresSQL

```xml
<!--необходимо изменить файл конфигурации hibernate.cfg.xml в resources-->
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- Database connection settings -->
        <property name="hibernate.connection.driver_class">org.postgresql.Driver</property>
        <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/your_database</property>
        <property name="hibernate.connection.username">your_username</property>
        <property name="hibernate.connection.password">your_password</property>
        <property name="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</property>

        <!-- Echo all executed SQL to stdout -->
        <property name="hibernate.show_sql">true</property>
        
        <!-- Update the database schema on startup -->
        <property name="hibernate.hbm2ddl.auto">update</property>
        
        <!-- Mention annotated classes -->
        <mapping class="com.example.Post"/>
        <mapping class="com.example.PostComment"/>
        <mapping class="com.example.User"/>
    </session-factory>
</hibernate-configuration>
```