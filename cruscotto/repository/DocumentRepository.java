package it.cyberSec.cruscotto.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cyberSec.cruscotto.model.DBFile;
import it.cyberSec.cruscotto.model.Document;
import it.cyberSec.cruscotto.model.User;

@Repository
public interface DocumentRepository  extends JpaRepository<Document, Long>{

	Optional<Document> findById(Long id);
    
    Boolean existsByDataCaricamento(Date data);
    Optional<List<Document>> findByDataCaricamento(Date data);    
    
	Boolean existsByFile(DBFile file);
	Optional<Document> findByFile(DBFile file);

}
