package yiwon.nlec.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import yiwon.nlec.web.entity.Member;


public class MemberService {

	private String url = "jdbc:oracle:thin:@59.21.132.215:51521/LSMS";
	private String uid = "nlec";
	private String passwd = "nlec_real";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String sql = "";
	
	public MemberService() {
		// TODO Auto-generated constructor stub
	}

	
	public int insertMember(Member member) {
		
		int result = 0;
		
		sql = "insert into member (id, 	pwd, 	name, 	gender, birthday, 	phone, 	regdate, email)"
				+ "         values( ?, 	?, 		?, 		?, 		?,			?,		sysdate, ?)";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, member.getId());
			pst.setString(2, member.getPwd());
			pst.setString(3, member.getName());
			pst.setString(4, member.getGender());
			pst.setString(5, member.getBirthday());
			pst.setString(6, member.getPhone());
			pst.setString(7, member.getEmail());
			
			result =  pst.executeUpdate();

			pst.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public String getLogin(String argId, String argPwd) {
		
		String pwd = "";
		String ret = "0";
		
		sql = "select pwd"
				+ " from member"
				+ " where id = ?";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, argId);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				pwd = rs.getString("pwd");
				if(pwd == null)
					ret = "존재하지않는 아이디 입니다!"	;
				else
				{
				
					if(!pwd.equals(argPwd))
						ret = "잘못된 비밀번호 입니다!";
				}
			}
			else
				ret = "존재하지않는 아이디 입니다!"	;

			rs.close();
			pst.close();
			con.close();
			

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return ret;
	}
	public int idChk(String argId) {
		
		int cnt = 0;
		
		sql = "select count(*) cnt"
				+ " from member"
				+ " where id = ?";
		
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, argId);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) 
				cnt = rs.getInt("cnt");
			else
				cnt = 0;
			
			rs.close();
			pst.close();
			con.close();
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return cnt;
	}


}
