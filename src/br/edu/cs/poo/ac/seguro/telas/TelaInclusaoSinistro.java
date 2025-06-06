package br.edu.cs.poo.ac.seguro.telas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;
import br.edu.cs.poo.ac.seguro.mediators.DadosSinistro;
import br.edu.cs.poo.ac.seguro.mediators.SinistroMediator;

public class TelaInclusaoSinistro {

    protected Shell shell;
    private Text txtPlaca, txtDataHora, txtUsuario, txtValor;
    private Combo comboTipo;
    private SinistroMediator mediator = SinistroMediator.getInstancia();

    public static void main(String[] args) {
        TelaInclusaoSinistro window = new TelaInclusaoSinistro();
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
        shell.setText("Inclusão de Sinistro");

        Label lblPlaca = new Label(shell, SWT.NONE);
        lblPlaca.setBounds(20, 20, 100, 20);
        lblPlaca.setText("Placa");
        txtPlaca = new Text(shell, SWT.BORDER);
        txtPlaca.setBounds(130, 20, 150, 25);

        Label lblDataHora = new Label(shell, SWT.NONE);
        lblDataHora.setBounds(20, 60, 100, 20);
        lblDataHora.setText("Data e Hora");
        txtDataHora = new Text(shell, SWT.BORDER);
        txtDataHora.setBounds(130, 60, 200, 25);
        txtDataHora.setText("2025-01-01T10:00:00");

        Label lblUsuario = new Label(shell, SWT.NONE);
        lblUsuario.setBounds(20, 100, 100, 20);
        lblUsuario.setText("Usuário");
        txtUsuario = new Text(shell, SWT.BORDER);
        txtUsuario.setBounds(130, 100, 200, 25);

        Label lblValor = new Label(shell, SWT.NONE);
        lblValor.setBounds(20, 140, 150, 20);
        lblValor.setText("Valor do Sinistro");
        txtValor = new Text(shell, SWT.BORDER);
        txtValor.setBounds(170, 140, 120, 25);

        Label lblTipo = new Label(shell, SWT.NONE);
        lblTipo.setBounds(20, 180, 100, 20);
        lblTipo.setText("Tipo");
        comboTipo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        comboTipo.setBounds(130, 180, 250, 25);

        TipoSinistro[] tipos = TipoSinistro.values();
        Arrays.sort(tipos, Comparator.comparing(TipoSinistro::getNome));
        for (TipoSinistro tipo : tipos) {
            comboTipo.add(tipo.getNome());
        }
        comboTipo.select(0);

        Button btnIncluir = new Button(shell, SWT.NONE);
        btnIncluir.setBounds(130, 240, 90, 30);
        btnIncluir.setText("Incluir");

        Button btnLimpar = new Button(shell, SWT.NONE);
        btnLimpar.setBounds(240, 240, 90, 30);
        btnLimpar.setText("Limpar");

        btnIncluir.addListener(SWT.Selection, e -> {
            try {
                String placa = txtPlaca.getText().trim();
                LocalDateTime dataHora = LocalDateTime.parse(txtDataHora.getText().trim());
                String usuario = txtUsuario.getText().trim();
                double valor = Double.parseDouble(txtValor.getText().trim());
                int codigoTipo = getCodigoTipoSelecionado();

                DadosSinistro dados = new DadosSinistro(placa, dataHora, usuario, valor, codigoTipo);
                String numeroSinistro = mediator.incluirSinistro(dados, LocalDateTime.now());

                JOptionPane.showMessageDialog(null, "Sinistro incluído com sucesso! Anote o número do sinistro: " + numeroSinistro);
                limparCampos();
            } catch (ExcecaoValidacaoDados ex) {
                JOptionPane.showMessageDialog(null, String.join("\n", ex.getMensagens()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao processar os dados. Verifique os campos.");
            }
        });

        btnLimpar.addListener(SWT.Selection, e -> limparCampos());
    }

    private int getCodigoTipoSelecionado() {
        String nomeSelecionado = comboTipo.getText();
        for (TipoSinistro tipo : TipoSinistro.values()) {
            if (tipo.getNome().equals(nomeSelecionado)) {
                return tipo.getCodigo();
            }
        }
        return -1;
    }

    private void limparCampos() {
        txtPlaca.setText("");
        txtDataHora.setText("2025-01-01T10:00:00");
        txtUsuario.setText("");
        txtValor.setText("");
        comboTipo.select(0);
    }
}
