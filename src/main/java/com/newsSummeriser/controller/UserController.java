package com.newsSummeriser.controller;

import com.newsSummeriser.dto.UserDto;
import com.newsSummeriser.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // // 🧾 View Profile
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile() {
        UserDto userDto = userService.viewProfile();
        return ResponseEntity.ok(userDto);
    }

//     // 🔐 Update Password
    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        boolean updated = userService.updatePassword(oldPassword, newPassword);
        if (updated) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect.");
        }
   }
}
