package kz.bitlab.project_Trello.Repository;

import kz.bitlab.project_Trello.model.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<TaskCategories,Long> {
}
