package notesApp.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String content;
    private String editStatus = "";
    private LocalDate creationDate = LocalDate.now();
    private LocalTime creationTime = LocalTime.now();
    private String editTime;
    private String editDate;
    @ManyToOne
    private AppUser appUser;
}
