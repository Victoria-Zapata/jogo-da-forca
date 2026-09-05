package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.factory.EntityFactory;

public class JogadorFactoryImpl extends EntityFactory implements JogadorFactory{
	private static JogadorFactoryImpl soleInstance;
	
	public static void createSoleInstance(JogadorRepository repository) {
		if(soleInstance == null) {
			soleInstance = new JogadorFactoryImpl(repository);
		}
	}
	
	public static JogadorFactoryImpl getSoleInstance() {
		return soleInstance;
	}
	
	private JogadorFactoryImpl(JogadorRepository repository) {
		super(repository);
	}
	
	private JogadorRepository getJogadorRepository() {
		return (JogadorRepository) super.getRepository();
	}
	
	public Jogador getJogador(String nome) {
		if(getJogadorRepository().getPorNome(nome) != null) {
			throw new IllegalArgumentException("Erro: Já existe um Jogador com esse nome!");
		}
		return Jogador.criar(this.getProximoId(), nome);
	}
	
}

