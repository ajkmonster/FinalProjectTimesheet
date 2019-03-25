package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Department {
    @OneToOne
    Supervisor supervisor;

}
