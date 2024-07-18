package tela;

import java.io.IOException;
import java.util.Scanner;

import dao.TokenDominioDAO;
import servico.Config;
import servico.EnviaXML;
import servico.ServicoConfig;
import util.Util;
import view.Empresas;

public class Inicializacao {

	static String valor = "";

	public static void main(String[] args) throws InterruptedException, IOException {

		
			Scanner scanner = new Scanner(System.in);

			// Iniciar uma thread para aguardar a tecla Enter
			Thread thread = new Thread(() -> {
				System.out.print(
						"\nSe está executando pela primeira vez ou deseja \nprosseguir com a aplicação aguarde \nou pressione S + Enter para abrir o configurador.");
				valor = scanner.nextLine(); // Aguarda até que o Enter seja pressionado

			});
			thread.start();

			try {
				int count = 0;
				for (int c = 10; c >= 0; c--) {
					Thread.sleep(1000);
					System.out.print(".");
					count++;
					if (valor.equalsIgnoreCase("S")) {

						System.out.println("Abrindo o configurador...\n");
						Empresas e = new Empresas();
						e.main(args);
						c = 0;

					} else if (count == 10) {

						TokenDominioDAO tdd = new TokenDominioDAO();
						ServicoConfig serv = new ServicoConfig();
						System.out.println("\n\nTempo esgotado! " + count + " segundos passados");
						System.out.println("Carregando dados do sistema");
						serv.trataConfig();
						limparLog();

//						verificar se é a primeira vez da configuração e abre o configurador se for
						System.out.println("\nQuantidade de Registros de configuração: " + tdd.qtdLojasConfiguradas());
						if (tdd.qtdLojasConfiguradas() == 0) {
							tdd.atualizaAntigo();
							Empresas e = new Empresas();
							e.main(args);
						}
						
						while(1==1) {
							for (int i = 0; i < Config.lojastoken.size(); i++) {
								EnviaXML envia = new EnviaXML();
								System.out.println("\n\nProcesso da loja " + Config.lojastoken.get(i).getDescricao()
										+ " em andamento.\n");
								envia.geraNotaSaida(Config.lojastoken.get(i));
								envia.geraNotaEntrada(Config.lojastoken.get(i));
								envia.geraCupom(Config.lojastoken.get(i));
								System.out.println("\n\n");
								limparLog();
							}
							
							
													
							Util u = new Util();
							
							u.apagaLog();
							u.apagaXMLs();
						}
					}

				}
			} catch (InterruptedException e) {
				System.out.println("Contagem de tempo interrompida.");
			} finally {
				scanner.close();
			}
		

	}

	private static void limparLog() {
//		  Apagando o log 

		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");

			System.out.print("Lista Apagada!");
//				
//				for (int c = 5; c >= 0; c--) {
//					Thread.sleep(1000);
//					System.out.print(".");
//				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
