package br.com.springbootessentials.javaclient;

import br.com.springbootessentials.model.PageableResponse;
import br.com.springbootessentials.model.Student;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JavaSpringClientTest {

  public static void main(String[] args) {
    JavaClientDAO dao = new JavaClientDAO();

    Student studentPost = new Student();
    studentPost.setName("Fulano tests update");
    studentPost.setEmail("email@teste.com");
    studentPost.setId(25L);

//    System.out.println(dao.save(studentPost));
//
//    System.out.println(dao.findById(501));
//
//    List<Student> students = dao.listAlll();
//    System.out.println(students);

  //  dao.update(studentPost);
    dao.delete(25);

  }
}
