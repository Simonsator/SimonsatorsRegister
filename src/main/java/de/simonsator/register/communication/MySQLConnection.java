package de.simonsator.register.communication;

import de.simonsator.partyandfriends.communication.sql.MySQLData;
import de.simonsator.partyandfriends.communication.sql.SQLCommunication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * @author Simonsator
 * @version 1.0.0 12.04.17
 */
public class MySQLConnection extends SQLCommunication {
	private final String TABLE_PREFIX;

	public MySQLConnection(MySQLData pMySQLData) {
		super(pMySQLData);
		TABLE_PREFIX = pMySQLData.TABLE_PREFIX;
		importDatabase();
	}

	private void importDatabase() {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `" + TABLE_PREFIX + "register_friend_manger` (`player_id` INT(8) NOT NULL, " + "`password` CHAR(40) NOT NULL);");
			prepStmt.executeUpdate();
			prepStmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(prepStmt);
		}
	}

	public boolean isAlreadyRegistered(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_id FROM " + TABLE_PREFIX + "register_friend_manger WHERE player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}

		return false;
	}

	public void register(int pPlayerID, String pPassword) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement(
					"INSERT INTO " + TABLE_PREFIX + "register_friend_manger VALUES (?, ?);");
			prepStmt.setInt(1, pPlayerID);
			prepStmt.setString(2, encryptPassword(pPassword));
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(prepStmt);
		}
	}

	public void changePassword(int pPlayerID, String pPassword) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement(
					"UPDATE " + TABLE_PREFIX + "register_friend_manger SET password=? WHERE player_id=?;");
			prepStmt.setString(1, encryptPassword(pPassword));
			prepStmt.setInt(2, pPlayerID);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(prepStmt);
		}
	}

	private String encryptPassword(String pPassword) {
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(pPassword.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++)
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
