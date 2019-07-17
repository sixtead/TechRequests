package org.sixtead.techrequests.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    public String index(Model model) {
        Iterable<Group> groups = groupService.getAll();

        model.addAttribute("fragment", "groups");
        model.addAttribute("title", "groups");
        model.addAttribute("groups", groups);
//        return "groups";
        return "layout";
    }
}
