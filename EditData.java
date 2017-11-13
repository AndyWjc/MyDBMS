package sql;
/*
 * 
 * EditData类，提供新建，删除，修改的功能
 * 
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;

public class EditData extends JFrame implements ActionListener {
	ArrayList<JLabel> colName = new ArrayList<JLabel>();
	ArrayList<JTextField> values = new ArrayList<JTextField>();
	HashMap<String, String> data;
	private String database;
	private String table;
	private int x;
	private int y;
	private int width;
	private int height;
	boolean nu = true;// 标记是新建还是修改，true是修改，false是新建

	EditData(String database, String table, int x, int y, int width,
			int height, int width2, HashMap<String, String> data) {
		// 参数作用域转移
		this.database = database;
		this.table = table;
		this.data = data;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		// 设定窗体显示位置
		this.setLocation(x, y + height);

		// 根据窗体宽度，计算每行能够安排的项目数,每个列提供了200个像素点
		int cols = (width + width2) / 200;

		// 计算需要几行能够显示全部项目
		int rows = 0;
		if (data.size() % cols == 0) {// 处理除不开的问题
			rows = data.size() / cols;
		} else {
			rows = data.size() / cols + 1;
		}
		// 计算窗体的高度为，每行15个像素点，加上下方按钮的100个像素点
		int h = rows * 15 + 100;
		this.setSize(width + width2, h);

		// 循环new　JLabel
		for (String col : data.keySet()) {
			JLabel l = new JLabel(col);
			colName.add(l);
		}

		// 循环new JTextField
		for (String v : data.values()) {
			JTextField f = new JTextField(v);
			values.add(f);
		}

		// new功能按钮
		JButton btnNew = new JButton("添加");
		JButton btnDelete = new JButton("删除");
		JButton btnSave = new JButton("保存");

		// 注册事件监听
		btnNew.addActionListener(this);
		btnDelete.addActionListener(this);
		btnSave.addActionListener(this);

		// 按钮布局
		JPanel panButton = new JPanel();
		panButton.setLayout(new GridLayout(1, 3));

		panButton.add(btnNew);
		panButton.add(btnDelete);
		panButton.add(btnSave);

		// 输入框布局
		JPanel panInput = new JPanel();
		panInput.setLayout(new GridLayout(rows, cols));

		// 将标签和输入框组合在一个小的面板中，放在两者不在一起
		for (int i = 0; i < data.size(); i++) {
			JPanel p = new JPanel();
			p.setLayout(new GridLayout(1, 2));
			p.add((JLabel) colName.get(i));
			p.add((JTextField) values.get(i));

			panInput.add(p);
		}

		this.setLayout(new BorderLayout());

		this.add(panInput, BorderLayout.CENTER);
		this.add(panButton, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("添加")) {
			// 清空所以的输入框
			nu = false;
			for (JTextField txt : values) {
				txt.setText("");
			}
		}
		if (arg0.getActionCommand().equals("删除")) {
			try {
				// 拼接SQL语句
				String SQLStr = "delete from " + table;
				SQLStr += " where 1=1 ";
				for (String col : data.keySet()) {
					SQLStr += " and " + col + "='";
					SQLStr += changCode(data.get(col).toString()) + "' ";
				}

				// 发出SQL指令
				Connection cn = DataBase.getConnection(database);
				Statement st = cn.createStatement();
				st.executeUpdate(SQLStr);

				review();
			} catch (Exception e) {
			}
		}
		if (arg0.getActionCommand().equals("保存")) {
			if (nu) {
				// 修改
				try {
					// 拼接SQL语句
					String SQLStr = "update " + table + " set ";
					for (int i = 0; i < colName.size(); i++) {
						SQLStr += colName.get(i).getText() + "='";
						SQLStr += changCode(values.get(i).getText());
						SQLStr += "',";
					}
					SQLStr = SQLStr.substring(0, SQLStr.length() - 1);// 去掉最后的一个逗号
					SQLStr += " where 1=1 ";
					for (String col : data.keySet()) {
						if (data.get(col) != null) {
							SQLStr += " and " + col + "='";
							SQLStr += changCode(data.get(col).toString()) + "' ";
						}
					}

					System.out.println(SQLStr);

					// 发出SQL指令
					Connection cn = DataBase.getConnection(database);
					Statement st = cn.createStatement();
					st.executeUpdate(SQLStr);

					review();

				} catch (Exception e) {
				}
			} else {
				// 新建
				try {
					// 拼接SQL语句
					String SQLStr = "insert into " + table + "(";
					for (String col : data.keySet()) {
						SQLStr += col + ",";
					}
					SQLStr = SQLStr.substring(0, SQLStr.length() - 1);// 去掉最后的一个逗号
					SQLStr += ") values(";
					for (JTextField txt : values) {
						SQLStr += "'" + txt.getText() + "',";
					}
					SQLStr = SQLStr.substring(0, SQLStr.length() - 1);// 去掉最后的一个逗号
					SQLStr += ")";

					// 发出SQL指令
					Connection cn = DataBase.getConnection(database);
					Statement st = cn.createStatement();
					st.executeUpdate(SQLStr);

					review();
				} catch (Exception e) {
				}
			}
		}
	}

	public void review() {
		this.setVisible(false);
		ShowTable.w.setVisible(false);
		ShowTable.w = new ShowData(database, table, x, y, width, height);
		ShowTable.w.setVisible(true);
	}
	public String changCode(String s) {
		String ss ="";
		try{
			ss =  new String(s.getBytes("gb2312") , "iso-8859-1") ;
		}catch(Exception e){}
		return ss ;
	}
}