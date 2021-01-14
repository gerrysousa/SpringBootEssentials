package br.com.springbootessentials.endpoint;

import static java.util.Arrays.asList;

import br.com.springbootessentials.model.Student;
import br.com.springbootessentials.util.DateUtil;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student")
public class StudentEndpoint {
  @Autowired
  private DateUtil dateUtil;

  @RequestMapping(method = RequestMethod.GET, path="/")
  public List<Student> listAll(){
    System.out.println(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
    return asList(new Student("Zé"), new Student("Tião"));
  }
}
