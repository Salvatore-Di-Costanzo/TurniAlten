package org.example.dependent.repository;

import org.example.dependent.pojo.Dependent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DependentRepo extends JpaRepository<Dependent, Integer> {


}
