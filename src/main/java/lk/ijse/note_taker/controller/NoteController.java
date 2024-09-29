package lk.ijse.note_taker.controller;

import lk.ijse.note_taker.exception.DataPersistFailedException;
import lk.ijse.note_taker.exception.NoteNotFoundException;
import lk.ijse.note_taker.service.NoteService;
import lk.ijse.note_taker.dto.NoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    @Autowired
    private final NoteService noteService;

    //Save a note
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveNote(@RequestBody NoteDTO noteDTO) {
        if (noteDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            try {
                noteService.saveNote(noteDTO);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }catch (DataPersistFailedException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    //Get all notes
    @GetMapping(value = "allnotes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteDTO> getAllNotes() {
        return noteService.getAllNotes();
    }

    //Get a note by ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteDTO getNote(@PathVariable ("id") String noteId)  {
       return noteService.getNoteById(noteId);
    }

    //Update a note
    @PatchMapping(value = "/{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateNote(
            @PathVariable("id") String id,
            @RequestPart("noteTitle") String noteTitle,
            @RequestPart("noteDescription") String noteDescription,
            @RequestPart("priorityLevel") String priorityLevel,
            @RequestPart("createdDateTime") String createdDateTime) {

        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteTitle(noteTitle);
        noteDTO.setNoteDescription(noteDescription);
        noteDTO.setPriorityLevel(priorityLevel);
        noteDTO.setCreatedDateTime(createdDateTime);

        try {
            noteService.updateNote(id, noteDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoteNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Delete a note
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteNote(@PathVariable("id") String id) {
        return noteService.deleteNote(id) ? ResponseEntity.ok("Note deleted successfully") : ResponseEntity.badRequest().body("Failed to delete the note");
    }

}










