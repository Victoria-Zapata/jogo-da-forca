package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.factory.EntityFactory;

public class JogadorFactoryImpl extends EntityFactory implements JogadorFactory{
	private static JogadorFactoryImpl soleInstance;
	
	public static void createSoleInstance(JogadorRepository repository) {
		soleInstance = new JogadorFactoryImpl(repository);
	}
	
	public static JogadorFactoryImpl getSoleInstance() {
		if (soleInstance == null) {
			throw new IllegalStateException("Fábrica não instanciada!");
		}
		return soleInstance; 
	}
	
	private JogadorFactoryImpl(JogadorRepository repository) {
		super(repository);
	}
	
	private JogadorRepository getJogadorRepository() {
		return (JogadorRepository) this.getRepository();
	}
	
	public Jogador getJogador(String nome) {
		return Jogador.criar(this.getProximoId(), nome);
	}
	
}

