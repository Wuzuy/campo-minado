package br.com.wuzuy.cm;

import br.com.wuzuy.cm.modelo.Tabuleiro;
import br.com.wuzuy.cm.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		
		Tabuleiro tabuleiro = new Tabuleiro (6,6,6);
		new TabuleiroConsole(tabuleiro);
	}
}