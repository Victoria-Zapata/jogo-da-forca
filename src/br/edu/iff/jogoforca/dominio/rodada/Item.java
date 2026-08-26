package br.edu.iff.jogoforca.dominio.rodada;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;

public class Item extends ObjetoDominioImpl {
    private long id;
    private Palavra palavra;
    private boolean[] posicoesDescobertas;
    private String palavraArriscada = null;

    private Item(long id, Palavra palavra) {
        super(id);
        if (palavra == null) {
            throw new IllegalArgumentException("A palavra não pode ser uma string nula.");
        }
        if (id < 0) {
            throw new IllegalArgumentException("O id não pode ser nulo.");
        }
        this.id = id;
        this.palavra = palavra;
        this.posicoesDescobertas = new boolean[palavra.getTamanho()];
    }

    private Item(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
        super(id);
        if (palavra == null) {
            throw new IllegalArgumentException("A palavra não pode ser uma string nula.");
        }
        this.id = id;
        this.palavra = palavra;
        this.posicoesDescobertas = new boolean[palavra.getTamanho()];

        if (posicoesDescobertas != null) {
            for (int posicao : posicoesDescobertas) {
                if (posicao >=0 && posicao < this.posicoesDescobertas.length) {
                    this.posicoesDescobertas[posicao] = true;
                }
            }
        }
        this.palavraArriscada = palavraArriscada;
    }

    static Item criar(int id, Palavra palavra) {
        return new Item(id, palavra);
    }

    public static Item reconstituir(int id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada){
        return new Item(id, palavra, posicoesDescobertas, palavraArriscada);
    }

    public Palavra getPalavra() {
        return this.palavra;
    }

    public Letra[] getLetrasDescobertas() {
        int quantidade = posicoesDescobertas.length - qtdeLetrasEncobertas();
        Letra[] letras = new Letra[quantidade];
        int indice = 0;

        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (posicoesDescobertas[i]) {
                letras[indice] = palavra.getLetra(i);
                indice++;
            }
        }
        return letras;
    }

    public Letra[] getLetrasEncobertas() {
        int quantidade = qtdeLetrasEncobertas();
        Letra[] letras = new Letra[quantidade];
        int indice = 0;

        for (int i = 0; i < posicoesDescobertas.length; i++) {
            if (!posicoesDescobertas[i]) {
                letras[indice] = palavra.getLetra(i);
                indice++;
            }
        }
        return letras;
    }

    public int qtdeLetrasEncobertas(){
        int quantidade = 0;
        for (boolean descoberta : posicoesDescobertas) {
            if (!descoberta) {
                quantidade++;
            }
        }
        return quantidade;
    }

    public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
        return qtdeLetrasEncobertas()*valorPorLetraEncoberta;
    }

    public boolean descobriu(){
        if (acertou()) {
            return true;
        }

        for (boolean descoberta : posicoesDescobertas) {
            if (!descoberta) {
                return false;
            }
        }
        return true;
    }

    public void exibir(Object contexto) {
        palavra.exibir(contexto, posicoesDescobertas);
    }

    boolean tentar(char codigo) {
        int[] posicoes = palavra.tentar(codigo);

        if (posicoes.length == 0) {
            return false;
        }

        for (int posicao : posicoes) {
            posicoesDescobertas[posicao] = true;
        }
        return true;
    }

    void arriscar(String palavra) {
        if (arriscou()) {
            throw new IllegalStateException("O jogador só tem uma chance para arriscar.");
        }
        if (palavra == null) {
            throw new IllegalArgumentException("A palavra arriscada não pode ser uma string nula.");
        }
        this.palavraArriscada = palavra;
    }

    public String getPalavraArriscada() {
        return palavraArriscada;
    }

    public boolean arriscou() {
        return (palavraArriscada != null);
    }

    public boolean acertou() {
        return (arriscou() && palavra.comparar(palavraArriscada));
    }
}
