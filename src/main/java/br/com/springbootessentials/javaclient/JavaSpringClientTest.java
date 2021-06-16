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
    RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080/v1/protected/students").basicAuthentication("admin", "123456").build();
    Student student = restTemplate.getForObject("/{id}", Student.class,1);
    System.out.println(student);
    ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class,1);
    System.out.println(forEntity.getBody());
//    Student [] students = restTemplate.getForObject("/", Student[].class);
//    System.out.println(Arrays.toString(students));
//    ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null, new ParameterizedTypeReference<List<Student>>() {
//    });
//    System.out.println(exchange.getBody());
    ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/?sort=id,desc&sort=name,asc", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Student>>() {
    });
    System.out.println(exchange.getBody().getContent());

  }
}
