package it.cyberSec.cruscotto.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lavorogiornaliero")
public class LavoroGiornaliero {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name="idrapportino")
	private RapportinoMensile rapportino;
	
	@ManyToOne
	@JoinColumn(name="idcommessa")
	private Commessa commessa;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rapportino_ore", 
    			joinColumns = @JoinColumn(name = "rapportinoid"), 
    	inverseJoinColumns = @JoinColumn(name = "oreid"))
    
	private Set<OreLavorative> orelavorative = new HashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RapportinoMensile getRapportino() {
		return rapportino;
	}

	public void setRapportino(RapportinoMensile rapportino) {
		this.rapportino = rapportino;
	}

	public Commessa getCommessa() {
		return commessa;
	}

	public void setCommessa(Commessa commessa) {
		this.commessa = commessa;
	}

	public Set<OreLavorative> getOrelavorative() {
		return orelavorative;
	}

	public void setOrelavorative(Set<OreLavorative> orelavorative) {
		this.orelavorative = orelavorative;
	}	
}
