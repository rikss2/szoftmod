package hu.unideb.notetakingapp.bootstrap;

import hu.unideb.notetakingapp.api.entity.Note;
import hu.unideb.notetakingapp.api.entity.Shared;
import hu.unideb.notetakingapp.api.entity.User;
import hu.unideb.notetakingapp.api.service.NoteService;
import hu.unideb.notetakingapp.api.service.SharedService;
import hu.unideb.notetakingapp.api.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BootStrapData implements CommandLineRunner {

    private final NoteService noteService;

    private final UserService userService;

    private final SharedService sharedService;

    public BootStrapData(NoteService noteService, UserService userService, SharedService sharedService) {
        this.noteService = noteService;
        this.userService = userService;
        this.sharedService = sharedService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("", "");
        User user2 = new User("randomUserName2", "randomPasswd2");
        Note note = new Note("randomBody...", "title1", LocalDate.now(), null, user1);
        Note note2 = new Note("randomBody2...", "title2", LocalDate.now(), null, user1);
        Note note3 = new Note("randomBody3...", "title3", LocalDate.now(), null, user2);

        userService.save(user1);
        userService.save(user2);

        noteService.save(note);
        noteService.save(note2);

        System.out.println(note3.getId());
        System.out.println(noteService.save(note3).getId());
        System.out.println(note3.getId());

        System.out.println("Started in Bootstrap...");

        User u = userService.findByUsername("randomUserName2");
        System.out.println(u.getUserName() + " " + u.getPasswordHash());

        Shared shared1 = new Shared(1L, 1L);
        Shared shared2 = new Shared(1L, 2L);
        Shared shared3 = new Shared(2L, 1L);
        Shared shared4 = new Shared(2L, 3L);

        sharedService.save(shared1);
        sharedService.save(shared2);
        sharedService.save(shared3);
        sharedService.save(shared4);

        List<Shared> sharedRecords;

        sharedRecords = sharedService.findByUserId(1L);
        printList(sharedRecords);
        System.out.println("============");
        sharedRecords = sharedService.findByUserId(2L);
        printList(sharedRecords);


        System.out.println("Webpage available at: http://localhost:8080/login");
    }

    public static void printList(List<Shared> sharedList) {
        for (Shared s : sharedList) {
            System.out.println(s.getUserId() + " " + s.getNoteId());
        }

    }
}