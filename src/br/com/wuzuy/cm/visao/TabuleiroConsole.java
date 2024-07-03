package br.com.wuzuy.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
	
import br.com.wuzuy.cm.excecao.ExplosaoException;
import br.com.wuzuy.cm.excecao.SairException;
import br.com.wuzuy.cm.modelo.Tabuleiro;

public class TabuleiroConsole {
	private Scanner entrada = new Scanner(System.in);

	private Tabuleiro tabuleiro;

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}

	private void executarJogo() {
		try {
			boolean continuar = true;
			
			while(continuar) {
				cicloDoJogo();
				
				System.out.println("Outra partida? (S/n) ");
				String resposta = entrada.nextLine();
				
				if("n".equalsIgnoreCase(resposta)) {
					continuar = false;
				}else {
					tabuleiro.reiniciar();
				}
			}
		}catch (SairException e) {
			System.out.println("Tchau!");
		}finally {
			entrada.close();
		}
		
	}

	private void cicloDoJogo() {
		try {
			
			while(!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);
				
				String digitado = capturarValorDigitado("Digite (x, y): ");
				
				Iterator<Integer>xy = Arrays.stream(digitado.split(","))
				.map(e -> Integer.parseInt(e.trim())).iterator();
				try {
					
				} catch (Exception e2) {
					System.out.println("Entrada inválida, insira novamente");
					continue;
				}
				
				if (!xy.hasNext()) {
					System.out.println("Entrada inválida, insira novamente!");
					continue;
				}
				
				int x = xy.next();
				
				if (!xy.hasNext()) {
					System.out.println("Entrada inválida, insira novamente!");
					continue;
				}
				
				int y = xy.next();
				
				System.out.println("["+x+","+y+"]");
				
				String acao = capturarValorDigitado("1 - Para abrir."
						+ "\n 2- Para (Des)marcar.");
				
				if("1".equals(acao)) {
					tabuleiro.abrir(x,y);
				}else if("2".equals(acao)) {
					tabuleiro.marcar(x,y);
				}
			}
			
			System.out.println("Você ganhou!");
		}catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você perdeu!");
		}
	}
	private String capturarValorDigitado(String texto) {
		System.out.println(texto);
		String digitado = entrada.nextLine();
		
		if("sair".equalsIgnoreCase(digitado)) {
			throw new SairException();
		}
		return digitado;
	}
}
