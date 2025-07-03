package riccardogulin.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// @DiscriminatorColumn(name = "tipo_animale")
// Annotazione OPZIONALE che serve per personalizzare il nome della Discriminator Column (default si chiama DTYPE)
/*
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
SINGLE TABLE: E' la strategia più semplice perchè genera un'unica tabella contenente tutti gli animali (tutti i figli quindi Cat e Dog nel nostro caso)
Ha la comodità di avere un unico posto dove trovare tutti gli animali, quindi risulta facilmente gestibile e anche performante in quanto le informazioni
su un singolo animale sono già tutte in quella tabella, inoltre anche tutti gli animali di diverso sono lì, quindi query che coinvolgono tutti gli
animali performano bene e non ho da fare dei join per recuperare tutti i dati
Di contro però ci troveremo ad avere, soprattutto se i figli differiscono in maniera sostanziale a livello di attributi, delle tabelle che possono
diventare parecchio disordinate con tanti campi null, ciò significa anche non poter mettere vincoli NON NULL su tali colonne e quindi dover gestire
il tutto tramite codice
* */
// @Inheritance(strategy = InheritanceType.JOINED)
/*
JOINED: Strategia che ci porta ad avere tabelle sia per il padre che per i figli. Nel nostro caso avremo 3 tabelle, una per Animal con tutti gli
attributi in comune, una per Dog con gli attributi solo dei cani ed una per Cat con gli attributi solo dei gatti. Otteniamo una struttura più
"pulita" rispetto alla SINGLE TABLE in quanto non abbiamo il problema dei null (e quindi possiamo mettere i vincoli che vogliamo).
Di contro però le operazioni di lettura dei dati coinvolgeranno spesso dei JOIN (seppur hanno un costo limitato, sono sempre un'operazione in più)
Questa strategia è da preferire quando i figli hanno pochi attributi in comune e tanti diversi
*  */

/*
TABLE_PER_CLASS: La potremmo anche chiamare Table per CONCRETE Class in quanto creerà tabelle solo per le classi concrete (nel nostro caso solo per
Dog e Cat in quanto Animal è astratta). Lo schema risultante è molto pulito in quanto tutto è ben separato, non abbiamo i problemi con i null, possiamo
mettere i vincoli che vogliamo ed inoltre non servono join per recuperare tutte le info di uno specifico animale.
Di contro però ha degli svantaggi abbastanza impattanti in termini di prestazioni, in quanto se ho bisogno di query polimorfiche, ovvero quelle che
coinvolgono tutti gli animali il db deve fare del lavoro extra per unire i dati da entrambe le tabelle. Se invece ci sono query che come target hanno
i cani o i gatti in maniera separata allora no problem, anzi si comporta molto bene
* */
@NamedQuery(name = "findAllNames", query = "SELECT a.name FROM Animal a")
@NamedQuery(name = "findByNameStartingWith", query = "SELECT a FROM Animal a WHERE LOWER(a.name) LIKE LOWER(:partialName)")
public abstract class Animal {
	@Id
	@GeneratedValue
	protected long id;
	protected String name;
	protected int age;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	protected Owner owner;

	public Animal() {
	}

	public Animal(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
