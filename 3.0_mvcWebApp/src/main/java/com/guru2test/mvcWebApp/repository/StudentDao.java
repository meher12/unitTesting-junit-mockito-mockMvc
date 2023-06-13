package com.guru2test.mvcWebApp.repository;

import com.guru2test.mvcWebApp.models.CollegeStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public interface StudentDao extends CrudRepository<CollegeStudent, Integer> {
   public CollegeStudent findByEmailAddress(String emailAddress);
}
