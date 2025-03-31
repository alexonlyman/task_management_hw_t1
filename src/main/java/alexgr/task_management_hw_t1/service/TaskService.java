package alexgr.task_management_hw_t1.service;

import alexgr.task_management_hw_t1.exceptions.TaskNotFoundException;
import alexgr.task_management_hw_t1.model.Task;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);

    Task getTask(Integer id) throws TaskNotFoundException;

    Task updateTask(Integer id,Task task) throws TaskNotFoundException;

    void deleteTask(Integer id) throws TaskNotFoundException;

    List<Task> getTasks();
}
