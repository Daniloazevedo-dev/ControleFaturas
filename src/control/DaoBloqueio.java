package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Bloqueio;

public class DaoBloqueio {
	
	private Connection connection;
	
	public DaoBloqueio(){
		connection = SingleConnection.getConnection();
	}
	
public void salvar(Bloqueio bloqueio) {
		
		try{
			String sql = "insert into bloqueio(nomecliente, bloqueado) values (?,?)";
			
			
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, bloqueio.getNomeCliente());
			insert.setBoolean(2, bloqueio.isBloqueado());
			insert.execute();
			connection.commit();
			JOptionPane.showMessageDialog(null,"Salvo com Sucesso!!");
		}catch(Exception e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Erro ao Salvar Dados!");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	
	}

public Bloqueio consultar(String id) throws SQLException {
	
	String sql = "select * from bloqueio where id = " + id  ;
	PreparedStatement statement = connection.prepareStatement(sql);
	ResultSet resultSet = statement.executeQuery();
	
	if (resultSet.next()){
		Bloqueio bloqueio = new Bloqueio();
		
		bloqueio.setId(resultSet.getLong("id"));
		bloqueio.setNomeCliente(resultSet.getString("nomecliente"));
		bloqueio.setBloqueado(resultSet.getBoolean("bloqueado"));
		
		
		return bloqueio;
		
		
	}
		
	return null;
}



//public static void main(String[] args) throws SQLException {
	
	//CadastroFaturas c = new CadastroFaturas();
	//DaoFaturas d = new DaoFaturas();
	
/*	SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");
	 Date data1 = new Date(System.currentTimeMillis()); //Pega data Do PC
	 String teste = formatoBrasileiro.format(data1);//trasforma a data do em formato de data e String
	 
	 String data2 = "14/07/2019";*/
	
	
	
	
	//c.setTitulo("curso");
	//c.setValor(280);
	//c.setVencimento(formatoBrasileiro.format(dataAtual));
	
	
	
	//System.out.println(teste);
	 
	
    
	/*if(teste.equals(data2)){
		System.out.println("Data igual");
	}else{
		System.out.println("Data Diferente");
	}
	
	
	d.salvar(c);*/
	
	/*DaoFaturas dao = new DaoFaturas();
	List<CadastroFaturas> list =  d.listar("a");
	for (CadastroFaturas cadastroFaturas : list) {
		System.out.println(cadastroFaturas.getTitulo());
		System.out.println("---------------------------------");
	}
			
		System.out.println(list);
	
}*/
	
	//d.delete("4");
	
	
	/*CadastroFaturas c = d.consultar("3");
	
	
	System.out.println(c.getId());
	System.out.println(c.getTitulo());
	System.out.println(c.getValor());
	System.out.println(c.getVencimento());
	System.out.println("---------------------------------");
	c.setValor(100);
	d.atualizar(c);*/
	

}


