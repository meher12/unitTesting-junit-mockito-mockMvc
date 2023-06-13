package com.guru2test.mvcWebApp.repository;


import com.guru2test.mvcWebApp.models.ScienceGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScienceGradesDao extends CrudRepository<ScienceGrade, Integer> {

    public void deleteByStudentId(int id);

    public Iterable<ScienceGrade> findGradeByStudentId(int id);
}
