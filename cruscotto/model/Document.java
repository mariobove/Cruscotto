package it.cyberSec.cruscotto.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@Entity
@Table(name = "documents")
public class Document implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional=true)
    @JoinColumn(name = "categoria_id")
	private ConfGen categoria;
	
	private String descrizione;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dataCaricamento;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
	
	/*
	private String filename;
	
	@Lob
	private byte[] file;
	*/
	
	@OneToOne
	private DBFile file;

	public Document() {};
	
	public Document(
			ConfGen categoria,
			String descrizione,
			DBFile file,
			Date dataCaricamento,
			User user
			) {
		this.setCategoria(categoria);
		this.descrizione = descrizione;
		this.file = file;
		this.dataCaricamento = dataCaricamento;
		this.user=user;
	}
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public DBFile getFile() {
		return file;
	}

	public void setFile(DBFile file) {
		this.file = file;
	}
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}

	public ConfGen getCategoria() {
		return categoria;
	}

	public void setCategoria(ConfGen categoria) {
		this.categoria = categoria;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
