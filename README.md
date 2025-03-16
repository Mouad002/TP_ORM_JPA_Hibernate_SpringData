## Practical work 2 : ORM | JPA | Hibernate | Spring DATA
### Java bean (Patient)
- we create `Patient` java bean at first with jpa and lombok annotation to generate the getters and setter and the constructors that we will need
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private Date dateNaissance;
    private boolean malade;
    private int score;
}
```
## Configure application.property
- the `application.property` file contains a lot of important configurations for our applications, and we will set the configuration of the port of `tomcat` as well as the `h2` database, and we will enable the h2 console to see the database table rows after every operation.
```
server.port=8085
spring.datasource.url=jdbc:h2:mem:patients-db
spring.h2.console.enabled=true
```
## PatientRepository
- we know that spring data use jpa, however we will create `PatientRepository` interface, this interface will contain the crud operations that we will need in our app, and by default it contains a lot of methods like `save()` that we will use it later.
```java
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
}
```
## CRUD
- now we can create an object of `PatientRepository` and we will use it to access `crud` operations of `Patient`
```java
@Autowired
private PatientRepository patientRepository;
```
- now we can do the crud operations
  - create a patient and save it using `save()`
  - get all patients with `findAll()`
  - get specific patient with `findById()`
```java
// create and save patient
Patient p1 = new Patient(0, "1", new Date(), false, 45);
patientRepository.save(p1);

// get all the patients and display them
List<Patient> patients = patientRepository.findAll();
patients.forEach(p -> {
    System.out.println(p);
});

// get a patient by id 1 and display its information
Patient patient = patientRepository.findById(Long.valueOf(1)).get();
System.out.println("***************");
System.out.println(patient.getId());
System.out.println(patient.getType());
System.out.println(patient.getDateNaissance());
System.out.println(patient.isMalade());
System.out.println(patient.getScore());
System.out.println("***************");
```
- we can't use insert or update without @Query and @Modifying and @Transactional annotations, unlike delete that works just like find, in our example we will update only the score.
```java
@Transactional
@Modifying
@Query("update Patient p set p.score = :score where p.id = :id")
public void updatePatientScoreById(@Param("id") long id, @Param("score") double score);
```
## H2 to Mysql
- h2 is just in memory database, it is great for testing the functionality of an app but not very efficient in production, so we will migrate from h2 to mysql database, by changing the properties of h2 with mysql properties in `application.property`
```
spring.datasource.url=jdbc:mysql://localhost:3306/patients-db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
