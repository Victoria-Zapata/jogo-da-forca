package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;

public class Palavra {
    private long id;
    private String palavra;
    private Tema tema;
    private static LetraFactory letraFactory;

    private Palavra(long id, String palavra, Tema tema) {
        this.id = id;
        this.palavra = palavra;
        this.tema = tema;

        if(letraFactory == null) {
            throw new IllegalStateException("A letraFactory precisa estar configurada para criar uma palavra.");
        }
    }

    public Letra[] getLetras(){
        Letra[] letras = new Letra[palavra.length()];
        for (int i = 0; i < palavra.length(); i++) {
            letras[i] = getLetra(i);
        }
        return letras;
    }

    public Letra getLetra(int posicao) {
        if (posicao < 0 || posicao >= palavra.length()) {
            throw new IndexOutOfBoundsException("Posicao inválida: " + posicao);
        }
        return letraFactory.getLetra(palavra.charAt(posicao));
    }

    public void exibir(Object contexto) {
        exibir(contexto, null);
    }

    public void exibir(Object contexto, boolean[] posicoes) {
        Letra[] letras = getLetras();
        for (int i = 0; i < letras.length; i++) {
            if (posicoes == null || posicoes[i]) {
                letras[i].exibir(contexto);
            } else {
                letraFactory.getLetraEncoberta().exibir(contexto);
            }
        }
    }

    public int[] tentar(char codigo) {
        int quantidade = 0;
        for (int i = 0; i < palavra.length(); i++) {
            if (Character.toLowerCase(palavra.charAt(i)) == Character.toLowerCase(codigo)) {
                quantidade++;
            }
        }

        int[] posicoes = new int[quantidade];
        int indice = 0;

        for (int i = 0; i < palavra.length(); i++) {
            if (Character.toLowerCase(palavra.charAt(i)) == Character.toLowerCase(codigo)) {
                posicoes[indice] = i;
                indice++;
            }
        }
        return posicoes;
    }

    public Tema getTema() {
        return this.tema;
    }

    public boolean comparar(String outraPalavra) {
        if (outraPalavra == null) {
            return false;
        }
        return palavra.equalsIgnoreCase(outraPalavra);
    }

    public int getTamanho() {
        return palavra.length();
    }

    public static void setLetraFactory(LetraFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("A LetraFactory não pode ser nula.");
        }
        letraFactory = factory;
    }

    public static LetraFactory getLetraFactory(){
        return letraFactory;
    }

    public static Palavra criar(long id, String palavra, Tema tema) {
        validarDados(id, palavra, tema);
        return new Palavra(id, palavra, tema);
    }

    public static Palavra reconstituir(long id, String palavra, Tema tema) {
        validarDados(id, palavra, tema);
        return new Palavra(id, palavra, tema);
    }

    private static void validarDados(long id, String palavra, Tema tema) {
        if (id < 0) {
            throw new IllegalArgumentException("O id não pode ser negativo.");
        }
        if (palavra == null || palavra.isEmpty()) {
            throw new IllegalArgumentException("A palavra não pode ser nula ou uma string vazia.");
        }
        if (tema == null) {
            throw new IllegalArgumentException("A palavra precisa de um tema válido.");
        }
    }

    @Override
    public String toString() {
        return palavra;
    }
}
