package br.edu.cs.poo.ac.seguro.daos;

import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaDAO extends SeguradoDAO {

	@Override
	public Class getClasseEntidade() {
		return SeguradoPessoa.class;
	}

	public SeguradoPessoa buscar(String numero) {
		return (SeguradoPessoa) super.buscar(numero);
	}

	public boolean incluir(SeguradoPessoa segurado) {
		return super.incluir(segurado);
	}

	public boolean alterar(SeguradoPessoa segurado) {
		return super.alterar(segurado);
	}

	public boolean excluir(String numero) {
		return super.excluir(numero);
	}
}
