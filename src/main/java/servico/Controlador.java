package servico;

import model.LojaToken;

public class Controlador {

	public void executar() {
		
		for (int i = 0; i < Config.lojastoken.size(); i++) {
			EnviaXML envia = new EnviaXML();
			System.out.println("\n\nProcesso da loja " + Config.lojastoken.get(i).getDescricao()
					+ " em andamento.\n");
//			envia.geraNotaSaida(Config.lojastoken.get(i));
//			envia.geraNotaEntrada(Config.lojastoken.get(i));
			envia.geraCupom(Config.lojastoken.get(i));
		}
		
	}
}
