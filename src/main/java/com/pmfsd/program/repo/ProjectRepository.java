/**
 * 
 */
package com.pmfsd.program.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pmfsd.program.entity.Project;

/**
 * @author kj
 *
 */
public interface ProjectRepository extends MongoRepository<Project, String> {

	public Project findByProjectId(String projectId);

	public List<Project> findByManagerId(String id);

}
