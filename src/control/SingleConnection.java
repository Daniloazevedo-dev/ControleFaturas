package control;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

/**
 * Responsável por fazer conexão com Banco de Dados
 * @author Danilo
 *
 */

public class SingleConnection {

	private static String banco = "jdbc:postgresql://localhost:5432/faturas?autoReconnect=true";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection connection = null;
	
	static {
		conectar();
	}

	public SingleConnection() {
		conectar();
		
	}
	
	public static void conectar(){
		try {
			if (connection == null){
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, password);
				connection.setAutoCommit(false);
				
				System.out.println("Conectado com Sucesso!!");

			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Erro ao Conectar com o Banco de  Dados!");
			throw new RuntimeException("Erro ao Conectar com o Banco de Dados!");
			
		}
		
	}
	
	public static Connection getConnection(){
		return connection;
	}
	

	/*public static void main(String[] args) {
			SingleConnection c = new SingleConnection();
			c.getConnection();
			
		}*/
}
