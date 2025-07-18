package tn.esprit.tpfoyer.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Bloc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BlocRestControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddBloc() throws Exception {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc Test");
        bloc.setCapaciteBloc(20);

        mockMvc.perform(post("/bloc/add-bloc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomBloc").value("Bloc Test"));
    }

    @Test
    public void testGetAllBlocs() throws Exception {
        mockMvc.perform(get("/bloc/retrieve-all-blocs"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBlocByIdNotFound() throws Exception {
        mockMvc.perform(get("/bloc/retrieve-bloc/9999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }
}
