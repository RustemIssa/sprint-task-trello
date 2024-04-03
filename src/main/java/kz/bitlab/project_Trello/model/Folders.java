package kz.bitlab.project_Trello.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Folders extends BaseModel{


    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    List<TaskCategories> categories;

    /*@OneToMany( fetch = FetchType.EAGER)
    private List<Task>tasks;*/
}
