package kz.bitlab.project_Trello.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskCategories extends BaseModel{


    @Column(name = "Categories_name")
    String name;
}
