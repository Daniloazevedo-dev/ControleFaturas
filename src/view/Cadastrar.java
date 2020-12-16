package view;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import com.toedter.calendar.JDateChooser;

import control.DaoFaturas;
import model.CadastroFaturas;
import model.MonetarioDocument;
import model.SoNumeros;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
//import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import java.util.Date;
import java.awt.Color;

public class Cadastrar extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final JPanel contentPanel = new JPanel();
	
	private JTextField textTitulo = null;
	private JTextField textField;
	private JTable table;
	private JLabel mostraValor;
	private JTextField textNumero = new JTextField();;
	
	
	

	
			//cadastro dialog = new cadastro();
			//dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			//dialog.setVisible(true);
		
	public Cadastrar() throws ParseException {
		textNumero.setDocument(new SoNumeros());//Permite Digitar só no números no textField
		setBounds(100, 100, 433, 269);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(Cadastrar.class.getResource("/img/sair.png")));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//fechar JDialog quando clicar no Botão cancelar
				Cadastrar.this.dispose();
				
				
			}
		});
		
		JLabel label = new JLabel("Cadastro Faturas");
		label.setFont(new Font("Arial", Font.BOLD, 25));
		
		JLabel label_4 = new JLabel("Id:");
		label_4.setFont(new Font("Arial", Font.PLAIN, 17));
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		
		
		JLabel label_1 = new JLabel("Titulo:");
		label_1.setFont(new Font("Arial", Font.PLAIN, 17));
		
		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		
		JLabel label_2 = new JLabel("Valor R$:");
		label_2.setFont(new Font("Arial", Font.PLAIN, 17));
		
		//Adiciona Mascara de dinheiro nas Caixa de Texto
				JFormattedTextField formattedTextValor = new JFormattedTextField();
				formattedTextValor .setDocument(new MonetarioDocument());
				
				JLabel label_3 = new JLabel("Vencimento:");
				label_3.setFont(new Font("Arial", Font.PLAIN, 17));
				
				JDateChooser dateVencimento = new JDateChooser();
				dateVencimento.setDate(null);
				dateVencimento.setBackground(Color.WHITE);
				 ((JTextComponent) dateVencimento.getDateEditor ()). setEditable (false);
				//Adiciona Mascara nas Caixa de Texto
				//formattedTextVencimento.setFormatterFactory(new DefaultFormatterFactory(
						//new MaskFormatter("##/##/####")));
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(Cadastrar.class.getResource("/img/salvar.png")));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				try {
					
					DaoFaturas daoDaturas = new DaoFaturas();
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); //Converte para formato Brasileiro
					Date dataHjBrasil = new Date(System.currentTimeMillis());
					
					if(textTitulo.getText() == null ||textTitulo.getText().isEmpty()){
						
						JOptionPane.showMessageDialog(null,"Insira o Titulo!");
						textTitulo.requestFocus();
						
					}  else if(formattedTextValor.getText() == null || formattedTextValor.getText().isEmpty()){
						
						JOptionPane.showMessageDialog(null,"Insira o Valor");
						formattedTextValor.requestFocus();
						
					}  else if(dateVencimento.getDate() == null){
						
						JOptionPane.showMessageDialog(null,"Insira o Vencimento!");
						dateVencimento.requestFocus();
						
					} else if(dateVencimento.getDate().before(dataHjBrasil)){
						JOptionPane.showMessageDialog(null,"Insira uma data Acima da data Atual!");
						dateVencimento.requestFocus();
					} else {
						
					String titulo = textTitulo.getText();
					String numero = textNumero.getText();
					String valor = formattedTextValor.getText();
					Date vencimento = dateVencimento.getDate();
					
					String minhaDataVencimento = format.format(vencimento); //Valor para Salvar no Banco
					
					CadastroFaturas cadastroFaturas = new CadastroFaturas();
						cadastroFaturas.setTitulo(titulo);
						cadastroFaturas.setValor(valor);
						cadastroFaturas.setVencimento(minhaDataVencimento);
						cadastroFaturas.setNumero(numero);
						
						daoDaturas.salvar(cadastroFaturas);
						
							TodasFaturas todasFaturas = new TodasFaturas();
							todasFaturas.limpaTabela(table);
							todasFaturas.carregaDados(table);
							
							String valorTotalString = String.format("%.2f", todasFaturas.somaValoresBanco());
							String valorTotalVirgula = valorTotalString.replace(",", "");
							mostraValor.setText(valorMonetario(valorTotalVirgula));
							
							
							textTitulo.setText("");
							formattedTextValor.setText("");
							dateVencimento.setDate(null);
							textNumero.setText("");
							
							textTitulo.requestFocus();
				} 
				
				}catch (ParseException e2) {
					e2.printStackTrace();
				}
				
				
				
			}
		});
		
		JLabel lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setFont(new Font("Arial", Font.PLAIN, 17));
		
		
		textNumero.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("(Opcional)");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(105)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(23)
					.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(23)
					.addComponent(label_1))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
							.addGap(5)
							.addComponent(btnCancelar))
						.addComponent(label_2)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblNumero, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(label_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(textNumero, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel))
								.addComponent(textTitulo, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(formattedTextValor, Alignment.LEADING)
									.addComponent(dateVencimento, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)))))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(label)
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_4)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(textTitulo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNumero, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(textNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_2)
						.addComponent(formattedTextValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_3)
						.addComponent(dateVencimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSalvar)
						.addComponent(btnCancelar))
					.addGap(19))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
	
	//Retorna Tabela
	public void retornaTabelaCadastro (JTable table){
		 this.table = table;
	}
	// Retorna o valor total para soma
	public void valorTotalRetorno(JLabel mostraValor){
		this.mostraValor = mostraValor;
	}
	public String valorMonetario(String valorMonetario){
		JFormattedTextField v = new JFormattedTextField();
		v.setDocument(new MonetarioDocument());
		v.setText(valorMonetario);
		return v.getText();
	}
}
