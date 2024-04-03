package kz.bitlab.project_Trello.Repository;

import kz.bitlab.project_Trello.model.Folders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FoldersRepository extends JpaRepository<Folders,Long> {
    Folders findAllById(Long id);
}
