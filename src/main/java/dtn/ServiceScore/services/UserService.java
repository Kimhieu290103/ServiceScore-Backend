package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.UserDTO;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.LoginRespone;

public interface UserService {
    User createUser(UserDTO userDTO) throws Exception;

    LoginRespone login(String username, String passsword) throws Exception;
}
