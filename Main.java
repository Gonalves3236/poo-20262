
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        CadastroFuncionarios cad;

        cad = CadastroFuncionarios.getInstance();

        //cad.add(new Funcionario(100,"Zezinho",1000));

        //cad.add(new Funcionario(200,"Huginho",2000));
        //cad.add(new Funcionario(300,"Luizinho",3000));

        // modo texto...
        //cad.saveFile("funcionarios.txt");
        
        // serializando...
        //cad.writeBin("funcionarios.dat");
        //cad.writeJson("funcionarios.json");
        
        cad.cleanAll();

        //cad.readFile("funcionarios.txt");
        //cad.readBin("funcionarios.dat");
        cad.readJson("funcionarios.json");
        System.out.println("");
        System.out.println(cad.toString());
        
        System.out.println("");
        System.out.println(cad.relatorio());

    }
}
