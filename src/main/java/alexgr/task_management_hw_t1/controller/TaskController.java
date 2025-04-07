package alexgr.task_management_hw_t1.controller;

import alexgr.task_management_hw_t1.dto.Status;
import alexgr.task_management_hw_t1.dto.UpdateStatusRequest;
import alexgr.task_management_hw_t1.entity.TaskEntity;
import alexgr.task_management_hw_t1.exceptions.TaskNotFoundException;
import alexgr.task_management_hw_t1.dto.Task;
import alexgr.task_management_hw_t1.service.taskService.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Integer id) throws TaskNotFoundException {
        return taskService.getTask(id);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task task) throws TaskNotFoundException {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Integer id) throws TaskNotFoundException {
        taskService.deleteTask(id);
    }

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public Task updateTaskStatus(@PathVariable Integer id,
                                       @RequestBody UpdateStatusRequest request) throws TaskNotFoundException {
        return taskService.updateStatus(id, request.status());
    }

}
