package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import db.ConexaoDB;
import model.Department;
import model.Seller;

public class Program_01 {
	
	
	private static final String SQL_CONSULTA_FULL_JOIN = 
			"SELECT s.id, s.name, s.email, s.birthdate, s.basesalary, s.departmentid, d.id, d.name FROM seller s INNER JOIN department d on s.departmentid = d.id ";
	
	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		List<Seller> listaSeller = new ArrayList<>();
		List<Department> listaDepartment = new ArrayList<>();
		
		Consumer<Object> print = System.out::println;
		try {
			conn = ConexaoDB.getConnection();
	
			st = conn.createStatement();
			
			rs = st.executeQuery(SQL_CONSULTA_FULL_JOIN);
			
			while (rs.next()) {
				Seller seller = new Seller();
				Department department = new Department();
				seller.setId(rs.getInt("id"));
				seller.setName(rs.getString("name"));
				seller.setEmail(rs.getString("email"));
				seller.setBirthDate(LocalDateTime.parse(rs.getString("birthdate"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				seller.setBaseSalary(rs.getDouble("basesalary"));
				seller.setDepartmentId(rs.getInt("departmentid"));
				department.setId(rs.getInt("d.id"));
				department.setName(rs.getString("d.name"));
				listaSeller.add(seller);
				listaDepartment.add(department);
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
		listaDepartment.stream().forEach(print);
	}
}
