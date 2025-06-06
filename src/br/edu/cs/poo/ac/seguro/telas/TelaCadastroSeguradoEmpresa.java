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
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;

public class TelaCadastroSeguradoEmpresa {

    protected Shell shell;
    private Text txtCnpj, txtNome, txtDataAbertura, txtFaturamento;
    private Button chkLocadora, btnIncluirAlterar, btnCancelar, btnExcluir;
    private SeguradoEmpresaMediator mediator = SeguradoEmpresaMediator.getInstancia();

    public static void main(String[] args) {
        try {
            TelaCadastroSeguradoEmpresa window = new TelaCadastroSeguradoEmpresa();
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
        shell.setSize(550, 400);
        shell.setText("Cadastro Segurado Empresa");

        Label lblCnpj = new Label(shell, SWT.NONE);
        lblCnpj.setBounds(20, 20, 100, 20);
        lblCnpj.setText("CNPJ");
        txtCnpj = new Text(shell, SWT.BORDER);
        txtCnpj.setBounds(130, 20, 150, 25);

        Button btnNovo = new Button(shell, SWT.NONE);
        btnNovo.setBounds(300, 20, 80, 30);
        btnNovo.setText("Novo");

        Button btnBuscar = new Button(shell, SWT.NONE);
        btnBuscar.setBounds(390, 20, 80, 30);
        btnBuscar.setText("Buscar");

        Label lblNome = new Label(shell, SWT.NONE);
        lblNome.setBounds(20, 60, 100, 20);
        lblNome.setText("Razão Social");
        txtNome = new Text(shell, SWT.BORDER);
        txtNome.setBounds(130, 60, 300, 25);
        txtNome.setEnabled(false);

        Label lblAbertura = new Label(shell, SWT.NONE);
        lblAbertura.setBounds(20, 100, 100, 20);
        lblAbertura.setText("Data Abertura");
        txtDataAbertura = new Text(shell, SWT.BORDER);
        txtDataAbertura.setBounds(130, 100, 150, 25);
        txtDataAbertura.setEnabled(false);

        Label lblFaturamento = new Label(shell, SWT.NONE);
        lblFaturamento.setBounds(20, 140, 100, 20);
        lblFaturamento.setText("Faturamento");
        txtFaturamento = new Text(shell, SWT.BORDER);
        txtFaturamento.setBounds(130, 140, 150, 25);
        txtFaturamento.setEnabled(false);

        chkLocadora = new Button(shell, SWT.CHECK);
        chkLocadora.setBounds(130, 180, 200, 20);
        chkLocadora.setText("É locadora de veículo");
        chkLocadora.setEnabled(false);

        btnIncluirAlterar = new Button(shell, SWT.NONE);
        btnIncluirAlterar.setBounds(110, 240, 90, 30);
        btnIncluirAlterar.setText("Incluir");
        btnIncluirAlterar.setEnabled(false);

        btnCancelar = new Button(shell, SWT.NONE);
        btnCancelar.setBounds(210, 240, 90, 30);
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);

        btnExcluir = new Button(shell, SWT.NONE);
        btnExcluir.setBounds(310, 240, 90, 30);
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);

        btnNovo.addListener(SWT.Selection, e -> {
            SeguradoEmpresa existente = mediator.buscarSeguradoEmpresa(txtCnpj.getText());
            if (existente != null) {
                JOptionPane.showMessageDialog(null, "CNPJ já cadastrado!");
            } else {
                habilitarCampos(true);
                txtCnpj.setEnabled(false);
                btnIncluirAlterar.setEnabled(true);
                btnCancelar.setEnabled(true);
            }
        });

        btnBuscar.addListener(SWT.Selection, e -> {
            SeguradoEmpresa seg = mediator.buscarSeguradoEmpresa(txtCnpj.getText());
            if (seg == null) {
                JOptionPane.showMessageDialog(null, "Empresa não encontrada!");
            } else {
                txtNome.setText(seg.getNome());
                txtDataAbertura.setText(seg.getDataAbertura().toString());
                txtFaturamento.setText(String.valueOf(seg.getFaturamento()));
                chkLocadora.setSelection(seg.getEhLocadoraDeVeiculos());
                habilitarCampos(true);
                txtCnpj.setEnabled(false);
                btnIncluirAlterar.setText("Alterar");
                btnIncluirAlterar.setEnabled(true);
                btnCancelar.setEnabled(true);
                btnExcluir.setEnabled(true);
            }
        });

        btnIncluirAlterar.addListener(SWT.Selection, e -> {
            try {
                LocalDate dataAbertura = LocalDate.parse(txtDataAbertura.getText());
                double faturamento = Double.parseDouble(txtFaturamento.getText());
                boolean locadora = chkLocadora.getSelection();
                Endereco end = new Endereco("Rua Y", "11111111", "100", "", "Brasil", "SP", "São Paulo");

                SeguradoEmpresa seg = new SeguradoEmpresa(
                        txtNome.getText(), end, dataAbertura, BigDecimal.ZERO, txtCnpj.getText(), faturamento, locadora);

                String msg;
                if (btnIncluirAlterar.getText().equals("Incluir")) {
                    msg = mediator.incluirSeguradoEmpresa(seg);
                } else {
                    msg = mediator.alterarSeguradoEmpresa(seg);
                }

                if (msg != null) {
                    JOptionPane.showMessageDialog(null, msg);
                } else {
                    JOptionPane.showMessageDialog(null, "Operação realizada com sucesso!");
                    resetarTela();
                }
            } catch (DateTimeParseException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erro de formato nos campos data ou faturamento!");
            }
        });

        btnCancelar.addListener(SWT.Selection, e -> resetarTela());

        btnExcluir.addListener(SWT.Selection, e -> {
            String msg = mediator.excluirSeguradoEmpresa(txtCnpj.getText());
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
        txtDataAbertura.setEnabled(ativo);
        txtFaturamento.setEnabled(ativo);
        chkLocadora.setEnabled(ativo);
    }

    private void resetarTela() {
        txtCnpj.setText("");
        txtNome.setText("");
        txtDataAbertura.setText("");
        txtFaturamento.setText("");
        chkLocadora.setSelection(false);
        txtCnpj.setEnabled(true);
        btnIncluirAlterar.setText("Incluir");
        btnIncluirAlterar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnExcluir.setEnabled(false);
        habilitarCampos(false);
    }
}
