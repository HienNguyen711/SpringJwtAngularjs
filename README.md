# SpringJwtAngularjs
  A simple demo using Spring Boot, Spring Data JPA, Angularjs, Jwt (Json Web Token), Satellizer, Google captcha... <br>
  
  Fork from: <br>
    Login and Registration Example Project with Spring Security: https://github.com/Baeldung/spring-security-registration <br>
    JWT Spring Security Demo: https://github.com/szerhusenBC/jwt-spring-security-demo <br>

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
  Right Click on project > Run As > Spring Boot App<br>
  Login with admin role: username: admin, password: 123456<br>
  Login with user role: username: admin, password: 123456<br>
