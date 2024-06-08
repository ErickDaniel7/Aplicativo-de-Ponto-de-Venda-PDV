
package com.mycompany.pdv.view.util;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class TableUtils {

    public static void preparaTabela(JTable table, String[] columnNames, Class<?>[] columnClasses) {
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Se necessário, implemente ações ao clicar na tabela
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[0][columnNames.length], // Inicializa com 0 linhas e o número de colunas fornecido
                columnNames
        ) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnClasses[columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public static <T> void preencherTabela(JTable table, List<T> items, String[] propertyNames) throws IOException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpa a tabela

        try {
            for (T item : items) {
                Object[] rowData = new Object[propertyNames.length];
                for (int i = 0; i < propertyNames.length; i++) {
                    Field field = item.getClass().getDeclaredField(propertyNames[i]);
                    field.setAccessible(true);
                    rowData[i] = field.get(item).toString();
                }
                model.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
