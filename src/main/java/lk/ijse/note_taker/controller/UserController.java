package lk.ijse.note_taker.controller;

import lk.ijse.note_taker.dto.UserDTO;
import lk.ijse.note_taker.service.UserService;
import lk.ijse.note_taker.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    //Add health check
    @GetMapping("/health")
    public String checkHealth(){
        return "User Controller is OK";
    }

    //Save user
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") String profilePic) {

        //Handle profile picture
        String base64ProfilePic = AppUtil.toBase64ProfilePic(profilePic);

        //Build the user object
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setProfilePic(base64ProfilePic);
        //Send the user object to the service
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

}
