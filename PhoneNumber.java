import java.sql.*;
import java.io.*;
public class PhoneNumber {
	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	String url, user, password;
	BufferedReader in;
	
	PhoneNumber() throws IOException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
		url="jdbc:mysql://localhost/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
		user="root";
		password="jacksb2an";
		in=new BufferedReader(new InputStreamReader(System.in));
		
		this.process();
		System.out.println("프로그램 종료!");
	}
	
	public void process() throws IOException{
		while(true) {
			System.out.println("1.추가 2.검색 3.삭제 4.수정 5.전체보기 ");
			int select= System.in.read() -48;
			if(select==5) return;
			in.readLine();
			switch(select) {
			case 1 : 
				insert();
				break;
			//case 2 :
				//search();
				//break;
			case 3:
				delete();
				break;
			case 4:
				modify();
				break;
			case 5:
				info();
				break;
			default :
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}
	
	public boolean checkNumber(String number) throws SQLException{
		String sql ="select * from telephone_db.telephone where _number=?";
		try {
			con=DriverManager.getConnection(url, user, password);
			ps=con.prepareStatement(sql);
			ps.setString(1,  number);
			if(rs.next())
				return true;
			else 
				return false;
		}finally {
			if(rs != null) 
				rs.close();
			if(ps != null) 
				ps.close();
			if(con != null) 
				con.close();
		}
	}
	
	public void insert() {
		String sql="insert into telephone_db.telephone valuse(?,?)";
		try{
			ps=con.prepareStatement(sql);
			boolean checkNumber=false;
			String number=null;
			do {
				System.out.println("전화번호 입력:");
				number=in.readLine();
				checkNumber=checkNumber(number);
			}while(checkNumber); 
				System.out.print("이름 입력:");
				String name = in.readLine();
				ps.setString(1, name);
				ps.setString(2, number);
				int n = ps.executeUpdate();
				if(n>0) {
					System.out.println("번호 추가 성공");
				}else {
					System.out.println("번호 추가 실패");
				}
			}catch(Exception e) {
				System.out.print(e.getMessage());
				System.exit(0);
			}finally {
				try {
					if(ps !=null)
						ps.close();
					if(con != null)
						con.close();
				}catch(SQLException e) {
					
				}
				
		}
	}
		
		
		
	
	
	public void delete() {
		String sql="delete from telephone_db.telephne where name=?";
		try {
			con=DriverManager.getConnection(url, user, password);
			ps=con.prepareStatement(sql);
			System.out.print("삭제할 이름은?");
			String name=in.readLine();
			ps.setString(1, name);
			int n =ps.executeUpdate();
			if(n>0) {
				System.out.println("삭제 성공");
			}else {
				System.out.println("삭제 실패");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}finally {
			try {
				if(ps != null)
					ps.close();
				if(con != null)
					con.close();
			}catch(SQLException e) {
			}
			
		}
		
	}
	
	public void modify() {
		String sql= "select * from telephone_db.telephone where name=?";
		try {
			con=DriverManager.getConnection(url, user, password);
			ps=con.prepareStatement(sql);
			System.out.println("수정할 이름:");
			String modname=in.readLine();
			ps.setString(1, modname);
			rs=ps.executeQuery();
			if(rs.next()) {
				
				sql="update telephone_db.telephone set name=? _number=?";
				System.out.println("수정할 이름 :");
				String newName=in.readLine();
				System.out.println("수정할 전화번호 :");
				String modTel=in.readLine();
				ps.setString(1, newName);
				ps.setString(2, modTel);
				
				int n=ps.executeUpdate();
				
				if(n>0) {
					System.out.println("수정 성공");
				}
				else {
					System.out.println("수정 실패");
				}
			}else {
				System.out.println(modname +"님은 등록되어 있지 않습니다.");
			}
		}
		catch(Exception e) {
		}finally {
				try {
					if(rs != null)
						rs.close();
					if(ps != null)
						ps.close();
					if( con != null)
						con.close();
					}
			catch(SQLException e) {}
		}
	}
			
	public void info() {
		String sql="select * from telephone_db.telephone";
		try {
			con=DriverManager.getConnection(url,user,password);
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next()) {
				String name=rs.getString(1);
				String number=rs.getString(2);
				System.out.println(name+"\t"+number);
			}
		}catch(Exception e) {
				e.printStackTrace();
		}finally {
			try {
					if(rs != null)
						rs.close();
					if(ps != null)
						ps.close();
					if(con !=null)
						con.close();
				}catch (SQLException e) {}
			}
		}
	
		
	
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new PhoneNumber();
	}

}
