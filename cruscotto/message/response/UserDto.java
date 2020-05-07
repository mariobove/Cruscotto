package it.cyberSec.cruscotto.message.response;

import java.io.Serializable;

import it.cyberSec.cruscotto.model.Contratto;

public class UserDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5749628636445632581L;
	
	private Long idUser;
	private String nome;
	private String cognome;
	private String email;
	private String ruolo;
	private String stato;
	private Contratto contratto;
	
	
	
	public UserDto(Long idUser,String nome, String cognome, String email, String ruolo, String stato, Contratto c) {
	
		this.idUser = idUser;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.ruolo = ruolo;
		this.stato = stato;
		this.contratto = c;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	public Contratto getContratto() {
		return contratto;
	}
	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}
}
