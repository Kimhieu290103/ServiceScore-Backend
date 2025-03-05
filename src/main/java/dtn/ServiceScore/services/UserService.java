package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.UserDTO;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.LoginRespone;

import java.util.List;

public interface UserService {
    void createUser(UserDTO userDTO) throws RuntimeException;

    LoginRespone login(String username, String password) throws RuntimeException;

    //List<User> searchByUsername(String username) throws Exception;
    List<User> findUsersByClassId(Long classId);
    //List<User> searchByUsername(String username) throws RuntimeException;

    User getUserById(Long userId);
}
