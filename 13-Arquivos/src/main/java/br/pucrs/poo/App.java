package br.pucrs.poo;

public class App {

    public static void main(String[] args) {
        CadastroFuncionarios cad;

        cad = CadastroFuncionarios.getInstance();

        
        cad.add(new Funcionario(100,"Zezinho",1000));

        cad.add(new Funcionario(200,"Huginho",2000));
        cad.add(new Funcionario(300,"Luizinho",3000));

        cad.saveFile("funcionarios.txt");
        

        cad.cleanAll();
        cad.readFile("funcionarios.txt");

        System.out.println(cad.toString());

        System.out.println(cad.relatorio());

    }
}
