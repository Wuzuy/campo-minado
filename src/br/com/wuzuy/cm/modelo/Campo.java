package br.com.wuzuy.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.wuzuy.cm.excecao.ExplosaoException;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	
	boolean adicionarVizinho(Campo vizinho) {	
	    int deltaLinha = Math.abs(linha - vizinho.linha);
	    int deltaColuna = Math.abs(coluna - vizinho.coluna);

	    if (deltaLinha <= 1 && deltaColuna <= 1 && (deltaLinha != 0 || deltaColuna != 0)) {
	        vizinhos.add(vizinho);
	        return true;
	    } else {
	        return false;
	    }
	}
	void alternarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	boolean abrir() {
		
		if(!aberto && !marcado) {
			aberto = true;
			
			if(minado) {
				throw new ExplosaoException(); 
			}
			
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			
			return true;
		}
		return false;
	}
	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	public boolean isFechado() {
		return !isAberto();
	}
	
	public int getLinha() {
		return linha;
	}
	
	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	long minasNaVizinhança() {
		return vizinhos.stream().filter( v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	@Override
	public String toString() {
		if(marcado) {
			return "X";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhança() > 0) {
			return Long.toString(minasNaVizinhança());
		} else if (aberto) {
			return " ";
		} else {
			return "?";
		}
	}
}
