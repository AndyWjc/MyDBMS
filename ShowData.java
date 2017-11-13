package sql;

import java.awt.* ;
import javax.swing.* ;

import java.awt.event.*;
import java.util.* ;

public class ShowData extends JFrame implements MouseListener{
	JTable t ;
	MyTableModel tm ;
	String database ;
	String table ;
	int x ;
	int y ;
	int width ;
	int height ;
	public EditData w ;
	ShowData(String database , String table , int x , int y , int width , int height) {
		this.database = database ;
		this.table = table ;
		this.x = x ;
		this.y = y ;
		this.width = width ;
		this.height = height ;
		
		//新的窗体出现在上一个窗体的右侧
		this.setLocation(x+width , y) ;
		
		tm = new MyTableModel(database , table) ;
		t = new JTable(tm) ;

		t.addMouseListener(this) ;
		
		int w = tm.getColumnCount()*100 ;
		
		this.setSize(width+w , 300) ;		
		
		this.add(new JScrollPane(t)) ;
	}

	/**
	 * Method mouseClicked
	 *
	 *
	 * @param e
	 *
	 */
	public void mouseClicked(MouseEvent e) {
		// TODO: Add your code here
	}

	/**
	 * Method mousePressed
	 *
	 *
	 * @param e
	 *
	 */
	public void mousePressed(MouseEvent e) {
		if(e.getClickCount()==2){
			int row = t.getSelectedRow() ;
			HashMap<String, String> hm = new HashMap<String , String>() ;
			int cols = tm.getColumnCount() ;
			for (int i = 0; i< cols; i++){
				hm.put(tm.getColumnName(i) , (String)tm.getValueAt(row , i)) ;
			}	
			
			if(w!=null){
				w.setVisible(false) ;
			}
			w = new EditData(database , table , x , y , width , height , this.getWidth() , hm ) ;
			w.setVisible(true) ;		
		}
	}

	/**z
	 * Method mouseReleased
	 *
	 *
	 * @param e
	 *
	 */
	public void mouseReleased(MouseEvent e) {
		// TODO: Add your code here
	}

	/**
	 * Method mouseEntered
	 *
	 *
	 * @param e
	 *
	 */
	public void mouseEntered(MouseEvent e) {
		// TODO: Add your code here
	}

	/**
	 * Method mouseExited
	 *
	 *
	 * @param e
	 *
	 */
	public void mouseExited(MouseEvent e) {
		// TODO: Add your code here
	}
}