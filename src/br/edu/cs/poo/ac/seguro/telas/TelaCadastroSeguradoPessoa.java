package br.edu.cs.poo.ac.seguro.telas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;

public class TelaCadastroSeguradoPessoa {

    protected Shell shell;
    private Text txtCpf, txtNome, txtNascimento, txtRenda;
    private Button btnIncluirAlterar, btnCancelar, btnExcluir;

    private SeguradoPessoaMediator mediator = SeguradoPessoaMediator.getInstancia();

    public static void main(String[] args) {
        try {
            TelaCadastroSeguradoPessoa window = new TelaCadastroSeguradoPessoa();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    protected void createContents() {
        shell = new Shell();
        shell.setSize(500, 350);
        shell.setText("Cadastro Segurado Pessoa");

        Label lblCpf = new Label(shell, SWT.NONE);
        lblCpf.setBounds(20, 20, 80, 20);
        lblCpf.setText("CPF");
        txtCpf = new Text(shell, SWT.BORDER);
        txtCpf.setBounds(110, 20, 150, 25);

        Button btnNovo = new Button(shell, SWT.NONE);
        btnNovo.setBounds(280, 20, 80, 30);
        btnNovo.setText("Novo");

        Button btnBuscar = new Button(shell, SWT.NONE);
        btnBuscar.setBounds(370, 20, 80, 30);
        btnBuscar.setText("Buscar");

        Label lblNome = new Label(shell, SWT.NONE);
        lblNome.setBounds(20, 60, 80, 20);
        lblNome.setText("Nome");
        txtNome = new Text(shell, SWT.BORDER);
        txtNome.setBounds(110, 60, 300, 25);
        txtNome.setEnabled(false);

        Label lblNascimento = new Label(shell, SWT.NONE);
        lblNascimento.setBounds(20, 100, 80, 20);
        lblNascimento.setText("Nascimento");
        txtNascimento = new Text(shell, SWT.BORDER);
        txtNascimento.setBounds(110, 100, 150, 25);
        txtNascimento.setEnabled(false);

        Label lblRenda = new Label(shell, SWT.NONE);
        lblRenda.setBounds(20, 140, 80, 20);
        lblRenda.setText("Renda");
        txtRenda = new Text(shell, SWT.BORDER);
        txtRenda.setBounds(110, 140, 150, 25);
        txtRenda.setEnabled(false);

        btnIncluirAlterar = new Button(shell, SWT.NONE);
        btnIncluirAlterar.setBounds(110, 200, 90, 30);
        btnIncluirAlterar.setText("Incluir");
        btnIncluirAlterar.setEnabled(false);

        btnCancelar = new Button(shell, SWT.NONE);
        btnCancelar.setBounds(210, 200, 90, 30);
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);

        btnExcluir = new Button(shell, SWT.NONE);
        btnExcluir.setBounds(310, 200, 90, 30);
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);

        btnNovo.addListener(SWT.Selection, e -> {
            SeguradoPessoa existente = mediator.buscarSeguradoPessoa(txtCpf.getText());
            if (existente != null) {
                JOptionPane.showMessageDialog(null, "CPF já cadastrado!");
            } else {
                habilitarCampos(true);
                txtCpf.setEnabled(false);
                btnIncluirAlterar.setEnabled(true);
                btnCancelar.setEnabled(true);
            }
        });

        btnBuscar.addListener(SWT.Selection, e -> {
            SeguradoPessoa seg = mediator.buscarSeguradoPessoa(txtCpf.getText());
            if (seg == null) {
                JOptionPane.showMessageDialog(null, "Segurado não encontrado!");
            } else {
                txtNome.setText(seg.getNome());
                txtNascimento.setText(seg.getDataNascimento().toString());
                txtRenda.setText(String.valueOf(seg.getRenda()));
                habilitarCampos(true);
                txtCpf.setEnabled(false);
                btnIncluirAlterar.setText("Alterar");
                btnIncluirAlterar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnExcluir.setEnabled(true);
            }
        });

        btnIncluirAlterar.addListener(SWT.Selection, e -> {
            try {
                LocalDate nascimento = LocalDate.parse(txtNascimento.getText());
                double renda = Double.parseDouble(txtRenda.getText());
                Endereco end = new Endereco("Rua X", "00000-000", "1", "", "Brasil", "PE", "Recife");
                SeguradoPessoa seg = new SeguradoPessoa(txtNome.getText(), end, nascimento, BigDecimal.ZERO, txtCpf.getText(), renda);
                String msg;
                if (btnIncluirAlterar.getText().equals("Incluir")) {
                    msg = mediator.incluirSeguradoPessoa(seg);
                } else {
                    msg = mediator.alterarSeguradoPessoa(seg);
                }
                if (msg != null) {
                    JOptionPane.showMessageDialog(null, msg);
                } else {
                    JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!");
                    resetarTela();
                }
            } catch (DateTimeParseException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erro de formato nos campos data ou renda!");
            }
        });

        btnCancelar.addListener(SWT.Selection, e -> resetarTela());

        btnExcluir.addListener(SWT.Selection, e -> {
            String msg = mediator.excluirSeguradoPessoa(txtCpf.getText());
            if (msg != null) {
                JOptionPane.showMessageDialog(null, msg);
            } else {
                JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso!");
                resetarTela();
            }
        });
    }

    private void habilitarCampos(boolean ativo) {
        txtNome.setEnabled(ativo);
        txtNascimento.setEnabled(ativo);
        txtRenda.setEnabled(ativo);
    }

    private void resetarTela() {
        txtCpf.setText("");
        txtNome.setText("");
        txtNascimento.setText("");
        txtRenda.setText("");
        txtCpf.setEnabled(true);
        btnIncluirAlterar.setText("Incluir");
        btnIncluirAlterar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnExcluir.setEnabled(false);
        habilitarCampos(false);
    }
}
