package br.edu.cs.poo.ac.seguro.entidades;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Sinistro implements Registro {
    private static final long serialVersionUID = 1L;

    private String numero;
    private Veiculo veiculo;
    private LocalDateTime dataHoraSinistro;
    private LocalDateTime dataHoraRegistro;
    private String usuarioRegistro;
    private BigDecimal valorSinistro;
    private TipoSinistro tipo;

    private int sequencial;
    private String numeroApolice;

    @Override
    public String getIdUnico() {
        return getNumero();
    }

    public int getSequencial() {
        return sequencial;
    }

    public void setSequencial(int sequencial) {
        this.sequencial = sequencial;
    }

    public Sinistro(String numero, Veiculo veiculo, LocalDateTime dataHoraSinistro, LocalDateTime dataHoraRegistro, String usuarioRegistro, BigDecimal valorSinistro, TipoSinistro tipo) {
        this.numero = numero;
        this.veiculo = veiculo;
        this.dataHoraSinistro = (dataHoraSinistro != null) ? dataHoraSinistro.withNano(0) : null;
        this.dataHoraRegistro = (dataHoraRegistro != null) ? dataHoraRegistro.withNano(0) : null;
        this.usuarioRegistro = usuarioRegistro;
        this.valorSinistro = (valorSinistro != null) ? valorSinistro.setScale(2) : null;
        this.tipo = tipo;
    }

    public String getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }
}
