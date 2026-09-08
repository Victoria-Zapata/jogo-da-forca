package br.edu.iff.jogoforca.dominio.rodada.sorteio;

import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;


public class RodadaSorteioFactory extends RodadaFactoryImpl {
    private static RodadaSorteioFactory soleInstance;

    private RodadaSorteioFactory(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        super(repository, temaRepository, palavraRepository);
    }

    private static void createSoleInstance(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        if (soleInstance == null) {
            soleInstance = new RodadaSorteioFactory(repository, temaRepository, palavraRepository);
        }
    }

    public static RodadaSorteioFactory getSoleInstance() {
        if (soleInstance == null) {
            throw new IllegalStateException("RodadaSorteioFactory não foi inicializada.");
        }
        return soleInstance;
    }

    @Override
    public Rodada getRodada(Jogador jogador) {
        return null; // só deus aqui
    }
}
