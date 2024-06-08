package com.mycompany.pdv.view;

import com.mycompany.pdv.modelos.Cliente;
import com.mycompany.pdv.modelos.ItemVenda;
import com.mycompany.pdv.modelos.Venda;
import com.mycompany.pdv.modelos.Produto;
import com.mycompany.pdv.services.ClienteService;
import com.mycompany.pdv.services.VendaService;
import com.mycompany.pdv.services.ProdutoService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 * @author estudo
 */
public class ControleVendasPanel extends javax.swing.JPanel {

    private ClienteService clienteService;
    private ProdutoService produtoService;
    private VendaService vendaService;
    private Cliente clienteSelecionado = new Cliente();
    private Produto produtoSelecionado = new Produto();
    private List<ItemVenda> carrinho = new ArrayList();
    private ItemVenda itemVendaSelecionada;

    public ControleVendasPanel(VendaService vendaService, ProdutoService produtoService, ClienteService clienteService) {
        initComponents();
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.vendaService = vendaService;
        adicionarButton.setEnabled(false);
        removerButton.setEnabled(false);
        limparButton.setEnabled(false);
        registrarVendaButton.setEnabled(false);
        preparaTabela();
        prepareCampoTexto();
    }

    private void preparaTabela() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });

        /*Não permitir edição direta na tabela */
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null}
                },
                new String[]{
                        "ID do Produto", "Desecrição", "Quantidade", "Valor"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void preencherTabela(List<ItemVenda> itens) throws IOException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Limpa a tabela
        if (itens == null) itens = this.carrinho;
        for (ItemVenda item : itens) {
            model.addRow(new Object[]{
                    item.getProduto().getId().toString(),
                    item.getProduto().getDescricao(),
                    item.getQuantidade().toString(),
                    item.getTotal().toString()
            });
        }
    }

    /*Pegar id a partir da celula da tabela e chamar serviço para obter cliente especifico */
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            // Obtém os valores das células da linha selecionada com verificação de null
            String id = getCellValueAsString(jTable1, selectedRow, 0);
            String descricao = getCellValueAsString(jTable1, selectedRow, 1);
            String quantidade = getCellValueAsString(jTable1, selectedRow, 2);
            String valor = getCellValueAsString(jTable1, selectedRow, 3);
            selecionarItemVenda(id, descricao, quantidade, valor);
        }
    }

    private String getCellValueAsString(JTable table, int row, int column) {
        Object value = table.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    private void selecionarItemVenda(String id, String descricao, String quantidade, String valor) {
        try {
            Produto produto = produtoService.obterProdutoPorId(Long.valueOf(id));
            jidProdutoField.setText(produto.getId().toString());
            jdescricaoField.setText(descricao);
            jquantidadeField.setText(quantidade);
            jvalorUnitarioField.setText(produto.getValor().toString());
            jvalorTotalField.setText(valor);
            itemVendaSelecionada = new ItemVenda(Integer.parseInt(quantidade), produto);
            removerButton.setEnabled(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao selecioar item da venda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void limparProdutoField() {
        jidProdutoField.setText("");
        jdescricaoField.setText("");
        jquantidadeField.setText("");
        jvalorUnitarioField.setText("");
        jvalorTotalField.setText("");
    }

    private void limparClienteField() {

        jnomeClienteField.setText("");
        jidClienteField.setText("");
        jidClienteField.setEditable(true);
    }

    private void resetarBotoes() {
        removerButton.setEnabled(false);
        registrarVendaButton.setEnabled(false);
        limparButton.setEnabled(false);
        adicionarButton.setEnabled(false);
    }

    private void resetarVariaveisGlobais() {
        clienteSelecionado = new Cliente();
        produtoSelecionado = new Produto();
        itemVendaSelecionada = new ItemVenda();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        confirmeEmprestimoDialog = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        observacoes = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        confirmarEmprestimoButtonDialog = new javax.swing.JButton();
        voltarButtonDialog = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        idClienteLabel = new javax.swing.JLabel();
        jidClienteField = new javax.swing.JTextField();
        idLivroLabel = new javax.swing.JLabel();
        jquantidadeField = new javax.swing.JTextField();
        jidProdutoField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jnomeClienteField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jdescricaoField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jvalorUnitarioField = new javax.swing.JTextField();
        jvalorTotalField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        selecionaClienteButton = new javax.swing.JButton();
        selecionaLivroButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jtotalVendaLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        adicionarButton = new javax.swing.JButton();
        removerButton = new javax.swing.JButton();
        limparButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        registrarVendaButton = new javax.swing.JButton();

        confirmeEmprestimoDialog.setTitle("Confirmar Registro de Venda");
        confirmeEmprestimoDialog.setLocationByPlatform(true);

        jLabel2.setText("Adiconar Observações Sobre a Venda");

        confirmarEmprestimoButtonDialog.setText("Confirmar");
        confirmarEmprestimoButtonDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarEmprestimoButtonDialogActionPerformed(evt);
            }
        });

        voltarButtonDialog.setText("Cancelar");
        voltarButtonDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voltarButtonDialogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator3)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(confirmarEmprestimoButtonDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(voltarButtonDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 154, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(confirmarEmprestimoButtonDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(voltarButtonDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(observacoes)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(0, 138, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(observacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout confirmeEmprestimoDialogLayout = new javax.swing.GroupLayout(confirmeEmprestimoDialog.getContentPane());
        confirmeEmprestimoDialog.getContentPane().setLayout(confirmeEmprestimoDialogLayout);
        confirmeEmprestimoDialogLayout.setHorizontalGroup(
                confirmeEmprestimoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(confirmeEmprestimoDialogLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        confirmeEmprestimoDialogLayout.setVerticalGroup(
                confirmeEmprestimoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(confirmeEmprestimoDialogLayout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        idClienteLabel.setText("Número de ID do Cliente");

        jidClienteField.setPreferredSize(new java.awt.Dimension(64, 33));

        idLivroLabel.setText("Número de ID do Produto");

        jquantidadeField.setPreferredSize(new java.awt.Dimension(64, 33));

        jidProdutoField.setPreferredSize(new java.awt.Dimension(64, 33));

        jLabel5.setText("Descrição");

        jLabel6.setText("Nome do Cliente");

        jnomeClienteField.setEditable(false);
        jnomeClienteField.setPreferredSize(new java.awt.Dimension(64, 33));

        jLabel7.setText("QTD");

        jdescricaoField.setEditable(false);
        jdescricaoField.setPreferredSize(new java.awt.Dimension(64, 33));

        jLabel9.setText("Valor Total");

        jvalorUnitarioField.setEditable(false);
        jvalorUnitarioField.setPreferredSize(new java.awt.Dimension(64, 33));

        jvalorTotalField.setEditable(false);
        jvalorTotalField.setPreferredSize(new java.awt.Dimension(64, 33));

        jLabel10.setText("Valor Unitário");

        selecionaClienteButton.setText("OK");
        selecionaClienteButton.setPreferredSize(new java.awt.Dimension(72, 33));
        selecionaClienteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionaClienteButtonActionPerformed(evt);
            }
        });

        selecionaLivroButton.setText("OK");
        selecionaLivroButton.setPreferredSize(new java.awt.Dimension(72, 33));
        selecionaLivroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionaLivroButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(idLivroLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(idClienteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jnomeClienteField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jidClienteField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(selecionaClienteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jidProdutoField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(selecionaLivroButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jquantidadeField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jdescricaoField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel7)
                                                        .addComponent(jLabel5)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jvalorUnitarioField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel10))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jLabel9)
                                                                        .addComponent(jvalorTotalField, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(idClienteLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jidClienteField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(selecionaClienteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jnomeClienteField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(idLivroLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jidProdutoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(selecionaLivroButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addGap(4, 4, 4)
                                .addComponent(jquantidadeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jdescricaoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jvalorUnitarioField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jvalorTotalField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(82, 82, 82))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "ID Produto", "Descrição do Produto", "Quantidade", "Valor Total"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        jtotalVendaLabel.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jtotalVendaLabel.setText("Total:R$0,00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jtotalVendaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtotalVendaLabel)
                                .addContainerGap())
        );

        adicionarButton.setText("Adicionar");
        adicionarButton.setPreferredSize(new java.awt.Dimension(72, 33));
        adicionarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarButtonActionPerformed(evt);
            }
        });

        removerButton.setText("Remover");
        removerButton.setPreferredSize(new java.awt.Dimension(72, 33));
        removerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerButtonActionPerformed(evt);
            }
        });

        limparButton.setText("Limpar");
        limparButton.setPreferredSize(new java.awt.Dimension(72, 33));
        limparButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparButtonActionPerformed(evt);
            }
        });

        registrarVendaButton.setText("VENDER");
        registrarVendaButton.setPreferredSize(new java.awt.Dimension(72, 33));
        registrarVendaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarVendaButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator2)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(adicionarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(removerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(limparButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(registrarVendaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(removerButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(limparButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(registrarVendaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(adicionarButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selecionaClienteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionaClienteButtonActionPerformed
        //TODO: Se cliente existir pegar o nome e as vendas registradas em seu nome
        try {
            clienteSelecionado = clienteService.obterClientePorID(jidClienteField.getText());
            if (clienteSelecionado.getId() != null) {
                jnomeClienteField.setText(clienteSelecionado.getNome());
                jidClienteField.setEditable(false);
                if (produtoSelecionado!=null && produtoSelecionado.getId()!= null  && clienteSelecionado!=null && clienteSelecionado.getId()!=null){
                    adicionarButton.setEnabled(true);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_selecionaClienteButtonActionPerformed

    private void resetLabelSelecaoClienteLivro() {
        idClienteLabel.setText("Número de ID do Cliente");

    }

    private void adicionarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarButtonActionPerformed
        try {
            
            if (jquantidadeField.getText() != null && jquantidadeField.getText().length() > 0 && Pattern.matches("^\\d{1,}", jquantidadeField.getText())) {
                ItemVenda item = new ItemVenda(Integer.valueOf(jquantidadeField.getText()), produtoSelecionado);
                
                carrinho.add(item);
                calcularTotalVenda(carrinho);
                preencherTabela(carrinho);
                produtoSelecionado = new Produto();
                
                jidClienteField.setEditable(false);
                removerButton.setEnabled(false);
                adicionarButton.setEnabled(false);
                registrarVendaButton.setEnabled(true);
                limparProdutoField();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar conjunto de itens de venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_adicionarButtonActionPerformed

    private void calcularTotalVenda(List<ItemVenda> carrinho) {
        BigDecimal valor = BigDecimal.ZERO;
        if ((carrinho != null && carrinho.size() > 0)) {
            for (ItemVenda item : carrinho) {
                valor = valor.add(item.getTotal());
            }
        }
        jtotalVendaLabel.setText(convertBigdecimalToString(valor));
    }

    private String convertBigdecimalToString(BigDecimal valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }

    private void confirmarEmprestimoButtonDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarEmprestimoButtonDialogActionPerformed
        //TODO: Lógica para registrar nova venda e mostrar sucesso no final
        try {
            Long id = Long.valueOf(clienteSelecionado.getId());
            Venda venda = new Venda(id, observacoes.getText(), carrinho);
            vendaService.registrarVenda(venda);
            jidClienteField.setEditable(true);
            observacoes.setText("");
            confirmeEmprestimoDialog.dispose();
            limparClienteField();
            limparProdutoField();
            resetarVariaveisGlobais();
            resetarBotoes();
            carrinho = new ArrayList();
            calcularTotalVenda(carrinho);
            preencherTabela(carrinho);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_confirmarEmprestimoButtonDialogActionPerformed

    private void selecionaLivroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionaLivroButtonActionPerformed
        try {
            produtoSelecionado = produtoService.obterProdutoPorId(Long.parseLong(jidProdutoField.getText()));
            if (produtoSelecionado.getId() != null ) {
                jdescricaoField.setText(produtoSelecionado.getDescricao());
                jvalorUnitarioField.setText(produtoSelecionado.getValor().toString());
                jquantidadeField.setText("1");
                calculaValorTotal(jquantidadeField.getText());
                if (clienteSelecionado!=null && clienteSelecionado.getId()!=null){
                    adicionarButton.setEnabled(true);
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao calcular venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ;
        }
    }//GEN-LAST:event_selecionaLivroButtonActionPerformed

    private void voltarButtonDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voltarButtonDialogActionPerformed
        confirmeEmprestimoDialog.dispose();
    }//GEN-LAST:event_voltarButtonDialogActionPerformed

    private void limparButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparButtonActionPerformed
        //TODO:Limpar carrinho de compras e seleções
        try {
            limparClienteField();
            limparProdutoField();
            resetarBotoes();
            resetarVariaveisGlobais();
            carrinho = new ArrayList();
            calcularTotalVenda(carrinho);
            preencherTabela(carrinho);


        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar itens da venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ;
        }
    }//GEN-LAST:event_limparButtonActionPerformed

    private void removerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerButtonActionPerformed
        //TODO: chamar dialog para registrar data de devolução.
        try {
            for (int i = 0; i < carrinho.size(); i++) {
                if (carrinho.get(i).equals(itemVendaSelecionada)) {
                    carrinho.remove(i);
                    break; // Remove apenas o primeiro item encontrado com o ID correspondente
                }
            }
            itemVendaSelecionada = new ItemVenda();
            produtoSelecionado = new Produto();
            removerButton.setEnabled(false);
            adicionarButton.setEnabled(false);
            preencherTabela(carrinho);
            calcularTotalVenda(carrinho);
            limparProdutoField();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro na limpeza de itens de venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_removerButtonActionPerformed

    private void registrarVendaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarVendaButtonActionPerformed
        // TODO lógica para registrar venda

        confirmeEmprestimoDialog.setSize(404, 223);
        confirmeEmprestimoDialog.setLocationRelativeTo(null);
        confirmeEmprestimoDialog.setVisible(true);

        //confirmeEmprestimoDialog.dispose();
    }//GEN-LAST:event_registrarVendaButtonActionPerformed

    private void popularDatasDeSaidaDevolucao() {

    }

    private LocalDate converteTextoParaData(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (data != null && !data.trim().isEmpty()) {
            try {
                return LocalDate.parse(data, formatter);
            } catch (DateTimeParseException e) {
                // Tratar a exceção de parsing, por exemplo, definir dataNascimento como null ou uma data padrão
                return LocalDate.MIN; // ou qualquer outra lógica desejada
            }
        }
        return LocalDate.MIN;
    }

    private void popularCamposLivro(Produto produto) {

    }

    private void popularCamposCliente(Cliente cliente) {

    }

    private void prepareCampoTexto() {
        DocumentListener listener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }
        };

        jidClienteField.getDocument().addDocumentListener(listener);
        jidProdutoField.getDocument().addDocumentListener(listener);
        jquantidadeField.getDocument().addDocumentListener(listener);

    }

    /*Quando o listener associado ao field é acionado esse metodo habilita ou nao os botoes de controle*/
    private void checkFields() {
        String idCliente = jidClienteField.getText();
        String idLivro = jidProdutoField.getText();
        String quantidade = jquantidadeField.getText();

        boolean allFieldsFilled = (idCliente != null && !idCliente.trim().isEmpty())
                && (idLivro != null && !idLivro.trim().isEmpty())
                && (quantidade != null && !quantidade.trim().isEmpty());

        boolean oneFieldsFilled = (idCliente != null && !idCliente.trim().isEmpty())
                || (idLivro != null && !idLivro.trim().isEmpty()
                || (quantidade != null && !quantidade.trim().isEmpty()));

        limparButton.setEnabled(oneFieldsFilled);
        calculaValorTotal(quantidade);
    }

    private void calculaValorTotal(String quantidade) {
        if (quantidade != null && quantidade.length() > 0 && Pattern.matches("^\\d{1,}", quantidade) && produtoSelecionado.getId() != null) {
            jvalorTotalField.setText(produtoSelecionado
                    .getValor()
                    .multiply(BigDecimal.valueOf(Integer.parseInt(quantidade))).toString());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionarButton;
    private javax.swing.JButton confirmarEmprestimoButtonDialog;
    private javax.swing.JDialog confirmeEmprestimoDialog;
    private javax.swing.JLabel idClienteLabel;
    private javax.swing.JLabel idLivroLabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jdescricaoField;
    private javax.swing.JTextField jidClienteField;
    private javax.swing.JTextField jidProdutoField;
    private javax.swing.JTextField jnomeClienteField;
    private javax.swing.JTextField jquantidadeField;
    private javax.swing.JLabel jtotalVendaLabel;
    private javax.swing.JTextField jvalorTotalField;
    private javax.swing.JTextField jvalorUnitarioField;
    private javax.swing.JButton limparButton;
    private javax.swing.JTextField observacoes;
    private javax.swing.JButton registrarVendaButton;
    private javax.swing.JButton removerButton;
    private javax.swing.JButton selecionaClienteButton;
    private javax.swing.JButton selecionaLivroButton;
    private javax.swing.JButton voltarButtonDialog;
    // End of variables declaration//GEN-END:variables
}
