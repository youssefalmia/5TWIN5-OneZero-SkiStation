package tn.esprit.spring.RegistrationTests;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.repositories.IRegistrationRepository;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.services.InstructorServicesImpl;
import tn.esprit.spring.services.RegistrationServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RegistrationServicesJUnitTest {

    @InjectMocks
    private RegistrationServicesImpl registrationServices;

    @Mock
    private IRegistrationRepository registrationRepository;

    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private ICourseRepository courseRepository;

    @Before()
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddRegistrationAndAssignToSkier() {
        // Arrange
        Long skierId = 1L;
        Skier skier = new Skier();
        skier.setNumSkier(skierId);

        Registration registration = new Registration();
        registration.setSkier(null); // Skier not assigned yet

        when(skierRepository.findById(skierId)).thenReturn(java.util.Optional.ofNullable(skier));
        when(registrationRepository.save(registration)).thenReturn(registration);

        // Act
        Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, skierId);

        // Assert
        assertNotNull(result);
        assertEquals(skier, result.getSkier());

    }

    @Test
    public void testAddRegistrationAndAssignToSkierSkierNotFound() {
        // Arrange
        Long skierId = 1L;

        // Simulate skier not found in the repository
        when(skierRepository.findById(skierId)).thenReturn(java.util.Optional.empty());

        Registration result = registrationServices.addRegistrationAndAssignToSkier(new Registration(), skierId);

        // Act and Assert
        assertNull(result);
    }

}
