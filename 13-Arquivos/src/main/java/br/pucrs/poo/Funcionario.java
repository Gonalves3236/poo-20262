package br.pucrs.poo;

public class Funcionario {

    private String nome;
    private int codigo;
    private double salarioBruto;

    public Funcionario(int codigo, String nome, double
            salarioBruto) {
        this.codigo = codigo;
        this.nome = nome;
        this.salarioBruto = salarioBruto;
    }

    public String getNome() { return nome; }
    public int getCodigo() { return codigo; }
    public double getSalarioBruto() { return
            salarioBruto; }

    public void setSalarioBruto(double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }
    public double getSalarioLiquido() {
        double inss = salarioBruto * 0.1;
        double ir = 0.0;
        if (salarioBruto > 2000.0)
            ir = (salarioBruto - 2000.0) * 0.12;
        return (salarioBruto - inss - ir);
    }

    @Override
    public String toString() {
        return ( "Codigo: " + getCodigo() +
                "\t Nome: " + getNome() +
                "\t Salario Bruto: " + getSalarioBruto() +
                "\t Salario liquido: " + getSalarioLiquido());
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if ( (obj instanceof Funcionario) == false) return
                false;
        if (this.codigo == ((Funcionario)obj).codigo &&
                this.nome.equals(((Funcionario)obj).nome) )
            return true; // ...sao iguais e retorna true.
        else
            return false; // Senão, retorna false.
    }
}
