package org.sixtead.techrequests.controller;

import org.sixtead.techrequests.domain.Group;
import org.sixtead.techrequests.exceptions.NotFoundException;
import org.sixtead.techrequests.exceptions.ServiceException;
import org.sixtead.techrequests.service.GroupService;
import org.sixtead.techrequests.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String index(Model model) {
        Iterable<Group> groups = groupService.getAll();

        model.addAttribute("groups", groups);
        return "group/index";
    }

    @GetMapping("/add")
    public String showAddForm(Group group, Model model) {
        model.addAttribute("roles", roleService.getAll());

        return "group/add";
    }

    @PostMapping("/add")
    public String addGroup(@Valid Group group, BindingResult result, Model model) {
        model.addAttribute("roles", roleService.getAll());

        if (result.hasErrors()) {
            return "group/add";
        }

        try {
            groupService.create(group);
        } catch (ServiceException e) {
            result.rejectValue("name", "name.unique", "name is not unique");
            return "group/add";
        }

        return "redirect:/groups";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("group", groupService.getById(id));
        model.addAttribute("roles", roleService.getAll());

        try {
            return "group/edit";
        } catch (NotFoundException e) {
            return "404";
        }
    }

    @PostMapping("/edit/{id}")
    public String editGroup(@PathVariable("id") Long id, @Valid Group group, BindingResult result, Model model) {

        try {
            groupService.update(group);
        } catch (ServiceException e) {
            result.rejectValue("name", "name.unique", "name is not unique");
        }

        if (result.hasErrors()) {
            group.setId(id);
            model.addAttribute("roles", roleService.getAll());
            return "group/edit";
        }

        return "redirect:/groups";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {

        try {
            Group group = groupService.getById(id);
            groupService.delete(group);
        } catch (NotFoundException e) {
            return "404";
        }

        return "redirect:/groups";
    }
}
