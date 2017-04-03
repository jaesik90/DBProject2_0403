//emp 테이블의 데이터를 처리하는 컨트롤러!!
package oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public class DeptModel extends AbstractTableModel{
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String[] column; //컬럼을 넣을 배열
	String[][] data; //레코드를 넣을 배열

	public DeptModel(Connection con){
		this.con=con;
	
		 /* 1. 드라이버 로드
		 * 2. 접속
		 * 3. 쿼리문 수행
		 * 4. 접속닫기
		 * */
		
		try {
	
			if(con!=null){
				String sql="select * from dept";
				//아래의 pstmt에 의해 생성되는 rs는 커서가
				//자유로울 수 있다...
				pstmt=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				//결과집합 반환
				rs=pstmt.executeQuery();
				
				//컬럼을 구해보자
				ResultSetMetaData meta=rs.getMetaData();
				int count=meta.getColumnCount();
				column = new String[count];
				//컬럼명을 채우자!
				for(int i=0; i<column.length;i++){
					//metaData는 첫번째 데이터를 0이 아닌 1번째부터 시작한다...
					column[i]=meta.getColumnName(i+1);
				}
				
				rs.last();//제일 마지막으로 보냄
				int total=rs.getRow();//레코드 번호
				rs.beforeFirst();
				//총 레코드수를 알았으니, 이차원배열을
				//생성해보자
				data= new String[total][column.length];
				
				//레코드를 이차원배열인 data에 채워넣기!!
				for(int a=0; a<data.length; a++){//층수
					rs.next();
					for(int i=0; i<data[a].length; i++){//호수
						data[a][i]=rs.getString(column[i]);
					}
				}
				
				
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
	
	public int getColumnCount() {

		return column.length;
	}
	
	public String getColumnName(int index) {
		
		return column[index];
	}

	public int getRowCount() {

		return data.length;
	}


	public Object getValueAt(int row, int col) {

		return data[row][col];
	}
 
}
