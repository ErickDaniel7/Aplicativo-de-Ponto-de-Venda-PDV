package com.mycompany.pdv.view;

import com.mycompany.pdv.modelos.Cliente;
import com.mycompany.pdv.services.ClienteService;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CadastroClientePanel extends javax.swing.JPanel {

    private ClienteService service;
    private Cliente clienteSelecionado = new Cliente();
    private String filtroSelecionado = "Nome";

    public CadastroClientePanel(ClienteService service) {
        initComponents();
        this.service = service;
        preparaTabela();
        preencherTabela(null);
        jScrollPane1.setViewportView(jTable1);
        jSalvar.setText("Salvar");
        jRemover.setEnabled(false);
        jSalvar.setEnabled(false);
        jLimpar.setEnabled(false);
        prepareCampoTexto();
        preparaLabel();
    }

    private void preparaLabel() {
        nomeLabel.setText("Nome");
        nomeLabel.setForeground(Color.BLACK);
        enderecoLabel.setText("Email");
        enderecoLabel.setForeground(Color.BLACK);
        telefoneLabel.setText("Telefone");
        telefoneLabel.setForeground(Color.BLACK);


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
                        "ID", "Nome", "Email", "Telefone"
                }
        ) {
            Class[] types = new Class[]{
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

    /*Pegar id a partir da celula da tabela e chamar serviço para obter cliente especifico */
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            // Obtém os valores das células da linha selecionada com verificação de null
            String id = getCellValueAsString(jTable1, selectedRow, 0);
            selecionarCliente(id);
        }
    }

    /**
     * Pegar cliente de banco pelo id e preencher campos de texto
     */
    private void selecionarCliente(String id) {
        try {
            clienteSelecionado = service.obterClientePorID(id);
            jnomeField.setText(clienteSelecionado.getNome());
            jenderecoField.setText(clienteSelecionado.getEmail());
            jtelefoneField.setText(clienteSelecionado.getTelefone());
            jSalvar.setText("Atualizar");
            jRemover.setEnabled(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao selecionar um cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Garantir que celulas não retornem valores nulos e evitar
     * RuntimeExceptions
     */
    private String getCellValueAsString(JTable table, int row, int column) {
        Object value = table.getValueAt(row, column);
        return value != null ? value.toString() : "";
    }

    /**
     * Preencher tabela com objetos vindos da fonte de dados
     */
    private void preencherTabela(List<Cliente> clientes) {
        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Limpa a tabela
            if (clientes == null) {
                clientes = service.listarClientes();
            }
            for (Cliente cliente : clientes) {
                model.addRow(new Object[]{
                        cliente.getId().toString(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getTelefone()
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter lista de clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void limpar() {
        jnomeField.setText("");
        jenderecoField.setText("");
        jtelefoneField.setText("");

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

    private void salvar() {
        try {
            preparaLabel();
            if (validarCampos() == true) {
                if (clienteSelecionado.getId() != null) {
                    clienteSelecionado.setNome(jnomeField.getText());
                    clienteSelecionado.setEmail(jenderecoField.getText());
                    clienteSelecionado.setTelefone(jtelefoneField.getText());
                    service.atualizar(clienteSelecionado);
                } else {
                    Cliente cliente = new Cliente(null,
                            jnomeField.getText(),
                            jenderecoField.getText(),
                            jtelefoneField.getText()
                    );
                    service.salvar(cliente);
                }
                preencherTabela(null);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*Verificar se todos os campos foram preenchidos com seus tipos corretos*/
    private boolean validarCampos() {
        boolean estado = true;
        if (!Pattern.matches("^\\d{10,11}$", jtelefoneField.getText())) {
            telefoneLabel.setText("Telefone: " + "Formato inválido");
            telefoneLabel.setForeground(Color.red);
            estado = false;
        }
        return estado;
    }

    private boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nomeLabel = new javax.swing.JLabel();
        jenderecoField = new javax.swing.JTextField();
        enderecoLabel = new javax.swing.JLabel();
        jnomeField = new javax.swing.JTextField();
        telefoneLabel = new javax.swing.JLabel();
        jtelefoneField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPesquisarField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSalvar = new javax.swing.JButton();
        jRemover = new javax.swing.JButton();
        jLimpar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        jPanel1.setPreferredSize(new java.awt.Dimension(338, 428));

        nomeLabel.setText("Nome");

        jenderecoField.setPreferredSize(new java.awt.Dimension(64, 31));

        enderecoLabel.setText("Email");

        jnomeField.setPreferredSize(new java.awt.Dimension(64, 31));

        telefoneLabel.setText("Telefone de Contato");

        jtelefoneField.setPreferredSize(new java.awt.Dimension(64, 31));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jenderecoField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jnomeField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jtelefoneField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(nomeLabel)
                                                        .addComponent(enderecoLabel)
                                                        .addComponent(telefoneLabel))
                                                .addGap(0, 197, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(nomeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jnomeField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enderecoLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jenderecoField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(telefoneLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtelefoneField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(409, 71));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                },
                new String[]{
                        "Nome", "Sobrenome", "Endereço", "Idade", "Telefone"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPesquisarField.setPreferredSize(new java.awt.Dimension(64, 31));

        jLabel6.setText("Pesquisar");

        jButton4.setText("OK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Nome", "Email", "Telefone"}));
        jComboBox1.setPreferredSize(new java.awt.Dimension(80, 23));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                mudarItemBoxHandler(evt);
            }
        });

        jLabel8.setText("Filtrar por");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPesquisarField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel6))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addGap(8, 8, 8))
                                                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jPesquisarField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jSalvar.setText("Salvar");
        jSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSalvarActionPerformed(evt);
            }
        });

        jRemover.setText("Remover");
        jRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRemoverActionPerformed(evt);
            }
        });

        jLimpar.setText("Limpar");
        jLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_jSalvarActionPerformed

    private void jLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLimparActionPerformed
        limpar();
        clienteSelecionado = new Cliente();
        jSalvar.setText("Salvar");
        preencherTabela(null);
        preparaLabel();
        jRemover.setEnabled(false);
    }//GEN-LAST:event_jLimparActionPerformed

    private void jRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRemoverActionPerformed
        try {
            service.remover(clienteSelecionado.getId());
            clienteSelecionado = new Cliente();
            jSalvar.setText("Salvar");
            jRemover.setEnabled(false);
            preencherTabela(null);
            limpar();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jRemoverActionPerformed

    private void mudarItemBoxHandler(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_mudarItemBoxHandler
        filtroSelecionado = evt.getItem().toString();
    }//GEN-LAST:event_mudarItemBoxHandler

    /*Filtro de pesquisa*/
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            List<Cliente> clientes = new ArrayList();
            if ("Nome".equals(filtroSelecionado)) {
                clientes = service.obterClientePorNome(jPesquisarField.getText());
            }
            if ("Telefone".equals(filtroSelecionado)) {
                clientes = service.obterClientePorTelefone(jPesquisarField.getText());
            }
            if ("Email".equals(filtroSelecionado)) {
                clientes = service.obterClientePorEmail(jPesquisarField.getText());
            }
            preencherTabela(clientes);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao obter cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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

        jnomeField.getDocument().addDocumentListener(listener);
        jenderecoField.getDocument().addDocumentListener(listener);
        jtelefoneField.getDocument().addDocumentListener(listener);
    }

    /*Quando o listener associado ao field é acionado esse metodo habilita ou nao os botoes de controle*/
    private void checkFields() {
        String nome = jnomeField.getText();
        String endereco = jenderecoField.getText();
        String telefone = jtelefoneField.getText();

        boolean allFieldsFilled = (nome != null && !nome.trim().isEmpty())
                && (endereco != null && !endereco.trim().isEmpty())
                && (telefone != null && !telefone.trim().isEmpty());

        boolean onFieldsFilled = (nome != null && !nome.trim().isEmpty())
                || (endereco != null && !endereco.trim().isEmpty())
                || (telefone != null && !telefone.trim().isEmpty());

        jSalvar.setEnabled(allFieldsFilled);
        jLimpar.setEnabled(onFieldsFilled);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel enderecoLabel;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JButton jLimpar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField jPesquisarField;
    private javax.swing.JButton jRemover;
    private javax.swing.JButton jSalvar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jenderecoField;
    private javax.swing.JTextField jnomeField;
    private javax.swing.JTextField jtelefoneField;
    private javax.swing.JLabel nomeLabel;
    private javax.swing.JLabel telefoneLabel;
    // End of variables declaration//GEN-END:variables
}
