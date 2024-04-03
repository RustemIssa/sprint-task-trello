package kz.bitlab.project_Trello.Repository;

import kz.bitlab.project_Trello.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comment,Long> {

}
