package br.com.springbootessentials.endpoint;

import static java.util.Arrays.asList;

import br.com.springbootessentials.error.CustomErrorType;
import br.com.springbootessentials.error.ResourceNotFoundException;
import br.com.springbootessentials.model.Student;
import br.com.springbootessentials.repository.StudentRepository;
import br.com.springbootessentials.util.DateUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
public class StudentEndpoint {
  private final StudentRepository studentDAO;

  @Autowired
  public StudentEndpoint(StudentRepository studentDAO) {
    this.studentDAO = studentDAO;
  }

  @GetMapping
  public ResponseEntity<?> listAll() {
    return new ResponseEntity<>(studentDAO.findAll(), HttpStatus.OK);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
    verifyIfStudentExists(id);
    Student student = studentDAO.findById(id).get();
    return new ResponseEntity<>(student, HttpStatus.OK);
  }

  @GetMapping(path = "/findByName/{name}")
  public ResponseEntity<?> getStudentByName(@PathVariable String name) {
      return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
  }

  @PostMapping
  @Transactional
  public ResponseEntity<?> save(@RequestBody Student student) {
       return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    verifyIfStudentExists(id);
    studentDAO.deleteById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<?> update(@RequestBody Student student) {
    verifyIfStudentExists(student.getId());
    studentDAO.save(student);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  private void verifyIfStudentExists(Long id){
    if (!studentDAO.findById(id).isPresent()){
      throw new ResourceNotFoundException("Student not found for ID: "+id);
    }
  }
}
