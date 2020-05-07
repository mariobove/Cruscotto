package it.cyberSec.cruscotto.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "modifiche_contratto")
public class ModificheContratto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1468362890646862540L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date inizio;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fine;
	
	private Integer oreSettimanali;
	
	private Date dataModifica;
	
	private String user;
	
	
    @ManyToOne
    @JoinColumn(name="contratto_id")
    private Contratto contratto;
	
	
	public ModificheContratto() {
	
	}
	

	public ModificheContratto(Date inizio, Date fine, Integer oreSettimanali, Date dataModifica, String user, Contratto contratto) {
	
		
		this.inizio = inizio;
		this.fine = fine;
		this.oreSettimanali = oreSettimanali;
		this.dataModifica = dataModifica;
		this.user = user;
		this.setContratto(contratto);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInizio() {
		return inizio;
	}

	public void setInizio(Date inizio) {
		this.inizio = inizio;
	}

	public Date getFine() {
		return fine;
	}

	public void setFine(Date fine) {
		this.fine = fine;
	}


	public Integer getOreSettimanali() {
		return oreSettimanali;
	}


	public void setOreSettimanali(Integer oreSettimanali) {
		this.oreSettimanali = oreSettimanali;
	}


	public Date getDataModifica() {
		return dataModifica;
	}


	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public Contratto getContratto() {
		return contratto;
	}


	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}
	
	

}
