package it.cyberSec.cruscotto.dto;

import java.util.Date;

import it.cyberSec.cruscotto.model.ConfGen;
import it.cyberSec.cruscotto.model.DBFile;

public class DescrizioneDocumento {
	private Long id;
	private String fileName;
	private String categoria;
	private String descrizione;
	private Date dataCaricamento;
	
	public DescrizioneDocumento(
			Long id,
			String categoria,
			String descrizione,
			String fileName,
			Date dataCaricamento
			) {
		this.id= id;
		this.categoria = categoria;
		this.descrizione = descrizione;
		this.fileName = fileName;
		this.dataCaricamento = dataCaricamento;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataCaricamento() {
		return dataCaricamento;
	}

	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
}
