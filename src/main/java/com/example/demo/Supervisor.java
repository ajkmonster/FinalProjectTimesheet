package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Supervisor {
    @OneToOne
    Department department;
}
