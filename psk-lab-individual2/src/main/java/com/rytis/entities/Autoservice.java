package com.rytis.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
@Table(name = "AUTOSERVICE")
@NamedQueries({
        @NamedQuery(name = "Autoservice.findAll", query = "select el from Autoservice as el"),
        @NamedQuery(name = "Autoservice.findByName", query = "select el from Autoservice as el where el.title= :name")
})
public class Autoservice implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;

    @Size(max = 50)
    @Column(name = "TITLE")
    String title;

    @OneToMany
    @JoinColumn(name = "AUTOSERVICE_ID")
    List<Car> cars;

    @ManyToMany(mappedBy = "autoservices")
    List<Mechanic> mechanics;

    public Autoservice(String title) {
        this.title = title;
    }

    public Autoservice(String title, List<Car> cars) {
        this(title);
        this.cars = cars;
    }

}
