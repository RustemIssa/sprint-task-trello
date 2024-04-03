package kz.bitlab.project_Trello.Controller;

import kz.bitlab.project_Trello.Repository.CategoryRepository;
import kz.bitlab.project_Trello.Repository.CommentsRepository;
import kz.bitlab.project_Trello.Repository.FoldersRepository;
import kz.bitlab.project_Trello.Repository.TaskRepository;
import kz.bitlab.project_Trello.Service.CommentService;
import kz.bitlab.project_Trello.model.Comment;
import kz.bitlab.project_Trello.model.Folders;
import kz.bitlab.project_Trello.model.Task;
import kz.bitlab.project_Trello.model.TaskCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FoldersRepository foldersRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/")
    public String getHome(Model model) {
        List<Folders> folders = foldersRepository.findAll();
        model.addAttribute("folders", folders);
        return "home";
    }


    @PostMapping(value = "/addFolder")
    public String addFolder(@RequestParam(name = "name") String folderName) {
        Folders folder = new Folders();
        folder.setName(folderName);
        foldersRepository.save(folder);
        return "redirect:/";
    }

    @GetMapping(value = "/detailsFolder/{id}")
    public String getDetailsFolder(@PathVariable(name = "id") Long id, Model model) {
        Folders folders = foldersRepository.findAllById(id);
        model.addAttribute("detailsFolder", folders);

        List<TaskCategories> taskCategories = categoryRepository.findAll();
        model.addAttribute("categories", taskCategories);

        List<Task> tasks = new ArrayList<>();
        for(Task t : taskRepository.findAll()){
            if(t.getFolder().getId()==id){
                tasks.add(t);
            }
        }
        model.addAttribute("tasks",tasks);
        return "detailsFolder";
    }


    @PostMapping(value = "/addTask")
    public String addTask(@RequestParam(name = "title") String title,
                          @RequestParam(name = "description") String description,
                          @RequestParam(name = "folder_id") Long folder_id) {
        Folders folder = foldersRepository.findById(folder_id).orElseThrow();
        if (folder != null) {
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(0);
            task.setFolder(folder);
            taskRepository.save(task);
        }
        return "redirect:/detailsFolder/" + folder_id;
    }

    @PostMapping(value = "/addCategory")
    public String addCategory(@RequestParam(name = "folder_id") Long folder_id,
                              @RequestParam(name = "category_id") Long category_id) {

        TaskCategories category = categoryRepository.findById(category_id).orElseThrow();
        Folders folders = foldersRepository.findAllById(folder_id);
        if (folders != null) {
            List<TaskCategories> categories = folders.getCategories();
            if (categories == null) {
                categories = new ArrayList<>();
                categories.add(category);
            } else{
                for(TaskCategories cat: categories){
                    if (cat.getId() != category.getId()) {
                        categories.add(category);
                    }
                }
            }
        }
        foldersRepository.save(folders);
        return "redirect:/detailsFolder/" + folder_id;
    }

    @GetMapping(value = "/detailsFolder/{folderId}/detailsTask/{taskId}")
    public String getTaskDetails(@PathVariable(name = "folderId") Long folder_id,
                                 @PathVariable(name = "taskId") Long task_id,
                                 Model model) {
        Folders folders = foldersRepository.findById(folder_id).orElseThrow();
        Task task = taskRepository.findById(task_id).orElseThrow();
        model.addAttribute("task", task);
        model.addAttribute("detailsFolder", folders);
        model.addAttribute("comments", commentService.getCommentsByTask(task));

        return "detailsTask";
    }

    @PostMapping(value = "/updateTask")
    public String updateTask(@RequestParam(name = "task_id") Long task_id,
                             @RequestParam(name = "folder_id") Long folder_id,
                             @RequestParam(name = "title") String title,
                             @RequestParam(name = "description") String description,
                             @RequestParam(name = "status") int status) {

        Task task = taskRepository.findById(task_id).orElseThrow();
        Folders folders = foldersRepository.findById(folder_id).orElseThrow();

        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        taskRepository.save(task);
        return "redirect:/detailsFolder/" + folder_id;
    }

    @PostMapping(value = "/deleteTask")
    public String deleteTask(@RequestParam(name = "task_id") Long task_id,
                             @RequestParam(name = "folder_id") Long folder_id) {
        for(Comment c : commentsRepository.findAll()){
            if(c.getTask().getId()==task_id){
                commentsRepository.deleteById(c.getId());
            }
        }
        taskRepository.deleteById(task_id);
        return "redirect:/detailsFolder/" + folder_id;
    }

    @PostMapping(value = "/addComment")
    public String addComment(@RequestParam(name = "task_id") Long task_id,
                             @RequestParam(name = "folder_id") Long folder_id,
                             @RequestParam(name = "comment") String comment) {
        Task task = taskRepository.findById(task_id).orElseThrow();
        Folders folders = foldersRepository.findById(folder_id).orElseThrow();
        commentService.addComment(task, comment);
        return "redirect:/detailsFolder/" + folder_id + "/detailsTask/" + task_id;
    }

}
