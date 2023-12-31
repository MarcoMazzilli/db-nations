package org.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
public class Main {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Cerca:");
		String toSearch = sc.nextLine();
		sc.close();
		
		final String url = "jdbc:mysql://localhost:8889/db-nations";
		final String user = "root";
		final String password = "root";
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			
			String sql = "SELECT c.country_id ,c.name,r.name ,c2.name \n"
					+ "from countries c \n"
					+ "JOIN regions r \n"
					+ "	ON c.region_id = r.region_id \n"
					+ "JOIN continents c2 \n"
					+ "	ON c2.continent_id = r.continent_id\n"
					+ "WHERE c.name LIKE ? "
					+ "ORDER BY c.name \n";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, '%'+ toSearch +'%');
			ResultSet result = ps.executeQuery();
			
			while(result.next()) {
				
				int country_id = result.getInt("country_id");
				String country = result.getString("c.name");
				String region = result.getString("r.name");
				String continent = result.getString("c2.name");
				
				System.out.println("country_id : " + country_id + "\n"
							    +"country : " + country + "\n"
								+"region : " + region + "\n"
								+"continent : " + continent + "\n"
						);
				
				System.out.println("\n------------------------------\n");
			}
		} catch (Exception e) {
			
			System.out.println("Errore di connessione: " + e.getMessage());
		}
		
	}

}
