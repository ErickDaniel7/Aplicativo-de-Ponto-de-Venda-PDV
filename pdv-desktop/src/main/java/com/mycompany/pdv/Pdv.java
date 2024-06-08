/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pdv;

import com.mycompany.pdv.dao.VendaDAO;
import com.mycompany.pdv.view.CadastroClientePanel;
import com.mycompany.pdv.view.CadastroProdutoPanel;
import com.mycompany.pdv.view.ControleVendasPanel;
import com.mycompany.pdv.services.ClienteService;
import com.mycompany.pdv.services.VendaService;
import com.mycompany.pdv.services.ProdutoService;
import com.mycompany.pdv.services.RelatorioService;
import com.mycompany.pdv.view.RelatorioPanel;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Pdv extends JFrame {

    public Pdv() throws IOException {
        try {
            VendaDAO vendaDAO = new VendaDAO(); //Conexão local
            ClienteService clienteService = new ClienteService();
            ProdutoService produtoService = new ProdutoService();
            VendaService vendaService = new VendaService(produtoService, vendaDAO);
            RelatorioService relatorioService = new RelatorioService();

            setTitle("Sistema de Controle de Vendas PDV");
            setSize(1024, 768);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Criar um JTabbedPane para organizar os painéis
            JTabbedPane tabbedPane = new JTabbedPane();

            // Adicionar os painéis ao JTabbedPane
            tabbedPane.addTab("Controle de Vendas", new ControleVendasPanel(vendaService, produtoService, clienteService));
            tabbedPane.addTab("Cadastro de Produto", new CadastroProdutoPanel(produtoService));
            tabbedPane.addTab("Cadastro de Clientes", new CadastroClientePanel(clienteService));
            tabbedPane.addTab("Relatórios", new RelatorioPanel(relatorioService));

            // Adicionar o JTabbedPane ao frame
            add(tabbedPane);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao iniciar a aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Pdv().setVisible(true);
                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }
            }
        });

    }
}
