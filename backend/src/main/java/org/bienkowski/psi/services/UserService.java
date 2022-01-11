package org.bienkowski.psi.services;

import org.bienkowski.psi.dto.CustomUserDetails;
import org.bienkowski.psi.dto.UserDTO;
import org.bienkowski.psi.enums.ERole;
import org.bienkowski.psi.exception.UserAlreadyExistsException;
import org.bienkowski.psi.model.Role;
import org.bienkowski.psi.model.User;
import org.bienkowski.psi.repository.RoleRepository;
import org.bienkowski.psi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public UserDTO saveUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userExists(userDTO)) {
            throw new UserAlreadyExistsException();
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(getUserRole());
        userRepository.save(user);
        userDTO.setIdUsr(user.getId());
        userDTO.setPassword("");
        return userDTO;
    }

    private boolean userExists(UserDTO userDTO) {
        Optional<User> usernameExists = userRepository.findByUsername(userDTO.getUsername());
        Optional<User> emailExists = userRepository.findByEmail(userDTO.getEmail());
        return usernameExists.isPresent() || emailExists.isPresent();
    }

    private Set<Role> getUserRole() {
        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(ERole.USER_ROLE);
        userRole.ifPresent(roles::add);
        return roles;
    }

}
