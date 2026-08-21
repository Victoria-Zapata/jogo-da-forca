package br.edu.iff.dominio;
import br.edu.iff.dominio.ObjetoDominio;

public abstract class ObjetoDominioImpl implements ObjetoDominio{
    private long id;

    public ObjetoDominioImpl(long id) {
        this.id = id;
    };

    @Override
    public long getId() {
        return id;
    }
}
