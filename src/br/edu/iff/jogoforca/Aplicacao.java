package br.edu.iff.jogoforca;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactory;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactoryImpl;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactoryImpl;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.jogoforca.dominio.rodada.RodadaFactory;
import br.edu.iff.jogoforca.emmemoria.MemoriaRepositoryFactory;
import br.edu.iff.jogoforca.embdr.BDRRepositoryFactory;
import br.edu.iff.jogoforca.texto.ElementoGraficoTextoFactory;
import br.edu.iff.jogoforca.imagem.ElementoGraficoImagemFactory;
import br.edu.iff.jogoforca.dominio.rodada.sorteio.RodadaSorteioFactory;

public class Aplicacao {
    private static Aplicacao soleInstance;

    private static final String[] TIPOS_REPOSITORY_FACTORY = {"memoria", "relacional"};
    private static final String[] TIPOS_ELEMENTO_GRAFICO_FACTORY = {"texto", "imagem"};
    private static final String[] TIPOS_RODADA_FACTORY = {"sorteio"};

    private String tipoRepositoryFactory = TIPOS_REPOSITORY_FACTORY[0];
    private String tipoElementoGraficoFactory = TIPOS_ELEMENTO_GRAFICO_FACTORY[0];
    private String tipoRodadaFactory = TIPOS_RODADA_FACTORY[0];

    private RepositoryFactory repositoryFactory;
    private ElementoGraficoFactory elementoGraficoFactory;
    private RodadaFactory rodadaFactory;

    public static Aplicacao getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new Aplicacao();
            soleInstance.configurar();
        }
        return soleInstance;
    }

    private Aplicacao() {}

    public void configurar() {
        if (tipoRepositoryFactory.equalsIgnoreCase("memoria")) {
            repositoryFactory = MemoriaRepositoryFactory.getSoleInstance();
        } else if (tipoRepositoryFactory.equalsIgnoreCase("relacional")) {
            repositoryFactory = BDRRepositoryFactory.getSoleInstance();
        }

        if (tipoElementoGraficoFactory.equalsIgnoreCase("texto")) {
            elementoGraficoFactory = ElementoGraficoTextoFactory.getSoleInstance();
        } else if (tipoElementoGraficoFactory.equalsIgnoreCase("imagem")) {
            elementoGraficoFactory = ElementoGraficoImagemFactory.getSoleInstance();
        }

        // Configura a fábrica de letras exigida pela classe Palavra
        Palavra.setLetraFactory(getLetraFactory());

        // 1. Inicializa as fábricas parametrizadas
        TemaFactoryImpl.createSoleInstance(repositoryFactory.getTemaRepository());
        PalavraFactoryImpl.createSoleInstance(repositoryFactory.getPalavraRepository());
        JogadorFactoryImpl.createSoleInstance(repositoryFactory.getJogadorRepository());

        if (tipoRodadaFactory.equalsIgnoreCase("sorteio")) {
            RodadaSorteioFactory.createSoleInstance(
                    repositoryFactory.getRodadaRepository(),
                    repositoryFactory.getTemaRepository(),
                    repositoryFactory.getPalavraRepository()
            );
            rodadaFactory = RodadaSorteioFactory.getSoleInstance();
        }

        // 2. Inicializa os AppServices
        PalavraAppService.createSoleInstance(
                repositoryFactory.getTemaRepository(),
                repositoryFactory.getPalavraRepository(),
                getPalavraFactory()
        );

        RodadaAppService.createSoleInstance(
                rodadaFactory,
                repositoryFactory.getRodadaRepository(),
                repositoryFactory.getJogadorRepository()
        );
    }

    public String[] getTiposRepositoryFactory() {
        return TIPOS_REPOSITORY_FACTORY;
    }

    public void setTipoRepositoryFactory(String tipo) {
        this.tipoRepositoryFactory = tipo;
    }

    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    public String[] getTiposElementoGraficoFactory() {
        return TIPOS_ELEMENTO_GRAFICO_FACTORY;
    }

    public void setTipoElementoGraficoFactory(String tipo) {
        this.tipoElementoGraficoFactory = tipo;
    }

    private ElementoGraficoFactory getElementoGraficoFactory() {
        return elementoGraficoFactory;
    }

    public BonecoFactory getBonecoFactory() {
        return (BonecoFactory) getElementoGraficoFactory();
    }

    public LetraFactory getLetraFactory() {
        return (LetraFactory) getElementoGraficoFactory();
    }

    public String[] getTiposRodadaFactory() {
        return TIPOS_RODADA_FACTORY;
    }

    public void setTipoRodadaFactory(String tipo) {
        this.tipoRodadaFactory = tipo;
    }

    public RodadaFactory getRodadaFactory() {
        return rodadaFactory;
    }

    public TemaFactory getTemaFactory() {
        return TemaFactoryImpl.getSoleInstance();
    }

    public PalavraFactory getPalavraFactory() {
        return PalavraFactoryImpl.getSoleInstance();
    }

    public JogadorFactory getJogadorFactory() {
        return JogadorFactoryImpl.getSoleInstance();
    }
}