package org.sixtead.techrequests.db;

import lombok.extern.slf4j.Slf4j;
import org.sixtead.techrequests.group.Group;
import org.sixtead.techrequests.group.GroupRepository;
import org.sixtead.techrequests.priority.Priority;
import org.sixtead.techrequests.priority.PriorityRepository;
import org.sixtead.techrequests.status.Status;
import org.sixtead.techrequests.status.StatusRepository;
import org.sixtead.techrequests.tag.Tag;
import org.sixtead.techrequests.tag.TagRepository;
import org.sixtead.techrequests.user.User;
import org.sixtead.techrequests.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Slf4j
public class LoadDatabase {
    private Logger log = LoggerFactory.getLogger("LoadDatabase");
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private PriorityRepository priorityRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("Initializing Database:");
            log.info("" + groupRepository.save(new Group("users")));
            log.info("" + statusRepository.save(new Status("NEW")));
            log.info("" + priorityRepository.save(new Priority("High")));
            log.info("" + tagRepository.save(new Tag("hardware")));
            log.info("" + userRepository.save(new User("user", "password", "User",
                    "Userov", groupRepository.save(new Group("superuser")))));
        };
    }
}
