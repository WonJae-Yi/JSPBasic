package yiwon.nlec.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yiwon.nlec.web.entity.Notice;
import yiwon.nlec.web.entity.NoticeView;

public class NoticeService {

	private String url = "jdbc:oracle:thin:@59.21.132.215:51521/LSMS";
	private String uid = "nlec";
	private String passwd = "nlec_real";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String sql = "";

	public List<NoticeView> getNoticeList() {

		return getNoticeList("title", "", 1, "1");

	}

	public List<NoticeView> getNoticeList(int page) {

		return getNoticeList("title", "", page,"1");

	}

	public List<NoticeView> getNoticeList(String field, String query, int page, String argPub) {

		List<NoticeView> list = new ArrayList<>();

		sql = "select id, title, writer_id, regdate, hit, files, cmt_count, pub"
				+ " 	from (select row_number()over (order by id desc) no, id, title, writer_id, regdate, hit, files, pub,"
				+ "           (select count(*) from comments where notice_id = a.id) cmt_count"
				+ " 			from notice a "
				+ "				where pub like ?"
				+ " 			and " + field + " like '%'||?||'%')"
				+ " 	where no between ? and ? ";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			PreparedStatement pst = con.prepareStatement(sql);
			//pst.setString(1, field);
			pst.setString(1, argPub);
			pst.setString(2, query);
			pst.setInt(3, 1 + (page - 1) * 10);
			pst.setInt(4, page * 10);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("Title");
				String writerId = rs.getString("writer_id");
				Date regDate = rs.getDate("regDate");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				int cmtCount = rs.getInt("cmt_count");
				String pub = rs.getString("pub");

				NoticeView notice = new NoticeView(id, title, writerId, regDate, hit, files, cmtCount,pub);
				list.add(notice);
			}
			rs.close();
			pst.close();
			con.close();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public int getNoticeCount() {
		return getNoticeCount("title", "", "1");
	}

	public int getNoticeCount(String field, String query, String argPub) {
		int cnt = 0;
		sql = "select count(*) cnt"
				+ " from notice"
				+ " where pub like ?"
				+ " and " + field + " like '%'||?||'%'";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, argPub);
			pst.setString(2, query);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next())
				cnt = rs.getInt("cnt");

			rs.close();
			pst.close();
			con.close();

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return cnt;
	}

	//상세내역 가져오기
	public Notice getNotice(int id) {

		Notice notice = null;
		sql = "select * from notice where id = ? ";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				String title = rs.getString("Title");
				String writerId = rs.getString("writer_id");
				Date regDate = rs.getDate("regDate");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				String pub = rs.getString("pub");
	
				notice = new Notice(id, title, writerId, regDate, hit, files, content, pub);
			}

			rs.close();
			pst.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return notice;
	}

	public Notice getNextNotice(String field, String query, String argPub, int argId) {

		Notice notice = null;

		sql = "select /*+ index_desc(notice, notice_pk) */ id, title, writer_id, regdate, hit, content, files, pub"
			+ " from notice"
			+ " where id < ?"
			+ " and rownum <= 1"
			+ " and pub like ?"
			+ " and " + field + " like '%'||?||'%'";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, argId);
			pst.setString(2, argPub);
			pst.setString(3, query);
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()) {

				int id = rs.getInt("id");
				String title = rs.getString("Title");
				String writerId = rs.getString("writer_id");
				Date regDate = rs.getDate("regDate");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				String pub = rs.getString("pub");

				notice = new Notice(id, title, writerId, regDate, hit, files, content, pub);

			}

			rs.close();
			pst.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return notice;
	}
	
	public Notice getPrevNotice(String field, String query, String argPub, int argId) {

		Notice notice = null;

		sql = "select /*+ index_asc(notice, notice_pk) */ id, title, writer_id, regdate, hit, content, files, pub"
			+ " from notice"
			+ " where id > ?"
			+ " and rownum <= 1"
			+ " and pub like ?"
			+ " and " + field + " like '%'||?||'%'";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, argId);
			pst.setString(2, argPub);
			pst.setString(3, query);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("Title");
				String writerId = rs.getString("writer_id");
				Date regDate = rs.getDate("regDate");
				int hit = rs.getInt("hit");
				String files = rs.getString("files");
				String content = rs.getString("content");
				String pub = rs.getString("pub");

				notice = new Notice(id, title, writerId, regDate, hit, files, content, pub);

			}

			rs.close();
			pst.close();
			con.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return notice;
	}	

	public int deleteNoticeAll(String[] ids) {
		
		int result = 0;
		
		String params = "";
			
		for(int i=0 ; i < ids.length ; i++) {
			params += ids[i];
			if(i < ids.length - 1)
				params += ",";		
		}
	

		sql = "delete from notice where id in (" + params + ")";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			Statement st = con.createStatement();
			result = st.executeUpdate(sql);

			st.close();
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
	
	//id가 int 배열로넘어 온 것을 list형식으로 변환하여 호출
	public int pubNoticeAll(int[] ids, int[] openIds) {
		List<String> idsList = new ArrayList<>();
		for(int i=0 ; i < ids.length ; i++) 
			idsList.add(String.valueOf(ids[i]));
			
		List<String> openIdsList = new ArrayList<>();
		for(int i=0 ; i < openIds.length ; i++) 
			openIdsList.add(String.valueOf(openIds[i]));
		
		return pubNoticeAll(idsList, openIdsList);
	}
	
	//id가 list로 넘어온것을  csv형식으로 변환하여 호출
	public int pubNoticeAll(List<String> ids, List<String> openIds) {
		
		String idsCVS = String.join(",", ids);
		String openIdsCVS = String.join(",", openIds);
		
		return pubNoticeAll(idsCVS, openIdsCVS);
	}
	
	//id가 String 배열로넘어 온 것을  csv형식으로 변환하여 호출
	public int pubNoticeAll(String[] ids, String[] openIds) {
				
		String idsParams = "";
		String openParams = "";
		
		for(int i=0 ; i < ids.length ; i++) {
			idsParams += ids[i];
			if(i < ids.length - 1)
				idsParams += ",";		
		}
		for(int i=0 ; i < openIds.length ; i++) {
			openParams += openIds[i];
			if(i < openIds.length - 1)
				openParams += ",";		
		}
		
		return pubNoticeAll(idsParams, openParams);
	}

	//id가 csv형식으로 넘어온다.
	public int pubNoticeAll(String idsParams, String openParams) {
		int result = 0;

		sql = "update notice"
			+ " set pub = case when id in (" + openParams + ") then '1' else '0' end"
			+ " where id in (" + idsParams + ")";
		System.out.println(sql);
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			Statement st = con.createStatement();
			result = st.executeUpdate(sql);
			
			st.close();
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

	public int insertNotice(Notice notice) {
		
		int result = 0;
		
		sql = "insert into notice (id, title, content, writer_id, pub, regdate, hit, files)"
				+ "values( SEQ_NOTICE_ID.nextval, ?, ?, ?, ?, sysdate, 0, ?)";

		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, passwd);
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, notice.getTitle());
			pst.setString(2, notice.getContent());
			pst.setString(3, notice.getWriterid());
			pst.setString(4, notice.getPub());
			pst.setString(5, notice.getFiles());
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


}
