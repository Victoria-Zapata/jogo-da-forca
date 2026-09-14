package br.edu.iff.jogoforca.dominio.rodada.emmemoria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import br.edu.iff.repository.RepositoryException;

public class MemoriaRodadaRepository implements RodadaRepository {

    private static MemoriaRodadaRepository soleInstance;
    private final Map<Long, Rodada> pool = new HashMap<>();
    private long contadorId = 0;

    private MemoriaRodadaRepository() {
    }

    public static MemoriaRodadaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaRodadaRepository();
        }
        return soleInstance;
    }

    @Override
    public long getProximoId() {
        this.contadorId++;
        return this.contadorId;
    }

    @Override
    public Rodada getPorId(long id) {
        return this.pool.get(id);
    }

    @Override
    public Rodada[] getPorJogador(Jogador jogador) {
        List<Rodada> rodadas = new ArrayList<>();

        for (Rodada rodada : this.pool.values()) {
            if (rodada.getJogador().getId() == jogador.getId()) {
                rodadas.add(rodada);
            }
        }
        return rodadas.toArray(new Rodada[0]);
    }

    @Override
    public void inserir(Rodada rodada) throws RepositoryException {
        this.pool.put(rodada.getId(), rodada);
    }

    @Override
    public void atualizar(Rodada rodada) throws RepositoryException {
        this.pool.put(rodada.getId(), rodada);
    }

    @Override
    public void remover(Rodada rodada) throws RepositoryException {
        this.pool.remove(rodada.getId());
    }
}