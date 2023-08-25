package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoticeDBController {
	private Connection cnx = null;

	public NoticeDBController() {
		try {
			this.cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/NoticeBoardDB", "chiragagarwals",
					"chiragagarwals");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveNotice(String title, String content, String name, String phone) throws SQLException {

		String insertQuery1 = "INSERT INTO notices (title,content,name, phone) VALUES (?,?,?,?)";
		try (PreparedStatement statement = cnx.prepareStatement(insertQuery1)) {

			statement.setString(1, title);
			statement.setString(2, content);
			statement.setString(3, name);
			statement.setString(4, phone);

			statement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void clearNotice() {
		String clearNotices = "DELETE FROM notices";
		try (PreparedStatement statement = cnx.prepareStatement(clearNotices)) {

			statement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public ResultSet getNotice() {
		PreparedStatement show;
		try {
			show = cnx.prepareStatement("SELECT * FROM notices ORDER BY id DESC LIMIT 6");
			ResultSet rs = show.executeQuery();
			return rs;

		} catch (SQLException e) {

			e.printStackTrace();
			return null;
		}
	}

}