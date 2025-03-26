package alexgr.task_management_hw_t1.service.TaskServiceImpl;

import alexgr.task_management_hw_t1.annotation.Logged;
import alexgr.task_management_hw_t1.exceptions.TaskNotFoundException;
import alexgr.task_management_hw_t1.entity.TaskEntity;
import alexgr.task_management_hw_t1.model.Task;
import alexgr.task_management_hw_t1.repository.TaskRepository;
import alexgr.task_management_hw_t1.service.TaskService;
import alexgr.task_management_hw_t1.utils.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskMapper mapper;
    private final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        TaskEntity entity = mapper.toEntity(task);
        TaskEntity savedEntity = taskRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Logged
    @Override
    public Task getTask(Integer id) throws TaskNotFoundException {
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));
        return mapper.toDto(entity);
    }

    @Logged
    @Override
    public Task updateTask(Integer id, Task task) throws TaskNotFoundException {
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found"));

        entity.setTitle(task.title());
        entity.setDescription(task.description());

        TaskEntity savedEntity = taskRepository.save(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public void deleteTask(Integer id) throws TaskNotFoundException {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task with ID " + id + " not found");
        }

        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasks() {
        List<TaskEntity> entities = taskRepository.findAll();
        return entities.stream().map(mapper::toDto).toList();
    }
}
