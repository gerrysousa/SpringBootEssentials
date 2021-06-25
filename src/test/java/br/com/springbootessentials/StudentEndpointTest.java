package br.com.springbootessentials;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import br.com.springbootessentials.model.Student;
import br.com.springbootessentials.repository.StudentRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Pageable;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class StudentEndpointTest {

  @Autowired private TestRestTemplate restTemplate;

  @LocalServerPort private int port;

  private MockRestServiceServer mockServer;

  @Autowired private MockMvc mockMvc;

  @MockBean private StudentRepository studentRepository;

  @TestConfiguration
  static class Config {
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
      return new RestTemplateBuilder().basicAuthentication("admin", "123456");
    }
  }

  @Test
  public void
      whenListStudentUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized() {
    restTemplate = restTemplate.withBasicAuth("1", "1");
    ResponseEntity<String> response =
        restTemplate.getForEntity("/v1/protected/students/", String.class);
    assertThat(response.getStatusCodeValue()).isEqualTo(401);
  }

  @Test
  public void
      whenGetStudentByIdUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized() {
    restTemplate = restTemplate.withBasicAuth("1", "1");
    ResponseEntity<String> response =
        restTemplate.getForEntity("/v1/protected/students/1", String.class);
    assertThat(response.getStatusCodeValue()).isEqualTo(401);
  }

  @Test
  public void
      whenFindStudentByNameUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized() {
    restTemplate = restTemplate.withBasicAuth("1", "1");
    ResponseEntity<String> response =
        restTemplate.getForEntity("/v1/protected/students/findByName/studentName", String.class);
    assertThat(response.getStatusCodeValue()).isEqualTo(401);
  }

  @Test
  @WithMockUser(
      username = "xxx",
      password = "xxx",
      roles = {"USER"})
  public void listStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200()
      throws Exception {
    List<Student> students =
        asList(
            new Student(1L, "Fulano", "fulano@test.com"),
            new Student(2L, "Deotrano", "deotrano@test.com"));

    Page<Student> pagedStudents = new PageImpl(students);

    when(studentRepository.findAll(isA(Pageable.class))).thenReturn(pagedStudents);

    mockMvc
        .perform(get("http://localhost:8080/v1/protected/students/"))
        .andExpect(status().isOk())
        .andDo(print());

    verify(studentRepository).findAll(isA(Pageable.class));
  }

  @Test
  @WithMockUser(username = "xx", password = "xx", roles = "USER")
  public void whenListStudentUsingCorrectStudentUsernameAndPassword_thenReturnCorrectData()
      throws Exception {
    List<Student> students =
        asList(
            new Student(1L, "Legolas", "legolas@lotr.com"),
            new Student(2L, "Aragorn", "aragorn@lotr.com"));

    Page<Student> pagedStudents = new PageImpl(students);

    when(studentRepository.findAll(isA(Pageable.class))).thenReturn(pagedStudents);

    mockMvc
        .perform(get("http://localhost:8080/v1/protected/students/"))
        .andExpect(jsonPath("$.content", hasSize(2)))
        .andExpect(jsonPath("$.content[0].id").value("1"))
        .andExpect(jsonPath("$.content[0].name").value("Legolas"))
        .andExpect(jsonPath("$.content[0].email").value("legolas@lotr.com"))
        .andExpect(jsonPath("$.content[1].id").value("2"))
        .andExpect(jsonPath("$.content[1].name").value("Aragorn"))
        .andExpect(jsonPath("$.content[1].email").value("aragorn@lotr.com"));

    verify(studentRepository).findAll(isA(Pageable.class));
  }

  @Test
  @WithMockUser(
      username = "xxx",
      password = "xxx",
      roles = {"USER"})
  public void listStudentByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200()
      throws Exception {
    Student student1 = new Student(1L, "Legolas", "legolas@lotr.com");
    when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(student1));

    mockMvc
        .perform(get("http://localhost:8080/v1/protected/students/{id}", 1))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
  public void listStudentByIdShouldReturnStatusCode200CorrectData() throws Exception {
    Student student1 = new Student(1L, "Legolas", "legolas@lotr.com");
    when(studentRepository.findById(1L)).thenReturn(java.util.Optional.of(student1));

    mockMvc.perform(get("http://localhost:8080/v1/protected/students/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.name").value("Legolas"))
        .andExpect(jsonPath("$.email").value("legolas@lotr.com"))
        .andDo(print());
  }

  @Test
  @WithMockUser(username = "xxx", password = "xxx", roles = {"USER"})
  public void listStudentByIdShouldReturnStatusCode404() throws Exception {
    mockMvc.perform(get("http://localhost:8080/v1/protected/students/{id}", -1))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

}
