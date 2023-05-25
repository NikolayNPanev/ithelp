package bg.tu_varna.sit.task_manager.data;

import bg.tu_varna.sit.task_manager.model.Task;
import bg.tu_varna.sit.task_manager.utils.XmlWorker;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@XmlRootElement(name="tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class Storage {
    private static Storage instance;
    @XmlElement(name="task")
    private static List<Task> taskList = new ArrayList<>();
    private static Long id= 1L;
    private static String path = "F:\\TU\\IT\\class-project\\task-manager\\src\\main\\resources\\static\\xml\\storage.xml";

    private Storage() {
    }

    public static Storage getInstance() {
         if(instance == null) {
             try {
                 instance = XmlWorker.readerFromXMLFile(path);
                 id = taskList.size()+1L;
             } catch (JAXBException | FileNotFoundException | UnsupportedEncodingException e) {
                 e.printStackTrace();
             }
         }
         return instance;
    }

    public Task addTask(Task task) {
        task.setId(id++);
        taskList.add(task);
        updateXml();
        return task;
    }

    public Optional<Task> getTaskByIdOpt(Long id) {
       /* return taskList.stream()
                .filter(t->t.getId().equals(id))
                .findFirst();*/

        Optional<Task> taskOptional = Optional.empty();

        if(!taskList.isEmpty()) {
            for (Task t:taskList) {
                if(t.getId().equals(id)) {
                    taskOptional = Optional.of(t);
                }
            }
        }
        return  taskOptional;
       /* if(!taskList.isEmpty()) {
            for (Task t:taskList) {
                if(t.getId().equals(id)) {
                    return t;
                }
            }
        }
        return null;*/
    }


    public Task getTaskById(Long id) {
        if(!taskList.isEmpty()) {
            for (Task t:taskList) {
                if(t.getId().equals(id)) {
                    return t;
                }
            }
        }
        return null;
    }

    public Task updateTask(Long id, Task task) {
        Task updatedTask = getTaskById(id);
        if(updatedTask!=null) {
            updatedTask.setTitle(task.getTitle());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setDeadline(task.getDeadline());
            updateXml();
        }
        return updatedTask;
    }

    public void deleteTask(Long id) {
        Task deletedTask = getTaskById(id);
        if(deletedTask!=null) {
            taskList.remove(deletedTask);
            updateXml();
        }
    }

    public List<Task> getAllTasks() {
        return taskList;
    }

    private void updateXml() {
        try {
            XmlWorker.writeToXMLFile(path,this);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
