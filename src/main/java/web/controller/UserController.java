package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/{id}")
    public String findUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "find-person";
    }

    @GetMapping("/create")
    public String createUserForm(@ModelAttribute("user") User user) {
        return "user-create";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user-create";
        }

        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        Optional<User> userById = userService.findById(id);

        if (userById.isPresent()) {
            model.addAttribute("user", userById.get());
            return "edit-user";
        } else {
            return "redirect:/user";
        }
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUser(id, user);
        return "redirect:/user";
    }

    @PostMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/user";
    }
}
