package com.guru2test.springmvc.repository;

import com.guru2test.springmvc.models.HistoryGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryGradesDao extends CrudRepository<HistoryGrade, Integer> {

    public Iterable<HistoryGrade> findGradeByStudentId (int id);

    public void deleteByStudentId(int id);
}
