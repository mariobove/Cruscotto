package it.cyberSec.cruscotto.security.services;

import java.util.List;

import it.cyberSec.cruscotto.dto.DescrizioneDocumento;
import it.cyberSec.cruscotto.dto.LoginForm;
import it.cyberSec.cruscotto.dto.LoginUser;
import it.cyberSec.cruscotto.dto.RequestConfigurazione;
import it.cyberSec.cruscotto.dto.RequestRegistrazione;
import it.cyberSec.cruscotto.dto.ServerResponse;
import it.cyberSec.cruscotto.message.response.UserDto;
import it.cyberSec.cruscotto.model.Contratto;
import it.cyberSec.cruscotto.model.Document;
import javassist.NotFoundException;

public interface UserService {
	
	//public LogOutResponse salvaLogout(LogOut logout);
	//public Date salvaLogin(String username, String password);
	//public List<ListaPresenze> listaLoginUser(String username, String password);
	public List<UserDto> listaLoginSuperUser(String email) throws NotFoundException, RuntimeException;
	public ServerResponse registraUtente(RequestRegistrazione req) throws NotFoundException, RuntimeException;
	public ServerResponse configuraUtente(RequestConfigurazione req) throws NotFoundException, RuntimeException;
	public List<DescrizioneDocumento> listaDocumentiUser(LoginUser loginRequest) throws NotFoundException;
	public List<Contratto> listaContrattiUser(Long id) throws NotFoundException;

}
