package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.UserDTO;
import dtn.ServiceScore.responses.LoginRespone;

public interface UserService {
    void createUser(UserDTO userDTO) throws RuntimeException;

    LoginRespone login(String username, String password) throws RuntimeException;

    //List<User> searchByUsername(String username) throws RuntimeException;

}
