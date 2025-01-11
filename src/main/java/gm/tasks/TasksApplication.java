package gm.tasks;

import gm.tasks.presentation.TasksSystemFx;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TasksApplication {

	public static void main(String[] args) {
		// SpringApplication.run(TasksApplication.class, args);
		Application.launch(TasksSystemFx.class, args);
	}

}
