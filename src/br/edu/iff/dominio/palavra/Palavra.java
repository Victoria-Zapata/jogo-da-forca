package br.edu.iff.dominio.palavra;

import br.edu.iff.dominio.letra.Letra;
import br.edu.iff.dominio.letra.LetraFactory;
import br.edu.iff.dominio.tema.Tema;

public class Palavra {

    private Palavra(long id, String palavra, Tema tema) {
    }

    public Letra[] getLetras(){
        return null;
    }

    public Letra getLetra(int posicao) {
        return null;
    }

    public void exibir(Object contexto) {
    }

    public void exibir(Object contexto, boolean[] posicoes) {
    }

    public int[] tentar(char codigo) {
        return null;
    }

    public Tema getTema() {
        return null;
    }

    public boolean comparar(String palavra) {
        return false;
    }

    public int getTamanho() {
        return 0;
    }

    public static void setLetraFactory(LetraFactory factory) {
    }

    public static LetraFactory getLetraFactory(){
        return null;
    }

    public static Palavra criar(long id, String palavra, Tema tema) {
        return null;
    }

    public static Palavra reconstituir(long id, String palavra, Tema tema) {
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
