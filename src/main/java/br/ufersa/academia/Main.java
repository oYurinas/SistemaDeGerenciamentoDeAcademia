package br.ufersa.academia;

import br.ufersa.academia.database.ConexaoBanco;
import br.ufersa.academia.view.MenuPrincipal;

public class Main {

    public static void main(String[] args) {
        ConexaoBanco.getInstancia();
        new MenuPrincipal().iniciar();
    }
}
