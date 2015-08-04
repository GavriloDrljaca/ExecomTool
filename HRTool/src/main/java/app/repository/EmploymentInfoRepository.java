package app.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import app.model.EmploymentInfo;

@Repository
public interface EmploymentInfoRepository extends JpaRepository<EmploymentInfo, Integer> {

}
