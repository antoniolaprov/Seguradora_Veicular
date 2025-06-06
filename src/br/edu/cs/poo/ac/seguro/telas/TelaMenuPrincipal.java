package br.edu.cs.poo.ac.seguro.telas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TelaMenuPrincipal {

    protected Shell shell;

    public static void main(String[] args) {
        TelaMenuPrincipal window = new TelaMenuPrincipal();
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
        shell.setSize(400, 300);
        shell.setText("Menu Principal");

        Button btnPessoa = new Button(shell, SWT.PUSH);
        btnPessoa.setBounds(120, 30, 160, 30);
        btnPessoa.setText("Segurado Pessoa");
        btnPessoa.addListener(SWT.Selection, e -> TelaCadastroSeguradoPessoa.main(null));

        Button btnEmpresa = new Button(shell, SWT.PUSH);
        btnEmpresa.setBounds(120, 80, 160, 30);
        btnEmpresa.setText("Segurado Empresa");
        btnEmpresa.addListener(SWT.Selection, e -> TelaCadastroSeguradoEmpresa.main(null));

        Button btnApolice = new Button(shell, SWT.PUSH);
        btnApolice.setBounds(120, 130, 160, 30);
        btnApolice.setText("Incluir Apólice");
        btnApolice.addListener(SWT.Selection, e -> TelaInclusaoApolice.main(null));

        Button btnSinistro = new Button(shell, SWT.PUSH);
        btnSinistro.setBounds(120, 180, 160, 30);
        btnSinistro.setText("Incluir Sinistro");
        btnSinistro.addListener(SWT.Selection, e -> TelaInclusaoSinistro.main(null));
    }
}
