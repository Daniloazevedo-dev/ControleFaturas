package view;


import java.awt.Font;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import control.DaoBloqueio;
import model.Bloqueio;

public class TelaBloqueio {

	public JFrame frame;

	
	public TelaBloqueio() {
		initialize();
	}
	
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 523, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Retira o Botão Maximizar
			frame.setResizable(false);
		//Abre frame no meio da Tela
			frame.setLocationRelativeTo(null);
		
		JLabel lblBemVindo = new JLabel("Bem vindo!");
		lblBemVindo.setFont(new Font("Arial", Font.BOLD, 30));
				
				JLabel bloqueioLabel = new JLabel("");
				bloqueioLabel.setIcon(new ImageIcon(TelaBloqueio.class.getResource("/img/bloqueio.png")));
				
				JLabel lblBloqueado = new JLabel("Por favor, contate o administrador do sistema.");
				lblBloqueado.setFont(new Font("Arial", Font.PLAIN, 20));
				GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
				groupLayout.setHorizontalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap(51, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(bloqueioLabel, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
									.addGap(107))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(lblBloqueado, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE)
									.addGap(39))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(177)
							.addComponent(lblBemVindo)
							.addContainerGap(181, Short.MAX_VALUE))
				);
				groupLayout.setVerticalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(57)
							.addComponent(lblBemVindo)
							.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
							.addComponent(lblBloqueado, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bloqueioLabel, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
							.addGap(113))
				);
				frame.getContentPane().setLayout(groupLayout);
				
	}
	
	/*//Verifica se o sistema esta bloqueado
	public boolean verificarBloqueio() throws SQLException{
		
		
		
			DaoBloqueio daoBloqueio = new DaoBloqueio();
			
			Bloqueio bloqueio = daoBloqueio.consultar("1");
			
			if(bloqueio.isBloqueado() == true){
				return true;
			}
			
			//System.out.println("Cliente: " + bloqueio.getNomeCliente()+ " Status: " + bloqueio.isBloqueado() );
			//bloqueio.getNomeCliente();
		
		return false;
		
		
	}*/
}
