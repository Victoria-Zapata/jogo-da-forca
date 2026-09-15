package br.edu.iff.bancodepalavras.dominio.tema.emmemoria;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

import java.util.HashMap;
import java.util.Objects;

public class MemoriaTemaRepository implements TemaRepository {
    private static MemoriaTemaRepository soleInstance;
    private HashMap<Long, Tema> pool;

    private MemoriaTemaRepository() {
        // Inicializa o mapa para evitar NullPointerException
        this.pool = new HashMap<>();
    }

    public static MemoriaTemaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaTemaRepository();
        }
        return soleInstance;
    }

    @Override
    public Tema getPorId(long id) {
        // Com HashMap, a busca por ID é direta e eficiente pela chave
        return this.pool.get(id);
    }

    @Override
    public Tema getPorNome(String nome) {
        // Itera sobre os valores do mapa para buscar pelo nome
        for (Tema temaAtual : this.pool.values()) {
            if (Objects.equals(temaAtual.getNome(), nome)) {
                return temaAtual;
            }
        }
        return null;
    }

    @Override
    public Tema[] getTodos() {
        // Retorna todos os valores do mapa convertidos para array
        return this.pool.values().toArray(new Tema[0]);
    }

    @Override
    public void inserir(Tema tema) throws RepositoryException {
        if (this.pool.containsKey(tema.getId())) {
            throw new RepositoryException("Tema com ID " + tema.getId() + " já existe.");
        }
        this.pool.put(tema.getId(), tema);
    }

    @Override
    public void atualizar(Tema tema) throws RepositoryException {
        if (!this.pool.containsKey(tema.getId())) {
            throw new RepositoryException("Tema com ID " + tema.getId() + " não encontrado para atualização.");
        }
        this.pool.put(tema.getId(), tema);
    }

    @Override
    public void remover(Tema tema) throws RepositoryException {
        if (tema == null || !this.pool.containsKey(tema.getId())) {
            throw new RepositoryException("Tema não encontrado para remoção.");
        }
        this.pool.remove(tema.getId());
    }

    @Override
    public long getProximoId() {
        if (this.pool.isEmpty()) {
            return 1;
        }
        long maxId = 0;
        for (Long id : this.pool.keySet()) {
            if (id > maxId) {
                maxId = id;
            }
        }
        return maxId + 1;
    }
}