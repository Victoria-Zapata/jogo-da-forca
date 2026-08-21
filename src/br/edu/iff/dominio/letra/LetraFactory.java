package br.edu.iff.dominio.letra;

public interface LetraFactory {
    public Letra getLetra(char codigo);
    public Letra getLetraEncoberta();
}
