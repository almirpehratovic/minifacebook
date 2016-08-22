package ba.pehli.facebook.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Zajednički kod za sve kontrolere
 * @author almir
 *
 */
@ControllerAdvice
public class CommonController {
	private static final Logger logger = Logger.getLogger(CommonController.class);
	
	/**
	 * Postavljanje dateTime model atributa sa trenutnim datumom i vremenon. Poziva se prije
	 * pozvanog @RequestMapping handler metoda
	 */
	@ModelAttribute(name="dateTime")
	public Date dateTime(){
		logger.debug("##### > dateTime()");
		return new Date();
	}
	
	/**
	 * Metod koji će biti pozvan kada u bilo kojem handler metodu nastupi bilo koja greška
	 * (može se ograničiti postavljanjem tipova izuzetaka kao argumenata metoda ili anotacije)
	 */
	@ExceptionHandler
	public String errorHandler(Model ui,Exception e){
		ui.addAttribute("error", e);
		return "error";
	}
	
	/**
	 * Konfiguracija data binding procesa (prikupljanje @RequestParam parametara kako bi se
	 * konstruisao @ModelAttribute).
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
