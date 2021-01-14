package br.com.springbootessentials.endpoint;

import static java.util.Arrays.asList;

import br.com.springbootessentials.model.Student;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student")
public class StudentEndpoint {
  @RequestMapping(method = RequestMethod.GET, path="/")
  public List<Student> listAll(){
    return asList(new Student("Zé"), new Student("Tião"));
  }
}
