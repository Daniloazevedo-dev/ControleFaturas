package view;


import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


import control.DaoFaturas;
import model.CadastroFaturas;
import model.MonetarioDocument;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class TodasFaturas {

	public JFrame frame;
	private JTable table;
	JLabel total = new JLabel("0");
	private JTextField textPesquisa;
	

	public TodasFaturas() throws ParseException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	@SuppressWarnings("serial")
	private void initialize() throws ParseException {
		frame = new JFrame();
		frame.setBounds(100, 100, 523, 643);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Retira o Botão Maximizar
		frame.setResizable(false);

		// Abre frame no meio da Tela
		frame.setLocationRelativeTo(null);

		JLabel lblFaturasDiarias = new JLabel("Todas Faturas");
		lblFaturasDiarias.setFont(new Font("Arial", Font.BOLD, 30));

		JScrollPane scrollPane = new JScrollPane();

		JPanel panel = new JPanel();
		

		JButton btnTodasAsFaturas = new JButton("Nova Fatura");
		btnTodasAsFaturas.setIcon(new ImageIcon(TodasFaturas.class.getResource("/img/FormatFactoryimages.png")));
		btnTodasAsFaturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Cadastrar dialog = new Cadastrar();
					dialog.valorTotalRetorno(total);
					dialog.retornaTabelaCadastro(table);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					//Não redimensiona
					dialog.setResizable(false);
					// Abre Jdialog no meio da Tela
					dialog.setLocationRelativeTo(null);
					// Desativa janela Principal
					dialog.setModal(true);
					dialog.setVisible(true);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setIcon(new ImageIcon(TodasFaturas.class.getResource("/img/voltar.png")));
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaPrincipal window = new TelaPrincipal();
				window.frame.setVisible(true);
				frame.dispose();
			}
		});

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setIcon(new ImageIcon(TodasFaturas.class.getResource("/img/atualizar.png")));
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Atualizar dialog;

				try {

					DaoFaturas dao = new DaoFaturas();

					int linha = table.getSelectedRow();

					// Verifica se a linha da Jtable foi selecionada
					if (linha != -1) {

						String idTabela = table.getValueAt(table.getSelectedRow(), 0).toString();

						CadastroFaturas cadastroFaturas = dao.consultar(idTabela);

						String id = Long.toString(cadastroFaturas.getId());
						String titulo = cadastroFaturas.getTitulo();
						String valor = cadastroFaturas.getValor();
						String vencimento = cadastroFaturas.getVencimento();
						String numero = cadastroFaturas.getNumero();

						dialog = new Atualizar();
						dialog.valorTotalRetorno(total);
						//Não redimensiona
						dialog.setResizable(false);
						dialog.consulta(id, titulo, valor, vencimento,numero);//Passa Dados para as caixas de Edição
						dialog.retornaTabela(table); //Retorna Table na pagina para atualizar
						// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CSLOSE);
						// Abre Jdialog no meio da Tela
						dialog.setLocationRelativeTo(null);
						// Desativa janela Principal
						dialog.setModal(true);
						dialog.setVisible(true);
						
						
					} else {
						JOptionPane.showMessageDialog(null, "Por favor, Selecione a linha que deseja Atualziar!");
					}

				} catch (ParseException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});

		JButton btnApagar = new JButton("Apagar");
		btnApagar.setIcon(new ImageIcon(TodasFaturas.class.getResource("/img/apagar.png")));
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				DaoFaturas dao = new DaoFaturas();

				int linha = table.getSelectedRow();// Pega linha selecionada da
													// JTable

				if (linha != -1) {

					// Mostra Confirmação de exclusão na tela e o email para o
					// usuário
					String titulo = table.getValueAt(table.getSelectedRow(), 1).toString();

					String[] options = { "Sim", "Não" };
					int resposta = JOptionPane.showOptionDialog(null,
							"Tem certeza que deseja apagar o\n Titulo \" " + titulo + " \"?", "Confirmação",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

					if (resposta == 0) {
						
						// Lê dados da linha selecionada
						String id = table.getValueAt(table.getSelectedRow(), 0).toString();
						dao.delete(id);
						
						limpaTabela(table);
						carregaDados(table);
						
						try {
							String valorTotalString;
							valorTotalString = String.format("%.2f", somaValoresBanco());
							String valorTotalVirgula = valorTotalString.replace(",", "");
							total.setText(valorMonetario(valorTotalVirgula));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						JOptionPane.showMessageDialog(null,"Apagado com sucesso!!");
						
						
						
						
				
						
					} else if (resposta == 1) {
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, Selecione a linha em que deseja Apagar!");

				}

			}

		});

		JLabel lblTotal = new JLabel("TOTAL:");
		lblTotal.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(lblTotal);

		JLabel lblR = new JLabel("R$");
		lblR.setForeground(Color.RED);
		lblR.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(lblR);

		
		total.setForeground(Color.RED);
		total.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(total);

		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
					
				},
				
				new String[] { "ID", "TITULO", "VALOR", "VENCIMENTO", "NÚMERO"}){
				public boolean isCellEditable(int linha, int coluna) {  // Desativa Edição da Jtable
	                return false;
	            } 
			});

		table.getColumnModel().getColumn(0).setPreferredWidth(1);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Seleciona
																		// Apenas
																		// uma
																		// Linha
																		// da
																		// Jtable

		scrollPane.setViewportView(table);

		// Dados carregados na tabela
		carregaDados(table);
		
		String valorTotalString = String.format("%.2f", somaValoresBanco()); // Acrescenta um casa decimal
		String valorTotalVirgula = valorTotalString.replace(",", "");
		total.setText(valorMonetario(valorTotalVirgula));
		
		textPesquisa = new JTextField();
		textPesquisa.setColumns(10);
		
		JRadioButton radioTodas = new JRadioButton("Todas");
		
		JRadioButton radioNome = new JRadioButton("Nome");
		
		JRadioButton radioNumero = new JRadioButton("N\u00FAmero");
		
		ButtonGroup grupo1 = new ButtonGroup();
		grupo1.add(radioTodas);
		grupo1.add(radioNome);
		grupo1.add(radioNumero);
		radioTodas.setSelected(true);
		
		
		JLabel lblP = new JLabel("");
		lblP.setIcon(new ImageIcon(TodasFaturas.class.getResource("/img/pesquisa2.png")));
		
		lblP.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DaoFaturas d = new DaoFaturas();
				
				try {
					
					String pesquisa = textPesquisa.getText();
				if(radioTodas.isSelected()){
					textPesquisa.setText("");
					limpaTabela(table);
					carregaDados(table);
				}else if(radioNome.isSelected()){
					SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");
					Date dataHjBrasil = new Date(System.currentTimeMillis()); //Pega data Do PC
					String dataHjBrasilFormatoBrasileiro = formatoBrasileiro.format(dataHjBrasil);
						//radioNumero.setSelected(false);
						List<CadastroFaturas> list = d.listar(pesquisa,"titulo");
						limpaTabela(table);
						for (CadastroFaturas cadastroFaturas : list) {

							String id = String.valueOf(cadastroFaturas.getId());
							String titulo = cadastroFaturas.getTitulo();
							String valor = cadastroFaturas.getValor();
							String vencimento = cadastroFaturas.getVencimento();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
							java.util.Date minhaData = format.parse(vencimento);
							String numero = cadastroFaturas.getNumero();
							DefaultTableModel val = (DefaultTableModel) table.getModel();
							
							if(dataHjBrasilFormatoBrasileiro.equals(vencimento)){
								val.addRow(new String[] { id, titulo, valor, vencimento,numero });
							}else if(minhaData.after(dataHjBrasil)){
									val.addRow(new String[] { id, titulo, valor, vencimento,numero });
								
							}
						}
					} else if (radioNumero.isSelected()){
						
						SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");
						Date dataHjBrasil = new Date(System.currentTimeMillis()); //Pega data Do PC
						String dataHjBrasilFormatoBrasileiro = formatoBrasileiro.format(dataHjBrasil);
						
						List<CadastroFaturas> list = d.listar(pesquisa,"numero");
						limpaTabela(table);
						for (CadastroFaturas cadastroFaturas : list) {

							String id = String.valueOf(cadastroFaturas.getId());
							String titulo = cadastroFaturas.getTitulo();
							String valor = cadastroFaturas.getValor();
							String vencimento = cadastroFaturas.getVencimento();
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
							java.util.Date minhaData = format.parse(vencimento);
							
							String numero = cadastroFaturas.getNumero();
							DefaultTableModel val = (DefaultTableModel) table.getModel();
						
							if(dataHjBrasilFormatoBrasileiro.equals(vencimento)){
								val.addRow(new String[] { id, titulo, valor, vencimento,numero });
							}else if(minhaData.after(dataHjBrasil)){
									val.addRow(new String[] { id, titulo, valor, vencimento,numero });
								
							}
							
						}
					}
					
							
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblP.setIcon(new ImageIcon(TodasFaturas.class.getResource("/img/pesquisa2.png")));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				lblP.setIcon(new ImageIcon(TodasFaturas.class.getResource("/img/pesquisa3.png")));
			}
		});
		
		JLabel lblPesquisar = new JLabel("Pesquisar:");
		lblPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lblFaturasDiarias)
							.addGap(155))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(btnVoltar, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnTodasAsFaturas, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnApagar, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addGap(18))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(10, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPesquisar)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPesquisa, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblP, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(radioTodas, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(radioNome)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(radioNumero, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(44, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addComponent(lblFaturasDiarias)
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblPesquisar)
							.addComponent(textPesquisa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(radioTodas)
							.addComponent(radioNome)
							.addComponent(radioNumero))
						.addComponent(lblP, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(25)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTodasAsFaturas)
						.addComponent(btnVoltar)
						.addComponent(btnAtualizar)
						.addComponent(btnApagar))
					.addGap(74))
		);
		frame.getContentPane().setLayout(groupLayout);

	}
	 //Carrega Dados do Banco na Tabela
	public void carregaDados(JTable table) {
		DaoFaturas dao = new DaoFaturas();

		try {
			
			SimpleDateFormat formatoBrasileiro = new SimpleDateFormat("dd/MM/yyyy");
			Date dataHjBrasil = new Date(System.currentTimeMillis()); //Pega data Do PC
			String dataHjBrasilFormatoBrasileiro = formatoBrasileiro.format(dataHjBrasil);
			
			List<CadastroFaturas> list = dao.listar();

			for (CadastroFaturas cadastrofaturas : list) {
				
					String id = String.valueOf(cadastrofaturas.getId());
					String titulo = cadastrofaturas.getTitulo();
					String valor = cadastrofaturas.getValor();
					String vencimento = cadastrofaturas.getVencimento();
					
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					java.util.Date minhaData = format.parse(vencimento);
					String numero = cadastrofaturas.getNumero();
					DefaultTableModel val = (DefaultTableModel) table.getModel();
					
					if(dataHjBrasilFormatoBrasileiro.equals(vencimento)){
						val.addRow(new String[] { id, titulo, valor, vencimento,numero });
					}else if(minhaData.after(dataHjBrasil)){
							val.addRow(new String[] { id, titulo, valor, vencimento,numero });
						
					}
					
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	
	//Limpa Tabela
	public void limpaTabela(JTable table){
		while(table.getModel().getRowCount() > 0){
			((DefaultTableModel) table.getModel()).removeRow(0);
		}
	}
	
	
	public double somaValoresBanco() throws ParseException{
		DaoFaturas dao = new DaoFaturas();
		 double somaValores = 0;
		List<String> list =  dao.valoresBanco();

			for (String valores : list) {
				String valorFormatado = valores.replace(".", "").replace(",", ".");
				double valorRetirarDouble = Double.parseDouble(valorFormatado);
				somaValores = somaValores + valorRetirarDouble;
				
			}
			return somaValores;
	}
	
	public String valorMonetario(String valorMonetario){
		JFormattedTextField v = new JFormattedTextField();
		v.setDocument(new MonetarioDocument());
		v.setText(valorMonetario);
		return v.getText();
	}
}
