package servico;

import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import conexao.ConexaoServidor;
import dao.TokenDominioDAO;
import vrrecifeframework.classes.VrProperties;

public class ServicoConfig {

	private void lerProperties() {
		
		
		Config.host = VrProperties.getString("database.ip");
		Config.base = VrProperties.getString("database.nome");
		Config.porta = VrProperties.getInt("database.porta");
		Config.usuario = VrProperties.getString("database.usuario");
		Config.senha = VrProperties.getString("database.senha");
		Config.audience ="409f91f6-dc17-44c8-a5d8-e0a1bafd8b67";
		Config.client_id = "zDlMNx0QGOUjoWCClWEC6dPMVcyFd9MB";
		Config.client_secret = "A_PD7SPh1hR_g87EICtXPquT1mFecg07wqTFcwx9V2-1QyqT-MNzAHiGVFDsuEHF";
		Config.diretorio = "xml/";
	}
	
	private void create() {
		lerProperties();
		ConexaoServidor cs = new ConexaoServidor();

		try {
			cs.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);
			String add_cofre_nfs ="alter table notasaidanfe add column if not exists cofre integer;\r\n"
					+ "alter table notaentradanfe add column if not exists cofre integer;\r\n"
					+ "alter table pdv.vendanfce add column if not exists cofre integer;";
			
			
			String createFuncao = "CREATE OR REPLACE FUNCTION dominio_gatilho() RETURNS trigger AS $dominio_gatilho$\r\n"
					+ "    BEGIN\r\n"
					+ "		IF(NEW.id_situacaonfe <> OLD.id_situacaonfe) THEN\r\n"
					+ "			NEW.cofre := 0;\r\n"
					+ "        END IF;\r\n"
					+ "        RETURN NEW;\r\n"
					+ "    END;\r\n"
					+ "$dominio_gatilho$ LANGUAGE plpgsql;\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "CREATE OR REPLACE FUNCTION dominio_nfce_gatilho() RETURNS trigger AS $dominio_nfce_gatilho$\r\n"
					+ "    BEGIN\r\n"
					+ "		IF(NEW.id_situacaonfce <> OLD.id_situacaonfce) THEN\r\n"
					+ "			NEW.cofre := 0;\r\n"
					+ "        END IF;\r\n"
					+ "        RETURN NEW;\r\n"
					+ "    END;\r\n"
					+ "$dominio_nfce_gatilho$ LANGUAGE plpgsql;\r\n"
					+ "";
			String createGatilho = " DROP TRIGGER IF EXISTS dominio_gatilho ON notasaidanfe;\r\n"
					+ "   	create TRIGGER dominio_gatilho BEFORE UPDATE ON notasaidanfe\r\n"
					+ "    FOR EACH ROW EXECUTE PROCEDURE dominio_gatilho();\r\n"
					+ "   \r\n"
					+ "   DROP TRIGGER IF EXISTS dominio_gatilho ON notaentradanfe;\r\n"
					+ "   create TRIGGER dominio_gatilho BEFORE UPDATE ON notaentradanfe\r\n"
					+ "    FOR EACH ROW EXECUTE PROCEDURE dominio_gatilho();\r\n"
					+ "   \r\n"
					+ "   DROP TRIGGER IF EXISTS dominio_nfce_gatilho ON pdv.vendanfce;\r\n"
					+ "   create TRIGGER dominio_nfce_gatilho BEFORE UPDATE ON pdv.vendanfce\r\n"
					+ "    FOR EACH ROW EXECUTE PROCEDURE dominio_nfce_gatilho();\r\n"
					+ "";
			
			String createSchema = "CREATE schema IF NOT EXISTS dominio_api;\r\n";
			String createTabela = "CREATE TABLE IF NOT EXISTS dominio_api.token_dominio(id_loja integer primary key,token text, integration_key text, data_inicio date);\r\n";
			
			PreparedStatement pstm_01 = cs.prepareStatement(add_cofre_nfs);
			PreparedStatement pstm_02 = cs.prepareStatement(createFuncao);
			PreparedStatement pstm_03 = cs.prepareStatement(createGatilho);
			PreparedStatement pstm_04 = cs.prepareStatement(createSchema);
			PreparedStatement pstm_05 = cs.prepareStatement(createTabela);
		
			
			if(!pstm_01.execute()){
				System.out.println("Script de cofre executado! ");
				}
			
			if(!pstm_02.execute()){
				System.out.println("Script de funcao executado! ");
			}
			if(!pstm_03.execute()){
				System.out.println("Script de gatilhos executado! ");
			}
			if(!pstm_04.execute()){
				System.out.println("Script de schema executado! ");
			}
			if(!pstm_05.execute()){
				System.out.println("Script de tabela executado! ");
			}
			System.out.println();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro na conexão com o banco de dados. \nVerifique sua conexão no arquivo vr.properties!");
			System.exit(0); 
		}
		// criar métod de create
		
	}

	
	
	public void trataConfig() {
		TokenDominioDAO tdd = new TokenDominioDAO();
		create();
	
		lerProperties();
		Config.lojastoken = tdd.listarLojaToken();
		
		
		System.out.println("\nOs dados carregados sao: ");
		System.out.println("\n**** Dados da conexao: **** ");
		System.out.println("Servidor: " + Config.host);
		System.out.println("Banco: " + Config.base);
		System.out.println("Porta: " + Config.porta);
		System.out.println("Usuario: " + Config.usuario);
		System.out.println("Senha: " + Config.senha);

		
		System.out.println("\n**** Lojas diponiveis são: **** ");
		for(int i =0;i < Config.lojastoken.size();i++) {
			System.out.println("Loja: "+Config.lojastoken.get(i).getId_loja()+
					" - Nome: "+Config.lojastoken.get(i).getFantasia()+
					" - Cnpj: "+Config.lojastoken.get(i).getCnpj()+
					" - Status: "+Config.lojastoken.get(i).getStatus());
		}
		
		System.out.println("**********************************\n");
	}
	
	
}
