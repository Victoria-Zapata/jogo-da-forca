package br.edu.iff.jogoforca.dominio.boneco.texto;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoTexto implements Boneco {
    private static BonecoTexto soleInstance;

    private BonecoTexto() {
    }

    public static BonecoTexto getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoTexto();
        }
        return soleInstance;
    }

    @Override
    public void exibir(Object contexto, int partes) {
        System.out.print(contexto + " | ");
        System.out.print(partes);
    }
}
