package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.AnimalsDAO;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;

public class Application {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d14pu");

	public static void main(String[] args) {
		EntityManager em = emf.createEntityManager();
		AnimalsDAO ad = new AnimalsDAO(em);

		Cat felix = new Cat("Felix", 10, 1);
		Dog ringhio = new Dog("Ringhio", 4, 20);
		Cat tom = new Cat("Tom", 2, 2);
		Dog rex = new Dog("Rex", 5, 30);

		/* ad.save(felix);
		ad.save(ringhio);
		ad.save(tom);
		ad.save(rex); */

		/*
		Animal animalFromDb = ad.findById(1);
		System.out.println(animalFromDb);

		try {
			Cat catFromDb = ad.findCatById(1); // SELECT * FROM animals WHERE id = 2 AND tipo_animale = 'Gatto'
			System.out.println(catFromDb);
		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}
		Dog dogFromDB = ad.findDogById(2);
		System.out.println(dogFromDB);*/

		ad.findAll().forEach(System.out::println);

		ad.findAllDogs().forEach(System.out::println);

		ad.findAllNames().forEach(System.out::println);

		ad.findAnimalsByNameStartingWith("r").forEach(System.out::println);

		ad.findAnimalsByNameAndUpdateName("Rex", "Ringhio");

		ad.findAnimalsByNameAndDelete("Ringhio");

		ad.findAnimalsByOwnerName("Aldo Baglio").forEach(System.out::println);

		em.close();
		emf.close();
	}
}
