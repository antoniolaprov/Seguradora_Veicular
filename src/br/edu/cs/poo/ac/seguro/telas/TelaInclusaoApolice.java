package br.edu.cs.poo.ac.seguro.telas;

import java.math.BigDecimal;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.mediators.ApoliceMediator;
import br.edu.cs.poo.ac.seguro.mediators.DadosVeiculo;
import br.edu.cs.poo.ac.seguro.mediators.RetornoInclusaoApolice;

public class TelaInclusaoApolice {

    protected Shell shell;
    private Text txtCpfCnpj, txtPlaca, txtAno, txtValor;
    private Combo comboCategoria;
    private ApoliceMediator mediator = ApoliceMediator.getInstancia();

    public static void main(String[] args) {
        TelaInclusaoApolice window = new TelaInclusaoApolice();
        window.open();
    }

    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
    }

    protected void createContents() {
        shell = new Shell();
        shell.setSize(500, 400);
        shell.setText("Inclusão de Apólice");

        Label lblCpfCnpj = new Label(shell, SWT.NONE);
        lblCpfCnpj.setBounds(20, 20, 100, 20);
        lblCpfCnpj.setText("CPF/CNPJ");
        txtCpfCnpj = new Text(shell, SWT.BORDER);
        txtCpfCnpj.setBounds(130, 20, 200, 25);

        Label lblPlaca = new Label(shell, SWT.NONE);
        lblPlaca.setBounds(20, 60, 100, 20);
        lblPlaca.setText("Placa");
        txtPlaca = new Text(shell, SWT.BORDER);
        txtPlaca.setBounds(130, 60, 150, 25);

        Label lblAno = new Label(shell, SWT.NONE);
        lblAno.setBounds(20, 100, 100, 20);
        lblAno.setText("Ano");
        txtAno = new Text(shell, SWT.BORDER);
        txtAno.setBounds(130, 100, 100, 25);

        Label lblValor = new Label(shell, SWT.NONE);
        lblValor.setBounds(20, 140, 150, 20);
        lblValor.setText("Valor Máx. Segurado");
        txtValor = new Text(shell, SWT.BORDER);
        txtValor.setBounds(170, 140, 120, 25);

        Label lblCategoria = new Label(shell, SWT.NONE);
        lblCategoria.setBounds(20, 180, 100, 20);
        lblCategoria.setText("Categoria");
        comboCategoria = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        comboCategoria.setBounds(130, 180, 250, 25);

        CategoriaVeiculo[] categorias = CategoriaVeiculo.values();
        Arrays.sort(categorias, Comparator.comparing(CategoriaVeiculo::getNome));
        for (CategoriaVeiculo cat : categorias) {
            comboCategoria.add(cat.getNome());
        }
        comboCategoria.select(0);

        Button btnIncluir = new Button(shell, SWT.NONE);
        btnIncluir.setBounds(130, 240, 90, 30);
        btnIncluir.setText("Incluir");

        Button btnLimpar = new Button(shell, SWT.NONE);
        btnLimpar.setBounds(240, 240, 90, 30);
        btnLimpar.setText("Limpar");

        btnIncluir.addListener(SWT.Selection, e -> {
            try {
                String cpfCnpj = txtCpfCnpj.getText().trim();
                String placa = txtPlaca.getText().trim();
                int ano = Integer.parseInt(txtAno.getText().trim());
                BigDecimal valor = new BigDecimal(txtValor.getText().trim());
                int codigoCategoria = getCodigoCategoriaSelecionada();

                DadosVeiculo dados = new DadosVeiculo(cpfCnpj, placa, ano, valor, codigoCategoria);
                RetornoInclusaoApolice retorno = mediator.incluirApolice(dados);

                if (retorno.getMensagemErro() != null) {
                    JOptionPane.showMessageDialog(null, retorno.getMensagemErro());
                } else {
                    JOptionPane.showMessageDialog(null, "Apólice incluída com sucesso! Anote o número da apólice: " + retorno.getNumeroApolice());
                    limparCampos();
                }
            } catch (NumberFormatException | DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao converter campos numéricos.");
            }
        });

        btnLimpar.addListener(SWT.Selection, e -> limparCampos());
    }

    private int getCodigoCategoriaSelecionada() {
        String nomeSelecionado = comboCategoria.getText();
        for (CategoriaVeiculo cat : CategoriaVeiculo.values()) {
            if (cat.getNome().equals(nomeSelecionado)) {
                return cat.getCodigo();
            }
        }
        return -1;
    }

    private void limparCampos() {
        txtCpfCnpj.setText("");
        txtPlaca.setText("");
        txtAno.setText("");
        txtValor.setText("");
        comboCategoria.select(0);
    }
}
