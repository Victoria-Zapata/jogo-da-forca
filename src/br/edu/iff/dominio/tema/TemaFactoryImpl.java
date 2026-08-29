package br.edu.iff.dominio.tema;

public class TemaFactoryImpl implements TemaFactory{
    private static TemaFactoryImpl soleInstance;//a variavel que ser pra gente guarde uma unica instancia do nosso tema, ele guarda e nunca mais muda

    public static void createSoleInstance (){
        
    }
        @Override
    public Tema getTema(String nome) {
       
        throw new UnsupportedOperationException("Unimplemented method 'getTema'");
    }

    
}