package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import br.edu.iff.jogoforca.dominio.jogador.Jogador;

import java.util.ArrayList;
import java.util.List;

public class Rodada extends ObjetoDominioImpl {
    private static BonecoFactory bonecoFactory;
    private static int maxPalavras = 3;
    private static int maxErros = 10;
    private static int pontosPorLetraEncoberta = 15;
    private static int pontosQuandoDescobreTodasAsPalavras = 100;

    private Jogador jogador;
    private Tema tema;
    private Item[] itens;
    private List<Letra> erradas;

    private Rodada(long id, Palavra[] palavras, Jogador jogador) {
        super(id);
        if (palavras == null || palavras.length == 0) {
            throw new IllegalArgumentException("A rodada precisa ter palavras sorteadas.");
        }
        this.jogador = jogador;
        this.tema = palavras[0].getTema();
        this.erradas = new ArrayList<>();

        // Cria os itens correspondentes a cada palavra sorteada
        this.itens = new Item[palavras.length];
        for (int i = 0; i < palavras.length; i++) {
            this.itens[i] = Item.criar(i, palavras[i]);
        }
    }

    public static Rodada criar(long id, Palavra[] palavras, Jogador jogador) {
        return new Rodada(id, palavras, jogador);
    }

    public static Rodada reconstituir(long id, Palavra[] palavras, Jogador jogador) {
        return new Rodada(id, palavras, jogador);
    }

    public Jogador getJogador() {
        return this.jogador;
    }

    public Tema getTema() {
        return this.tema;
    }

    public int getNumPalavras() {
        return this.itens.length;
    }

    public Palavra[] getPalavras() {
        Palavra[] palavras = new Palavra[this.itens.length];
        for (int i = 0; i < this.itens.length; i++) {
            palavras[i] = this.itens[i].getPalavra();
        }
        return palavras;
    }

    public int getQtdeErros() {
        return this.erradas.size();
    }

    public int getQtdeTentativasRestantes() {
        return maxErros - getQtdeErros();
    }

    public boolean arriscou() {
        for (Item item : this.itens) {
            if (item.arriscou()) {
                return true;
            }
        }
        return false;
    }

    public boolean descobriu() {
        for (Item item : this.itens) {
            if (!item.descobriu()) {
                return false;
            }
        }
        return true;
    }

    public boolean encerrou() {
        return arriscou() || descobriu() || getQtdeErros() >= maxErros;
    }

    public void tentar(char codigo) {
        if (encerrou()) {
            return;
        }

        boolean acertouEmAlgum = false;
        for (Item item : this.itens) {
            if (item.tentar(codigo)) {
                acertouEmAlgum = true;
            }
        }

        if (!acertouEmAlgum) {
            Letra letraErrada = Palavra.getLetraFactory().getLetra(codigo);
            // Evita duplicar letras erradas iguais na lista
            boolean jaExiste = false;
            for (Letra l : this.erradas) {
                if (l.equals(letraErrada)) {
                    jaExiste = true;
                    break;
                }
            }
            if (!jaExiste) {
                this.erradas.add(letraErrada);
            }
        }
    }

    public void arriscar(String[] palavrasArriscadas) {
        if (encerrou() || arriscou()) {
            return;
        }
        // Na regra do jogo, permite arriscar o conjunto ou a primeira palavra dependendo do fluxo
        if (palavrasArriscadas.length > 0) {
            this.itens[0].arriscar(palavrasArriscadas[0]);
        }
    }

    public int calcularPontos() {
        if (!descobriu()) {
            return 0;
        }
        int pontos = pontosQuandoDescobreTodasAsPalavras;
        for (Item item : this.itens) {
            pontos += item.calcularPontosLetrasEncobertas(pontosPorLetraEncoberta);
        }
        return pontos;
    }

    public void exibirPalavras(Object contexto) {
        for (Item item : this.itens) {
            item.exibir(contexto);
            System.out.print(" ");
        }
        System.out.println();
    }

    public void exibirLetrasErradas(Object contexto) {
        System.out.print("Letras erradas: ");
        for (Letra l : this.erradas) {
            l.exibir(contexto);
            System.out.print(" ");
        }
        System.out.println();
    }

    public void exibirBoneco(Object contexto) {
        if (bonecoFactory != null) {
            Boneco boneco = bonecoFactory.getBoneco();
            if (boneco != null) {
                boneco.exibir(contexto, getQtdeErros());
            }
        }
    }

    // Configurações estáticas exigidas pelo modelo
    public static void setBonecoFactory(BonecoFactory factory) {
        bonecoFactory = factory;
    }

    public BonecoFactory getBonecoFactory() {
        return bonecoFactory;
    }

    public static int getMaxPalavras() { return maxPalavras; }
    public static void setMaxPalavras(int max) { maxPalavras = max; }
    public static int getMaxErros() { return maxErros; }
    public static void setMaxErros(int max) { maxErros = max; }
    public static int getPontosPorLetraEncoberta() { return pontosPorLetraEncoberta; }
    public static void setPontosPorLetraEncoberta(int p) { pontosPorLetraEncoberta = p; }
    public static int getPontosQuandoDescobreTodasAsPalavras() { return pontosQuandoDescobreTodasAsPalavras; }
    public static void setPontosQuandoDescobreTodasAsPalavras(int p) { pontosQuandoDescobreTodasAsPalavras = p; }
}