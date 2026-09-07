package br.edu.iff.jogoforca.dominio.jogador.emmemoria;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;
import java.util.List;
import java.util.ArrayList;

public class MemoriaJogadorRepository implements JogadorRepository{
	private static MemoriaJogadorRepository soleInstance;
	private List<Jogador> pool;
	
	public static MemoriaJogadorRepository getSoleInstance() {
		if(soleInstance != null) {
			return soleInstance;
		}
		soleInstance = new MemoriaJogadorRepository();
		return soleInstance;
		
	}
	
	private MemoriaJogadorRepository() {
		this.pool = new ArrayList<>();
	}

	@Override
	public long getProximoId() {
		if(this.pool.isEmpty()) {
			return 1;
		}
		return pool.get(this.pool.size() - 1).getId() + 1;
	}

	@Override
	public Jogador getPorId(long id) {
		for(Jogador jogadorAtual : this.pool) {
			if(jogadorAtual.getId() == id) {
				return jogadorAtual;
			}
		}
		return null;
	}

	@Override
	public Jogador getPorNome(String nome) {
		for(Jogador jogadorAtual : this.pool) {
			if(jogadorAtual.getNome().equalsIgnoreCase(nome)) {
				return jogadorAtual;
			}
		}
		return null;
	}

	@Override
	public void inserir(Jogador jogador) throws RepositoryException {
		if(getPorId(jogador.getId()) != null) {
			throw new RepositoryException();
		}
		this.pool.add(jogador);
		
	}

	@Override
	public void atualizar(Jogador jogador) throws RepositoryException {
		Jogador jogadorAntigo = getPorId(jogador.getId());
		if(jogadorAntigo == null) {
			throw new RepositoryException();
		}
		
		int indice = this.pool.indexOf(jogadorAntigo);
		pool.set(indice, jogador);
	}

	@Override
	public void remover(Jogador jogador) throws RepositoryException {
		if(getPorId(jogador.getId()) == null) {
			throw new RepositoryException();
		}
		this.pool.remove(jogador);
	}
		
}
	