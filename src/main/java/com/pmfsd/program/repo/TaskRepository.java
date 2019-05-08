/**
 * 
 */
package com.pmfsd.program.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pmfsd.program.entity.Task;

/**
 * @author kj
 *
 */
public interface TaskRepository extends MongoRepository<Task, String> {

	public Task findByTaskId(String taskId);

	public List<Task> findByUserId(String id);
		
	public List<Task> findByProjectId(String projectId);

}
