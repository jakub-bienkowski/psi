package org.bienkowski.psi.repository;

import org.bienkowski.psi.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TaskRepository extends CrudRepository<Task, Integer> {
}
