
import com.mycompany.pdv.dao.VendaDAO;
import com.mycompany.pdv.services.VendaService;
import com.mycompany.pdv.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class VendaServiceTest {
    @Mock
    private VendaDAO emprestimoDAO;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private VendaService vendaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   
    @Test void testeOrdenacaoDataSaidaDevolucao(){

    }
}
