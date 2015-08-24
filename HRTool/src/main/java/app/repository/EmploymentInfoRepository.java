package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.EmploymentInfo;

@Repository
public interface EmploymentInfoRepository extends JpaRepository<EmploymentInfo, Integer>{

}
