package com.myproject.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBaseEntity {
    public static final int START_SEQ = 100000;

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    public int id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }
    public boolean isNew() {
        return this.id == null;
    }
}
