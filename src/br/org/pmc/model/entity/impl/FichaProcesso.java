package br.org.pmc.model.entity.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.pmc.model.entity.IEntity;


@Entity
@Table(name="ficha_processo")
public class FichaProcesso implements IEntity{

	private Long id;
	private Long numeroProcesso;
	private Requerente requerente;
	private Servico servico;
	private Date dataProcesso;
	private List<OrgaoMovimentacao> encaminhamentos;
	private String documentos;
	private Secretaria secretaria;
	private Boolean status;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	@OneToOne
	@JoinColumn(name="id_servico")
	public Servico getServico() {
		return servico;
	}
	
//	@OneToMany(mappedBy="ficha" ,fetch = FetchType.EAGER)
//	public List<OrgaoMovimentacao> getEncaminhamentos() {
//		return encaminhamentos;
//	}
	@OneToOne
	@JoinColumn(name="id_requerente")
	public Requerente getRequerente() {
		return requerente;
	}
	@OneToOne
	@JoinColumn(name="id_secretaria")
	public Secretaria getSecretaria() {
		return secretaria;
	}
	@Column(name = "dataProcesso", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataProcesso() {
		return dataProcesso;
	}
	
//	
//	public void setEncaminhamentos(List<OrgaoMovimentacao> encaminhamentos) {
//		this.encaminhamentos = encaminhamentos;
//	}
	public Long getNumeroProcesso() {
		return numeroProcesso;
	}
	public String getDocumentos() {
		return documentos;
	}
	
	public Boolean getStatus() {
		return status;
	}
	public void setNumeroProcesso(Long numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setRequerente(Requerente requerente) {
		this.requerente = requerente;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public void setDataProcesso(Date dataProcesso) {
		this.dataProcesso = dataProcesso;
	}
	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}

	public void setSecretaria(Secretaria secretaria) {
		this.secretaria = secretaria;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "FichaProcesso [id=" + id + ", numeroProcesso=" + numeroProcesso + ", requerente=" + requerente
				+ ", servico=" + servico + ", dataProcesso=" + dataProcesso + ", encaminhamentos=" + encaminhamentos
				+ ", documentos=" + documentos + ", secretaria=" + secretaria + ", status=" + status + "]";
	}
	@Override
	public boolean validateEmptyFields() {
		if(getDataProcesso() ==null){
			return false;
		}else if(getSecretaria()== null){
			return false;
		}else{
		return true;
		}
	}
	
	
	
}
