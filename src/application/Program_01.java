package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import db.ConexaoDB;
import model.Seller;

public class Program_01 {
	public static void main(String[] args) {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		List<Seller> listaSeller = new ArrayList<>();
		Consumer<Object> print = System.out::println;
		try {
			conn = ConexaoDB.getConnection();
	
			st = conn.createStatement();
			
			rs = st.executeQuery("SELECT * FROM seller");
			
			while (rs.next()) {
				Seller seller = new Seller();
				seller.setId(rs.getInt("id"));
				seller.setName(rs.getString("name"));
				seller.setEmail(rs.getString("email"));
				seller.setBirthDate(LocalDateTime.parse(rs.getString("birthdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				seller.setBaseSalary(rs.getDouble("basesalary"));
				seller.setDepartmentId(rs.getInt("departmentid"));
				listaSeller.add(seller);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConexaoDB.closeResultSet(rs);
			ConexaoDB.closeStatement(st);
			ConexaoDB.closeConnection();
		}
		listaSeller.stream().forEach(print);
	}
}
