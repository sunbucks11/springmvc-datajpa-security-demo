package com.java.blog.web.controllers;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.java.blog.entity.User;
import com.java.blog.service.UserService;


import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

/*
@Controller
@RequestMapping("/TwoFactorAuthController")
public class TwoFactorAuthController {
	
	  public static final String TWO_FACTOR_AUTHENTICATION_SUCCESS = "TWO_FACTOR_AUTHENTICATION";
	  private static final String BASE_URL = "/admin/auth";
	  
	  

		public static boolean isResetTwoFactorAuth = false;
		public static boolean isVerificationRequired = true;

		public static boolean TWO_FACTOR_AUTHENTICATION_INT = false;

		public static GoogleAuthenticatorKey SecretKey;

		public static boolean TWO_FACTOR_VERIFIED = false;
	  
	  
		@Autowired
		private UserService userService;
	  
	
	  

		@RequestMapping(method = RequestMethod.GET)
		public ModelAndView handleGetTwoFactorAuth(HttpServletRequest request, HttpServletResponse response) throws IOException 
		{
			ModelAndView modelAndView = new ModelAndView("barcode");
			
			HttpSession session = request.getSession(true);
			SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
			String username = null;
		
			if (sci != null) {
				
						UserDetails cud = (UserDetails) sci.getAuthentication().getPrincipal();
						username = cud.getUsername();
						userService.findOne(username);	
					
			    try {	      
			           modelAndView.getModelMap( ).put( "title", "Second Step Verification Required" );

				      if ( !userService.findOne(username).getTwoFactorAuthInitialised() ) {
				        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator( );
			
				        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials( );
				        final String secret = key.getKey( );
			
				        //String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL( userService.findOne(username).getName()), dhpUser.getEmail( ), key );
			

						String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/"
								+ userService.findOne(username).getName() + "?secret=" + secret;
				        
				        modelAndView.getModelMap( ).put( "secretKey", secret );
				        modelAndView.getModelMap( ).put( "barCodeUrl", otpAuthURL );
				        modelAndView.getModelMap( ).put( "initAuth", true );
				      } else {
				        modelAndView.getModelMap( ).put( "initAuth", false );
				      }
		
			    } catch ( Exception e ) {
			      e.printStackTrace( );
			      modelAndView = redirectWithMessage( "/", "Something went wrong while authenticating user" );
			    }
			  }
			
		    return modelAndView;
		}
		
		
		
		
		// Verify method
		
		  @RequestMapping(method = RequestMethod.POST, value = "/verify")
		  public ModelAndView handleVerification( HttpServletRequest request, HttpServletResponse response, TwoFactorAuthForm twoFactorAuthForm, BindingResult bindingResult ) throws ServletException, IOException 
		  {
				  
			    Integer code = twoFactorAuthForm.getVerificationCode( );
	
			    ModelAndView modelAndView = new ModelAndView( "auth" );
			  
				HttpSession session = request.getSession(true);
				SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
			
				if (sci != null) {
			    try {
			    	
			      UserDetails dhpUser = (UserDetails) sci.getAuthentication().getPrincipal();
			      GoogleAuthenticator ga = new GoogleAuthenticator( );
			      boolean isCodeValid = ga.authorize( ((TwoFactorAuthForm) dhpUser).getSecretKey( ), code );
	
			      if ( isCodeValid ) {
			        System.out.println( "code is correct" );
			        request.getSession( true ).setAttribute( TWO_FACTOR_AUTHENTICATION_SUCCESS, true );
			        return new ModelAndView( "redirect:/admin/" );
			      } else {
			        return handleInvalidVerificationCode( request );
			      }
			    } catch ( Exception e ) {
			    System.out.println( e.getMessage( ) );
			      e.printStackTrace( );
			      modelAndView = redirectWithMessage( BASE_URL, "Something went wrong" );
			    }
			    
				}
			
		    return modelAndView;
		}
		
	
		
		  


		  @RequestMapping(method = RequestMethod.POST, value = "/init")
		  public ModelAndView handleTwoFactorAuthInitialisation( HttpServletRequest request, HttpServletResponse response,
		      TwoFactorAuthForm twoFactorAuthForm, BindingResult bindingResult ) throws ServletException, IOException 
		  {

			    System.out.println( "handleTwoFactorAuthInitialisation" );
	
			    Integer code = twoFactorAuthForm.getVerificationCode( );
			    String secretKey = twoFactorAuthForm.getSecretKey( );
	
			    ModelAndView modelAndView = new ModelAndView( "auth" );
			    
				HttpSession session = request.getSession(true);
				SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
			
				if (sci != null) {
			    
			    try {
			    UserDetails dhpUser = (UserDetails) sci.getAuthentication().getPrincipal();
			      GoogleAuthenticator ga = new GoogleAuthenticator( );
			      boolean isCodeValid = ga.authorize( secretKey, code );
	
			      System.out.println("isCodeValid :" + isCodeValid );
	
			      if ( isCodeValid ) {
			    	System.out.println( "code is correct" );
			        ((TwoFactorAuthForm) dhpUser).setSecretKey( secretKey );
			        ((TwoFactorAuthForm) dhpUser).setTwoFactorAuthInitialised( true );
			        userService.save((User) dhpUser );
			        request.getSession( true ).setAttribute( TWO_FACTOR_AUTHENTICATION_SUCCESS, true );
			        modelAndView = redirectWithMessage( "/admin/", "SUCCESS" );
			      } else {
			        return handleInvalidVerificationCode( request );
			      }
			    } catch ( Exception e ) {
			    	System.out.println( e.getMessage( ) );
			      e.printStackTrace( );
			      modelAndView = redirectWithMessage( BASE_URL, "Something Went Wrong" );
			    }
			    
				}
			
		    return modelAndView;
		  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		
		
		 // Helper Method
		private ModelAndView redirectWithMessage( String redirectUrl, String errorMessage ) {
			    ModelAndView mav = new ModelAndView( "redirect:" + redirectUrl );
			    mav.getModelMap( ).put( "message", errorMessage );
			    return mav;
			  }
		
		 
		private ModelAndView handleInvalidVerificationCode( HttpServletRequest request ) {
			    System.out.println( "incorrect code" );
			    request.getSession( true ).setAttribute( TWO_FACTOR_AUTHENTICATION_SUCCESS, false );
			    return redirectWithMessage( BASE_URL, "Verification code is invalid" );
			  }
	  
}
*/

