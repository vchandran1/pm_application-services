/**
 * 
 */
package com.pmfsd.program.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pmfsd.program.entity.ParentTask;

/**
 * @author kj
 *
 */
public interface ParentTaskRepository extends MongoRepository<ParentTask, String> {

	public ParentTask findByParentId(String parentId);
	
}
