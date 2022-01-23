package com.spring.springboot.controller;

import com.spring.springboot.entity.UserEntity;
import com.spring.springboot.exeption.UserAlreadyExistExeption;
import com.spring.springboot.exeption.UserNotFoundExeption;
import com.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody UserEntity user){
        try {
            userService.registration(user);
            return ResponseEntity.ok("User added");
        }catch (UserAlreadyExistExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/get_user")
    public ResponseEntity getUser(@RequestParam Long id){
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        }catch (UserNotFoundExeption e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/server_test")
    public ResponseEntity<String> getUsers(){
        try {
            return ResponseEntity.ok("Server working");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok("User with id "+ userService.delete(id) + " was deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
