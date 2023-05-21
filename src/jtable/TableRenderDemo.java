package jtable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * oracletutorial.component.TableDemo is just like oracletutorial.component.SimpleTableDemo, except that it
 * uses a custom TableModel.
 */
public class TableRenderDemo extends JPanel {
    private boolean DEBUG = false;
    private static final int MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

    public TableRenderDemo() {
        super(new GridLayout(1, 0));

        JTable table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Set up column sizes.
        initColumnSizes(table);

        //Fiddle with the Sport column's cell editors/renderers.
//        setUpSportColumn(table, table.getColumnModel().getColumn(2));
        // TODO: Create the method implementation for this call.

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
        private void initColumnSizes (JTable table){
            MyTableModel model = (MyTableModel) table.getModel();
            TableColumn column = null;
            Component comp = null;
            int headerWidth = 0;
            int cellWidth = 0;
            Object[] longValues = model.longValues;
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();

            for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
                column = table.getColumnModel().getColumn(i);

                comp = headerRenderer.getTableCellRendererComponent(null,
                        column.getHeaderValue(), false, false, 0, 0);
                headerWidth = comp.getPreferredSize().width;

                comp = table.getDefaultRenderer(model.getColumnClass(i))
                        .getTableCellRendererComponent(table, longValues[i], false, false,
                                0, i);
                cellWidth = comp.getPreferredSize().width;

                if (DEBUG) {
                    System.out.println("Initializing width of column "
                            + i + ". "
                            + "headerWidth = " + headerWidth
                            + "; cellWidth = " + cellWidth);
                }

                column.setPreferredWidth(Math.max(headerWidth, cellWidth));
            }
        }


    class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
        private Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", 5, false},
                {"John", "Doe",
                        "Rowing", 3, true},
                {"Sue", "Black",
                        "Knitting", 2, false},
                {"Jane", "White",
                        "Speed reading", 20, true},
                {"Joe", "Brown",
                        "Pool", 10, false}
        };

        public final Object[] longValues = {"Jane", "Kathy",
                "None of the above",
                20, Boolean.TRUE};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }

    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("oracletutorial.TableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TableDemo newContentPane = new TableDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}