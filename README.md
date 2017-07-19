# SpringJwtAngularjs
  A simple demo using Spring Boot, Spring Data JPA, Angularjs, Jwt (Json Web Token), Satellizer, Google captcha... <br>
  
  Fork from:
   * [Login and Registration Example Project with Spring Security](https://github.com/Baeldung/spring-security-registration)
   * [JWT Spring Security Demo](https://github.com/szerhusenBC/jwt-spring-security-demo)

  // Todo: process message error resonse from backend

# Requirements: 
Maven 3, Java 1.8, Eclipse Neon 4.6.3 installed STS (Spring Tool Suite)

# Setup: 
1. run springjwtangularjs.sql<br>

2. Open file application.properties and change:<br>

        spring.datasource.username=Your username db
        spring.datasource.password=Your password db
        spring.mail.username=your google mail
        spring.mail.password=your password google mail

If you want to use recaptcha for your site, then follow step 3, step 4, Step 5<br>

3. Regist recaptcha from here: https://www.google.com/recaptcha/intro/<br>

4. Open file captcha.properties and change <br>

       google.recaptcha.site-key=Your site key (from step 3)
       google.recaptcha.secret-key=Your secret key (from step 3)
       
5. Open file app.constants.js and change<br>

      PUBLIC_RECAPTCHA_KEY : 'your puplic site key'<br>
      
# Run: 
  * Right Click on project > Run As > Spring Boot App<br>
  * Login with admin role: username: admin, password: 123456<br>
  * Login with user role: username: user, password: 123456<br>
  
# Screeenshot:  
  * Home page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/home.PNG)
  
  * Login page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/signin.PNG)
    
  * Signup page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/sign_up.PNG)
    
  * Post detail page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/post_detail.PNG)
        
  * Confirm reset password page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/confirm_reset_password.PNG)
    
  * Manage api page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/manage_api.PNG)
    
  * Manage post page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/manage_post.PNG)
      
  * Manage tag page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/manage_tag.PNG)
        
  * Manage user page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/manage_user.PNG)
    
  * Add or edit post page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/add_or_edit_post.PNG)
    
  * Add or edit user page:
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/add_ore_dit_user.PNG)  
 
    * Manage profile user page: // todo
  
    ![alt text](https://github.com/truonglehcm/SpringJwtAngularjs/blob/master/src/main/resources/static/img/profile.PNG)  
