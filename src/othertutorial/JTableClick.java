package othertutorial;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JTableClick extends JFrame {

    private JTable jTableClick;

    /**
     * Create new form
     */
    public JTableClick() throws HeadlessException {
        initComponents();
    }

    private void initComponents() {
        JScrollPane jScrollPane1 = new JScrollPane();
        jTableClick = new JTable();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    jTableClick.setModel(new DefaultTableModel(
            new Object[][] {
        {"0", "1", "2", "3"},
        {"4", "5", "6", "7"},
        {"8", "9", "10", "10"},
        {"10", "10", "20", "20"},
        {"20", "20", "30", "30"},
        {"50", "50", "40", "40"},
        {"100", "200", "300", "400"},
        {"500", "500", "1000", "2000"}
    },
            new String[] {
        "Value 1", "Value 2", "Value 3", "Value 4"
    }
    ));
        jTableClick.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jTableClickMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableClick);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(15, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
}
    private void jTableClickMouseClicked(MouseEvent evt) {

        int index = jTableClick.getSelectedRow();

        TableModel model = jTableClick.getModel();

        int value1 = Integer.parseInt(model.getValueAt(index, 0).toString());
        int value2 = Integer.parseInt(model.getValueAt(index, 1).toString());
        int value3 = Integer.parseInt(model.getValueAt(index, 2).toString());
        int value4 = Integer.parseInt(model.getValueAt(index, 3).toString());

        int sum = value1+value2+value3+value4;

        JOptionPane.showMessageDialog(null, "Sum = "+sum);
    }

        public static void main(String[] args) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                     IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(JTableClick.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new JTableClick().setVisible(true);
                }
            });
    }
}