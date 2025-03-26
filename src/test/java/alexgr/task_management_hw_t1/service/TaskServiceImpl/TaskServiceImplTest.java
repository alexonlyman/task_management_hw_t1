package alexgr.task_management_hw_t1.service.TaskServiceImpl;

import alexgr.task_management_hw_t1.entity.TaskEntity;
import alexgr.task_management_hw_t1.exceptions.TaskNotFoundException;
import alexgr.task_management_hw_t1.model.Task;
import alexgr.task_management_hw_t1.repository.TaskRepository;
import alexgr.task_management_hw_t1.utils.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void createTask_ShouldReturnCreatedTask() {
        Task task = new Task(1,"test title", "test desc",1);
        TaskEntity entity = new TaskEntity();
        entity.setDescription("test desc");
        entity.setTitle("test title");
        entity.setId(1);
        entity.setUserId(1);
        TaskEntity savedEntity = new TaskEntity();
        savedEntity.setId(entity.getId());
        savedEntity.setTitle(entity.getTitle());
        savedEntity.setDescription(entity.getDescription());
        savedEntity.setUserId(entity.getUserId());
        Task createdTask = new Task(1,"test title", "test desc",1);

        when(mapper.toEntity(task)).thenReturn(entity);
        when(taskRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(createdTask);

        Task result = taskService.createTask(task);

        assertEquals(createdTask, result);
        verify(taskRepository).save(entity);
        verify(mapper).toEntity(task);
        verify(mapper).toDto(savedEntity);
    }

    @Test
    void getTask_ShouldReturnTask_WhenTaskExists() throws TaskNotFoundException {

        int id = 1;
        TaskEntity entity = new TaskEntity();
        entity.setDescription("test desc");
        entity.setTitle("test title");
        entity.setId(1);
        entity.setUserId(1);
        Task task = new Task(1,"test title", "test desc",1);

        when(taskRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(task);

        Task result = taskService.getTask(id);

        assertEquals(task, result);
        verify(taskRepository).findById(id);
        verify(mapper).toDto(entity);
    }

    @Test
    void getTask_ShouldThrowException_WhenTaskNotFound() {
        int id = 1;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(id));
        verify(taskRepository).findById(id);
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask() throws TaskNotFoundException {
        int id = 1;
        Task task = new Task(1,"test title", "test desc",1);
        TaskEntity entity = new TaskEntity();
        entity.setDescription("test desc");
        entity.setTitle("test title");
        entity.setId(1);
        entity.setUserId(1);

        TaskEntity updatedEntity = new TaskEntity();
        updatedEntity.setId(1);
        updatedEntity.setTitle("Updated Title");
        updatedEntity.setDescription("Updated Description");
        updatedEntity.setUserId(1);
        Task updatedTask = new Task(1, "Updated title", "Updated Description", 1);

        when(taskRepository.findById(id)).thenReturn(Optional.of(entity));
        when(taskRepository.save(entity)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(updatedTask);

        Task result = taskService.updateTask(id, task);

        assertEquals(updatedTask, result);
        assertEquals("Updated Title", updatedEntity.getTitle());
        assertEquals("Updated Description", updatedEntity.getDescription());
        verify(taskRepository).findById(id);
        verify(taskRepository).save(entity);
        verify(mapper).toDto(updatedEntity);
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenTaskExists() throws TaskNotFoundException {
        int id = 1;
        when(taskRepository.existsById(id)).thenReturn(true);

        taskService.deleteTask(id);

        verify(taskRepository).deleteById(id);
        verify(taskRepository).existsById(id);
    }

    @Test
    void getTasks_ShouldReturnListOfTasks() {
        TaskEntity entity1 = new TaskEntity();
        entity1.setDescription("test desc");
        entity1.setTitle("test title");
        entity1.setId(1);
        entity1.setUserId(1);

        TaskEntity entity2 = new TaskEntity();
        entity2.setDescription("test2 desc");
        entity2.setTitle("test2 title");
        entity2.setId(2);
        entity2.setUserId(2);
        List<TaskEntity> entities = List.of(
                entity1,entity2
        );


        List<Task> tasks = List.of(
                new Task(1,"test title", "test desc",1),
                new Task(2,"test2 title", "test2 desc",2)
        );
        when(taskRepository.findAll()).thenReturn(entities);
        when(mapper.toDto(any(TaskEntity.class))).thenAnswer(invocation -> {
            TaskEntity entity = invocation.getArgument(0);
            return new Task(entity.getId(), entity.getTitle(), entity.getDescription(),entity.getUserId());
        });

        List<Task> result = taskService.getTasks();

        assertEquals(tasks, result);
        verify(taskRepository).findAll();
        verify(mapper, times(entities.size())).toDto(any(TaskEntity.class));
    }




}