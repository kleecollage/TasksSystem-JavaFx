package gm.tasks.controller;

import gm.tasks.model.Task;
import gm.tasks.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    private final TaskService taskService;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, Integer> idTaskColumn;

    @FXML
    private TableColumn<Task, String> nameTaskColumn;

    @FXML
    private TableColumn<Task, String> responsiveTaskColumn;

    @FXML
    private TableColumn<Task, String> statusTaskColumn;

    private final ObservableList<Task> tasksLists = FXCollections.observableArrayList();

    public IndexController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configColumns();
        listTasks();
    }

    private void configColumns() {
        idTaskColumn.setCellValueFactory(new PropertyValueFactory<>("idTask"));
        nameTaskColumn.setCellValueFactory(new PropertyValueFactory<>("nameTask"));
        responsiveTaskColumn.setCellValueFactory(new PropertyValueFactory<>("responsible"));
        statusTaskColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        taskTable.setItems(tasksLists);
    }

    private void listTasks() {
        logger.info("Executing listTasks()");
        tasksLists.clear();
        tasksLists.addAll(taskService.tasksList());
        taskTable.setItems(tasksLists);

    }
}

















