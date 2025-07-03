package riccardogulin.entities;

import jakarta.persistence.Entity;

@Entity
// @DiscriminatorValue("Gatto") // <-- OPZIONALE. Serve per personalizzare il contenuto della Discriminator Column (default nome della classe, Cat)
public class Cat extends Animal {
	private double maxJumpHeight;

	public Cat() {
	}

	public Cat(String name, int age, double maxJumpHeight) {
		super(name, age);
		this.maxJumpHeight = maxJumpHeight;
	}

	public double getMaxJumpHeight() {
		return maxJumpHeight;
	}

	public void setMaxJumpHeight(double maxJumpHeight) {
		this.maxJumpHeight = maxJumpHeight;
	}

	@Override
	public String toString() {
		return "Cat{" +
				"maxJumpHeight=" + maxJumpHeight +
				", id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				"} ";
	}
}
