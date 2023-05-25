package bg.tu_varna.sit.task_manager.service;

import bg.tu_varna.sit.task_manager.model.Task;
import bg.tu_varna.sit.task_manager.payload.TaskDto;

import java.util.List;

public interface TaskService {
    TaskDto addTask(TaskDto taskDto);
    TaskDto getTaskById(Long id);
    TaskDto updateTask(Long id, TaskDto taskDto);
    void deleteTask(Long id);
    List<TaskDto> getAllTasks();
}
