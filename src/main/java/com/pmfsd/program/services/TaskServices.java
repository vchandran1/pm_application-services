/**
 * 
 */
package com.pmfsd.program.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmfsd.program.entity.ParentTask;
import com.pmfsd.program.entity.Project;
import com.pmfsd.program.entity.Task;
import com.pmfsd.program.entity.User;
import com.pmfsd.program.repo.ParentTaskRepository;
import com.pmfsd.program.repo.ProjectRepository;
import com.pmfsd.program.repo.TaskRepository;
import com.pmfsd.program.repo.UserRepository;

/**
 * @author kj
 *
 */
@RestController
@RequestMapping("/task")
public class TaskServices {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskServices.class);

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ParentTaskRepository parentTaskRepository;

	@RequestMapping("/getTasks")
	public List<Task> getTasks() {
		logger.info("Method getTasks() executed");
		List<Task> tasks = taskRepository.findAll();

		for (Task task : tasks) {
			String parentId = task.getParentId();
			if (parentId != null) {
				ParentTask parentTask = parentTaskRepository.findById(parentId).get();
				task.setParentTask(parentTask.getParentTask());
				
			}
			if (task.getUserId() != null && !task.getUserId().isEmpty()) {
				try {
					User user = userRepository.findById(task.getUserId()).get();
					Project project = projectRepository.findById(task.getProjectId()).get();
					task.setProjectName(project.getProjectName());
					task.setUserName(user.getFirstName() + " " + user.getLastName());
				} catch (Exception e) {
					logger.error("Error occured -- No UserId matching recored");
				}
			}
		}

		return tasks;
	}

	@RequestMapping("/getParentTasks")
	public List<ParentTask> getParentTasks() {
		return parentTaskRepository.findAll();
	}

	@RequestMapping("/addUpdate")
	public List<Task> addUpdateTask(@RequestBody Map<String, String> requestMap) throws ParseException {
		logger.info("Method addUpdateTask() executed");
		taskRepository.save(updateTaskEntity(requestMap));
		return getTasks();
	}

	@RequestMapping("/endTask")
	public List<Task> endTask(@RequestBody Map<String, String> requestMap) throws ParseException {
		Task taskEntity = updateTaskEntity(requestMap);
		taskEntity.setStatus("Completed");
		taskRepository.save(taskEntity);
		return getTasks();
	}

	@RequestMapping("/deleteTask")
	public List<Task> deleteTask(@RequestBody Task task) {
		taskRepository.delete(task);
		return taskRepository.findAll();
	}

	public Task updateTaskEntity(Map<String, String> requestMap) {
		Task taskEntity = new Task();
		ParentTask parentTaskEntity = new ParentTask();
		taskEntity.setId(requestMap.get("id"));
		taskEntity.setTask(requestMap.get("task"));
		taskEntity.setEndDate(requestMap.get("endDate"));
		taskEntity.setStartDate(requestMap.get("startDate"));
		String parentId = null;
		if ("true".equalsIgnoreCase(requestMap.get("isParentTask"))) {
			parentId = requestMap.get("parentId") != null ? requestMap.get("parentId") : UUID.randomUUID().toString();
			parentTaskEntity.setId(parentId);
			parentTaskEntity.setTaskId(requestMap.get("id"));
			parentTaskEntity.setParentTask(requestMap.get("task"));
			parentTaskRepository.save(parentTaskEntity);
			taskEntity.setParentTask(true);
		}
		taskEntity.setParentId(parentId);
		taskEntity.setStatus(requestMap.get("status"));
		taskEntity.setPriority(Integer.parseInt(requestMap.get("priority")));

		taskEntity.setProjectId(requestMap.get("projectId"));

		taskEntity.setUserId(requestMap.get("userId"));
		return taskEntity;
	}

}
