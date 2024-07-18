package view;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import conexao.ConexaoServidor;
import dao.TokenDominioDAO;
import model.LojaToken;
import model.TokenDominio;
import servico.Autorizacao;
import servico.Config;
import servico.ServicoConfig;
import util.Util;

public class Empresas {

	private JFrame frmConfiguradorDeLojas;
	private JTextField textFieldIdLoja;
	private JTextField textFieldIntegrationKey;
	private JDateChooser dateChooser;
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Empresas window = new Empresas();
					window.frmConfiguradorDeLojas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Empresas() {
		ServicoConfig sc = new ServicoConfig();
		
		sc.trataConfig();
		initialize();
		criarTabela();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConfiguradorDeLojas = new JFrame();
		frmConfiguradorDeLojas.setTitle("Configurador de lojas Dominio");
		frmConfiguradorDeLojas.setResizable(false);
		frmConfiguradorDeLojas.setBounds(100, 100, 627, 370);
		frmConfiguradorDeLojas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConfiguradorDeLojas.getContentPane().setLayout(null);
		
		Button bt_config = new Button("#");
		bt_config.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(168, 59, 433, 177);
		frmConfiguradorDeLojas.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		TokenDominioDAO tdd = new TokenDominioDAO();
		
	
		LojaToken  lt = new LojaToken();
		
		JComboBox comboBox = new JComboBox();
		comboBox.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
				comboBox.removeAll();
				for(LojaToken lt : Config.lojastoken) {
					comboBox.addItem(lt.getDescricao());
				}
				
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConexaoServidor cs = new ConexaoServidor();
				ServicoConfig sc = new ServicoConfig();
				
				// evento do combo
				try {
					String qery ="select \r\n"
							+ "l.id id_loja,\r\n"
							+ "l.descricao,\r\n"
							+ "f.nomefantasia,\r\n"
							+ "lpad(f.cnpj::text,14,'0')cnpj,\r\n"
							+ "case  when (tk.id_loja is null or tk.integration_key is null or trim(tk.integration_key) = '') then 'Pendente' else 'Configurada' end status,\r\n"
							+ "coalesce(tk.integration_key,'')integration_key,\r\n"
							+ "coalesce(tk.data_inicio, cast(now() as date))data_inicio\r\n"
							+ "from loja l\r\n"
							+ "join fornecedor f on l.id_fornecedor = f.id\r\n"
							+ "left join dominio_api.token_dominio tk on l.id = tk.id_loja\r\n"
							+ "where l.id_situacaocadastro = 1 and l.descricao = ? order by l.id;";
					cs.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);
					PreparedStatement pst = cs.prepareStatement(qery);
					pst.setString(1, (String) comboBox.getSelectedItem());
					
					ResultSet rs = pst.executeQuery();
					
					while(rs.next()) {
						textFieldIdLoja.setText(rs.getString(1));
						textFieldIntegrationKey.setText(rs.getString(6));
						dateChooser.setDate(rs.getDate(7));
						if(rs.getString(5).equalsIgnoreCase("Configurada")) {
							textFieldIntegrationKey.setEnabled(false);
							bt_config.setEnabled(true);
						}else {
							textFieldIntegrationKey.setEnabled(true);
							bt_config.setEnabled(false);
						}
						//System.out.println(rs.getString(1)+" - "+rs.getString(7));
						
					}
					pst.close();
					cs.close();
					sc.trataConfig();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Erro na conexão com o banco de dados. \nVerifique sua conexão!");
				}
				
			}
		
		});
		comboBox.setBounds(10, 59, 148, 42);
		frmConfiguradorDeLojas.getContentPane().add(comboBox);
		
		textFieldIdLoja = new JTextField();
		textFieldIdLoja.setText("0");
		textFieldIdLoja.setEditable(false);
		textFieldIdLoja.setBounds(10, 128, 46, 20);
		frmConfiguradorDeLojas.getContentPane().add(textFieldIdLoja);
		textFieldIdLoja.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Id Loja");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 103, 46, 14);
		frmConfiguradorDeLojas.getContentPane().add(lblNewLabel);
		
		textFieldIntegrationKey = new JTextField();
		textFieldIntegrationKey.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldIntegrationKey.setBounds(10, 247, 590, 20);
		frmConfiguradorDeLojas.getContentPane().add(textFieldIntegrationKey);
		textFieldIntegrationKey.setColumns(10);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(10, 190, 148, 20);
		frmConfiguradorDeLojas.getContentPane().add(dateChooser);
		
		JLabel lblDataIncio = new JLabel("Data Início");
		lblDataIncio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDataIncio.setBounds(10, 165, 72, 14);
		frmConfiguradorDeLojas.getContentPane().add(lblDataIncio);
		
		JLabel lblIntegrationKey = new JLabel("Integration Key");
		lblIntegrationKey.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIntegrationKey.setBounds(10, 220, 93, 14);
		frmConfiguradorDeLojas.getContentPane().add(lblIntegrationKey);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TokenDominio td = new TokenDominio();
				TokenDominioDAO tdo = new TokenDominioDAO();
				ServicoConfig sc = new ServicoConfig();
				
				td.setId_loja(Integer.parseInt(textFieldIdLoja.getText()));
				td.setData_inicio(dateChooser.getDate());
				td.setIntegration_key(textFieldIntegrationKey.getText().trim());
				if(td.getIntegration_key().length()>= 40 && td.getIntegration_key().length()<=50 ) {
					bt_config.setEnabled(true);
					textFieldIntegrationKey.setEnabled(false);
				}else {
					bt_config.setEnabled(false);
					textFieldIntegrationKey.setEnabled(true);
				}
				tdo.save(td);	

				sc.trataConfig();
				
				criarTabela();	
				
				try {
					String token = "";
					Autorizacao aut = new Autorizacao();
					
						int val = JOptionPane.showConfirmDialog(null, "Deseja gerar novo token?");
						
						if(val == 0) { 
							
							System.out.println("Gerando novo token...");
							 token = aut.retornaToken(textFieldIntegrationKey.getText().trim(), 
						    		Config.client_id, Config.client_secret, Config.audience,Integer.parseInt(textFieldIdLoja.getText()));
							
							JOptionPane.showMessageDialog(null, "Token gerado com sucesso!");
							
						}
						int val2 = JOptionPane.showConfirmDialog(null, "Deseja gerar nova chave?");
						if (val2 == 0) {
							System.out.println("Gerando nova chave...");
							
							String chave = aut.retornaChaveIntegracao(token, textFieldIntegrationKey.getText(),Integer.parseInt(textFieldIdLoja.getText()));
							if(!chave.equals("")) {
							textFieldIntegrationKey.setText(chave);

							System.out.println("Chave gerada! ");
							}else {

								System.out.println("Chave gerada anteriormente! ");
							}
						
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSalvar.setBounds(49, 278, 99, 30);
		frmConfiguradorDeLojas.getContentPane().add(btnSalvar);
		
		JLabel lblNewLabel_1 = new JLabel("Loja");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 28, 72, 20);
		frmConfiguradorDeLojas.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Configurações");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_2.setBounds(200, 2, 200, 40);
		frmConfiguradorDeLojas.getContentPane().add(lblNewLabel_2);
		
		
		bt_config.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPasswordField password = new JPasswordField(10);
				password.setEchoChar('*'); 
				
				JLabel rotulo = new JLabel("Entre com a senha padrão VR:");
				
				JPanel entUsuario = new JPanel();
				entUsuario.add(rotulo);
				entUsuario.add(password);
				
				JOptionPane.showMessageDialog(null, entUsuario, "Acesso restrito", JOptionPane.PLAIN_MESSAGE);
				Util u = new Util();
				String senha = password.getText();
				if(u.confirmaSenha(senha)) {
					textFieldIntegrationKey.setEnabled(true);
				}else {
					JOptionPane.showMessageDialog(null,	"Senha incorreta!");
				}
				
//				System.out.println("Você digitou: "+senha+"\nFim de execucao.");
			}
		});
		bt_config.setActionCommand("");
		bt_config.setFont(new Font("Dialog", Font.PLAIN, 18));
		bt_config.setBounds(579, 31, 22, 22);
		frmConfiguradorDeLojas.getContentPane().add(bt_config);
		
	}
	
	
	private void criarTabela() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		model.setNumRows(0);
		model.setColumnCount(0);
		
		
		model.addColumn("Id");
		model.addColumn("Descrição");
		model.addColumn("CNPJ");
		model.addColumn("Status");
		model.addColumn("Data");
		
		
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(2).setPreferredWidth(85);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		
		for(LojaToken l: Config.lojastoken) {
			model.addRow(new Object[] {
				l.getId_loja(), l.getDescricao(),l.getCnpj(),l.getStatus(),l.getData_inicio()
			});
		}
		
	}
}
