package it.cyberSec.cruscotto.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "contratto")
public class Contratto implements Serializable{
	
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
	
	private Date dataCreazione;
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy="contratto", fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	private Set<ModificheContratto> modificheContratto = new HashSet<>();
	
	public Contratto() {
	
	}
	

	public Contratto(Date inizio, Date fine, Integer oreSettimanali, Date dataCreazione, User user) {
	
		
		this.inizio = inizio;
		this.fine = fine;
		this.oreSettimanali = oreSettimanali;
		this.dataCreazione = dataCreazione;
		this.user = user;
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


	public Date getDataCreazione() {
		return dataCreazione;
	}


	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Set<ModificheContratto> getModificheContratto() {
		return modificheContratto;
	}


	public void setModificheContratto(Set<ModificheContratto> modificheContratto) {
		this.modificheContratto = modificheContratto;
	}
	
	

}
