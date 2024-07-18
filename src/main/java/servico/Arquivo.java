package servico;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
	
	public String diretorio;
	
	public List<String> listarArquivosXML(String diretorio) {
		File dir = new File(diretorio);
		List<String> arquivos = new ArrayList<String>();
		for(File file:dir.listFiles()) {
			if(file.getName().endsWith("xml") || file.getName().endsWith("XML")) {
//			System.out.println(dir+"\\"+file.getName());
			arquivos.add(dir+"\\"+file.getName());
			}
		}
		return arquivos;
	}
	
	public void deletarArquivo(String arquivo) {
		File file = new File(arquivo);
		System.out.println("Arquivo "+arquivo+" excluido com sucesso!");
	}
	
	public void geraArquivo(String arquivo, String xml) {
		File file = new File(arquivo);
		try {
			file.createNewFile();
//			 FileWriter fileWriter = new FileWriter(arquivo, false);
//	         PrintWriter printWriter = new PrintWriter(fileWriter);
			 PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(arquivo), "UTF-8"));
	         printWriter.print(xml);
	         printWriter.flush();
	            printWriter.close();
			System.out.println("XML "+arquivo+" gerado com sucesso");
			
		}  catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	 public void escreverLog(String linha) {
			File file = new File("log_"+LocalDate.now().toString()+"_.txt");
			try {
			if (!file.exists()) {
				 file.createNewFile();
				System.out.print("Arquivo de log criado!");
			}
			FileWriter fileWriter = new FileWriter(file, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(linha);
			printWriter.flush();
			printWriter.close();
			
			} catch (IOException e) {
			    e.printStackTrace(); 
			} 
			
			
	 }
}
