package com.mycompany.pdv.view;

import com.mycompany.pdv.modelos.DetalhesVenda;
import com.mycompany.pdv.modelos.ItemVenda;
import com.mycompany.pdv.modelos.Venda;
import com.mycompany.pdv.services.RelatorioService;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class RelatorioPanel extends javax.swing.JPanel {

    private RelatorioService relatorioService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private LocalDate dataInico = LocalDate.of(2000, 1, 1);
    private LocalDate dataFim = LocalDate.now();

    private Boolean modoVenda = true;

    public RelatorioPanel(RelatorioService relatorioService) {
        initComponents();
        this.relatorioService = relatorioService;
        preparaTabelaRelatorioVendas();
        preencherTabela(null, null, null);
        //prepareTextFOrmattedField();
    }

    private void calcularTotalVenda(Venda[] vendas) {
        BigDecimal valor = BigDecimal.ZERO;
        for (Venda venda : vendas) {
            valor = valor.add(venda.getTotal());
        }
        jTotalVendasLabel.setText("Total em Vendas:" + convertBigdecimalToString(valor));
    }

    private void preparaTabelaRelatorioVendas() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });

        /*Não permitir edição direta na tabela */
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null}
                },
                new String[]{
                        "ID VENDA", "ID CLIENTE", "OBSERVAÇÕES", "DATA", "TOTAL"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class
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

    private void preparaTabelaDetalheDeUmaVenda() {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });

        /*Não permitir edição direta na tabela */
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null}
                },
                new String[]{
                        "ID VENDA", "CLIENTE", "PRODUTO", "QUANTIDADE", "VALOR UNITÁRIO", "VALOR TOTAL"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class
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

    private void preencherTabela(Venda[] vendas, LocalDate inicio, LocalDate fim) {
        try {
            modoVenda = true;
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Limpa a tabela
            if (vendas == null) {
                vendas = relatorioService.obterRelatorioDeVendasPorData(inicio, fim);
            }
            for (Venda venda : vendas) {

                model.addRow(new Object[]{
                        venda.getId().toString(),
                        venda.getClienteId().toString(),
                        venda.getObservacoes(),
                        convertDateToString(venda.getDataVenda()),
                        convertBigdecimalToString(venda.getTotal())
                });
            }
            calcularTotalVenda(vendas);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao iniciar a aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void preencherTabelaDetalhes(DetalhesVenda detalhe, Long idVenda) {
        try {
            modoVenda = false;
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Limpa a tabela
            if (detalhe == null) {
                detalhe = relatorioService.obterRelatorioDetalhesVendaPorId(idVenda);
            }
            String cliente = detalhe.getCliente().getNome();

            for (ItemVenda item : detalhe.getItensVenda()) {
                item.setTotal();
                model.addRow(new Object[]{
                        detalhe.getId(),
                        cliente,
                        item.getProduto().getDescricao(),
                        item.getQuantidade().toString(),
                        convertBigdecimalToString(item.getProduto().getValor()),
                        convertBigdecimalToString(item.getTotal())

                });
            }
            jTotalVendasLabel.setText("Total desta Venda:" + convertBigdecimalToString(detalhe.getTotal()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao iniciar a aplicação: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataVendaStr = sdf.format(date);
        return dataVendaStr;
    }

    private LocalDate convertStringToLocalDate(String data) {
        return LocalDate.parse(data, formatter);
    }

    private String convertBigdecimalToString(BigDecimal valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return nf.format(valor);
    }

    /*Pegar id a partir da celula da tabela e chamar serviço para obter venda especifico */
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            if (modoVenda == true) {
                // Obtém os valores das células da linha selecionada com verificação de null
                String id = getCellValueAsString(jTable1, selectedRow, 0);
                idClienteField.setText(id);
            }
        }
    }

    private String getCellValueAsString(JTable table, int row, int column) {
        Object value = table.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    private JFormattedTextField createFormattedTextField(String mask, JFormattedTextField formattedTextField) {

        try {
            MaskFormatter maskFormatter = new MaskFormatter(mask);
            maskFormatter.setPlaceholderCharacter('_');
            formattedTextField = new JFormattedTextField(maskFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedTextField;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTotalVendasLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        dataInicioField = new javax.swing.JFormattedTextField();
        dataFimField = new javax.swing.JFormattedTextField();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        idClienteField = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Title 1", "Title 2", "Title 3", "Title 4"
                }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTotalVendasLabel.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jTotalVendasLabel.setText("Total em Vendas:R$0,00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                                        .addComponent(jTotalVendasLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTotalVendasLabel)
                                .addContainerGap())
        );

        jButton2.setText("Limpar Filtros");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jSeparator1)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jButton2)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        dataInicioField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataInicioFieldActionPerformed(evt);
            }
        });

        dataFimField.setPreferredSize(new java.awt.Dimension(149, 33));

        jButton4.setText("Gerar Relatório");
        jButton4.setPreferredSize(new java.awt.Dimension(84, 33));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setText("Início");

        jLabel2.setText("Fim");

        jLabel3.setText("Código de Venda");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(dataInicioField, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(dataFimField, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(idClienteField)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(dataInicioField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(dataFimField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(idClienteField))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    private void dataInicioFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataInicioFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataInicioFieldActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //TODO: Caso filtro contiver id valido, transforma tabela em detalhes de venda caso contrario faz filtragem com data
        String DATE_PATTERN = "^\\d{2}/\\d{2}/\\d{4}$";
        String NUMBER_PATTERN = "^\\d{1,}";
        String inicioStr = dataInicioField.getText();
        String fimStr = dataFimField.getText();
        String idCliente = idClienteField.getText();
        if (idCliente != null && Pattern.matches(NUMBER_PATTERN, idCliente)) {
            preparaTabelaDetalheDeUmaVenda();
            preencherTabelaDetalhes(null, Long.valueOf(idCliente));
        } else {
            preparaTabelaRelatorioVendas();
            if (Pattern.matches(DATE_PATTERN, inicioStr) && Pattern.matches(DATE_PATTERN, fimStr)) {
                LocalDate inicio = convertStringToLocalDate(inicioStr);
                LocalDate fim = convertStringToLocalDate(fimStr);
                preencherTabela(null, inicio, fim);
            } else {
                preencherTabela(null, LocalDate.of(2024, 1, 1), LocalDate.now());
            }
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dataInicioField.setText("");
        dataFimField.setText("");
        idClienteField.setText("");
        preparaTabelaRelatorioVendas();
        preencherTabela(null, null, null);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField dataFimField;
    private javax.swing.JFormattedTextField dataInicioField;
    private javax.swing.JTextField idClienteField;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel jTotalVendasLabel;
    // End of variables declaration//GEN-END:variables
}
