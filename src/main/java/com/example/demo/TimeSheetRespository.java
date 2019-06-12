package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TimeSheetRespository extends CrudRepository<TimeSheet, Long> {
    Iterable<TimeSheet> findByUser(User user);
}
