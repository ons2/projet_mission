package tn.esprit.tpfoyer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    private Bloc bloc1;
    private Bloc bloc2;

    @BeforeEach
    public void init() {
        bloc1 = new Bloc();
        bloc1.setIdBloc(1L);
        bloc1.setNomBloc("Bloc A");
        bloc1.setCapaciteBloc(20);

        bloc2 = new Bloc();
        bloc2.setIdBloc(2L);
        bloc2.setNomBloc("Bloc B");
        bloc2.setCapaciteBloc(10);
    }

    @Test
    public void testAddBloc() {
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc1);

        Bloc result = blocService.addBloc(bloc1);

        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    public void testRetrieveBloc() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc1));

        Bloc result = blocService.retrieveBloc(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdBloc());
    }

    @Test
    public void testModifyBloc() {
        bloc1.setCapaciteBloc(30);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc1);

        Bloc result = blocService.modifyBloc(bloc1);

        assertEquals(30, result.getCapaciteBloc());
    }

    @Test
    public void testRemoveBloc() {
        doNothing().when(blocRepository).deleteById(1L);
        blocService.removeBloc(1L);
        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRetrieveBlocsSelonCapacite() {
        List<Bloc> blocs = Arrays.asList(bloc1, bloc2); // Capacités : 20 et 10
        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(15);

        assertEquals(1, result.size());
        assertEquals("Bloc A", result.get(0).getNomBloc());
    }

    @Test
    public void testRetrieveAllBlocs() {
        List<Bloc> blocs = Arrays.asList(bloc1, bloc2);
        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> result = blocService.retrieveAllBlocs();

        assertEquals(2, result.size());
    }

    @Test
    public void testTrouverBlocsSansFoyer() {
        List<Bloc> blocs = Collections.singletonList(bloc1);
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(blocs);

        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        assertEquals(1, result.size());
    }

    @Test
    public void testTrouverBlocsParNomEtCap() {
        List<Bloc> blocs = Arrays.asList(bloc1);
        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc A", 20)).thenReturn(blocs);

        List<Bloc> result = blocService.trouverBlocsParNomEtCap("Bloc A", 20);

        assertEquals(1, result.size());
    }
}
