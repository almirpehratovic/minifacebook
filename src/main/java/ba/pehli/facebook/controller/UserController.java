package ba.pehli.facebook.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ba.pehli.facebook.domain.Post;
import ba.pehli.facebook.domain.ProfilePicture;
import ba.pehli.facebook.domain.User;
import ba.pehli.facebook.domain.WSPost;
import ba.pehli.facebook.service.PostService;
import ba.pehli.facebook.service.UserService;

@Controller
@RequestMapping("/")
@SessionAttributes("user")			// postavi u sesiju ovaj atribut kad budeš spašavao model
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	SimpMessagingTemplate simp;	// slanje stomp - web socket objekata svim prijavljenim klijentima
	
	/**
	 * Dodaje se u model kod svakog zahtjeva a koristi u profile.jsp
	 */
	@ModelAttribute(name="countries")
	private String[] countries(Locale locale){
		return new String[]{messageSource.getMessage("user.country.bih", null, locale),
				messageSource.getMessage("user.country.ita", null, locale),
				messageSource.getMessage("user.country.usa", null, locale)};
	}
	
	
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String home(Model ui,HttpServletRequest request, 
			@ModelAttribute(binding=false) Date dateTime,
			@SessionAttribute User user,
			@RequestAttribute String interceptorMessage,
			@CookieValue("JSESSIONID") String sessionId){
		
		logger.debug("##### > home()");
		logger.debug("##### > dateTime = " + dateTime);
		logger.debug("##### > interceptorMessage = " + interceptorMessage);
		logger.debug("##### > JSESSIONID = " + sessionId);
		
		
		if (user.getId() == null){
			logger.info("##### > reading user from database and saving into session");
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = userService.findByUsername(userDetails.getUsername());
			ui.addAttribute("user", user);
		}
		
		ui.addAttribute("posts", postService.findAll());
		ui.addAttribute("post", new Post());
		return "home";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Model ui,@RequestParam(required=false) String logout,
			@RequestParam(required=false) String error,Locale locale){
		ui.addAttribute("user", new User());
		if (logout != null){
			return "logout";
		}
		if (error != null){
			ui.addAttribute("messageError",messageSource.getMessage("login.form.error", null, locale));
		}
		return "login";
	}
	
	/**
	 * Omogućava registraciju korisnika
	 * @param user
	 * @param binding
	 * @param ui
	 * @param attr
	 * @param locale
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(@Valid User user,BindingResult binding,Model ui,RedirectAttributes attr,Locale locale){
		if (binding.hasErrors()){
			logger.error("Error during registration of user " + user.getUsername());
			
			ui.addAttribute("user", user);
			return "login";
		}
		userService.save(user);
		
		// atribut se dodaje kao query parametar url-a a flash atribut kao atribut u sesiji (ne vidi se u url-u)
		attr.addAttribute("success", true);
		attr.addFlashAttribute("messageSuccess", messageSource.getMessage("login.register.success", new String[]{user.getUsername()}, locale));
		
		logger.debug("User " + user.getUsername() + " successfully registered");
		return "redirect:/login";
	}
	
	/**
	 * Omogućava spašavanje posta. Bez RedirectAttributes argumenta url-u bi u redirekciji bio dodat 
	 * svaki model atribut (u ovom slučaju dateTime)
	 * @param post
	 * @param binding
	 * @param ui
	 * @param attr 
	 * @return
	 */
	@RequestMapping(value="/post",method=RequestMethod.POST)
	public String post(@Valid Post post, BindingResult binding ,Model ui,RedirectAttributes attr, @SessionAttribute User user){
		if (binding.hasErrors()){
			logger.error("error during creating post:" + post.getText());
			for (ObjectError error : binding.getAllErrors()){
				logger.error("Error detail:  " + error);
			}
			ui.addAttribute("post", post);
			return "home";
		}
		
		logger.info("##### > spašavam post " + post.getText() + " korisnik = " + user + " / "+ user.getPictures().size());
		
		post.setDateTime(new Date());
		// nije potrebno pozvati setPoster(user) jer je forma upunila poster.id
		post = postService.save(post);
		
		// pošalji WebSocket klijentima
		simp.convertAndSend("/topic/post", new WSPost(post.getId(), user.getId(),user.getFirstName() + " " 
				+ user.getLastName(), user.getPictures().get(0).getId(), post.getDateTime(), post.getText()));
		
		return "redirect:/home";
	}
	
	@RequestMapping(value="/like",method=RequestMethod.GET)
	public String like(@RequestParam int id,@SessionAttribute User user,RedirectAttributes attr){
		Post post = postService.findById(id);
		post.addUserLikingThis(user);
		postService.save(post);
		
		return "redirect:/home";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String showEdit(Model ui,@SessionAttribute User user, HttpSession session){
		ui.addAttribute("profile", user);
		return "edit";
	}
	
	/**
	 * Ako naziv model atributa ne odgovara nazivu klase (profile != user) onda obavezno moramo
	 * koristiti @ModelAttribute anotaciju, inače form:errors ne radi!
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String edit(@ModelAttribute("profile") @Valid User profile,BindingResult binding, 
			@RequestParam(name="picture",required=false) MultipartFile picture, Model ui, 
			RedirectAttributes attr, HttpSession session, Locale locale){
		if (binding.hasErrors()){
			String error = "";
			logger.error("There are errors during profile update");
			ui.addAttribute("profile", profile);
			ui.addAttribute("messageError", messageSource.getMessage("user.error", null, locale));
			return "edit";
		}
		
		profile.setPosts(postService.findAllByPoster(profile));
		profile.setPictures(userService.findAllProfilePicturesByUser(profile));
		
		if (!picture.isEmpty()){
			ProfilePicture pp = new ProfilePicture();
			try {
				pp.setImage(picture.getBytes());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			profile.addPicture(pp);
			pp.setUser(profile);
		}
		
		profile = userService.save(profile);
		ui.addAttribute("user", profile);
		attr.addFlashAttribute("messageSuccess", messageSource.getMessage("user.success", null, locale));
		return "redirect:/edit";
	}
	
	@RequestMapping(value="/users/picture/{id}")
	@ResponseBody
	public byte[] showPicture(@PathVariable int id){
		ProfilePicture picture = userService.findProfilePictureById(id);
		return picture.getImage();
	}
	
	/**
	 * Jasper izvještaj je u Springu obični view, što znači da kontrolerski kod izgleda isto kao
	 * i za JSP. Ako jasper view (bean) konfigurišemo koji naziv data source atribut mora imati,
	 * onda ovdje moramo poštovati tu konvenciju (vidjeti mvc-config.xml) 
	 * @param ui
	 * @param locale
	 * @return
	 */
	@RequestMapping(value="/report", method=RequestMethod.GET)
	public String report(Model ui,Locale locale){
		ui.addAttribute("title", messageSource.getMessage("post.report.title", null, locale));
		ui.addAttribute("colDateTime", messageSource.getMessage("post.dateTime", null, locale));
		ui.addAttribute("colText", messageSource.getMessage("post.postText", null, locale));
		ui.addAttribute("like", messageSource.getMessage("post.like2", null, locale));
		ui.addAttribute("dataSource", postService.findAll());
		// ui.addAttribute("subReportDataSource", postService.findAll());
		return "postReport";
	}
	
	/**
	 * Za duge taskove treba vratiti Callable; ovo garantuje izvršavanje kontrolerskog 
	 * taska na drugom threadu, a DispatcherServlet ostaje dostupan ostalim zahtjevima
	 * Napomena: svi filteri i servleti moraju biti ispravno kofigurisani: async-supported!
	 * @param ui
	 * @return
	 */
	@RequestMapping(value="/report2", method=RequestMethod.GET)
	public Callable<String> report2(final Model ui,final Locale locale){
		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				ui.addAttribute("title", messageSource.getMessage("post.report.title", null, locale));
				ui.addAttribute("colDateTime", messageSource.getMessage("post.dateTime", null, locale));
				ui.addAttribute("colText", messageSource.getMessage("post.text", null, locale));
				ui.addAttribute("like", messageSource.getMessage("post.like2", null, locale));
				ui.addAttribute("dataSource", postService.findAll());
				// ui.addAttribute("subReportDataSource", postService.findAll());
				return "postReport";
			}
		};
		
	}
	
	/**
	 * Streaming sadržaja: preporučuje se za velike sadržaje (npr. video)
	 * @return
	 */
	@RequestMapping("/stream")
	public StreamingResponseBody stream(){
		return new StreamingResponseBody() {
			
			@Override
			public void writeTo(OutputStream outputStream) throws IOException {
				InputStream is = new FileInputStream(new File("/home/almir/Downloads/youtoube/adele.mp4"));
				
				byte[] buffer = new byte[2048];
				int read = 0;
				while ((read = is.read(buffer)) > 0){
					outputStream.write(buffer,0,read);
				}
				outputStream.flush();
			}
		};
	}
	
	@RequestMapping("/bug")
	public String generateException(){
		int i = 0;
		int j = 25 / i;
		return "neverCalled";
	}
	
}
