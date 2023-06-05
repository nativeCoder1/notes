package notesApp.data.repositories;

import notesApp.data.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteRepository extends JpaRepository<Note, Long> {
    Note findNoteById(Long noteId);
}
