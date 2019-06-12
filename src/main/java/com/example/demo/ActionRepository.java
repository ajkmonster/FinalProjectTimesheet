package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface ActionRepository extends CrudRepository<Action, Long> {
    Iterable<Action> findByUserAction(User user);

}
