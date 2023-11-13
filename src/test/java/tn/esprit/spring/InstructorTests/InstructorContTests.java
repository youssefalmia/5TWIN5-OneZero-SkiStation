package tn.esprit.spring.InstructorTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import tn.esprit.spring.dd.InstructorDTO;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
public class InstructorContTests {

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @Before()
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    @Test
    void testGetAllInstructors() throws Exception {
        // Mock data
        Instructor instructor1 = new Instructor(/* provide necessary data */);
        Instructor instructor2 = new Instructor(/* provide necessary data */);
        List<Instructor> instructors = Arrays.asList(instructor1, instructor2);

        // Mock the service method
        when(instructorServices.retrieveAllInstructors()).thenReturn(instructors);

        // Perform the request
        ResultActions resultActions = mockMvc.perform(get("/instructors/all")
                .contentType(MediaType.APPLICATION_JSON));

        // Verify the response
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


}
