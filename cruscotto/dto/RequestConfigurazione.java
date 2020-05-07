package it.cyberSec.cruscotto.dto;

public class RequestConfigurazione {
	private Long id;
	private String ruolo;
	private String stato;
	private Boolean isDeterminato;
	private int mesi;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public Boolean getIsDeterminato() {
		return isDeterminato;
	}
	public void setIsDeterminato(Boolean isDeterminato) {
		this.isDeterminato = isDeterminato;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public int getMesi() {
		return mesi;
	}
	public void setMesi(int mesi) {
		this.mesi = mesi;
	}
	

}
