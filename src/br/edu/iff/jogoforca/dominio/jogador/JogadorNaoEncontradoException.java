package br.edu.iff.jogoforca.dominio.jogador;

public class JogadorNaoEncontradoException extends java.lang.Exception{
	private String jogador;
	
	public JogadorNaoEncontradoException(String jogador) {
		super("O jogador '" + jogador + "' não foi encontrado.");
		this.jogador = jogador;
	}

	public String getJogador() {
		return this.jogador;
	}
}
