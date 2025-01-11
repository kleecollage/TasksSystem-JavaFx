package gm.tasks.service;

import gm.tasks.model.Task;
import gm.tasks.repository.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {

    private final ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> tasksList() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }
}
