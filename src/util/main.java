package util;

import java.sql.SQLException;

import view.TelaPrincipal;

public class main {

	public static void main(String[] args) throws SQLException{
		
		TelaPrincipal window = new TelaPrincipal();
		//TelaBloqueio telaBloqueio = new TelaBloqueio();
		window.frame.setVisible(true);
		
		/*if(telaBloqueio.verificarBloqueio() == true){
			telaBloqueio.frame.setVisible(true);
		} else{
		
			window.frame.setVisible(true);
		
		}*/
		
	}
	

}
