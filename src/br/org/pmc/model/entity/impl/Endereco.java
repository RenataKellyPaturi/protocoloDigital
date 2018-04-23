package br.org.pmc.model.entity.impl;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.org.pmc.model.entity.IEntity;


@Embeddable
public class Endereco implements IEntity {

	private String rua;
	private String bairro;
	private String cep;
	private String numero;
	private String quadra;
	private String lotes;
	private String loteamento;
	private String complemento;

	public Endereco() {

	}

	@Column(nullable = false)
	public String getRua() {
		return rua;
	}

	@Column(nullable = false)
	public String getBairro() {
		return bairro;
	}

	public String getCep() {
		return cep;
	}

	public String getNumero() {
		return numero;
	}

	public String getQuadra() {
		return quadra;
	}

	public String getLotes() {
		return lotes;
	}

	public String getLoteamento() {
		return loteamento;
	}

	public String getComplemento() {
		return complemento;
	}

	

	public void setRua(String rua) {
		this.rua = rua;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setQuadra(String quadra) {
		this.quadra = quadra;
	}

	public void setLotes(String lotes) {
		this.lotes = lotes;
	}

	public void setLoteamento(String loteamento) {
		this.loteamento = loteamento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	
	@Override
	public String toString() {
		return "Endereco [rua=" + rua + ", bairro=" + bairro + ", cep=" + cep + ", numero=" + numero
				+ ", quadra=" + quadra + ", lotes=" + lotes + ", loteamento=" + loteamento + ", complemento="
				+ complemento + "]";
	}

	@Override
	public boolean validateEmptyFields() {
		if (getRua() == null || getRua().trim().equals("")) {
			return false;
		} else if (getBairro() == null || getBairro().trim().equals("")) {
			return false;
		} else if (getCep() == null || getCep().trim().equals("")) {
			return false;
		} else {
			return true;
		}

	}

}
