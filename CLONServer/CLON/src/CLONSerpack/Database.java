package CLONSerpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private Connection con;
	private Statement stmt;
	private ResultSet rs = null;
	private String ip;
	private String name;
	private String id;
	private String pw;
	private String onoff;
	private String ban;
	private Socket c;
	private String appcheck;
	String logincheck = null;

	public Database() {
		init();
	}

	public void setalloff() {
		String str = "update clon_member set onoff = 'off';";

		try {
			stmt.executeUpdate(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager
					.getConnection("jdbc:mysql://localhost/CLON?user=root&password=1234");
			stmt = con.createStatement();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean connect() {
		String str = "Select * from CLON_member where id='" + this.id
				+ "' and pwd='" + this.pw + "';";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			if (rs.getString("id").equals(this.id)
					&& rs.getString("pwd").equals(this.pw)
					&& rs.getString("ip").equals(this.ip)
					&& rs.getString("ban").equals("x")) {
				if (rs.getString("onoff").equals("off")) {
					String str2 = "Update Clon_member set onoff = 'on' where id ='"
							+ this.id + "';";
					stmt.executeUpdate(str2);
					logincheck = "yes";
					return true;
				} else {
					logincheck = "no";
					return false;
				}
			} else if (rs.getString("ban").equals("o")) {
				logincheck = "ban";
				return false;
			} else
				return false;
		} catch (SQLException e) {
			logincheck = "yes";
			return false;
		}

	}

	public boolean appconnect() {
		String str = "Select * from CLON_member where id='" + this.id
				+ "' and pwd='" + this.pw + "';";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			if (rs.getString("id").equals(this.id)
					&& rs.getString("pwd").equals(this.pw)
					&& rs.getString("appcheck").equals("o")
					&& rs.getString("ban").equals("x")
					&& rs.getString("onoff").equals("off")) {
				String str2 = "Update Clon_member set onoff = 'on' where id ='"
						+ this.id + "';";
				stmt.executeUpdate(str2);
				logincheck = "yes";
				return true;
			} else if (rs.getString("ban").equals("o")) {
				logincheck = "ban";
				return false;
			} else if (rs.getString("appcheck").equals("x")) {
				logincheck = "appfalse";
				return false;
			} else {
				logincheck = "no";
				return false;
			}
		} catch (SQLException e) {
			logincheck = "no";
			return false;
		}
	}

	public boolean Newmemconnect() {
		String str = "Select * from CLON_member where ip='" + this.ip + "';";
		String str2 = "Insert into CLON_member values('" + this.id + "', '"
				+ this.pw + "', '" + this.name + "', '" + this.ip
				+ "', 'off','x', '" + this.appcheck + "');";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			if (rs.getString("ip").equals(this.ip)) {
				// /인설트
				return false;
			} else {
				stmt.execute(str2);
				return true;
			}
		} catch (SQLException e) {
			try {
				stmt.execute(str2);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return true;
		}

	}

	public boolean Checkconnect() {
		String str = "Select * from CLON_member where id='" + this.id + "';";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			if (rs.getString("id").equals(this.id)) {

				return false;
			} else {

				return true;
			}

		} catch (SQLException e) {

			return true;
		}

	}

	public boolean Findidconnect() {
		String str = "Select * from CLON_member where ip='" + this.ip + "';";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			if (rs.getString("ip").equals(this.ip)) {

				return true;
			} else {

				return false;
			}

		} catch (SQLException e) {
			return false;
		}
	}

	public boolean Findidpwconnect() {
		String str = "Select * from CLON_member where ip='" + this.ip
				+ "' and id = '" + this.id + "';";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			if (rs.getString("ip").equals(this.ip)
					&& rs.getString("id").equals(this.id)) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			return false;
		}

	}

	public void setonoff(String str) {
		String str2 = "Update Clon_member set onoff = 'off' where id = '" + str
				+ "';";
		try {
			stmt.executeUpdate(str2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ban(String str) {
		String str2 = "Update Clon_member set ban = 'o', onoff = 'off' where id = '"
				+ str + "';";
		try {
			stmt.executeUpdate(str2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getnic() {
		String str = "Select nicname from CLON_member where id ='" + this.id
				+ "';";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			this.name = rs.getString("nicname");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.name;
	}

	public String getid() {
		String str = "Select id from CLON_member where ip ='" + this.ip + "';";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			this.id = rs.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.id;
	}

	public String getpw() {
		String str = "Select pwd from CLON_member where ip ='" + this.ip
				+ "' and id = '" + this.id + "';";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			this.pw = rs.getString("pwd");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.pw;
	}

	public String getappcheck() {
		String str = "Select appcheck from CLON_member where id ='" + this.id
				+ "';";
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			this.appcheck = rs.getString("appcheck");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.appcheck;
	}

	public Connection getcon() {
		return this.con;
	}

	public Statement getstmt() {
		return this.stmt;
	}

	public int getcount() {
		String str = "Select * from CLON_member;";
		int count = 0;
		try {
			rs = stmt.executeQuery(str);
			rs.first();
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			return count;
		}
		return count;
	}

	public void setappcheck(String appcheck) {
		String str = "Update clon_member set appcheck = '" + appcheck
				+ "' where id = '" + this.id + "'; ";
		try {
			stmt.executeUpdate(str);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDataip(String ip) {
		this.ip = ip;
	}

	public void setDataip(String id, String ip) {
		this.ip = ip;
		this.id = id;
	}

	public void setData(String id) {// 중복체크
		this.id = id;
	}

	public void setData(String id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	public void setData(String id, String pw, String ip) { // 로그인
		this.id = id;
		this.pw = pw;
		this.ip = ip;
	}

	public void setData(String id, String pw, String name, String ip,
			String appcheck) { // 회원가입
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.ip = ip;
		this.appcheck = appcheck;
	}

}
