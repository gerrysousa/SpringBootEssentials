package br.com.springbootessentials;

import br.com.springbootessentials.model.Student;
import br.com.springbootessentials.repository.StudentRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.validation.ConstraintViolationException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // usando o próprio bd  <- ativa testes
public class StudentRepositoryTest {
  @Autowired private StudentRepository studentRepository;

  @PersistenceContext private EntityManager entityManager;

  @Test
  public void createShouldPersistData() {
    Student student = new Student("Nome", "email@test.com");
    this.studentRepository.save(student);

    assertThat(student.getId()).isNotNull();
    assertThat(student.getName()).isEqualTo("Nome");
    assertThat(student.getEmail()).isEqualTo("email@test.com");
  }

  @Test
  public void deleteShouldDeleteData() {
    Student student = new Student("Nome", "email@test.com");
    this.studentRepository.save(student);
    studentRepository.delete(student);

    assertThat(studentRepository.findById(student.getId())).isEmpty();
  }

  @Test
  public void updateShouldUpdateData() {
    Student student = new Student("Nome", "email@test.com");
    this.studentRepository.save(student);
    student.setName("Nome Updated");
    student.setEmail("emailUpdate@test.com");
    this.studentRepository.save(student);
    student = this.studentRepository.findById(student.getId()).orElse(null);

    assertThat(student.getId()).isNotNull();
    assertThat(student.getName()).isEqualTo("Nome Updated");
    assertThat(student.getEmail()).isEqualTo("emailUpdate@test.com");
  }

  @Test
  public void findByNameShouldIgnoreCase() {
    Student student = new Student("Nome", "email@test.com");
    Student student2 = new Student("nome", "email2@test.com");
    this.studentRepository.save(student);
    this.studentRepository.save(student2);
    List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("nome");

    assertThat(studentList.size()).isEqualTo(2);
  }

  @Test
  public void whenNameIsNullShouldThrowConstraintViolationException() {
    Student student = new Student();
    student.setEmail("email@test.com");
    Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
      studentRepository.save(student);
      entityManager.flush();
    });
    assertTrue(exception.getMessage().contains("Field 'name' is mandatory!"));
  }

  @Test
  public void whenEmailIsNullShouldThrowConstraintViolationException() {
    Student student = new Student();
    student.setName("Nome");
    Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
      studentRepository.save(student);
      entityManager.flush();
    });
    assertTrue(exception.getMessage().contains("Field 'email' is mandatory!"));
  }

  @Test
  public void whenEmailIsInvalidShouldThrowConstraintViolationException() {
    Student student = new Student();
    student.setName("Nome");
    student.setEmail("test.com");
    Throwable exception = assertThrows(ConstraintViolationException.class, () -> {
      studentRepository.save(student);
      entityManager.flush();
    });
    assertTrue(exception.getMessage().contains("Email must be valid!"));
  }
}
