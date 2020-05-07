package it.cyberSec.cruscotto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.cyberSec.cruscotto.model.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, Long> {
	public DBFile findByFileName(String fileName);
	Boolean existsByFileName(String FileName);
}
