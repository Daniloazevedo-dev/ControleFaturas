package control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.CadastroFaturas;

public class DaoFaturas {
	
	private Connection connection;
	
	public DaoFaturas(){
		connection = SingleConnection.getConnection();
	}
	
public void salvar(CadastroFaturas cadastroFaturas) {
		
		try{
			String sql = "insert into fatura(titulo,valor,vencimento,numero) values (?,?,?,?)";
			
			
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, cadastroFaturas.getTitulo());
			insert.setString(2, cadastroFaturas.getValor());
			insert.setString(3,cadastroFaturas.getVencimento());
			insert.setString(4,cadastroFaturas.getNumero());
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

public List<CadastroFaturas> listar (String descricaoconsulta,String tipo) throws SQLException{
	
	List<CadastroFaturas> listar = new ArrayList<CadastroFaturas>();
	String sql = "select * from fatura where " + tipo + " like'%"+descricaoconsulta+"%'";
	PreparedStatement statement = connection.prepareStatement(sql);
	ResultSet resultset = statement.executeQuery();

	while(resultset.next()){
		CadastroFaturas cadastroFaturas = new CadastroFaturas();
		cadastroFaturas.setId(resultset.getLong("id"));
		cadastroFaturas.setTitulo(resultset.getString("titulo"));
		cadastroFaturas.setValor(resultset.getString("valor"));
		cadastroFaturas.setVencimento(resultset.getString("vencimento"));
		cadastroFaturas.setNumero(resultset.getString("numero"));
		
		listar.add(cadastroFaturas);
	}
	
	
	return listar;
	
}
public List<CadastroFaturas> listar() throws SQLException{
	
	List<CadastroFaturas> listar = new ArrayList<CadastroFaturas>();
	
	String sql = "select * from fatura ";
	PreparedStatement statement = connection.prepareStatement(sql);
	ResultSet resultset = statement.executeQuery();

	while(resultset.next()){
		CadastroFaturas cadastroFaturas = new CadastroFaturas();
		cadastroFaturas.setId(resultset.getLong("id"));
		cadastroFaturas.setTitulo(resultset.getString("titulo"));
		cadastroFaturas.setValor(resultset.getString("valor"));
		cadastroFaturas.setVencimento(resultset.getString("vencimento"));
		cadastroFaturas.setNumero(resultset.getString("numero"));
		
		listar.add(cadastroFaturas);
	}
	
	
	return listar;
	
}

public void delete(String id){
	
	try {
		String sql = "delete from fatura where id = " + id;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.execute();
		connection.commit();
		
	} catch (SQLException e) {
		try {
			connection.rollback();
			JOptionPane.showMessageDialog(null,"Erro ao Apagar");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();
	}
	
}
public CadastroFaturas consultar(String id) throws SQLException {
	
	String sql = "select * from fatura where id = " + id  ;
	PreparedStatement statement = connection.prepareStatement(sql);
	ResultSet resultSet = statement.executeQuery();
	
	if (resultSet.next()){
		CadastroFaturas cadastroFaturas = new CadastroFaturas();
		
		cadastroFaturas.setId(resultSet.getLong("id"));
		cadastroFaturas.setTitulo(resultSet.getString("titulo"));
		cadastroFaturas.setValor(resultSet.getString("valor"));
		cadastroFaturas.setVencimento(resultSet.getString("vencimento"));
		cadastroFaturas.setNumero(resultSet.getString("numero"));
		
		return cadastroFaturas;
		
		
	}
		
	return null;
}

public void atualizar(CadastroFaturas cadastroFaturas) {
	String sql = "update fatura set titulo = ?, valor = ?, vencimento = ?,numero = ? where id = "
			+ cadastroFaturas.getId();

	PreparedStatement stantement;
	try {
		stantement = connection.prepareStatement(sql);

		stantement.setString(1, cadastroFaturas.getTitulo());
		stantement.setString(2, cadastroFaturas.getValor());
		stantement.setString(3, cadastroFaturas.getVencimento());
		stantement.setString(4, cadastroFaturas.getNumero());

		stantement.execute();

		connection.commit();
		
		JOptionPane.showMessageDialog(null,"Atualizado com Sucesso!!");
		
		

	} catch (SQLException e) {
		try {
			JOptionPane.showMessageDialog(null,"Erro ao Atualizar");
			connection.rollback();
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		e.printStackTrace();
	}
	
}

public List<String> valoresBanco() throws ParseException{
	
	List<String> valoresBanco= new ArrayList<String>();
	
	try {
		
		String sql = "SELECT * FROM fatura";
		PreparedStatement statement;
		statement = connection.prepareStatement(sql);
		ResultSet resultset = statement.executeQuery();
		
		while(resultset.next()){
			
			SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");
			Date dataHjBrasil = new Date(System.currentTimeMillis()); //Pega data Do PC
			String dataHjBrasilFormatoBrasileiro = formatoBrasileiro.format(dataHjBrasil);
			
			String vencimento = resultset.getString("vencimento");
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date minhaData = format.parse(vencimento);
			
			String valor = resultset.getString("valor");
			
			if(dataHjBrasilFormatoBrasileiro.equals(vencimento)){
				valoresBanco.add(valor);
			}else if(minhaData.after(dataHjBrasil)){
				valoresBanco.add(valor);
		}
			
		}
		
		
		return valoresBanco;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return valoresBanco;
	

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

