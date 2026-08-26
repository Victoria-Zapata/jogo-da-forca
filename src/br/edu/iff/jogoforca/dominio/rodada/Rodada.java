package br.edu.iff.jogoforca.dominio.rodada;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;

public class Rodada extends ObjetoDominioImpl {
    private static BonecoFactory bonecoFactory;
    private static int maxPalavras = 3;
    private static int maxErros = 10;
    private static int pontosPorLetraEncoberta = 15;
    private static int pontosQuandoDescobreTodasAsPalavras = 100;

    private Rodada(long id, Palavra[] palavras, Jogador jogador){
        super(id);
        if (palavras == null) {
            throw new IllegalArgumentException("A rodada precisa ter palavras sorteadas.");
        }
    }

    public static void setBonecoFactory(BonecoFactory bonecoFactory) {
        Rodada.bonecoFactory = bonecoFactory;
    }

    public BonecoFactory getBonecoFactory() {
        return bonecoFactory;
    }

    public static int getMaxPalavras() {
        return maxPalavras;
    }

    public static void setMaxPalavras(int maxPalavras) {
        Rodada.maxPalavras = maxPalavras;
    }

    public static int getMaxErros() {
        return maxErros;
    }

    public static void setMaxErros(int maxErros) {
        Rodada.maxErros = maxErros;
    }

    public static int getPontosPorLetraEncoberta() {
        return pontosPorLetraEncoberta;
    }

    public static void setPontosPorLetraEncoberta(int pontosPorLetraEncoberta) {
        Rodada.pontosPorLetraEncoberta = pontosPorLetraEncoberta;
    }

    public static int getPontosQuandoDescobreTodasAsPalavras() {
        return pontosQuandoDescobreTodasAsPalavras;
    }

    public static void setPontosQuandoDescobreTodasAsPalavras(int pontosQuandoDescobreTodasAsPalavras) {
        Rodada.pontosQuandoDescobreTodasAsPalavras = pontosQuandoDescobreTodasAsPalavras;
    }
}
