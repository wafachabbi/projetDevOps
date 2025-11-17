package tn.esprit.studentmanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;
import tn.esprit.studentmanagement.services.StudentService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Student createStudent(Long id, String fName) {
        Student student = new Student(
                id,
                fName,
                "TestLast",
                "email@test.com",
                "12345678",
                LocalDate.of(2000, 1, 1),
                "Tunis",
                null,
                Collections.emptyList()
        );
        return student;
    }

    @Test
    void testGetAllStudents() {
        Student s1 = createStudent(1L, "Wafa");
        Student s2 = createStudent(2L, "Adem");

        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById() {
        Student s1 = createStudent(1L, "Wafa");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s1));

        Student result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals("Wafa", result.getFirstName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveStudent() {
        Student s1 = createStudent(1L, "Wafa");

        when(studentRepository.save(any(Student.class))).thenReturn(s1);

        Student result = studentService.saveStudent(s1);

        assertNotNull(result);
        assertEquals(1L, result.getIdStudent());
        verify(studentRepository, times(1)).save(s1);
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}

