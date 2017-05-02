package tasks;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

import sql.SQLExecute;
import sql.SQLfileReader;
import sql.exceptions.NoQueryFoundException;

public class task1 {
	public static void main(String[] args) {
		String[] queries;
		try {
			queries = SQLfileReader.getQueries("src/tasks/task1sql.sql");
			ResultSet result = SQLExecute.executeQuery(queries[0]);
			while (result.next()) {
				System.out.println(result.getString(1));
			}
		} catch (FileNotFoundException | NoQueryFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
}
