package gestaoeventos.api.rest;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {
	
	/* Interceptar erros mais comuns no projeto */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		String msg = "";
		List<ObjectError> list = ex.getBindingResult().getAllErrors();
		
		for (ObjectError objectError : list) {
			msg += objectError.getDefaultMessage() + "\n";
		}
		
		ObjetoErro objetoErro = new ObjetoErro();
		objetoErro.setError(msg);
		objetoErro.setCode(status.value() + " ==> " + HttpStatus.valueOf(status.value()).getReasonPhrase());
		
		return new ResponseEntity<>(objetoErro, headers, status);
	}
	
	
	/* Tratamento de exceções gerais */
	@ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
	public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
		
		ObjetoErro objetoErro = new ObjetoErro();
		objetoErro.setError(ex.getMessage());
		objetoErro.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + " ==> " + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		return new ResponseEntity<>(objetoErro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/* Tratamento da maioria dos erros a nivel de banco de dados */
	@ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, PSQLException.class, SQLException.class})
	protected ResponseEntity<Object> handleExceptionDataIntegry(Exception ex) {
		
		String msg = "";
		
		if (ex instanceof DataIntegrityViolationException) {
			msg = ((DataIntegrityViolationException) ex).getMostSpecificCause().getMessage();
		} else if (ex instanceof ConstraintViolationException) {
			msg = ((ConstraintViolationException) ex).getSQLException().getMessage();
		} else if (ex instanceof PSQLException) {
			msg = ((PSQLException) ex).getMessage();
		} else if (ex instanceof SQLException) {
			msg = ((SQLException) ex).getMessage();
		} else {
			msg = ex.getMessage(); /*Outras mensagens de erro*/
		}
		
		ObjetoErro objetoErro = new ObjetoErro();
		objetoErro.setError(msg);
		objetoErro.setCode(HttpStatus.INTERNAL_SERVER_ERROR + " ==> " + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		return new ResponseEntity<>(objetoErro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
