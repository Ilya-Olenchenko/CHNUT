/*
 * @(#)OldJTable.java	1.4 99/04/23
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

import java.lang.Thread;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.table.*;


/**
 *  The OldJTable is an unsupported class containing some methods that were 
 *  deleted from the JTable between releases 0.6 and 0.7
 */
public class OldJTable extends JTable
{
   /*
    *  A new convenience method returning the index of the column in the co-ordinate 
    *  space of the view. 
    */
    public int getColumnIndex(Object identifier) { 
        return getColumnModel().getColumnIndex(identifier); 
    }
    
// 
//  Methods deleted from the JTable because they only work with the 
//  DefaultTableModel. 
//

    public TableColumn addColumn(Object columnIdentifier, int width) {
	return addColumn(columnIdentifier, width, null, null, null);
    }

    public TableColumn addColumn(Object columnIdentifier, Vector columnData) {
	return addColumn(columnIdentifier, -1, null, null, columnData);
    }

    // Override the new JTable implementation - it will not add a column to the 
    // DefaultTableModel. 
    public TableColumn addColumn(Object columnIdentifier, int width,
				 TableCellRenderer renderer,
				 TableCellEditor editor) {
        return addColumn(columnIdentifier, width, renderer, editor, null);
    }

    public TableColumn addColumn(Object columnIdentifier, int width,
				 TableCellRenderer renderer,
				 TableCellEditor editor, Vector columnData) {
	checkDefaultTableModel();

	// Set up the model side first
	DefaultTableModel m = (DefaultTableModel)getModel(); 
	m.addColumn(columnIdentifier, columnData);
	
	// The column will have been added to the end, so the index of the 
	// column in the model is the last element. 
	TableColumn newColumn = new TableColumn(m.getColumnCount()-1, width, renderer, editor); 
        super.addColumn(newColumn); 
        return newColumn; 
    }

    // Not possilble to make this work the same way ... change it so that 
    // it does not delete columns from the model. 
    public void removeColumn(Object columnIdentifier) {
	super.removeColumn(getColumn(columnIdentifier));
    }

    public void addRow(Object[] rowData) {
	checkDefaultTableModel();
	((DefaultTableModel)getModel()).addRow(rowData);
    }

    public void addRow(Vector rowData) {
	checkDefaultTableModel();
	((DefaultTableModel)getModel()).addRow(rowData);
    }
    
    public void removeRow(int rowIndex) {
	checkDefaultTableModel();
	((DefaultTableModel)getModel()).removeRow(rowIndex);
    }

    public void moveRow(int startIndex, int endIndex, int toIndex) {
	checkDefaultTableModel();
	((DefaultTableModel)getModel()).moveRow(startIndex, endIndex, toIndex);
    }

    public void insertRow(int rowIndex, Object[] rowData) {
	checkDefaultTableModel();
	((DefaultTableModel)getModel()).insertRow(rowIndex, rowData);
    }

    public void insertRow(int rowIndex, Vector rowData) {
	checkDefaultTableModel();
	((DefaultTableModel)getModel()).insertRow(rowIndex, rowData);
    }

    public void setNumRows(int newSize) {
	checkDefaultTableModel();
	((DefaultTableModel)getModel()).setNumRows(newSize);
    }

    public void setDataVector(Vector newData, Vector columnIds) {
	checkDefaultTableModel();
	((DefaultTableModel)getModel()).setDataVector(newData, columnIds);
    }

    public void setDataVector(Object[][] newData, Object[] columnIds) {
	checkDefaultTableModel();
	((DefaultTableModel)getModel()).setDataVector(newData, columnIds);
    }
        
    protected void checkDefaultTableModel() {
        if(!(dataModel instanceof DefaultTableModel))
            throw new InternalError("In order to use this method, the data model must be an instance of DefaultTableModel.");
    }

//
//  Methods removed from JTable in the move from identifiers to ints. 
//

    public Object getValueAt(Object columnIdentifier, int rowIndex) {
	return  super.getValueAt(rowIndex, getColumnIndex(columnIdentifier));
    }
    
    public boolean isCellEditable(Object columnIdentifier, int rowIndex) {
	return  super.isCellEditable(rowIndex, getColumnIndex(columnIdentifier));
    }
    
    public void setValueAt(Object aValue, Object columnIdentifier, int rowIndex) {
	super.setValueAt(aValue, rowIndex, getColumnIndex(columnIdentifier));
    }

    public boolean editColumnRow(Object identifier, int row) {
	return super.editCellAt(row, getColumnIndex(identifier));
    }

    public void moveColumn(Object columnIdentifier, Object targetColumnIdentifier) {
	moveColumn(getColumnIndex(columnIdentifier),
		   getColumnIndex(targetColumnIdentifier));
    }

    public boolean isColumnSelected(Object identifier) { 
	return isColumnSelected(getColumnIndex(identifier));
    }

    public TableColumn addColumn(int modelColumn, int width) {
	return addColumn(modelColumn, width, null, null);
    }

    public TableColumn addColumn(int modelColumn) {
	return addColumn(modelColumn, 75, null, null);
    }
    
    /**
     *  Creates a new column with <I>modelColumn</I>, <I>width</I>,
     *  <I>renderer</I>, and <I>editor</I> and adds it to the end of
     *  the JTable's array of columns. This method also retrieves the
     *  name of the column using the model's <I>getColumnName(modelColumn)</I>
     *  method, and sets the both the header value and the identifier
     *  for this TableColumn accordingly.
     *  <p>
     *  The <I>modelColumn</I> is the index of the column in the model which
     *  will supply the data for this column in the table. This, like the
     *  <I>columnIdentifier</I> in previous releases, does not change as the
     *  columns are moved in the view.
     *  <p>
     *  For the rest of the JTable API, and all of its associated classes,
     *  columns are referred to in the co-ordinate system of the view, the
     *  index of the column in the model is kept inside the TableColumn
     *  and is used only to retrieve the information from the appropraite
     *  column in the model.
     *  <p>
     *
     *  @param	modelColumn     The index of the column in the model
     *  @param	width		The new column's width.  Or -1 to use
     *				the default width
     *  @param	renderer	The renderer used with the new column.
     *				Or null to use the default renderer.
     *  @param	editor		The editor used with the new column.
     *				Or null to use the default editor.
     */
    public TableColumn addColumn(int modelColumn, int width,
				 TableCellRenderer renderer,
				 TableCellEditor editor) {
	TableColumn newColumn = new TableColumn(modelColumn, width, renderer, editor);        
	addColumn(newColumn); 
	return newColumn; 
    }

//
//  Methods that had their arguments switched. 
//

// These won't work with the new table package. 

/*  
    public Object getValueAt(int columnIndex, int rowIndex) {
	return super.getValueAt(rowIndex, columnIndex);
    }

    public boolean isCellEditable(int columnIndex, int rowIndex) {
	return super.isCellEditable(rowIndex, columnIndex);
    }
    
    public void setValueAt(Object aValue, int columnIndex, int rowIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex); 
    }
*/

    public boolean editColumnRow(int columnIndex, int rowIndex) {
	return super.editCellAt(rowIndex, columnIndex);
    }

    public boolean editColumnRow(int columnIndex, int rowIndex, EventObject e){
        return super.editCellAt(rowIndex, columnIndex, e); 
    }


}  // End Of Class OldJTable
