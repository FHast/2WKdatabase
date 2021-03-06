package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import sql.exceptions.InvalidParameterTypeException;

public class SQLExecute {

	public static Connection con;
	public static boolean hasData = false;

	/**
	 * Executes a given SQLite Query without parameters.
	 * 
	 * @param query
	 *            query of SQLite type.
	 * @return The query's ResultSet
	 * @throws SQLException
	 *             If the SQLite Query is incorrect.
	 */
	public static ResultSet executeQuery(String query) throws SQLException {
		if (con == null) {
			try {
				getConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

		Statement state = con.createStatement();
		ResultSet res = state.executeQuery(query);

		return res;
	}

	public static void execute(String query) throws SQLException {
		if (con == null) {
			try {
				getConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

		Statement state = con.createStatement();
		state.execute(query);
	}

	/**
	 * Executes a given SQLite Query with the given parameters.
	 * 
	 * @param query
	 *            SQLite Query
	 * @param parameters
	 *            Parameters for the '?'s in the Query, ordered
	 * @return The ResultSet for the query
	 * @throws SQLException
	 *             If Query syntax is incorrect.
	 * @throws InvalidParameterTypeException
	 */
	public static ResultSet executeQuery(String query, Object[] parameters)
			throws SQLException, InvalidParameterTypeException {
		if (con == null) {
			try {
				getConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

		PreparedStatement prep = con.prepareStatement(query);
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i] instanceof Integer) {
				prep.setInt(i + 1, (int) parameters[i]);
			} else if (parameters[i] instanceof Long) {
				prep.setLong(i + 1, (long) parameters[i]);
			} else if (parameters[i] instanceof String) {
				prep.setString(i + 1, (String) parameters[i]);
			} else if (parameters[i] == null) {
				// do nothing
			} else {
				throw new InvalidParameterTypeException(parameters[i].getClass().getName());
			}
		}
		ResultSet res = prep.executeQuery();

		return res;
	}

	public static void execute(String query, Object[] parameters) throws SQLException, InvalidParameterTypeException {
		if (con == null) {
			try {
				getConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

		PreparedStatement prep = con.prepareStatement(query);
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i] instanceof Integer) {
				prep.setInt(i + 1, (int) parameters[i]);
			} else if (parameters[i] instanceof Long) {
				prep.setLong(i + 1, (long) parameters[i]);
			} else if (parameters[i] instanceof String) {
				prep.setString(i + 1, (String) parameters[i]);
			} else if (parameters[i] instanceof Double) {
				prep.setDouble(i + 1, (double) parameters[i]);
			} else if (parameters[i] == null) {
				// do nothing
			} else {
				throw new InvalidParameterTypeException(parameters[i].getClass().getName());
			}
		}
		prep.execute();
	}

	/**
	 * Prints the ResultSet given in a readable form to standard output
	 * (console).
	 * 
	 * @param resultSet
	 *            the ResultSet which should be printed.
	 * @throws SQLException
	 */
	public static void printResultSet(ResultSet resultSet) throws SQLException {
		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (resultSet.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1)
					System.out.print(" | ");
				String columnValue = resultSet.getString(i);
				System.out.print("[" + rsmd.getColumnName(i) + "] " + columnValue);
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		try {
			getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setup for connection to the database, called if connection is not yet
	 * established.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static void getConnection() throws ClassNotFoundException, SQLException {
		String user = "di82";
		String password = "hV2oQcsM";
		String url = "jdbc:postgresql://castle.ewi.utwente.nl:5432/di82"; //?user=" + user + "&password=" + password;
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection(url, user, password);
		// SQLExecute.execute("PRAGMA foreign_keys = ON");
		// initialize();
	}
}
