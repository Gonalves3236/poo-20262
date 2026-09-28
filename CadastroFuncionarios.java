
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import java.nio.charset.Charset;

import java.util.ArrayList;

public class CadastroFuncionarios {

    private ArrayList<Funcionario> lista;

    private static CadastroFuncionarios cad = null;

    private CadastroFuncionarios() {
        lista = new ArrayList<>();
    }

    public static CadastroFuncionarios getInstance() {
        if (cad == null) {
            cad = new CadastroFuncionarios();
        }
        return cad;
    }

    public void cleanAll() { lista = new ArrayList<>(); }

    public boolean add(Funcionario f) {
        return lista.add(f);
    }

    public boolean saveFile(String nomeArq) {
        Path path1 = Paths.get(nomeArq);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path1, Charset.forName("utf8")))) {
           // writer.println("nome;");
            for(Funcionario f: lista)
              //  writer.format("%s",
              //          f.getNome());
                writer.format("%d;%s;%.2f%n", f.getCodigo(),
                        f.getNome(), f.getSalarioBruto());

        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
        return true;
    }

    public boolean readFile(String nomeArq) {
        Path path1 = Paths.get(nomeArq);
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("utf8"))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] dados = line.split(";");
                //line <- "713;Donald;11000,0
                // line.split(";" ->"[ "713", "Donald", "11000,0"]
                int cod = Integer.parseInt(dados[0]);
                double sal = Double.parseDouble(dados[2].replace(",","."));
                Funcionario f = new Funcionario(cod,dados[1],sal);
                cad.add(f);
            }
        }
        catch (IOException x) {
            System.err.format("Erro de E/S: %s%n", x);
        }
        return true;
    }

    @Override
    public String toString() {
        return "CadastroFuncionarios{" + "lista=" + lista + '}';
    }

    public String relatorio() {
        StringBuilder rel = new StringBuilder("");
        for (Funcionario f : lista) {
            rel.append(f.toString());
            rel.append("\n");
        }
        return rel.toString();
    }


    public void writeJson(String filename) {
        Gson gson = new Gson();
        // converte objetos Java para JSON e retorna JSON como String
        String json = gson.toJson(lista);

        try {
            //Escreve Json convertido em arquivo chamado "file.json"
            FileWriter writer = new FileWriter(filename);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readJson(String filename) {
        Gson gson = new Gson();

        // GSON tem um problema com desserialização de genéricos
        // ver: https://sites.google.com/site/gson/gson-user-guide#TOC-Collections-Examples
        Type funcType = new TypeToken<ArrayList<Funcionario>>() {}.getType();

        try {

            BufferedReader br = new BufferedReader(new FileReader(filename));

            //Converte String JSON para objeto Java
             lista = gson.fromJson(br, funcType);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeBin(String filename) {
        Path caminho = Paths.get(filename);
        try (ObjectOutputStream arq = new ObjectOutputStream(Files.newOutputStream(caminho)))
        {
            arq.writeObject(lista);
        }
        catch(IOException e)
        {
            System.out.println("erro (writeBin): "+ e.getMessage());
            System.exit(1);
        }
    }

    public void readBin(String filename) {
        Path caminho = Paths.get(filename);
        try (ObjectInputStream arq = new ObjectInputStream(Files.newInputStream(caminho))) {
            lista = (ArrayList<Funcionario>) arq.readObject();
        }
        catch(ClassNotFoundException e) {
            System.out.println("Erro (SerialBin): ArrayList<Funcionario> não encontrada!");
            System.exit(1);
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
