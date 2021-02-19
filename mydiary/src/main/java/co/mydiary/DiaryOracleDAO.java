package co.mydiary;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DiaryOracleDAO implements DAO {
	
	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;

	@Override
	public int insert(DiaryVO vo) {
		int r = 0;
		try {
			conn = JdbcUtil.connect();
			String sql = "INSERT INTO DIARY VALUES(?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getWdate());
			pstmt.setString(2, vo.getContents());
			r = pstmt.executeUpdate();
			System.out.println(r + "건이 등록되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.disconnect(conn);
		}
		return r;
	}

	@Override
	public void update(DiaryVO vo) {
		try {
			conn = JdbcUtil.connect();
			String sql = "UPDATE DIARY SET CONTENTS = ? WHERE WDATE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getContents());
			pstmt.setString(2, vo.getWdate());
			int r = pstmt.executeUpdate();
			System.out.println(r + "건이 수정되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.disconnect(conn);
		}
	}

	@Override
	public int delete(String date) {
		int r = 0;
		try {
			conn = JdbcUtil.connect();
			String sql = "DELETE FROM DIARY WHERE WDATE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			r = pstmt.executeUpdate();
			System.out.println(r + "건이 삭제되었습니다.");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.disconnect(conn);
		}
		return r;
	}

	@Override
	public DiaryVO selectDate(String date) {
		DiaryVO vo = new DiaryVO();
		try {
			conn = JdbcUtil.connect();
			String sql = "SELECT WDATE, "
								+ "CONTENTS "
								+ "FROM DIARY "
								+ "WHERE WDATE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new DiaryVO();
				vo.setWdate(rs.getString("wdate"));
				vo.setContents(rs.getString("contents"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return vo;
	}

	@Override
	public List<DiaryVO> selectContent(String content) {
		List<DiaryVO> list = new ArrayList<DiaryVO>();
		try {
			conn = JdbcUtil.connect();
			String sql = "SELECT WDATE, "
								+ "CONTENTS "
								+ "FROM DIARY "
								+ "WHERE CONTENTS LIKE '%' || ? || '%'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DiaryVO vo = new DiaryVO();
				vo.setWdate(rs.getString("wdate"));
				vo.setContents(rs.getString("contents"));
				list.add(vo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return list;
	}

	@Override
	public List<DiaryVO> selectAll() {
		List<DiaryVO> list = new ArrayList<DiaryVO>();
		try {
			conn = JdbcUtil.connect();
			String sql = "SELECT WDATE, "
								+ "CONTENTS "
								+ "FROM DIARY "
								+ "ORDER BY WDATE";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DiaryVO vo = new DiaryVO();
				vo.setWdate(rs.getString("wdate"));
				vo.setContents(rs.getString("contents"));
				list.add(vo);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.disconnect(conn);
		}
		return list;
	}

}
