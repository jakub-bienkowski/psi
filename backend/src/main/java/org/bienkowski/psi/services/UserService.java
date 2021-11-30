package org.bienkowski.psi.services;

import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.exception.UserAlreadyExistsException;
import org.bienkowski.psi.model.User;
import org.bienkowski.psi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public UserDTO saveUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (emailExists(userDTO.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        userDTO.setIdUsr(user.getId());
        return userDTO;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

}
