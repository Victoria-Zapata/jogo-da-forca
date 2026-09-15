package br.edu.iff.bancodepalavras.dominio.palavra.emmemoria;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.repository.RepositoryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoriaPalavraRepository implements PalavraRepository {
    private static MemoriaPalavraRepository soleInstance;
    private Map<Long, Palavra> pool;

    public static MemoriaPalavraRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaPalavraRepository();
        }
        return soleInstance;
    }

    private MemoriaPalavraRepository() {
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
    public Palavra getPorId(long id) {
        return this.pool.get(id);
    }

    @Override
    public Palavra[] getPorTema(Tema tema) {
        List<Palavra> palavrasEncontradas = new ArrayList<>();

        for (Palavra palavra : this.pool.values()) {
            if (palavra.getTema().equals(tema)) {
                palavrasEncontradas.add(palavra);
            }
        }

        return palavrasEncontradas.toArray(new Palavra[0]);
    }

    @Override
    public Palavra[] getTodas() {
        return this.pool.values().toArray(new Palavra[0]);
    }

    @Override
    public Palavra getPalavra(String palavra) {
        for (Palavra palavraAtual : this.pool.values()) {
            if (palavraAtual.comparar(palavra)) {
                return palavraAtual;
            }
        }
        return null;
    }

    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
        if (getPorId(palavra.getId()) != null) {
            throw new RepositoryException("Erro: Já existe uma palavra com o ID " + palavra.getId());
        }

        this.pool.put(palavra.getId(), palavra);
    }

    @Override
    public void atualizar(Palavra palavra) throws RepositoryException {
        if (getPorId(palavra.getId()) == null) {
            throw new RepositoryException("Erro: Palavra com ID " + palavra.getId() + " não encontrada.");
        }

        this.pool.put(palavra.getId(), palavra);
    }

    @Override
    public void remover(Palavra palavra) throws RepositoryException {
        if (getPorId(palavra.getId()) == null) {
            throw new RepositoryException("Erro: Palavra com ID " + palavra.getId() + " não encontrada.");
        }

        this.pool.remove(palavra.getId());
    }
}