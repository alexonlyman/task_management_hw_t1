package alexgr.task_management_hw_t1.service.taskService.TaskServiceImpl;

import alexgr.task_management_hw_t1.annotation.Logged;
import alexgr.task_management_hw_t1.dto.Status;
import alexgr.task_management_hw_t1.exceptions.TaskNotFoundException;
import alexgr.task_management_hw_t1.entity.TaskEntity;
import alexgr.task_management_hw_t1.dto.Task;
import alexgr.task_management_hw_t1.kafka.KafkaTaskProducer;
import alexgr.task_management_hw_t1.repository.TaskRepository;
import alexgr.task_management_hw_t1.service.taskService.TaskService;
import alexgr.task_management_hw_t1.utils.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskMapper mapper;
    private final TaskRepository taskRepository;
    private final KafkaTaskProducer kafkaTaskProducer;

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

    @Override
    public Task  updateStatus(Integer id, Status newStatus) throws TaskNotFoundException {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskEntity.setStatus(newStatus);
        taskRepository.save(taskEntity);
        Task taskDto = mapper.toDto(taskEntity);
        kafkaTaskProducer.send("task_status_exchanged", taskDto);
        return taskDto;
    }
}
