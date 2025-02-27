package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.UserDTO;
import dtn.ServiceScore.responses.LoginRespone;

public interface UserService {
    void createUser(UserDTO userDTO) throws Exception;

    LoginRespone login(String username, String passsword) throws Exception;

    //List<User> searchByUsername(String username) throws Exception;

}
