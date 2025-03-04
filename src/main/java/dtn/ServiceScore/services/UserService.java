package dtn.ServiceScore.services;

import dtn.ServiceScore.dtos.UserDTO;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.LoginRespone;

import java.util.List;

public interface UserService {
    void createUser(UserDTO userDTO) throws Exception;

    LoginRespone login(String username, String password) throws Exception;

    //List<User> searchByUsername(String username) throws Exception;
    List<User> findUsersByClassId(Long classId);
}
