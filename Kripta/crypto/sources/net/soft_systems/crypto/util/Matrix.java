/* Generated by Together */

package net.soft_systems.crypto.util;
import net.soft_systems.integrator.Debug;
public class Matrix {
	protected int rowCount;
	protected int colCount;
	protected Object values[] [];
	public int getRowCount() { return rowCount; }
	public int getColCount() { return colCount; }
	public Matrix(int rowCount, int colCount) {
		this.rowCount = rowCount;
		this.colCount = colCount;
		values = initEmptyMatrix(rowCount, colCount);
	}
	public Matrix() {
		this.rowCount = 0;
		this.colCount = 0;
		values = initEmptyMatrix(rowCount, colCount);
	}
	protected Object[] [] initEmptyMatrix(int rowCount, int colCount) {
		if (rowCount > 0 && colCount > 0) { return new Object[rowCount] [colCount]; }
		else { return null; }
	}
	public void setValue(int row, int col, Object value) {
		if (values != null) {
			try { values[row] [col] = value; }
			catch (ArrayIndexOutOfBoundsException ex) {
				Debug.error("???????? ?????? ??? ????????? ???????? " + value + " ? ?????:" + row +
					" ???????:" + col);
			}
		}
		else {
			Debug.error("?????? ???????? ? ??????? ??? ????????? ???????? " + value + " ? ?????:" + row +
				" ???????:" + col);
		}
	}
	public Object getValue(int row, int col) {
		if (values != null) {
			try { return values[row] [col]; }
			catch (ArrayIndexOutOfBoundsException ex) {
				Debug.error("???????? ?????? ??? ????????? ???????? ? ?????:" + row + " ???????:" + col);
			}
		}
		else { Debug.error("???????? ?????? ??? ????????? ???????? ? ?????:" + row + " ???????:" + col); }
		return null;
	}
	public void addRow() {
		Object newValues[] [] = initEmptyMatrix(rowCount + 1, colCount);
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) { newValues[r] [c] = values[r] [c]; }
		}
		rowCount++;
		values = newValues;
	}
	public void addColumn() {
		Object newValues[] [] = initEmptyMatrix(rowCount, colCount + 1);
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) { newValues[r] [c] = values[r] [c]; }
		}
		colCount++;
		values = newValues;
	}
	public void copyRows(int from, int to) {
		for (int c = 0; c < colCount; c++) { values[to] [c] = values[from] [c]; }
	}
	public void delLastRow() {
		Object newValues[] [] = initEmptyMatrix(rowCount - 1, colCount);
		for (int r = 0; r < rowCount - 1; r++) {
			for (int c = 0; c < colCount; c++) { newValues[r] [c] = values[r] [c]; }
		}
		rowCount--;
		values = newValues;
	}
	public void delLastColumn() {
		Object newValues[] [] = initEmptyMatrix(rowCount, colCount - 1);
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount - 1; c++) { newValues[r] [c] = values[r] [c]; }
		}
		colCount--;
		values = newValues;
	}
	public void delRow(int row) {
		Object newValues[] [] = initEmptyMatrix(rowCount - 1, colCount);
		int oldR;
		for (int r = 0; r < rowCount - 1; r++) {
			oldR = (r >= row) ? r - 1 : r;
			for (int c = 0; c < colCount; c++) { newValues[r] [c] = values[oldR] [c]; }
		}
		rowCount--;
		values = newValues;
	}
	public void delColumn(int col) {
		Object newValues[] [] = initEmptyMatrix(rowCount, colCount - 1);
		int oldC;
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount - 1; c++) {
				oldC = (c >= col) ? c - 1 : c;
				newValues[r] [c] = values[r] [oldC];
			}
		}
		colCount--;
		values = newValues;
	}
	public void insertColumn(int col) {
		Object newValues[] [] = initEmptyMatrix(rowCount, colCount + 1);
		int oldC;
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) {
				oldC = (c >= col) ? c + 1 : c;
				newValues[r] [c] = values[r] [oldC];
			}
		}
		colCount++;
		values = newValues;
	}
	public void insertRow(int row) {
		Object newValues[] [] = initEmptyMatrix(rowCount + 1, colCount);
		int oldR;
		for (int r = 0; r < rowCount; r++) {
			oldR = (r >= row) ? r + 1 : r;
			for (int c = 0; c < colCount; c++) { newValues[r] [c] = values[oldR] [c]; }
		}
		rowCount++;
		values = newValues;
	}
}

