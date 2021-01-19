package br.com.springbootessentials.repository;

import br.com.springbootessentials.model.Student;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface StudentRepository extends CrudRepository<Student, Long> {
  List<Student> findByNameIgnoreCaseContaining(String name);

}
