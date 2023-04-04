package br.com.diegomarques.meusgastos.domains;

import java.time.LocalDate;
import java.util.List;

import br.com.diegomarques.meusgastos.domains.enums.ETipoTitulo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Titulo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
    private String descricao;

    @ManyToMany
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private ETipoTitulo tipo;
    
    @ManyToMany
    @JoinTable(
        name = "titulo_centrodecusto",
        joinColumns = @JoinColumn(name = "titulo_id"),
        inverseJoinColumns = @JoinColumn(name = "centrodecusto_id")
    )
    private List<CentroDeCusto> centrosDeCustos;

    @Column(nullable = false)
    private Double valor;

    private LocalDate dataCadastro;

    private LocalDate dataReferencia;

    private LocalDate dataVencimento;

    private LocalDate dataPagamento;

    @Column(columnDefinition = "TEXT")
    private String observacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ETipoTitulo getTipo() {
		return tipo;
	}

	public void setTipo(ETipoTitulo tipo) {
		this.tipo = tipo;
	}

	public List<CentroDeCusto> getCentrosDeCustos() {
		return centrosDeCustos;
	}

	public void setCentrosDeCustos(List<CentroDeCusto> centrosDeCustos) {
		this.centrosDeCustos = centrosDeCustos;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDate getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(LocalDate dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
    
    
	
}
