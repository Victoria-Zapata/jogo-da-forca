package br.edu.iff.dominio.item;
import br.edu.iff.dominio.ObjetoDominioImpl;
import br.edu.iff.dominio.letra.Letra;
import br.edu.iff.dominio.palavra.Palavra;

public class Item extends ObjetoDominioImpl {
    private boolean[] posicoesDescobertas;
    private String palavraArriscada = null;

    private Item(int id, Palavra palavra) {
        super(id);
    }

    private Item(int id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
        super(id);
    }

    static Item criar(int id, Palavra palavra) {
        return null;
    }

    public static Item reconstituir(int id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada){
        return null;
    }

    public Palavra getPalavra() {
        return null;
    }

    public Letra[] getLetrasDescobertas() {
        return null;
    }

    public Letra[] getLetrasEncobertas() {
        return null;
    }

    public int qtdeLetrasEncobertas(){
        return 0;
    }

    public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
        return 0;
    }

    public boolean descobriu(){
        return false;
    }

    public void exibir(Object contexto) {
    }

    boolean tentar(char codigo) {
        return false;
    }

    void arriscar(String palavra) {
    }

    public String getPalavraArriscada() {
        return "";
    }

    public boolean arriscou() {
        return false;
    }

    public boolean acertou() {
        return false;
    }
}
