package ru.gb.junior.lesson4_JPA_Hibernate.L4;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "test.magic")
public class Magic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "название")
    private String name;
    @Column(name = "повреждение")
    private int damage;
    @Column(name = "атака")
    private int attBonus;
    @Column(name = "броня")
    private int def;

    public Magic(String name, int damage, int attBonus, int def) {
        this.name = name;
        this.damage = damage;
        this.attBonus = attBonus;
        this.def = def;
    }

    public Magic() {
    }

}
