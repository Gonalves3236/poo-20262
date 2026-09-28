package br.pucrs.poo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

    private static final TypeToken<List<Funcionario>> LISTA_FUNCIONARIOS = new TypeToken<>() {};

    private static final Gson GSON = new GsonBuilder()
        .setPrettyPrinting()   // JSON indentado; remova se quiser o arquivo compacto
        .create();

    public void writeJson(String fileName) {
        Path arquivo = Path.of(fileName);
        try (Writer writer = Files.newBufferedWriter(arquivo, StandardCharsets.UTF_8)) {
            GSON.toJson(lista, LISTA_FUNCIONARIOS.getType(), writer);
        } catch (IOException e) {
            System.out.printf("Erro ao gravar JSON em  %s: %s %n", fileName, e.getMessage());

        }
    }

     public List<Funcionario> readJson(String fileName)  {
         Path arquivo = Path.of("funcionarios.json");
         try (Reader reader = Files.newBufferedReader(arquivo, StandardCharsets.UTF_8)) {
             lista = GSON.fromJson(reader, LISTA_FUNCIONARIOS.getType());
         } catch (IOException e) {
             System.out.printf("\nJSON inválido em %s: %s\n",fileName, e.getMessage());
         }
         return lista;
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
