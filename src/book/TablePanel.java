/*
 JTable이 얹혀질 패널
 * */
package book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TablePanel extends JPanel{
	Connection con;
	JTable table;
	JScrollPane scroll;
	TableModel model;
	Book dto;
	//Vector ArrayList는 둘다 같다
	//차이점? 동기화 지원 여부
	//Vector는 객체 자체의 동기화를 가지고 있다...
	//동기화는 안정성이 좋지만 속도가 느리다..
	Vector list= new Vector();
	Vector<String> columName= new Vector<String>();
	int cols;//테이블의 칼럼 값
	String[] column;
	
	
	public TablePanel(){
		
		table = new JTable();
		scroll = new JScrollPane(table);
		this.setLayout(new BorderLayout());
		this.add(scroll);
		
		this.setBackground(Color.PINK);
		setPreferredSize(new Dimension(650, 550));
	}
	
	public void setConnection(Connection con){
		this.con=con;
		init();
		
		//테이블 모델을 jtable에 적용
		model = new AbstractTableModel() {
			
			public int getRowCount() {
				return list.size();
			}
			
			public int getColumnCount() {
				return cols;
			}
			public String getColumnName(int index) {
				
				return column[index];
			}

			public Object getValueAt(int row, int col) {
				Vector vec=(Vector) list.get(row);
				return vec.elementAt(col);
			}
			
		};
		//테이블에 모델적용
		table.setModel(model);
		
	}
	
	//book 테이블의 레코드 가져오기
	public void init(){
		String sql="select * from book order by book_id asc";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			cols=rs.getMetaData().getColumnCount();
			
			//기존 데이터 모두 지우기
			list.removeAll(list);
			column =new String[cols];
			//System.out.println(column);
			for(int i=0; i<column.length; i++){
				column[i]=rs.getMetaData().getColumnName(i+1);
			}
			
			//rs의 정보를 컬렉션의 DTO로 옮겨담자!!
			while(rs.next()){
			Vector<String> data = new Vector<String>();
			
			data.add(Integer.toString(rs.getInt("book_id")));
			data.add(rs.getString("book_name"));
			data.add(Integer.toString(rs.getInt("price")));
			data.add(rs.getString("img"));
			data.add(Integer.toString(rs.getInt("subcategory_id")));
			
			list.add(data);//기존 벡터에 벡터를 추가
			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
