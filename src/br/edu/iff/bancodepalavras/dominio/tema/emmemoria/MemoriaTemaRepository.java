package br.edu.iff.bancodepalavras.dominio.tema.emmemoria;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

import java.util.List;
import java.util.Objects;

public class MemoriaTemaRepository implements TemaRepository {
    private static MemoriaTemaRepository soleInstance;
    private List<Tema> pool;

    private MemoriaTemaRepository() {
    }

    public static MemoriaTemaRepository getSoleInstance() {
        if (soleInstance == null) {
            return soleInstance = new MemoriaTemaRepository();
        } else {
            return soleInstance;
        }
    }

    @Override
    public Tema getPorId(long id) {
        for (Tema temaAtual : this.pool) {
            if (temaAtual.getId() == id) {
                return temaAtual;
            }
        }
        return null;
    }

    @Override
    public Tema getPorNome(String nome) {
        for (Tema temaAtual : this.pool) {
            if (Objects.equals(temaAtual.getNome(), nome)) {
                return temaAtual;
            }
        }
        return null;
    }

    @Override
    public Tema[] getTodos() {
        return this.pool.toArray(new Tema[0]);
    }

    @Override
    public void inserir(Tema tema) throws RepositoryException {
        if (getPorId(tema.getId()) != null) {
            throw new RepositoryException();
        }
        this.pool.add(tema);
    }

    @Override
    public void atualizar(Tema tema) throws RepositoryException {
        Tema temaAntigo = getPorId(tema.getId());
        if (temaAntigo == null) {
            throw new RepositoryException();
        }

        int indice = this.pool.indexOf(temaAntigo);
        this.pool.set(indice, tema);
    }

    @Override
    public void remover(Tema tema) throws RepositoryException {
        if (getPorId(tema.getId()) == null) {
            throw new RepositoryException();
        }
        this.pool.remove(tema);
    }

    @Override
    public long getProximoId() {
        if (this.pool.isEmpty()) {
            return 1;
        }
        return this.pool.get(this.pool.size() - 1).getId() + 1;
    }
}
