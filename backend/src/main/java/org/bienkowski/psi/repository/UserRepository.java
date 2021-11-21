package org.bienkowski.psi.repository;

import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {

    UserDTO findByEmail(String email);
}
