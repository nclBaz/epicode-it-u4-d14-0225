package riccardogulin.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_animale")
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
public abstract class Animal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;
	protected String name;
	protected int age;

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
