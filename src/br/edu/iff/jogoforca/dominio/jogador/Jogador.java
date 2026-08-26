package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.dominio.ObjetoDominioImpl;

public class Jogador extends ObjetoDominioImpl{
	private String nome;
	private int pontuacao;
	
	//construtor para criar um jogador NOVO
	private Jogador(long id, String nome) {
		super(id);
		this.setNome(nome);
		this.pontuacao = 0;
	}
	
	//construtor para pegar um jogador do BANCO
	private Jogador(long id, String nome, int pontuacao) {
		super(id);
		this.nome = nome;
		this.pontuacao = pontuacao;
	}
	
	public Jogador criar(long id, String nome) {
		return new Jogador(id, nome);
	}
	
	public Jogador reconstituir(long id, String nome, int pontuacao) {
		return new Jogador(id, nome, pontuacao);
	}
	
	public String getNome() {
		return this.nome;
		
	}
	
	public void setNome(String nome) {
		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome não pode ser vazio ou formado por espaços!");
		}
		this.nome = nome;
	}
	
	public int getPontuacao() {
		return this.pontuacao;
		
	}
	
	public void atualizarPontuacao(int pontos) {
		this.pontuacao += pontos;
		
	}
}
