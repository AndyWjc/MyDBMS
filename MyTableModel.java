package sql;

import javax.swing.table.TableModel;
import javax.swing.*;
import javax.swing.event.*;
import java.sql.* ;

public class MyTableModel implements TableModel{
	private ResultSet rs = null ;
	private ResultSetMetaData rsmd = null;
	MyTableModel(String database , String table) {
		try {
			Connection cn = DataBase.getConnection() ;
			Statement st = cn.createStatement() ;
			st.execute("use "+database) ;
			rs = st.executeQuery("select * from "+table) ;
			rsmd = rs.getMetaData() ;
	    }
	    catch (Exception ex) {
	    }
	}
	public int getRowCount() {
		int count = 0 ;
		try {
			rs.last() ;
			count = rs.getRow() ;
	    }
	    catch (Exception ex) {
	    }
		return count ;
	}

	/**
	 * Method getColumnCount
	 *
	 *
	 * @return
	 *
	 */
	public int getColumnCount() {
		int count = 0 ;
		try {
			count = rsmd.getColumnCount() ;
	    }
	    catch (Exception ex) {
	    }
		return count ;
	}

	/**
	 * Method getColumnName
	 *
	 *
	 * @param columnIndex
	 *
	 * @return
	 *
	 */
	public String getColumnName(int columnIndex) {
		String name = null ;
		try {
			name = rsmd.getColumnName(columnIndex+1) ;
	    }
	    catch (Exception ex) {
	    }
		return name ;
	}

	/**
	 * Method getColumnClass
	 *
	 *
	 * @param columnIndex
	 *
	 * @return
	 *
	 */
	public Class getColumnClass(int columnIndex) {
		return String.class ;
	}

	/**
	 * Method isCellEditable
	 *
	 *
	 * @param rowIndex
	 * @param columnIndex
	 *
	 * @return
	 *
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false ;
	}

	/**
	 * Method getValueAt
	 *
	 *
	 * @param rowIndex
	 * @param columnIndex
	 *
	 * @return
	 *
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object value = null ;
		try{
			rs.absolute(rowIndex+1) ;
			value = new String(rs.getString(columnIndex+1).getBytes("ISO-8859-1") , "gb2312") ;
		}catch(Exception e){}
		return value ;
	}

	/**
	 * Method setValueAt
	 *
	 *
	 * @param aValue
	 * @param rowIndex
	 * @param columnIndex
	 *
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO: Add your code here
	}

	/**
	 * Method addTableModelListener
	 *
	 *
	 * @param l
	 *
	 */
	public void addTableModelListener(TableModelListener l) {
		// TODO: Add your code here
	}

	/**
	 * Method removeTableModelListener
	 *
	 *
	 * @param l
	 *
	 */
	public void removeTableModelListener(TableModelListener l) {
		// TODO: Add your code here
	}

}