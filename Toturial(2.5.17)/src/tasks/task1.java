package tasks;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

import sql.SQLExecute;
import sql.SQLfileReader;
import sql.exceptions.InvalidParameterTypeException;
import sql.exceptions.NoQueryFoundException;

public class task1 {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Missing input String.");
			return;
		}
		String input = args[0];

		String[] queries;
		try {
			// read query
			queries = SQLfileReader.getQueries("src/tasks/task1sql.sql");
			// fetch results
			ResultSet result = SQLExecute.executeQuery(queries[0], new Object[] { input });

			// print results

			String outprint = "";
			int index = 0;
			while (result.next()) {
				outprint += result.getString(1) + "\n";
				index++;
			}
			System.out.println("Received " + index + " results!\n\n");
			System.out.println(outprint);
		} catch (FileNotFoundException | NoQueryFoundException | SQLException | InvalidParameterTypeException e) {
			e.printStackTrace();
		}

	}
}
