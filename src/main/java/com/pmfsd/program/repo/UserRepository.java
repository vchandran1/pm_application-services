/**
 * 
 */
package com.pmfsd.program.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pmfsd.program.entity.User;

/**
 * @author kj
 *
 */
public interface UserRepository extends MongoRepository<User, String> {

}
