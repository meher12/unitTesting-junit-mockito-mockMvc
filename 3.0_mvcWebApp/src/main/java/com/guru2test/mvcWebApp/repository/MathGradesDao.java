package com.guru2test.mvcWebApp.repository;

import com.guru2test.mvcWebApp.models.MathGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathGradesDao extends CrudRepository<MathGrade, Integer> {

    public Iterable<MathGrade> findGradeByStudentId(int id);
}
