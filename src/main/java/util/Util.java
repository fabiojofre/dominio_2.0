package util;


import java.io.File;
import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dominio.ApiStatus;
import dominio.BoxeStatus;
import dominio.FilesExpanded;
import dominio.Retorno;
import dominio.Status;
import servico.Config;

public class Util {

	public String trataToken(JSONObject json) {
		
//		 Extrai apenas o array do bloco mensagem do JSon 		
		String dados = json.getString("access_token");
		return dados;
	}
	public String trataChaveIntegracao(JSONObject json) {
		
//		 Extrai apenas o array do bloco mensagem do JSon 		
		String dados = json.getString("integrationKey");
		return dados;
	}
	public String[] trataValoresChaveIntegracao(JSONObject json) {
		
//		 Extrai apenas o array do bloco mensagem do JSon 		
		String[] dados  = null;
		dados[0]=json.getString("accountantOfficeName");
		dados[1]=json.getString("accountantOfficeNationalIdentity");
		dados[2]=json.getString("clientName");
		dados[3]=json.getString("clientNationalIdentity");
		
		return dados;
	}

	
	
	public Retorno retornaRetorno(String json) {
		Retorno ret = new Retorno();
		if(json.contains("Failed to decode invalid or incorrectly formatted token") || (json.contains("Token has expired"))) {
			ret.setStatus(new Status("TO", "Falha no token"));
		}else {
		try {
		     JSONObject jsonRetorno = new JSONObject(json);
		      JSONArray jsonFilesExpanded = jsonRetorno.getJSONArray("filesExpanded");
		      JSONObject jsonStatus = jsonRetorno.getJSONObject("status");

		      ret.setType(jsonRetorno.getString("type"));
		      ret.setLastStatusOn(jsonRetorno.getString("lastStatusOn"));
		      ret.setApiVersion(jsonRetorno.getString("apiVersion"));
		      ret.setBoxeFile(jsonRetorno.getBoolean("boxeFile"));
		      ret.setId(jsonRetorno.getString("id"));
		      ret.setStatus(new Status(jsonStatus.getString("code"), jsonStatus.getString("message")));
		      	      
		      for(int i =0;i<jsonFilesExpanded.length();i++) {
		    	  // selarei os JSONs
		    	  JSONObject object = jsonFilesExpanded.getJSONObject(i);
		    	  JSONObject apiStatus = object.getJSONObject("apiStatus");
		    	  JSONObject boxeStatus = object.getJSONObject("boxeStatus");
		    	  // distrinchei os subobjetos
		    	  ApiStatus a = new ApiStatus(apiStatus.getString("code"),apiStatus.getString("message"));
		    	  BoxeStatus b = new BoxeStatus(boxeStatus.getString("code"),boxeStatus.getString("message"));
		    	  // conclui o objeto superior
		    	  ret.setFilesExpanded(new FilesExpanded(object.getString("lastApiStatusOn"), object.getString("lastBoxeStatusOn"), a, b));
		      }	 
		      
		    } catch (JSONException err) {
		      System.out.println("Exception : " + err.toString());
		      ret = null;
		    }
		}
		return ret;
	
	}
	
	public boolean confirmaSenha(String senha) {
		boolean confirma;
		
		String diaAux = Integer.toString(LocalDate.now().getDayOfMonth());
		String mes =  Integer.toString(LocalDate.now().getMonthValue()); 
		String ano = Integer.toString(LocalDate.now().getYear()).substring(2,4);
		String dia = ("00" + diaAux).substring(diaAux.length());

		
		
		if(senha.equalsIgnoreCase("vr"+ mes+ dia+ano+"oft")) {
			confirma = true;
			System.out.println("Campo liberado");
		}
		else {
			confirma = false;	
			System.out.println("Senha inrorreta!");
		}
		
//		System.out.println("Acesso ao parâmetro confirmado!");
		return confirma;
	}
	

	public void apagaLog() {
		System.out.println("Apagando arquivos de log...\n");
		LocalDate dia = LocalDate.now();
		
		String dias[] = {dia.toString(), 
				dia.plusDays(-1).toString(), 
				dia.plusDays(-2).toString(),
				dia.plusDays(-3).toString(),
				dia.plusDays(-4).toString(),
				dia.plusDays(-5).toString(),
				dia.plusDays(-6).toString()};
		
		File file = new File(".");
		File afile[] = file.listFiles();
		int i = 0;
		for (int j = afile.length; i < j; i++) {
			if(afile[i].toString().contains(".txt")&&
					!afile[i].toString().contains(dias[0]) &&
					!afile[i].toString().contains(dias[1]) &&
					!afile[i].toString().contains(dias[2]) &&
					!afile[i].toString().contains(dias[3]) &&
					!afile[i].toString().contains(dias[4]) &&
					!afile[i].toString().contains(dias[5]) &&
					!afile[i].toString().contains(dias[6])) {
				File arquivos = afile[i];
				System.out.println(arquivos.getName());
				afile[i].delete();
				
			System.out.println("Arquivos de log apagados.\n");
			}
			
		}
	}
	
	public void apagaXMLs() {
		System.out.println("Deletando arquivos XMLs... \n");
		File file = new File(Config.diretorio);
		File afile[] = file.listFiles();
		int i = 0;
		for (int j = afile.length; i < j; i++) {
			if(afile[i].toString().contains(".xml")) {
				File arquivos = afile[i];
				System.out.println(arquivos.getName());
				afile[i].delete();
				System.out.println("XMLs apagados.\n ");
			}
			
		}
	}
	
}
