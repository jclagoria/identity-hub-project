package com.user.management.infrastructure.adapter.in;

import com.user.management.application.service.UserService;
import com.user.management.domian.model.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserManagementController {

    private final UserService userService;

    @PostMapping
    public Mono<ResponseEntity<?>> createUser(@RequestParam String email, @RequestParam String password) {
        return userService.createUser(email, password)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserProfile>> getUser(@PathVariable long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Flux<UserProfile> listUsers() {
        return userService.listAllUsers();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<?>> updateUser(@PathVariable long id, @RequestParam String fullName) {
        return userService.updateUser(id, fullName).map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable long id) {
        return userService.deleteUser(id).thenReturn(ResponseEntity.accepted().build());
    }

}
