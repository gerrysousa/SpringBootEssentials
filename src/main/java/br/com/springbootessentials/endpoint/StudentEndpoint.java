package br.com.springbootessentials.endpoint;

import static java.util.Arrays.asList;

import br.com.springbootessentials.error.CustomErrorType;
import br.com.springbootessentials.model.Student;
import br.com.springbootessentials.util.DateUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("students")
public class StudentEndpoint {

  private DateUtil dateUtil;

  @Autowired
  public StudentEndpoint(DateUtil dateUtil) {
    this.dateUtil = dateUtil;
  }

  @RequestMapping(method = RequestMethod.GET, path = "/") // quando nao é passado o path="/"
  public ResponseEntity<?> listAll() {
    // System.out.println(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
    return new ResponseEntity<>(Student.studentList, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/{id}") // quando nao é passado o path="/"
  public ResponseEntity<?> getStudentById(@PathVariable("id") int id) {
    Student student = new Student();
    student.setId(id);
    int index = Student.studentList.indexOf(student);

    if (index == -1) {
      return new ResponseEntity<>(new CustomErrorType("Student not found"), HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(Student.studentList.get(index), HttpStatus.OK);
  }
}
