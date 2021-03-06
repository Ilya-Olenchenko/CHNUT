/*
 * @(#)TableExample3.java	1.9 99/04/23
 *
 * Copyright (c) 1997-1999 by Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

/**
 * An example showing the JTable with a dataModel that is not derived
 * from a database. We add the optional TableSorter object to give the
 * JTable the ability to sort.
 *
 * @version 1.3 10/14/97
 * @author Philip Milne
 */

import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;

public class TableExample3 {

    public TableExample3() {
        JFrame frame = new JFrame("Table");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}});

        // Take the dummy data from SwingSet.
        final String[] names = {"First Name", "Last Name", "Favorite Color",
                                "Favorite Number", "Vegetarian"};
        final Object[][] data = {
	    {"Mark", "Andrews", "Red", new Integer(2), new Boolean(true)},
	    {"Tom", "Ball", "Blue", new Integer(99), new Boolean(false)},
	    {"Alan", "Chung", "Green", new Integer(838), new Boolean(false)},
	    {"Jeff", "Dinkins", "Turquois", new Integer(8), new Boolean(true)},
	    {"Amy", "Fowler", "Yellow", new Integer(3), new Boolean(false)},
	    {"Brian", "Gerhold", "Green", new Integer(0), new Boolean(false)},
	    {"James", "Gosling", "Pink", new Integer(21), new Boolean(false)},
	    {"David", "Karlton", "Red", new Integer(1), new Boolean(false)},
	    {"Dave", "Kloba", "Yellow", new Integer(14), new Boolean(false)},
	    {"Peter", "Korn", "Purple", new Integer(12), new Boolean(false)},
	    {"Phil", "Milne", "Purple", new Integer(3), new Boolean(false)},
	    {"Dave", "Moore", "Green", new Integer(88), new Boolean(false)},
	    {"Hans", "Muller", "Maroon", new Integer(5), new Boolean(false)},
	    {"Rick", "Levenson", "Blue", new Integer(2), new Boolean(false)},
	    {"Tim", "Prinzing", "Blue", new Integer(22), new Boolean(false)},
	    {"Chester", "Rose", "Black", new Integer(0), new Boolean(false)},
	    {"Ray", "Ryan", "Gray", new Integer(77), new Boolean(false)},
	    {"Georges", "Saab", "Red", new Integer(4), new Boolean(false)},
	    {"Willie", "Walker", "Phthalo Blue", new Integer(4), new Boolean(false)},
	    {"Kathy", "Walrath", "Blue", new Integer(8), new Boolean(false)},
	    {"Arnaud", "Weber", "Green", new Integer(44), new Boolean(false)}
        };

        // Create a model of the data.
        TableModel dataModel = new AbstractTableModel() {
            // These methods always need to be implemented.
            public int getColumnCount() { return names.length; }
            public int getRowCount() { return data.length;}
            public Object getValueAt(int row, int col) {return data[row][col];}

            // The default implementations of these methods in
            // AbstractTableModel would work, but we can refine them.
            public String getColumnName(int column) {return names[column];}
            public Class getColumnClass(int col) {return getValueAt(0,col).getClass();}
            public boolean isCellEditable(int row, int col) {return (col==4);}
            public void setValueAt(Object aValue, int row, int column) {
                data[row][column] = aValue;
            }
         };

        // Instead of making the table display the data as it would normally with:
        // JTable tableView = new JTable(dataModel);
        // Add a sorter, by using the following three lines instead of the one above.
        TableSorter  sorter = new TableSorter(dataModel);
        JTable    tableView = new JTable(sorter);
        sorter.addMouseListenerToHeaderInTable(tableView);

        JScrollPane scrollpane = new JScrollPane(tableView);

        scrollpane.setPreferredSize(new Dimension(700, 300));
        frame.getContentPane().add(scrollpane);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TableExample3();
    }
}
