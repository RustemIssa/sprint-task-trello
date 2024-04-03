package kz.bitlab.project_Trello.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task extends BaseModel{


    private String title;

    @Column(columnDefinition = "TEXT")
    String description; // TEXT

    int status; // 0 - todo, 1 - intest, 2 - done, 3 - failed

    @ManyToOne(fetch = FetchType.EAGER)
    Folders folder;
}
