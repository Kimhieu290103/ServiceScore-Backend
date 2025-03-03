package dtn.ServiceScore.services.impl;

import dtn.ServiceScore.components.JwtTokenUtil;
import dtn.ServiceScore.dtos.UserDTO;
import dtn.ServiceScore.exceptions.DataNotFoundException;
import dtn.ServiceScore.model.Class;
import dtn.ServiceScore.model.Role;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.repositories.ClassRepository;
import dtn.ServiceScore.repositories.RoleRepository;
import dtn.ServiceScore.repositories.UserRepository;
import dtn.ServiceScore.responses.LoginRespone;
import dtn.ServiceScore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClassRepository classRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public void createUser(UserDTO userDTO) throws Exception {
        String username = userDTO.getUsername();
        if(userRepository.existsByUsername(username)){
            throw new DataIntegrityViolationException("username existed");
        }
        User newUser= User.builder()
                .fullname(userDTO.getFullname())
                .phoneNumber(userDTO.getPhoneNumber())
                .studentId(userDTO.getStudentId())
                .address(userDTO.getAddress())
                .password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDateOfBirth())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .build();
        Role role = roleRepository.findByName(userDTO.getRoleName()).
                orElseThrow(() -> new DataNotFoundException("Không tìm thấy vai trò"));
        Class _class = classRepository.findByName(userDTO.getClassName()).orElse(null);
        newUser.setRole(role);
        newUser.setClazz(_class);
        newUser.setActive(true);

        String password = userDTO.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        userRepository.save(newUser);
    }

    @Override
    public LoginRespone login(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new DataNotFoundException("khong tim thay user"));
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new BadCredentialsException("Wrong username or password");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
        authenticationManager.authenticate(authenticationToken);
        LoginRespone loginRespone = new LoginRespone();
        loginRespone.setAccessToken(jwtTokenUtil.generateToken(user));
        String role = user.getRole().getName();  // Nếu getRoles() trả về một Role

        loginRespone.setRole(role);
        return loginRespone;
    }

}
