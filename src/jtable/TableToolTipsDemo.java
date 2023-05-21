package jtable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * TableToolTipsDemo is just like TableDemo except that it
 * sets up tool tips for both cells and column headers.
 */
public class TableToolTipsDemo extends JPanel {
    private boolean DEBUG = false;
    protected String[] columnToolTips = {null,
            null,
            "The person's favorite sport to participate in",
            "The number of years the person has played the sport",
            "If checked, the person eats no meat"};

    public TableToolTipsDemo() {
        super(new GridLayout(1, 0));

        JTable table = new JTable(new MyTableModel()) {

            //Implement table cell tool tips.
            public String getToolTipText(MouseEvent event) {
                String tip = null;
                Point p = event.getPoint();
                int rowIndex = rowAtPoint(p);
                int colIndex = columnAtPoint(p);
                int realColumnIndex = convertColumnIndexToModel(colIndex);

                if (realColumnIndex == 2) { //Currently the Sport column
                    tip = "This person's favorite sport to participate in is: "
                            + getValueAt(rowIndex, colIndex);
                } else if (realColumnIndex == 4) { //Veggie column
                    TableModel model = getModel();
                    String firstName = (String) model.getValueAt(rowIndex, 0);
                    String lastName = (String) model.getValueAt(rowIndex, 1);
                    Boolean veggie = (Boolean) model.getValueAt(rowIndex, 4);
                    if (Boolean.TRUE.equals(veggie)) {
                        tip = firstName + " " + lastName
                                + " is a vegetarian";
                    } else {
                        tip = firstName + " " + lastName
                                + " is not a vegetarian";
                    }
                } else {
                    //You can omit this part if you know you don't
                    //have any renderers that supply their own tool
                    //tips.
                    tip = super.getToolTipText(event);
                }
                return tip;
            }

            //Implement table header tool tips.
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(this.columnModel) {
                    public String getToolTipText(MouseEvent event) {
                    String tip = null;
                        Point p = event.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        return columnToolTips[realIndex];
                    }
                };
            }
        };
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        /*
        The scroll pane automatically places the table header at the top of the viewport.
        The column names remain visible at the top of the viewing area when the table
        data is scrolled. If you are using a table without a scroll pane, then you must
        get the table header component and place it yourself
         */
//        this.setLayout(new BorderLayout());
//        this.add(table.getTableHeader(),BorderLayout.PAGE_START);
//        this.add(table, BorderLayout.CENTER);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }


    class MyTableModel extends AbstractTableModel  {

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
        JFrame frame = new JFrame("TableToolTipsDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new TableToolTipsDemo();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    //    private void addKeyBindings() {
//        //root maps
//        InputMap im = this.getRootPane().getInputMap(
//                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
//        ActionMap am = this.getRootPane().getActionMap();
//        //add custom action
//        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, MASK), "delete");
//        am.put("delete", saveAction());
//    }
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