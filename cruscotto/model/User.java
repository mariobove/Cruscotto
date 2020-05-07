package it.cyberSec.cruscotto.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3291141645163402801L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String name;

    @NotBlank
    @Size(min=3, max = 50)
    private String cognome;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min=6, max = 100)
    private String password;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY,cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<Contratto> contratti = new HashSet<>();
    

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional=true)
    @JoinColumn(name = "stato_id")
    private StatoLogin statoLogin;
    
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY,cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<Document> documenti = new HashSet<>();
    
    

    public User() {}

    public User(String name, String cognome, String email, String password) {
        this.name = name;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	public Set<Contratto> getContratti() {
		return contratti;
	}

	public void setContratti(Set<Contratto> contratti) {
		this.contratti = contratti;
	}
	
	public Set<Document> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(Set<Document> documenti) {
		this.documenti = documenti;
	}

	public StatoLogin getStatoLogin() {
		return statoLogin;
	}

	public void setStatoLogin(StatoLogin statoLogin) {
		this.statoLogin = statoLogin;
	}
	
	public Contratto getContrattoRecente() {
		if(!contratti.isEmpty()) {
			Contratto recente = contratti.iterator().next();
				for(Contratto c : contratti)
				{
					if(c.getInizio().after(recente.getInizio()))
						recente = c;
				}
			return recente;
		}else {
			return null;
		}
			
	}
	
	public void aggiungiDocumento(Document d) {
		this.documenti.add(d);
	}
	
	
	public void aggiungiContratto(Contratto c) {
		this.contratti.add(c);
	}
	
	public void rimuoviContratto(Contratto c) {
		this.contratti.remove(c);
	}
    
}