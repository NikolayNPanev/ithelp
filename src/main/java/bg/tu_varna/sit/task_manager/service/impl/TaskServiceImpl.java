package bg.tu_varna.sit.task_manager.service.impl;

import bg.tu_varna.sit.task_manager.data.Storage;
import bg.tu_varna.sit.task_manager.exception.ResourceNotFoundException;
import bg.tu_varna.sit.task_manager.model.Task;
import bg.tu_varna.sit.task_manager.payload.TaskDto;
import bg.tu_varna.sit.task_manager.repository.TaskRepository;
import bg.tu_varna.sit.task_manager.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    //private Storage storage = Storage.getInstance();
    private TaskRepository taskRepository;
    private ModelMapper mapper;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper mapper) {
        this.taskRepository=taskRepository;
        this.mapper = mapper;
    }

    @Override
    public TaskDto addTask(TaskDto taskDto) {
        Task task = mapToEntity(taskDto);
        //Task addedTask = storage.addTask(task);
        Task addedTask = taskRepository.save(task);
        return mapToDto(addedTask);
    }

    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        return mapToDto(task);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task newDataTask = mapToEntity(taskDto);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        task.setTitle(newDataTask.getTitle());
        task.setDescription(newDataTask.getDescription());
        task.setDeadline(newDataTask.getDeadline());

        Task updatedTask = taskRepository.save(task);
        //Task updatedTask = storage.updateTask(id, newDataTask);
        return mapToDto(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        taskRepository.delete(task);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks =  taskRepository.findAll();
        return tasks.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private TaskDto mapToDto(Task task) {
        return mapper.map(task, TaskDto.class);
    }

    private Task mapToEntity(TaskDto taskDto) {
        return mapper.map(taskDto, Task.class);
    }
}
