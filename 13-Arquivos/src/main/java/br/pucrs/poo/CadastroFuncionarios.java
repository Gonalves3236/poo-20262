package br.pucrs.poo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            for(Funcionario f: lista)
                writer.format("%d;%s;%.1f%n", f.getCodigo(),
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
}
