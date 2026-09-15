package br.edu.iff.jogoforca.dominio.rodada.emmemoria;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import br.edu.iff.repository.RepositoryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoriaRodadaRepository implements RodadaRepository {
    private static MemoriaRodadaRepository soleInstance;
    private Map<Long, Rodada> pool;

    public static MemoriaRodadaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaRodadaRepository();
        }
        return soleInstance;
    }

    private MemoriaRodadaRepository() {
        this.pool = new HashMap<>();
    }

    @Override
    public long getProximoId() {
        long max = 0;
        for (Long id : this.pool.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max + 1;
    }

    @Override
    public Rodada getPorId(long id) {
        return this.pool.get(id);
    }

    @Override
    public Rodada[] getPorJogador(Jogador jogador) {
        List<Rodada> rodadasEncontradas = new ArrayList<>();
        for (Rodada rodada : this.pool.values()) {
            if (rodada.getJogador().equals(jogador)) {
                rodadasEncontradas.add(rodada);
            }
        }
        return rodadasEncontradas.toArray(new Rodada[0]);
    }

    @Override
    public Rodada[] getTodas() {
        return this.pool.values().toArray(new Rodada[0]);
    }

    @Override
    public void inserir(Rodada rodada) throws RepositoryException {
        if (getPorId(rodada.getId()) != null) {
            throw new RepositoryException("Erro: Já existe uma rodada com o ID " + rodada.getId());
        }
        this.pool.put(rodada.getId(), rodada);
    }

    @Override
    public void atualizar(Rodada rodada) throws RepositoryException {
        if (getPorId(rodada.getId()) == null) {
            throw new RepositoryException("Erro: Rodada com ID " + rodada.getId() + " não encontrada.");
        }
        this.pool.put(rodada.getId(), rodada);
    }

    @Override
    public void remover(Rodada rodada) throws RepositoryException {
        if (getPorId(rodada.getId()) == null) {
            throw new RepositoryException("Erro: Rodada com ID " + rodada.getId() + " não encontrada.");
        }
        this.pool.remove(rodada.getId());
    }
}