package com.guru2test.mvcWebApp.repository;

import com.guru2test.mvcWebApp.models.HistoryGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryGradesDao extends CrudRepository<HistoryGrade, Integer> {

    public Iterable<HistoryGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
