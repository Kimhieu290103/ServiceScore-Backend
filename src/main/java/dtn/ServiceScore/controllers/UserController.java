package dtn.ServiceScore.controllers;

import dtn.ServiceScore.dtos.UserDTO;
import dtn.ServiceScore.dtos.UserLoginDTO;
import dtn.ServiceScore.model.User;
import dtn.ServiceScore.responses.LoginRespone;
import dtn.ServiceScore.responses.UserResponse;
import dtn.ServiceScore.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }

            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("mat khau khong trung khop");
            }
            userService.createUser(userDTO);
            return  ResponseEntity.ok("dang ki thanh cong") ;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        try {
            LoginRespone loginRespone = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());

            return ResponseEntity.ok(loginRespone);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/info")
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
    @GetMapping("/by_class/{classId}")
    public List<?> getUsersByClass(@PathVariable Long classId) {
        List<UserResponse> userResponses = userService.findUsersByClassId(classId)
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .phoneNumber(user.getPhoneNumber())
                        .fullname(user.getFullname())
                        .studentId(user.getStudentId())
                        .build()
                ).collect(Collectors.toList());

        // Đảo ngược danh sách
        Collections.reverse(userResponses);
        return userResponses;
    }
}
