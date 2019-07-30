package org.sixtead.techrequests.user;

import org.sixtead.techrequests.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.getAll());

        return "user/index";
    }

    @GetMapping("/add")
    public String showAddForm(User user, Model model) {
        model.addAttribute("groups", groupService.getAll());

        return "user/add";
    }

    @PostMapping("/add")
    public String addUser(@Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "user/add";
        }
        userService.create(user);

        return "redirect:/users";
    }
}
