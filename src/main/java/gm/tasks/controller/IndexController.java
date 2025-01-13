package gm.tasks.controller;

import gm.tasks.model.Task;
import gm.tasks.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final TaskService taskService;

    public IndexController(TaskService taskService) {
        this.taskService = taskService;
    }

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, Integer> idTaskColumn;

    @FXML
    private TableColumn<Task, String> nameTaskColumn;

    @FXML
    private TableColumn<Task, String> responsibleTaskColumn;

    @FXML
    private TableColumn<Task, String> statusTaskColumn;

    private final ObservableList<Task> tasksLists = FXCollections.observableArrayList();

    @FXML
    private TextField nameTaskText;

    @FXML
    private TextField responsibleTaskText;

    @FXML
    private TextField statusTaskText;

    private Integer idTaskInternal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configColumns();
        listTasks();
    }

    private void configColumns() {
        idTaskColumn.setCellValueFactory(new PropertyValueFactory<>("idTask"));
        nameTaskColumn.setCellValueFactory(new PropertyValueFactory<>("nameTask"));
        responsibleTaskColumn.setCellValueFactory(new PropertyValueFactory<>("responsible"));
        statusTaskColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        taskTable.setItems(tasksLists);
    }

    private void listTasks() {
        logger.info("Executing listTasks()");
        tasksLists.clear();
        tasksLists.addAll(taskService.tasksList());
        taskTable.setItems(tasksLists);
    }


    public void addTask(ActionEvent actionEvent) {
        if (nameTaskText.getText().isEmpty()) {
            showMessage("Validation Error", "Must provides a name for the task");
            nameTaskText.requestFocus();
            return;
        }
        else {
            var task = new Task();
            collectFormData(task);
            task.setIdTask(null);
            taskService.saveTask(task);
            showMessage("Information", "Task added successfully");
            clearForm();
            listTasks();
        }
    }

    public void loadTaskForm(MouseEvent mouseEvent) {
        var task = taskTable.getSelectionModel().getSelectedItem();
        if (task != null) {
            idTaskInternal = task.getIdTask();
            nameTaskText.setText(task.getNameTask());
            responsibleTaskText.setText(task.getResponsible());
            statusTaskText.setText(task.getStatus());
        }
    }

    private void collectFormData(Task task) {
        if(idTaskInternal != null)
            task.setIdTask(idTaskInternal);
        task.setNameTask(nameTaskText.getText());
        task.setResponsible(responsibleTaskText.getText());
        task.setStatus(statusTaskText.getText());
    }

    public void modifyTask(ActionEvent actionEvent) {
        if(idTaskInternal == null) {
            showMessage("Information", "Please select a task");
            return;
        }
        if (nameTaskText.getText().isEmpty()) {
            showMessage("Validation Error", "Must provide a name for the task");
            nameTaskText.requestFocus();
            return;
        }
        var task = new Task();
        collectFormData(task);
        taskService.saveTask(task);
        showMessage("Information", "Task modified successfully");
        clearForm();
        listTasks();
    }

    public void removeTask(ActionEvent actionEvent) {
        var task = taskTable.getSelectionModel().getSelectedItem();
        if (task != null) {
            logger.info("Task to remove: " + task);
            taskService.deleteTask(task.getIdTask());
            showMessage("Information", "Task with ID: " + task.getIdTask() + " removed successfully");
            clearForm();
            listTasks();
        } else {
            showMessage("Information", "Please select a task");
        }
    }

    public void clearForm() {
        idTaskInternal = null;
        nameTaskText.clear();
        responsibleTaskText.clear();
        statusTaskText.clear();
    }

    private void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}

















