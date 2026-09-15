package br.edu.iff.jogoforca.dominio.rodada.sorteio;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

import java.util.Random;

public class RodadaSorteioFactory extends RodadaFactoryImpl {
    private static RodadaSorteioFactory soleInstance;

    private RodadaSorteioFactory(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
        super(repository, temaRepository, palavraRepository);
    }

    public static void createSoleInstance(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
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
        TemaRepository temaRepo = getTemaRepository();
        PalavraRepository palavraRepo = getPalavraRepository();

        // Garante a obtenção do repositório de rodadas de forma segura
        RodadaRepository rodadaRepo = getRodadaRepository();
        if (rodadaRepo == null) {
            rodadaRepo = Aplicacao.getSoleInstance().getRepositoryFactory().getRodadaRepository();
        }

        Tema[] temas = temaRepo.getTodos();
        if (temas == null || temas.length == 0) {
            return null;
        }

        // Sorteia um tema aleatório
        Random random = new Random();
        Tema temaSorteado = temas[random.nextInt(temas.length)];

        // Obtém as palavras do tema sorteado
        Palavra[] palavrasTema = palavraRepo.getPorTema(temaSorteado);
        if (palavrasTema == null || palavrasTema.length == 0) {
            return null;
        }

        // Define quantas palavras vão compor a rodada (respeitando o limite máximo)
        int maxP = Rodada.getMaxPalavras();
        int qtdPalavras = Math.min(maxP, palavrasTema.length);

        // Sorteia as palavras sem repetição
        Palavra[] palavrasSorteadas = new Palavra[qtdPalavras];
        boolean[] escolhidas = new boolean[palavrasTema.length];
        for (int i = 0; i < qtdPalavras; i++) {
            int indice;
            do {
                indice = random.nextInt(palavrasTema.length);
            } while (escolhidas[indice]);
            escolhidas[indice] = true;
            palavrasSorteadas[i] = palavrasTema[indice];
        }

        long proximoId = rodadaRepo.getProximoId();
        return Rodada.criar(proximoId, palavrasSorteadas, jogador);
    }
}