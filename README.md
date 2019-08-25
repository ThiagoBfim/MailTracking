# MailTracking
API to send Mail with Tracking.

This API will store in a database the e-mails to be send,
 and will track the e-mail state.

There are 3 states of email: "PENDING","SENT","READ"

This API support a file CSV with 3 columns: addressee, message, subject 
that will be used to create the emails in the database.
To use, send a post to <b><SERVER-URL>/upload</b> with the csv file.
 

#### How use:
You should have to create a file named "prod.properties", 
who's should have the datasource and the mail private properties.

This project have to be on internet. 
And the propertie <b><i>mail.system.domainName</i></b> have to be configured,
for use the tracking mode. Otherwise, the tracking mode will not work.

#### Technology used in this project use:

* JavaMail.
* Spring Boot.
* Spring Data.
* Hibernate.
* Maven.

### Reference Documentation

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web Starter](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-developing-web-applications)
* [Java Mail Sender](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-emailDestinatario)
