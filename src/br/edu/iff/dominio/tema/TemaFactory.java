package br.edu.iff.dominio.tema;

public interface TemaFactory {

    Tema getTema(String nome); //inversão da dependencia, nosso cliente não vai depender de uma classe concreta
}