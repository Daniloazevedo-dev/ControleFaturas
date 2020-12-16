package view;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.DaoFaturas;
import model.CadastroFaturas;
import model.MonetarioDocument;

public class TelaPrincipal {

	public JFrame frame;
	private JTable table;
	private double valorTotal = 0;

	
	public TelaPrincipal() {
		initialize();
	}
	
	
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 523, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Retira o Botão Maximizar
			frame.setResizable(false);
		//Abre frame no meio da Tela
			frame.setLocationRelativeTo(null);
		
		JLabel lblFaturasDiarias = new JLabel("Faturas Di\u00E1rias");
		lblFaturasDiarias.setFont(new Font("Arial", Font.BOLD, 30));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		
		JButton btnTodasAsFaturas = new JButton("Todas as Faturas");
		btnTodasAsFaturas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/img/consultar.png")));
		btnTodasAsFaturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				TodasFaturas window;
				try {
					window = new TodasFaturas();
					window.frame.setVisible(true);
					frame.dispose();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JButton btnSair = new JButton("Sair");
		btnSair.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/img/sair.png")));
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JLabel lblTotal = new JLabel("TOTAL:");
		lblTotal.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(lblTotal);
		
		JLabel lblR = new JLabel("R$");
		lblR.setForeground(Color.RED);
		lblR.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(lblR);
		
		JLabel total = new JLabel("0,00");
		total.setForeground(Color.RED);
		total.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(total);
		
		table = new JTable();
	
		table.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			
			new String[] { "ID", "TITULO", "VALOR", "VENCIMENTO", "NÚMERO"}){
			public boolean isCellEditable(int linha, int coluna) {  
                return false;
            } 
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		
		scrollPane.setViewportView(table);
		
		// Dados carregados na tabela
				carregaDados(table);
				String valorTotalString = String.format("%.2f", valorTotal); // Acrescenta um casa decimal
				String valorSemVirgula = valorTotalString.replace(",", "");
				//System.out.println(teste(valorSemVirgula));
				total.setText(valorMonetario(valorSemVirgula));
				GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
				groupLayout.setHorizontalGroup(
					groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(139)
									.addComponent(lblFaturasDiarias))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(panel, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(109)
									.addComponent(btnTodasAsFaturas, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(10, Short.MAX_VALUE))
				);
				groupLayout.setVerticalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(36)
							.addComponent(lblFaturasDiarias)
							.addGap(37)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(27)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnTodasAsFaturas)
								.addComponent(btnSair))
							.addContainerGap(77, Short.MAX_VALUE))
				);
				frame.getContentPane().setLayout(groupLayout);
				
	}

	
	
	 //Carrega Dados do Banco na Tabela
		public void carregaDados(JTable table) {
			DaoFaturas dao = new DaoFaturas();
			
			SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");
			Date dataHjBrasil = new Date(System.currentTimeMillis()); //Pega data Do PC
			String dataHjBrasilFormatoBrasileiro = formatoBrasileiro.format(dataHjBrasil);

			try {
				List<CadastroFaturas> list = dao.listar();
				
				for (CadastroFaturas cadastrofaturas : list) {

					String id = String.valueOf(cadastrofaturas.getId());
					String titulo = cadastrofaturas.getTitulo();
					String valor = cadastrofaturas.getValor();
					String numero = cadastrofaturas.getNumero();
					
					String vencimento = cadastrofaturas.getVencimento();
					DefaultTableModel val = (DefaultTableModel) table.getModel();
					
					if (dataHjBrasilFormatoBrasileiro.equals(vencimento)){
						valorTotal = valorTotal + totalValores(valor);
						
						val.addRow(new String[] { id, titulo, valor, vencimento,numero });
					}
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}
		
		public double totalValores(String valor){
			
			String valorSemVirgula = valor.replace(".", "").replace(",", ".");
			double valorFatura = Double.parseDouble(valorSemVirgula);
			return valorFatura;
		}
		
		
		
		public String valorMonetario(String teste){
			JFormattedTextField testeM = new JFormattedTextField();
			testeM.setDocument(new MonetarioDocument());
			testeM.setText(teste);
			return testeM.getText();
		}
		//Limpa Tabela
		public void limpaTabela(JTable table){
			while(table.getModel().getRowCount() > 0){
				((DefaultTableModel) table.getModel()).removeRow(0);
			}
		}
		
		
		
		
}
