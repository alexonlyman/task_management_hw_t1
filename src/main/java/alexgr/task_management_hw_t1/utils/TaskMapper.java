package alexgr.task_management_hw_t1.utils;

import alexgr.task_management_hw_t1.entity.TaskEntity;
import alexgr.task_management_hw_t1.model.Task;
import org.springframework.stereotype.Component;


@Component
public class TaskMapper {

    public TaskEntity toEntity(Task task) {
        TaskEntity entity = new TaskEntity();
        entity.setUserId(task.userId());
        entity.setDescription(task.description());
        entity.setTitle(task.title());
        return entity;
    }

    public Task toDto(TaskEntity task) {
        return new Task(task.getId(), task.getTitle(), task.getDescription(), task.getUserId());
    }

}
