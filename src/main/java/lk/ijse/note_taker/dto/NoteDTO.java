package lk.ijse.note_taker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoteDTO implements SuperDTO {
    private String id;
    private String noteTitle;
    private String noteDescription;
    private String priorityLevel;
    private String createdDateTime;
}
