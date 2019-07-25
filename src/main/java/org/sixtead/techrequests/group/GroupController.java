package org.sixtead.techrequests.group;

import org.sixtead.techrequests.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    public String index(Model model) {
        Iterable<Group> groups = groupService.getAll();

        model.addAttribute("groups", groups);
        return "group/index";
    }

    @GetMapping("/add")
    public String showAddForm(Group group) {
        return "group/add";
    }

    @PostMapping("/add")
    public String addGroup(@Valid Group group, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "group/add";
        }

        try {
            groupService.create(group);
        } catch (ServiceException e) {
//            model.addAttribute("message", e.getMessage());
            result.rejectValue("name", "name.unique", "name is not unique");
            return "group/add";
        }

        model.addAttribute("groups", groupService.getAll());
        return "redirect:/groups";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Group group = groupService.getById(id);

        model.addAttribute("group", group);
        return "group/edit";
    }

    @PostMapping("/edit/{id}")
    public String editGroup(@PathVariable("id") Long id, @Valid Group group, BindingResult result, Model model) {
        if (result.hasErrors()) {
            group.setId(id);
            return "group/edit";
        }

        try {
            groupService.update(group);
        } catch (ServiceException e) {
//            model.addAttribute("message", e.getMessage());
            result.rejectValue("name", "name.unique", "name is not unique");
            return "group/edit";
        }

        model.addAttribute("groups", groupService.getAll());
        return "redirect:/groups";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        Group group = groupService.getById(id);

        groupService.delete(group);
        model.addAttribute("groups", groupService.getAll());
        return "redirect:/groups";
    }
}
