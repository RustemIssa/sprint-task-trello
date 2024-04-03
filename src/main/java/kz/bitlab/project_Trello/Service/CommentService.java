package kz.bitlab.project_Trello.Service;

import kz.bitlab.project_Trello.Repository.CommentsRepository;
import kz.bitlab.project_Trello.model.Comment;
import kz.bitlab.project_Trello.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentsRepository commentsRepository;

    public List<Comment> getCommentsByTask(Task task){
        List<Comment> comments = new ArrayList<>();
        for(Comment com : commentsRepository.findAll()){
            if(com.getTask().getId()==task.getId()){
                comments.add(com);
            }
        }
        return comments;
    }

    public void addComment(Task task,String commentText){
        if (task.getStatus()!=3 || task.getStatus()!=2) {
            Comment comments = new Comment();
            comments.setTask(task);
            comments.setComment(commentText);
            commentsRepository.save(comments);
        }
    }
}
