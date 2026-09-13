package br.edu.iff.bancodepalavras.dominio.tema;

import br.edu.iff.factory.EntityFactory;

public class TemaFactoryImpl extends EntityFactory implements TemaFactory{
    private static TemaFactoryImpl soleInstance;
 //   private TemaRepository temaRepository;

    private TemaFactoryImpl(TemaRepository repository) {
        super(repository);
    }

    public static void createSoleInstance(TemaRepository repository) {
        if (soleInstance == null) {
            soleInstance = new TemaFactoryImpl(repository);
        }
    }

    public static TemaFactoryImpl getSoleInstance() {
        return soleInstance;
    }

    private TemaRepository getTemaRepository() {
        return (TemaRepository) super.getRepository();
    }

    @Override
    public Tema getTema(String nome) {
        return new Tema(getProximoId(), nome);
    }
}
