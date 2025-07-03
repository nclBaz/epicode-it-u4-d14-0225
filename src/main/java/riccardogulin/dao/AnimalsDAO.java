package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import riccardogulin.entities.Animal;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;
import riccardogulin.entities.Owner;
import riccardogulin.exceptions.NotFoundException;

import java.util.List;

public class AnimalsDAO {
	private final EntityManager entityManager;

	public AnimalsDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void save(Animal newAnimal) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(newAnimal);
		transaction.commit();
		System.out.println("L'animale " + newAnimal.getName() + " è stato creato correttamente!");
	}

	public Animal findById(long animalId) {
		Animal found = entityManager.find(Animal.class, animalId);
		if (found == null) throw new NotFoundException(animalId);
		return found;
	}

	public Dog findDogById(long dogId) {
		Dog found = entityManager.find(Dog.class, dogId);
		if (found == null) throw new NotFoundException(dogId);
		return found;
	}

	public Cat findCatById(long catId) {
		Cat found = entityManager.find(Cat.class, catId);
		if (found == null) throw new NotFoundException(catId);
		return found;
	}

	public List<Animal> findAll() {

		// La seguente query JPQL è equivalente a:
		// - SELECT * FROM animals nel caso della SINGLE TABLE
		// - Nel caso di una JOINED sarà come sopra ma con dei JOIN prelevando dati dalle tabelle "figlie"
		// - Nel caso delle TABLE PER CLASS fa SELECT * FROM cats, SELECT * FROM dogs e unisce tutto con un'operazione UNION

		TypedQuery<Animal> query = entityManager.createQuery("SELECT a FROM Animal a", Animal.class);
		return query.getResultList();
	}

	public List<Dog> findAllDogs() {
		TypedQuery<Dog> query = entityManager.createQuery("SELECT d FROM Dog d", Dog.class);
		return query.getResultList();
	}

	public List<Cat> findAllCats() {
		TypedQuery<Cat> query = entityManager.createQuery("SELECT c FROM Cat c", Cat.class);
		return query.getResultList();
	}

	public List<String> findAllNames() {
		TypedQuery<String> query = entityManager.createNamedQuery("findAllNames", String.class);
		return query.getResultList();
	}

	public List<Animal> findAnimalsByNameStartingWith(String partialName) {
		TypedQuery<Animal> query = entityManager.createNamedQuery("findByNameStartingWith", Animal.class);
		query.setParameter("partialName", partialName + "%");

		return query.getResultList();
	}

	public void findAnimalsByNameAndUpdateName(String oldName, String newName) {
		EntityTransaction transaction = entityManager.getTransaction(); // Siccome non stiamo facendo una semplice lettura ma una scrittura, JPA
		// vuole che utilizziamo le transazioni
		transaction.begin();

		Query query = entityManager.createQuery("UPDATE Animal a SET a.name = :newName WHERE a.name = :oldName");
		query.setParameter("oldName", oldName);
		query.setParameter("newName", newName);

		int numModificati = query.executeUpdate(); // Esegue la query UPDATE e ci torna il numero di elementi modificati

		transaction.commit();

		System.out.println(numModificati + " elementi sono stati modificati con successo!");
	}

	public void findAnimalsByNameAndDelete(String name) {
		EntityTransaction transaction = entityManager.getTransaction(); // Siccome non stiamo facendo una semplice lettura ma una scrittura, JPA
		// vuole che utilizziamo le transazioni
		transaction.begin();

		Query query = entityManager.createQuery("DELETE FROM Animal a WHERE a.name = :name");
		query.setParameter("name", name);

		int numCancellati = query.executeUpdate(); // Esegue la query DELETE e ci torna il numero di elementi cancellati

		transaction.commit();

		System.out.println(numCancellati + " elementi sono stati cancellati con successo!");
	}

	public List<Animal> findAnimalsByOwner(Owner owner) {
		TypedQuery<Animal> query = entityManager.createQuery("SELECT a FROM Animal a WHERE a.owner = :owner", Animal.class);
		query.setParameter("owner", owner);
		return query.getResultList();
	}

	public List<Animal> findAnimalsByOwnerName(String name) {
		TypedQuery<Animal> query = entityManager.createQuery("SELECT a FROM Animal a WHERE a.owner.name = :owner", Animal.class);
		query.setParameter("owner", name);
		return query.getResultList();
	}
}
