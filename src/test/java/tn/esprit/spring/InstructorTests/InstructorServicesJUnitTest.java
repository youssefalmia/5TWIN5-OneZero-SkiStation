package tn.esprit.spring.InstructorTests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


public class InstructorServicesJUnitTest {

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeAll()
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        when(instructorRepository.findAll()).thenReturn(instructors);
        List<Instructor> result = instructorServices.retrieveAllInstructors();
        Assertions.assertEquals(instructors, result);
    }

    @Test
    public void testAddInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor result = instructorServices.addInstructor(instructor);
        Assertions.assertEquals(instructor, result);
    }

    @Test
    public void testUpdateInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        Instructor result = instructorServices.updateInstructor(instructor);
        Assertions.assertEquals(instructor, result);
    }

    @Test
    public void testRetrieveInstructor() {
        Long id = 1L;
        Instructor instructor = new Instructor();
        when(instructorRepository.findById(id)).thenReturn(Optional.of(instructor));
        Instructor result = instructorServices.retrieveInstructor(id);
        Assertions.assertEquals(instructor, result);
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        // Mock data
        Long numCourse = 1L;
        Course course = new Course();
        Instructor instructor = new Instructor();

        when(courseRepository.findById(numCourse)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, numCourse);

        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(instructor, result);
        Assertions.assertEquals(1, instructor.getCourses().size());
        Assertions.assertTrue(instructor.getCourses().contains(course));

    }

}
