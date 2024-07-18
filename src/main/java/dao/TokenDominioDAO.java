package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import conexao.ConexaoServidor;
import model.LojaToken;
import model.TokenDominio;
import servico.Config;

public class TokenDominioDAO {
	 
	public void atualizaAntigo() {
		ConexaoServidor cs = new ConexaoServidor();
		
		String SQL = "insert into dominio_api.token_dominio(id_loja, token, integration_key, data_inicio)\r\n"
				+ "(select id_loja, token, chave, inicio from dominio_api.token where id_loja not in (select id_loja from dominio_api.token_dominio ));";
		
		PreparedStatement pstm = null; 
		
		try {
			cs.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);
			pstm = cs.prepareStatement(SQL);
			
			if(!pstm.execute()){
				
				System.out.println("Dados foram salvos com sucesso! ");
			}
			if(cs != null) {
				cs.close();
				}
				if(pstm !=null) {
					pstm.close();
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("\nNao precisou atualizacao de versao! ");
		}
		
	}
	
	public void save(TokenDominio td) {
		ConexaoServidor cs = new ConexaoServidor();
		
		String SALVAR = "INSERT INTO dominio_api.token_dominio(id_loja, integration_key, data_inicio)VALUES(?, ?, ?)"
				+ "on conflict (id_loja) DO UPDATE SET integration_key =?, data_inicio=?;";
		
		PreparedStatement pstm = null; 
		
		try {
			
			cs.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);
			pstm = cs.prepareStatement(SALVAR);
			
			pstm.setInt(1, td.getId_loja());
			pstm.setString(2, td.getIntegration_key());
			pstm.setDate(3, new Date(td.getData_inicio().getTime()));
			pstm.setString(4, td.getIntegration_key());
			pstm.setDate(5, new Date(td.getData_inicio().getTime()));
			
			if(!pstm.execute()){
				
				System.out.println("Dados foram salvos com sucesso! ");
				
				JOptionPane.showMessageDialog(null, "Loja "+td.getId_loja()+" configurada com sucesso!!");
			}
			if(cs != null) {
			cs.close();
			}
			if(pstm !=null) {
				pstm.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao salvar a loja: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao salvar a loja ");
			try {
				cs.close();
				pstm.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		}

	
	public List<LojaToken>listarLojaToken(){
		ConexaoServidor cs = new ConexaoServidor();
		
		List<LojaToken>lts = new ArrayList<>();
		// evento do combo
		try {
			String qery ="select \r\n"
					+ "l.id id_loja,\r\n"
					+ "l.descricao,\r\n"
					+ "f.nomefantasia,\r\n"
					+ "lpad(f.cnpj::text,14,'0')cnpj,\r\n"
					+ "case  when (tk.id_loja is null or tk.integration_key is null or trim(tk.integration_key) = '') then 'Pendente' else 'Configurada' end status,\r\n"
					+ "coalesce(tk.integration_key,'')integration_key,\r\n"
					+ "coalesce(tk.data_inicio, cast(now() as date))data_inicio,\r\n"
					+ "tk.token\r\n"
					+ "from loja l\r\n"
					+ "join fornecedor f on l.id_fornecedor = f.id\r\n"
					+ "left join dominio_api.token_dominio tk on l.id = tk.id_loja\r\n"
					+ "where l.id_situacaocadastro = 1 order by l.id;";
			cs.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);
			PreparedStatement pst = cs.prepareStatement(qery);
						
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				LojaToken lt = new LojaToken();
				lt.setId_loja(rs.getInt(1));
				lt.setDescricao(rs.getString(2));
				lt.setFantasia(rs.getString(3));
				lt.setCnpj(rs.getString(4));
				lt.setStatus(rs.getString(5));
				lt.setIntegration_key(rs.getString(6));
				lt.setData_inicio(rs.getDate(7));
				lt.setToken(rs.getString(8));
				System.out.println("Loja Token: "+rs.getString(1)+" - "+rs.getString(7));
				lts.add(lt);
			}
			pst.close();
			cs.close();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		return lts;
		
	}
	
	public int qtdLojasConfiguradas() {
		ConexaoServidor cs = new ConexaoServidor();
		int contador =0;
		try {
			String qery = "select count(*) from dominio_api.token_dominio";
			cs.abrirConexao(Config.host, Config.porta, Config.base, Config.usuario, Config.senha);
			PreparedStatement pst = cs.prepareStatement(qery);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				contador = rs.getInt(1); 
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contador;
		
	}
	
}
