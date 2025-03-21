# Practical work 2 : ORM | JPA | Hibernate | Spring DATA
## Part one (jpa with one entity)
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
### Configure application.property
- the `application.property` file contains a lot of important configurations for our applications, and we will set the configuration of the port of `tomcat` as well as the `h2` database, and we will enable the h2 console to see the database table rows after every operation.
```
server.port=8085
spring.datasource.url=jdbc:h2:mem:patients-db
spring.h2.console.enabled=true
```
### PatientRepository
- we know that spring data use jpa, however we will create `PatientRepository` interface, this interface will contain the crud operations that we will need in our app, and by default it contains a lot of methods like `save()` that we will use it later.
```java
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
}
```
### CRUD
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
### H2 to Mysql
- h2 is just in memory database, it is great for testing the functionality of an app but not very efficient in production, so we will migrate from h2 to mysql database, by changing the properties of h2 with mysql properties in `application.property`
```
spring.datasource.url=jdbc:mysql://localhost:3306/patients-db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
## Part two (One to many - Many to one - One to one)
### Entities
- first we are implementing `@OneToMany`, `@ManyToOne` and `@OneToOne` with four entites. `Patient`, `Medecin`, `RendezVous` and `Consultation`
![[assets/patients.png]]
- we create the entites as we did before with `jpa` and `lombok` annotations.
- additionally we are adding annotations `@OneToMany`, `@ManyToOne` and `@OneToOne` to represent the relations between the entites
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private boolean malade;
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;
}

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String specialite;
    @OneToMany(mappedBy = "medecin", fetch = FetchType.LAZY )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<RendezVous> rendezVous;
}

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class RendezVous {
    @Id
    private String id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private StatusRDV status;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Patient patient;
    @ManyToOne
    private Medecin medecin;
    @OneToOne(mappedBy = "rendezVous")
    private Consultation consultation;
}

@Data @AllArgsConstructor @NoArgsConstructor @Builder
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateConsultation;
    private String rapport;
    @OneToOne
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private RendezVous rendezVous;
}
```
- In `@OneToMany` we mention the name of the current class in the corresponding class using `mappedBy` parameters, and as an example, we have `Patient` class named as `patient` in `RendezVous` so we are writing. and basically we put mapped by in the class that will no hold the foreign key in the table.
- when setting fetch option to `FetchType.LAZY` we are preventing jpa from fetching the collection of `rendezVous` from the begenning, it will do so only when we are working with that collection.
```java
@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
private Collection<RendezVous> rendezVous;
```
- when we set the option `access` of `@JsonProperty` to `JsonProperty.Access.WRITE_ONLY` we are preventing the app from displying the corresponding class in json response, this help us to prevent `StackOverflowError`.
```java
@OneToMany(mappedBy = "medecin", fetch = FetchType.LAZY )
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private Collection<RendezVous> rendezVous;
```
### Repositories
- we will create repositories for each entity class
```java
public interface PatientRepository extends {
    public Patient findByNom(String name);
}

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    public Medecin findByNom(String nom);
}

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

}

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

}
```
### Service
- it is very not recommended to write the jdbc logic in the presentation layer, either the main or the controller as we will see later. the solution is to create a `Service` class that will hold the business logic and communicate with the `JPA` classes.
- in our case we are creating an interface that will hold the necessary methods and an implementation of the that class.
```java
public interface IHospitalService {
    Patient savePatient(Patient patient);
    Patient findPatientById(Long id);
    Patient findPatientByNom(String name);
    Medecin saveMedecin(Medecin medecin);
    Medecin findMedecinByNom(String name);
    RendezVous saveRendezVous(RendezVous rendezVous);
    RendezVous findRendezVousById(Long id);
    Consultation saveConsultation(Consultation consultation);
}
```
- in the implementation class we should use two annotations `@Service` and `@Transactional`.
- apparently one of the causes to use `@Transactional` is used for ensuring Lazy loading without an error.
- we will use the recommended way for dependencies injection which is the constructor. we will inject the repositories to use jpa classes.
```java
@Service
@Transactional
public class HospitalService implements IHospitalService {
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final RendezVousRepository rendezVousRepository;
    private final ConsultationRepository consultationRepository;

    public HospitalService(PatientRepository patientRepository,
                           MedecinRepository medecinRepository,
                           RendezVousRepository rendezVousRepository,
                           ConsultationRepository consultationRepository) {
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.consultationRepository = consultationRepository;
    }
    ...
}
```
### Controller
- for presentation we will use rest controller that will show the data in json format.
- we can create a controller by simply adding `@RestController` annotation to the controller class.
```java
@RestController
public class PatientRestController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}
```
## Part three (Many to Many)
### Entities
![[assets/users.png]]
- we have two entites as it is shown in class diagram `User` and `Role`, and there is many to many relation between them.
- we are using mapped by in one entity only because of the following:
  1. **`mappedBy` tells Hibernate that the other side owns the relationship**.
  2. **Only the owning side (Role) creates and manages the join table**.
  3. **Without `mappedBy`, Hibernate creates two separate join tables (which is incorrect for a bidirectional Many-to-Many relationship).**
- we use `ToString.Exclude` to tell lombok to exclude the following attribute from `toString()` method.
```java
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    private String userName;
    private String password;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
}

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "roles")
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String desc;
    @Column(unique = true, length = 20)
    private String roleName;
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "USERS_ROLES")
    private List<User> users = new ArrayList<>();
}
```
### Repositories
- in repository classes it is important to use `@Repository` annotation to tell the app that we have a repository class. 
```java
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String username);
}

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String name);
}
```
### Service
- Just like we did with `IHospitalService` and `HospitalService` we will do with `IUserService` and `UserService`.
```java
public interface IUserService {
    User addNewUser(User user);
    Role addNewRole(Role role);
    User findUserByUserName(String userName);
    Role findRoleByRoleName(String roleName);
    void addRoleToUser(String userName, String roleName);
    User authenticate(String username, String password);
}
```
```java
@Service
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
  ...
}
```
### Controller
- we can take a variable from the url and use it as an argument with the help of `@PathVariable` annotation.
```java
@RestController
public class UserRestController {
    @Autowired
    private UserService userService;
    @GetMapping("/users/{username}")
    public User user(@PathVariable String username) {
        return userService.findUserByUserName(username);
    }
}
```
- end








