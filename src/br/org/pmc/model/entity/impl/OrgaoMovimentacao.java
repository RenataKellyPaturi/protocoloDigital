package br.org.pmc.model.entity.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.org.pmc.model.entity.IEntity;

@Entity
public class OrgaoMovimentacao implements IEntity {

	private Long id;
//	private String origemEnviada;
	private Date dataMovimentacao;
//	private FichaProcesso ficha;
//	private String observacao;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

//	public String getOrigemEnviada() {
//		return origemEnviada;
//	}
//	@ManyToOne
//	@JoinColumn(name="id_ficha")
//	public FichaProcesso getFicha() {
//		return ficha;
//	}
//
//	@Lob
//	public String getObservacao() {
//		return observacao;
//	}
//
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "DATETIME")
	public Date getDataMovimentacao() {
		return dataMovimentacao;
	}
//	
//	
//	public void setFicha(FichaProcesso ficha) {
//		this.ficha = ficha;
//	}
//
//	public void setOrigemEnviada(String origemEnviada) {
//		this.origemEnviada = origemEnviada;
//	}
//
//	public void setObservacao(String observacao) {
//		this.observacao = observacao;
//	}
//
	public void setDataMovimentacao(Date dataMovimentacao) {
		this.dataMovimentacao = dataMovimentacao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Override
	public boolean validateEmptyFields() {
//		if (getDataMovimentacao() == null) {
//			return false;
//		} else {
//			return true;
//		}
		return true;
	}
}
