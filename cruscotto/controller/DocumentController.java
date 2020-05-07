package it.cyberSec.cruscotto.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.HttpHeaders;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.cyberSec.cruscotto.model.ConfGen;
import it.cyberSec.cruscotto.model.DBFile;
import it.cyberSec.cruscotto.model.Document;
import it.cyberSec.cruscotto.model.User;
import it.cyberSec.cruscotto.repository.ConfGenRepository;
import it.cyberSec.cruscotto.repository.DBFileRepository;
import it.cyberSec.cruscotto.repository.DocumentRepository;
import it.cyberSec.cruscotto.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

	@Autowired
	private DBFileRepository DBFileRepository;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private ConfGenRepository confgenRepo;

	@Autowired
	ConfGenRepository confGenRepo;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("email") String email, @RequestParam("categoria") String categoria,
			@RequestParam("descrizione") String descrizione, @RequestParam("anno") String anno,
			@RequestParam("mese") String mese, @RequestParam("file") MultipartFile file) throws Exception {
		try {

			Date dataBustaPaga = new GregorianCalendar(Integer.parseInt(anno), Integer.parseInt(mese) - 1, 10)
					.getTime();
			if (!userRepository.existsByEmail(email)) {
				return "UTENTE_INESISTENTE";
			} else {
				User user = userRepository.findByEmail(email).get();
				if (categoria.equals("Busta Paga")) {
					if (documentRepository.existsByDataCaricamento(dataBustaPaga)) {
						for (Document d : documentRepository.findByDataCaricamento(dataBustaPaga).get()) {
							if (user.getDocumenti().contains(d)) {
								return "ESISTENTE";
							}
						}
					}
				}
				DBFile dbFile = new DBFile(file.getOriginalFilename(), file.getContentType(), file.getBytes());
				DBFileRepository.save(dbFile);
				ConfGen confGen;
				if (confGenRepo.existsByChiaveAndValore("cat_doc", categoria)) {
					confGen = confGenRepo.findByChiaveAndValore("cat_doc", categoria).orElseThrow();
					if(confGen.getDeleted()) {
						confGen.setDeleted(false);
						confGenRepo.saveAndFlush(confGen);
					}
				} else {
					confGen = new ConfGen("cat_doc", categoria, false);
					confGenRepo.save(confGen);
				}
				Document documento;
				if (categoria.equals("Busta Paga"))
					documento = new Document(confGen, descrizione, dbFile, dataBustaPaga,user);
				else
					documento = new Document(confGen, descrizione, dbFile, new Date(),user);
				documentRepository.save(documento);
				return "SUCCESSO";
			}
		} catch (Exception e) {
			return "FALLITO";
		}
//File uploaded successfully! -> filename = " + file.getOriginalFilename()
//FAIL! Maybe You had uploaded the file before or the file's size > 500KB
	}
	

	@PostMapping("/sovrascrivi")
	public String sovrascriviFile(@RequestParam("email") String email, @RequestParam("categoria") String categoria,
			@RequestParam("descrizione") String descrizione, @RequestParam("anno") String anno,
			@RequestParam("mese") String mese, @RequestParam("file") MultipartFile file) throws Exception {
		try {
			Date dataBustaPaga = new GregorianCalendar(Integer.parseInt(anno), Integer.parseInt(mese) - 1, 10)
					.getTime();
			if (!userRepository.existsByEmail(email)) {
				return "UTENTE_INESISTENTE";
			} else {
				User user = userRepository.findByEmail(email).get();
				Document newDocumento;
				if (categoria.equals("Busta Paga")) {
					if (documentRepository.existsByDataCaricamento(dataBustaPaga)) {
						for (Document d : documentRepository.findByDataCaricamento(dataBustaPaga).get()) {
							if (user.getDocumenti().contains(d)) {
								user.getDocumenti().remove(d);
								userRepository.save(user);
								documentRepository.delete(d);
								DBFileRepository.delete(d.getFile());
								DBFile dbFile = new DBFile(file.getOriginalFilename(), file.getContentType(),
										file.getBytes());
								DBFileRepository.save(dbFile);
								ConfGen confGen = confGenRepo.findByChiaveAndValore("cat_doc", categoria).orElseThrow();
								newDocumento = new Document(confGen, descrizione, dbFile, dataBustaPaga,user);
								documentRepository.save(newDocumento);
								return "SUCCESSO";
							}
						}
					}
				}
			}
			return "FALLITO";
		} catch (Exception e) {
			return "FALLITO";
		}
	}

	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
		Optional<DBFile> dbFile = DBFileRepository.findById(id);

		if (dbFile.isPresent()) {
			DBFile file = dbFile.get();
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
					.body(file.getData());
		}

		return ResponseEntity.status(404).body(null);
	}

	@GetMapping("/getall")
	public List<Document> getListDocuments() {
		return documentRepository.findAll();
	}


    @GetMapping("/getCategorie")
    public List<String> getListCategorie() {
        List<ConfGen> listCon = confGenRepo.findByChiave("cat_doc").orElseThrow();
        List<String> valori = new ArrayList<String>();
        for (ConfGen confGen : listCon) {
        	if(!confGen.getDeleted())
        		valori.add(confGen.getValore());
        }
        return valori;
    }
    
    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<String> elimanaFile(@PathVariable Long id){
		try {
			Document doc = documentRepository.findById(id).get();
			documentRepository.delete(doc);
			DBFileRepository.delete(doc.getFile());
	    	return ResponseEntity.ok("ELIMINATO");
		} catch(Exception e) {
			return ResponseEntity.ok("Error");
		}
    }
    
    @PutMapping("/eliminaCategoria")
    public ResponseEntity<String> deleteCategoria(@RequestBody String categoria){
    	try {
    		if (confGenRepo.existsByChiaveAndValore("cat_doc", categoria)) {
    			ConfGen confGen = confGenRepo.findByChiaveAndValore("cat_doc", categoria).orElseThrow();
    			confGen.setDeleted(true);
    			confGenRepo.saveAndFlush(confGen);
    	    	return ResponseEntity.ok("ELIMINATO");
    		}
    		else {
    			return ResponseEntity.ok("Error");
    		}
    	} catch(Exception e) {
			return ResponseEntity.ok("Error");
    	}
    }
}
