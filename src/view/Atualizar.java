package view;

import java.awt.BorderLayout;
import java.awt.Color;

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
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;

public class Atualizar extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	final JPanel contentPanel = new JPanel();
	private JTextField textTitulo;
	private JTextField textId;
	JFormattedTextField formattedTextValor = new JFormattedTextField();
	JDateChooser dateVencimento = new JDateChooser();
	private JTable table;
	private JLabel mostraValor;
	private JTextField textNumero = new JTextField();;

	
			//cadastro dialog = new cadastro();
			//dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			//dialog.setVisible(true);
		
	public Atualizar() throws ParseException {
		textNumero.setDocument(new SoNumeros());//Permite Digitar só no números no textField
		setBounds(100, 100, 442, 269);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		
		JLabel lblAtualizarFaturas = new JLabel("Atualizar Faturas");
		lblAtualizarFaturas.setFont(new Font("Arial", Font.BOLD, 25));
		
		JLabel lblId = new JLabel("Id:");
		lblId.setFont(new Font("Arial", Font.PLAIN, 17));
		
		textId = new JTextField();
		textId.setColumns(10);
		
		JLabel label_1 = new JLabel("Titulo:");
		label_1.setFont(new Font("Arial", Font.PLAIN, 17));
		
		textTitulo = new JTextField();
		textTitulo.setColumns(10);
		
		JLabel label_2 = new JLabel("Valor R$:");
		label_2.setFont(new Font("Arial", Font.PLAIN, 17));
		
		
		dateVencimento.setDate(null);
		dateVencimento.setBackground(Color.WHITE);
		 ((JTextComponent) dateVencimento.getDateEditor ()). setEditable (false);
		//Add Mascada de Dinheiro
		formattedTextValor.setDocument(new MonetarioDocument());
		
		JLabel label_3 = new JLabel("Vencimento:");
		label_3.setFont(new Font("Arial", Font.PLAIN, 17));
				
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setIcon(new ImageIcon(Atualizar.class.getResource("/img/salvar.png")));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DaoFaturas dao = new DaoFaturas();
				
				try {
					
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
						
					} else if(!dateVencimento.getDate().after(dataHjBrasil)){
						
							JOptionPane.showMessageDialog(null,"Insira uma data Acima ou igual a data Atual!");
							dateVencimento.requestFocus();
						
					} else {

					String id = textId.getText();
					String tituloAtulizado = textTitulo.getText();
					String vencimentoAtualizado = format.format(dateVencimento.getDate());
					String valorAtualizado = formattedTextValor.getText();
					
					String numeroAtualizado = textNumero.getText();
					
						CadastroFaturas cadastroFaturas = dao.consultar(id);
						cadastroFaturas.setTitulo(tituloAtulizado);
						cadastroFaturas.setValor(valorAtualizado);
						cadastroFaturas.setVencimento(vencimentoAtualizado);
						cadastroFaturas.setNumero(numeroAtualizado);
						dao.atualizar(cadastroFaturas);
						
						TodasFaturas todasFaturas = new TodasFaturas();
						todasFaturas.limpaTabela(table);
						todasFaturas.carregaDados(table);
						
						String valorTotalString = String.format("%.2f", todasFaturas.somaValoresBanco());
						String valorTotalVirgula = valorTotalString.replace(",", "");
						mostraValor.setText(valorMonetario(valorTotalVirgula));
						
						Atualizar.this.dispose();
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(Atualizar.class.getResource("/img/sair.png")));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//fechar JDialog quando clicar no Botão cancelar
				
				Atualizar.this.dispose();
				
			}
		});
		
		JLabel lblNmero = new JLabel("N\u00FAmero:");
		lblNmero.setFont(new Font("Arial", Font.PLAIN, 17));
		
		
		textNumero.setColumns(10);
		
		JLabel label = new JLabel("(Opcional)");
		label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(109)
					.addComponent(lblAtualizarFaturas, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(35)
					.addComponent(lblId, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(79)
					.addComponent(textId, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(36)
					.addComponent(label_1)
					.addGap(79)
					.addComponent(textTitulo, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(36)
					.addComponent(lblNmero, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addComponent(textNumero, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(label))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(36)
					.addComponent(label_2)
					.addGap(54)
					.addComponent(formattedTextValor, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(36)
					.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(dateVencimento, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(51)
					.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(btnCancelar))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblAtualizarFaturas)
					.addGap(5)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblId)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(2)
							.addComponent(textId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_1)
						.addComponent(textTitulo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNmero)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(2)
							.addComponent(textNumero, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(label)))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_2)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(2)
							.addComponent(formattedTextValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(label_3)
						.addComponent(dateVencimento, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnSalvar)
						.addComponent(btnCancelar)))
		);
		contentPanel.setLayout(gl_contentPanel);
		
		
	}
	
	
	// Consulta Dados da outra Classe para a caixa de Edição
			public void consulta(String id, String titulo, String valor, String vencimento,String numero) throws ParseException{
				textId.setText(id);
				//Não Deixa caixa do ID ser Editada
				textId.setEditable(false);
				textTitulo.setText(titulo);
				
				String valorSemVirgula = valor.replace(",", "").replace(".", ""); //Retira a virgula

				formattedTextValor.setText(valorSemVirgula);
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); //Converte para formato Brasileiro
				Date vencimentoDate = format.parse(vencimento);// Converte Vencimento String para Date
				Calendar vencimentoCalendar = Calendar.getInstance(); // Instancia Calendar
				vencimentoCalendar.setTime(vencimentoDate); // Seta Calendar com a data do banco
				dateVencimento.setCalendar(vencimentoCalendar); //Seta a caixa na tela
				
				textNumero.setText(numero);
			}
			//Retorna Tabela
			public void retornaTabela (JTable table){
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
