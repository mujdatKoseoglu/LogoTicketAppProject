package com.ticket.Controller;

import com.ticket.Dto.UserCreateRequestDto;
import com.ticket.Model.Ticket;
import com.ticket.Model.User;
import com.ticket.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        return userService.createUser(userCreateRequestDto);
    }

    @GetMapping("/{userId}")
    public User getByIdUser(@PathVariable Integer userId) {
        return userService.getByUserId(userId);
    }
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Integer userId, @RequestBody UserCreateRequestDto userCreateRequestDto) {
        return userService.updateUser(userId, userCreateRequestDto);

    }

    @DeleteMapping("/{userId}")
    public User deleteUser(@PathVariable Integer userId) {

        return userService.deleteUser(userId);
    }

    /*@GetMapping("/myTickets/{userName}")
    public List<Ticket> getListMovies(@PathVariable String userName) {
        return userService.getListMyTickets(userName);
    }*/
}
