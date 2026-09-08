package br.edu.iff.bancodepalavras.dominio.palavra.emmemoria;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.repository.RepositoryException;
import java.util.ArrayList;
import java.util.List;

public class MemoriaPalavraRepository implements PalavraRepository {
    private static MemoriaPalavraRepository soleInstance;
    private List<Palavra> pool;

    private MemoriaPalavraRepository(){
        this.pool = new ArrayList<>();
    }

    public static MemoriaPalavraRepository getSoleInstance() {
        if (soleInstance == null) {
            return soleInstance = new MemoriaPalavraRepository();
        } else {
            return soleInstance;
        }
    }

    @Override
    public Palavra getPorId(long id) {
        for (Palavra palavraAtual : this.pool) {
            if (palavraAtual.getId() == id) {
                return palavraAtual;
            }
        }
        return null;
    }

    @Override
    public Palavra[] getPorTema(Tema tema) {
        List<Palavra> palavrasEncontradas = new ArrayList<>();

        for (Palavra palavra : this.pool) {
            if (palavra.getTema().equals(tema)) {
                palavrasEncontradas.add(palavra);
            }
        }

        return palavrasEncontradas.toArray(new Palavra[0]);
    }

    @Override
    public Palavra[] getTodas() {
        return this.pool.toArray(new Palavra[0]);
    }

    @Override
    public Palavra getPalavra(String palavra) {
        for (Palavra palavraAtual : this.pool) {
            if (palavraAtual.comparar(palavra)) {
                return palavraAtual;
            }
        }
        return null;
    }

    @Override
    public void inserir(Palavra palavra) throws RepositoryException {
        if (getPorId(palavra.getId()) != null) {
            throw new RepositoryException();
        }
        this.pool.add(palavra);
    }

    @Override
    public void atualizar(Palavra palavra) throws RepositoryException {
        Palavra palavraAntiga = getPorId(palavra.getId());
        if (palavraAntiga == null) {
            throw new RepositoryException();
        }

        int indice = this.pool.indexOf(palavraAntiga);
        this.pool.set(indice, palavra);
    }

    @Override
    public void remover(Palavra palavra) throws RepositoryException {
        if (getPorId(palavra.getId()) == null) {
            throw new RepositoryException();
        }
        this.pool.remove(palavra);
    }

    @Override
    public long getProximoId() {
        if (this.pool.isEmpty()) {
            return 1;
        }
        return this.pool.get(this.pool.size() - 1).getId() + 1;
    }
}
