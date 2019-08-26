package org.sixtead.techrequests.controller;

import org.sixtead.techrequests.domain.Role;
import org.sixtead.techrequests.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("roles", roleService.getAll());

        return "role/index";
    }

    @GetMapping("/add")
    public String showAddForm(Role role) {
        return "role/add";
    }

    @PostMapping("/add")
    public String addRole(Role role, BindingResult result, Model model) {
        roleService.create(role);

        return "redirect:/roles";
    }
}
