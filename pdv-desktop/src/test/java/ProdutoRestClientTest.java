
import com.mycompany.pdv.client.ProdutoRestClient;
import com.mycompany.pdv.modelos.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ProdutoRestClientTest {

    private ProdutoRestClient produtoRestClient;
    private Produto mockProduto;
    private Long mockId = 1L;

    @BeforeEach
    public void setUp() {
        produtoRestClient = mock(ProdutoRestClient.class);
        mockProduto = new Produto(mockId, "Produto Teste", "Categoria Teste", BigDecimal.TEN);
    }

    @Test
    public void testCreateProduto() throws IOException {
        when(produtoRestClient.createProduto(mockProduto)).thenReturn(mockProduto);

        Produto createdProduto = produtoRestClient.createProduto(mockProduto);

        verify(produtoRestClient).createProduto(mockProduto);
        assertEquals(mockProduto.getId(), createdProduto.getId());
        assertEquals(mockProduto.getDescricao(), createdProduto.getDescricao());
        assertEquals(mockProduto.getCategoria(), createdProduto.getCategoria());
        assertEquals(mockProduto.getValor(), createdProduto.getValor());
    }

    @Test
    public void testGetProdutoById() throws IOException {
        when(produtoRestClient.getProdutoById(mockId)).thenReturn(mockProduto);

        Produto retrievedProduto = produtoRestClient.getProdutoById(mockId);

        verify(produtoRestClient).getProdutoById(mockId);
        assertNotNull(retrievedProduto);
        assertEquals(mockId, retrievedProduto.getId());
        assertEquals(mockProduto.getDescricao(), retrievedProduto.getDescricao());
        assertEquals(mockProduto.getCategoria(), retrievedProduto.getCategoria());
        assertEquals(mockProduto.getValor(), retrievedProduto.getValor());
    }

    @Test
    public void testGetAllProdutos() throws IOException {
        List<Produto> mockProdutos = Arrays.asList(mockProduto, new Produto(2L, "Outro Produto", "Outra Categoria", BigDecimal.valueOf(20.00)));

        when(produtoRestClient.getAllProdutos()).thenReturn(mockProdutos.toArray(new Produto[0]));

        Produto[] retrievedProdutos = produtoRestClient.getAllProdutos();

        verify(produtoRestClient).getAllProdutos();
        assertNotNull(retrievedProdutos);
        assertEquals(2, retrievedProdutos.length);

        for (int i = 0; i < retrievedProdutos.length; i++) {
            Produto retrievedProduto = retrievedProdutos[i];
            Produto expectedProduto = mockProdutos.get(i);

            assertEquals(expectedProduto.getId(), retrievedProduto.getId());
            assertEquals(expectedProduto.getDescricao(), retrievedProduto.getDescricao());
            assertEquals(expectedProduto.getCategoria(), retrievedProduto.getCategoria());
            assertEquals(expectedProduto.getValor(), retrievedProduto.getValor());
        }
    }

    @Test
    public void testGetProdutosByCategoria() throws IOException {
        String categoria = "Categoria Teste";
        List<Produto> mockProdutos = Arrays.asList(mockProduto, new Produto(3L, "Produto da Categoria", categoria, BigDecimal.valueOf(30.00)));

        when(produtoRestClient.getProdutosByCategoria(categoria)).thenReturn(mockProdutos.toArray(new Produto[0]));

        Produto[] retrievedProdutos = produtoRestClient.getProdutosByCategoria(categoria);

        verify(produtoRestClient).getProdutosByCategoria(categoria);
        assertNotNull(retrievedProdutos);
        assertEquals(2, retrievedProdutos.length);

        for (int i = 0; i < retrievedProdutos.length; i++) {
            Produto retrievedProduto = retrievedProdutos[i];
            Produto expectedProduto = mockProdutos.get(i);

            assertEquals(expectedProduto.getId(), retrievedProduto.getId());
            assertEquals(expectedProduto.getDescricao(), retrievedProduto.getDescricao());
            assertEquals(expectedProduto.getCategoria(), retrievedProduto.getCategoria());
            assertEquals(expectedProduto.getValor(), retrievedProduto.getValor());
        }
    }
}