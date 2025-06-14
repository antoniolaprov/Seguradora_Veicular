package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.excecoes.ExcecaoValidacaoDados;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.util.ComparadorSinistroSequencial;

public class SinistroMediator {

	private VeiculoDAO daoVeiculo = new VeiculoDAO();
	private ApoliceDAO daoApolice = new ApoliceDAO();
	private SinistroDAO daoSinistro = new SinistroDAO();
	private static SinistroMediator instancia;

	public static SinistroMediator getInstancia() {
		if (instancia == null)
			instancia = new SinistroMediator();
		return instancia;
	}

	private SinistroMediator() {

	}

	public String incluirSinistro(DadosSinistro dados, LocalDateTime dataHoraAtual) throws ExcecaoValidacaoDados {
		ExcecaoValidacaoDados excecao = new ExcecaoValidacaoDados();

		if (dados == null) {
			excecao.adicionarMensagem("Dados do sinistro devem ser informados");
			throw excecao;
		}

		if (dados.getDataHoraSinistro() == null) {
			excecao.adicionarMensagem("Data/hora do sinistro deve ser informada");
		} else if (dados.getDataHoraSinistro().isAfter(dataHoraAtual)) {
			excecao.adicionarMensagem("Data/hora do sinistro deve ser menor que a data/hora atual");
		}

		if (dados.getPlaca() == null || dados.getPlaca().trim().isEmpty()) {
			excecao.adicionarMensagem("Placa do Veiculo deve ser informada");
		} else if (daoVeiculo.buscar(dados.getPlaca()) == null) {
			excecao.adicionarMensagem("Veiculo não cadastrado");
		}

		if (dados.getUsuarioRegistro() == null || dados.getUsuarioRegistro().trim().isEmpty()) {
			excecao.adicionarMensagem("Usuario do registro de sinistro deve ser informado");
		}

		if (dados.getValorSinistro() <= 0) {
			excecao.adicionarMensagem("Valor do sinistro deve ser maior que zero");
		}

		TipoSinistro tipoSinistro = TipoSinistro.getTipoSinistro(dados.getCodigoTipoSinistro());
		if (tipoSinistro == null) {
			excecao.adicionarMensagem("Codigo do tipo de sinistro invalido");
		}

		if (excecao.possuiErros()) throw excecao;

		var veiculo = daoVeiculo.buscar(dados.getPlaca());

		List<Object> todasApolicesObj = List.of(daoApolice.buscarTodos());
		List<Apolice> todasApolices = new ArrayList<>();
		for (Object obj : todasApolicesObj) {
			if (obj instanceof Apolice) {
				todasApolices.add((Apolice) obj);
			}
		}

		Apolice apoliceCobrindo = null;
		for (Apolice ap : todasApolices) {
			boolean placaIgual = ap.getVeiculo().getPlaca().equalsIgnoreCase(dados.getPlaca());
			LocalDate dataSinistro = dados.getDataHoraSinistro().toLocalDate();
			boolean dentroVigencia = !ap.getDataInicioVigencia().isAfter(dataSinistro) &&
					ap.getDataInicioVigencia().plusYears(1).isAfter(dataSinistro);

			if (placaIgual && dentroVigencia) {
				apoliceCobrindo = ap;
				break;
			}
		}

		if (apoliceCobrindo == null) {
			excecao.adicionarMensagem("Nao existe apolice vigente para o veiculo");
			throw excecao;
		}

		BigDecimal valorSinistro = BigDecimal.valueOf(dados.getValorSinistro());
		if (valorSinistro.compareTo(apoliceCobrindo.getValorMaximoSegurado()) > 0) {
			excecao.adicionarMensagem("Valor do sinistro nao pode ultrapassar o valor maximo segurado constante na apolice");
			throw excecao;
		}

		List<Sinistro> sinistros = daoSinistro.buscarPorNumeroApolice(apoliceCobrindo.getNumero());
		int sequencial = 1;
		if (!sinistros.isEmpty()) {
			sinistros.sort(new ComparadorSinistroSequencial());
			sequencial = sinistros.get(sinistros.size() - 1).getSequencial() + 1;
		}

		String numeroSinistro = "S" + apoliceCobrindo.getNumero() + String.format("%03d", sequencial);

		// 🔧 Normalização dos LocalDateTime (sem nanos)
		LocalDateTime dataRegistro = dataHoraAtual.withNano(0);
		LocalDateTime dataOcorrencia = dados.getDataHoraSinistro().withNano(0);

		Sinistro sinistro = new Sinistro(
				numeroSinistro,
				veiculo,
				dataOcorrencia,
				dataRegistro,
				dados.getUsuarioRegistro(),
				BigDecimal.valueOf(dados.getValorSinistro()),
				tipoSinistro
		);
		sinistro.setNumeroApolice(apoliceCobrindo.getNumero());
		sinistro.setSequencial(sequencial);

		daoSinistro.incluirSinistro(sinistro);
		return numeroSinistro;
	}

}
