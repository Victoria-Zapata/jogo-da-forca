package br.edu.iff.jogoforca.dominio.jogador;
import br.edu.iff.repository.*;

public interface JogadorRepository extends Repository{
	public Jogador getPorId(long id);
	
	public Jogador getPorNome(String nome);
	
	public void inserir(Jogador jogador) throws RepositoryException;
	
	public void atualizar(Jogador jogador) throws RepositoryException;
	
	public void remover(Jogador jogador) throws RepositoryException;	


}
