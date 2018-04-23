package br.org.pmc.model.entity.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.pmc.model.entity.IEntity;


@Entity
@Table(name="folha_requerimento")
public class FolhaRequerimento implements IEntity {
	
	private Long id;
	private Endereco endereco;
	private String numeroProtocolo;
	private Date dataNumeroProtocolo;
	private String nomeProprientario;
	private String cpf;
	private String cnpj;
	private String telefoneProprietario;
	private Requerente requerente;
	private Servico servico;
	private Float valorTaxaTotal;
	private String complementoObservacao;
	private Date dataRequerimento;
	private Boolean status;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	@Embedded
	public Endereco getEndereco() {
		return endereco;
	}
	@OneToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="id_requerente")
	public Requerente getRequerente() {
		return requerente;
	}
	public String getNomeProprientario() {
		return nomeProprientario;
	}
	public String getCpf() {
		return cpf;
	}
	
	public String getCnpj() {
		return cnpj;
	}
	public String getTelefoneProprietario() {
		return telefoneProprietario;
	}
	@OneToOne(fetch= FetchType.EAGER)
	@JoinColumn(name="id_servico")
	public Servico getServico() {
		return servico;
	}
//	@Column(name="status_processo")
//	@Enumerated(EnumType.STRING)
//	public EnumSituacaoStatus getStatusFolhaRequerimento() {
//		return statusFolhaRequerimento;
//	}
	public Float getValorTaxaTotal() {
		return valorTaxaTotal;
	}
	@Lob
	public String getComplementoObservacao() {
		return complementoObservacao;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column( columnDefinition ="DATETIME")
	public Date getDataReqeurimento() {
		return dataRequerimento;
	}
	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column( columnDefinition ="DATETIME")
	public Date getDataNumeroProtocolo() {
		return dataNumeroProtocolo;
	}
	public Boolean getStatus() {
		return status;
	}
	
//	public void setStatusFolhaRequerimento(EnumSituacaoStatus statusFolhaRequerimento) {
//		this.statusFolhaRequerimento = statusFolhaRequerimento;
//	}
	public void setRequerente(Requerente requerente) {
		this.requerente = requerente;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public void setNomeProprientario(String nomeProprientario) {
		this.nomeProprientario = nomeProprientario;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public void setTelefoneProprietario(String telefoneProprietario) {
		this.telefoneProprietario = telefoneProprietario;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public void setValorTaxaTotal(Float valorTaxaTotal) {
		this.valorTaxaTotal = valorTaxaTotal;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public void setComplementoObservacao(String complementoObservacao) {
		this.complementoObservacao = complementoObservacao;
	}
	public void setDataReqeurimento(Date dataReqeurimento) {
		this.dataRequerimento = dataReqeurimento;
	}
	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}
	public void setDataNumeroProtocolo(Date dataNumeroProtocolo) {
		this.dataNumeroProtocolo = dataNumeroProtocolo;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Override
	public boolean validateEmptyFields() {
		if(getNomeProprientario() ==null || getNomeProprientario().trim().equals("")){
			return false;
		}else if( getEndereco() ==null){
			return false;
		}else if (getDataNumeroProtocolo() == null){
			return false;
		}else if (getTelefoneProprietario()==null || getTelefoneProprietario().trim().equals("")){
			return false;
		}else{
			return true;
		}
	}
	

}
