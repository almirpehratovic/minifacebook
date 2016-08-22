package ba.pehli.facebook.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ba.pehli.facebook.service.UserService;

/**
 * Interceptor koji presreće sve handling mapping zahtjeve; obzirom da presrećemeo i
 * Callback kontrolerske metode, interface kojeg implementiramo je AsyncHandlerInterceptor
 * @author almir
 *
 */
public class GlobalAttributesInterceptor implements AsyncHandlerInterceptor {
	private static final Logger logger = Logger.getLogger(GlobalAttributesInterceptor.class);
	
	@Autowired
	private UserService userService;
	
	public GlobalAttributesInterceptor() {
		logger.debug("##### > new GlobalAttributesInterceptor()");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("##### > interceptor preHandle()");
		
		request.setAttribute("interceptorMessage", "Hello from interceptor");
		return true;
	}



	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("##### > interceptor postHandle()");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("##### > interceptor afterCompletion()");
		
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("##### > interceptor afterConcurrentHandlingStarted()");
		
	}
	
}
