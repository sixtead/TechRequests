package org.sixtead.techrequests.controller;

import org.sixtead.techrequests.domain.Role;
import org.sixtead.techrequests.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("roles", service.getAll());

        return "roles/index";
    }

    @GetMapping("/add")
    public String add(Role role) {
        return "roles/add";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Role role = service.getById(id);

        model.addAttribute("role", role);

        return "roles/edit";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Model model) {
        Role role = service.getById(id);
        service.delete(role);

        model.addAttribute("roles", service.getAll());

        return "roles/index";
    }

    @PostMapping("/create")
    public String create(@Valid Role role, BindingResult result, Model model) {
        if (!service.isNameUnique(role)) {
            result.rejectValue("name", "Unique");
        }

        if (result.hasErrors()) {
            return "roles/add";
        }

        service.save(role);
        model.addAttribute("roles", service.getAll());

        return "redirect:/roles";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @Valid Role role, BindingResult result, Model model) {
        if (!service.isNameUnique(role)) {
            result.rejectValue("name", "Unique");
        }

        if (result.hasErrors()) {
            return "roles/edit";
        }

        service.save(role);
        model.addAttribute("roles", service.getAll());

        return "redirect:/roles";
    }

}
