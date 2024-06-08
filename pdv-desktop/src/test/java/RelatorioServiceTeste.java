
import com.mycompany.pdv.client.RelatorioRestClient;
import com.mycompany.pdv.modelos.Venda;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;



public class RelatorioServiceTeste {
    
    private RelatorioRestClient client = new RelatorioRestClient();
    
    @Test
    public void testCreateProduto() throws IOException {
        LocalDate inicio_ = LocalDate.of(2000, 1, 1);
        LocalDate fim_ = LocalDate.now();
        Venda [] vendas = client.getRelatorioVendaByDate(inicio_, fim_);
        for (Venda v : vendas){
            System.out.println(v.getObservacoes());
        }
    }
    
}
