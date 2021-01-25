package br.com.springbootessentials.repository;

import br.com.springbootessentials.model.Student;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
  List<Student> findByNameIgnoreCaseContaining(String name);

}
