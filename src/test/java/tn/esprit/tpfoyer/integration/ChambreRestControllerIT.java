package tn.esprit.tpfoyer.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ChambreRestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddChambre() throws Exception {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.SIMPLE);

        mockMvc.perform(post("/chambre/add-chambre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chambre)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value(101));
    }

    @Test
    public void testGetAllChambres() throws Exception {
        mockMvc.perform(get("/chambre/retrieve-all-chambres"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRetrieveChambreByIdNotFound() throws Exception {
        mockMvc.perform(get("/chambre/retrieve-chambre/9999"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testGetChambresByType() throws Exception {
        mockMvc.perform(get("/chambre/trouver-chambres-selon-typ/SIMPLE"))
                .andExpect(status().isOk());
    }

    @Test
    public void testChambreSelonEtudiant_NotFound() throws Exception {
        mockMvc.perform(get("/chambre/trouver-chambre-selon-etudiant/12345678"))
                .andExpect(status().isOk());
    }

}
