package br.edu.iff.jogoforca.dominio.jogador.emmemoria;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;
import java.util.Map;
import java.util.HashMap;

public class MemoriaJogadorRepository implements JogadorRepository{
	private static MemoriaJogadorRepository soleInstance;
	private Map<Long, Jogador> pool;
	
	public static MemoriaJogadorRepository getSoleInstance() {
		if(soleInstance == null) {
			soleInstance = new MemoriaJogadorRepository();
		}
		return soleInstance;
		
	}
	
	private MemoriaJogadorRepository() {
		this.pool = new HashMap<>();
	}

	@Override
	public long getProximoId() {
		long max = 0;
		for(Long id : this.pool.keySet()) {
			if(id > max) {
				max = id;
			}
		}
		return max + 1;
	}

	@Override
	public Jogador getPorId(long id) {
		return this.pool.get(id);
	}

	@Override
	public Jogador getPorNome(String nome) {
		for(Jogador jogadorAtual : this.pool.values()) {
			if(jogadorAtual.getNome().equalsIgnoreCase(nome)) {
				return jogadorAtual;
			}
		}
		return null;
	}

	@Override
	public void inserir(Jogador jogador) throws RepositoryException {
		if(getPorId(jogador.getId()) != null) {
			throw new RepositoryException("Erro: Já existe um jogador com o ID " + jogador.getId());
		}
		
		if(getPorNome(jogador.getNome()) != null) {
	        throw new RepositoryException("Erro: Já existe um jogador com o nome '" + jogador.getNome() + "'.");
	    }
		
		this.pool.put(jogador.getId(), jogador);
		
	}

	@Override
	public void atualizar(Jogador jogador) throws RepositoryException {
		//vazio, pois o objeto se atualiza sozinho em memoria
	}

	@Override
	public void remover(Jogador jogador) throws RepositoryException {
		if(getPorId(jogador.getId()) == null) {
			throw new RepositoryException("Erro: Jogador com ID " + jogador.getId() + " não encontrado.");
		}
		this.pool.remove(jogador.getId());
	}
		
}
	