@Controller
@RequestMapping("/springmvc-datajpa-security-demo/TwoFactorAuthController")
public class TwoFactorAuthController {

	// private static Logger log = LoggerFactory.getLogger(
	// TwoFactorAuthController.class );

	public static final String TWO_FACTOR_AUTHENTICATION_SUCCESS = "TWO_FACTOR_AUTHENTICATION";

	public static boolean isResetTwoFactorAuth = false;
	public static boolean isVerificationRequired = true;

	public static boolean TWO_FACTOR_AUTHENTICATION_INT = false;

	public static GoogleAuthenticatorKey SecretKey;

	public static boolean TWO_FACTOR_VERIFIED = false;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleGetTwoFactorAuth(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		ModelAndView modelAndView = new ModelAndView("barcode");

		HttpSession session = request.getSession(true);
		SecurityContextImpl sci = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String emailAddress = null;

		if (sci != null) {
			UserDetails cud = (UserDetails) sci.getAuthentication().getPrincipal();
			emailAddress = cud.getUsername();
			//userService.findOne(username);
			User user = userService.findUserByEmail(emailAddress);
			String username = user.getName();	
			
			//System.out.println("Current User: " + userService.findOne(username).getName());

			try {

				//if (userService.findOne(username).getTwoFactorAuthInitialised() == null && !TWO_FACTOR_AUTHENTICATION_INT) {
				if (!userService.findOne(username).getTwoFactorAuthInitialised() && !TWO_FACTOR_AUTHENTICATION_INT) {
					GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

					final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
					final String secret = key.getKey();

					String otpAuthURL = "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/"
							+ userService.findOne(username).getName() + "?secret=" + secret;

					modelAndView.getModelMap().put("secretKey", secret);
					modelAndView.getModelMap().put("barCodeUrl", otpAuthURL);
					modelAndView.getModelMap().put("initAuth", true);

					// NOT SURE WHICH ERROR THIS REPRESENT
					request.getSession().setAttribute("isError", false);

					userService.findOne(username).setSecretKey(secret);
					this.SecretKey = key;
					TWO_FACTOR_AUTHENTICATION_INT = true;
					TwoFactorAuthController.isResetTwoFactorAuth = false;
				} else {
					request.getRequestDispatcher("/verification.html").forward(request, response);

				}
			} catch (Exception e) {
				e.printStackTrace();
				modelAndView = redirectWithMessage("/", "Something went wrong while authenticating user");
			}
		}

		return modelAndView;
	}

	private ModelAndView redirectWithMessage(String redirectUrl, String errorMessage) {
		ModelAndView mav = new ModelAndView("redirect:" + redirectUrl);
		mav.getModelMap().put("message", errorMessage);
		return mav;
	}
}
