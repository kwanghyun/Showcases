package concurrency.book.sharing_object;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import scala.collection.mutable.HashSet;

/**
 * Animals
 * <p/>
 * Thread confinement of local primitive and reference variables
 *
 * @author Brian Goetz and Tim Peierls
 */
public class I08_Animals {
	Ark ark;
	Species species;
	Gender gender;

	/*
	 * 3.3.2. Stack Confinement
	 * 
	 * Stack confinement is a special case of thread confinement in which an
	 * object can only be reached through local variables. Just as encapsulation
	 * can make it easier to preserve invariants, local variables can make it
	 * easier to confine objects to a thread. Local variables are intrinsically
	 * confined to the executing thread; they exist on the executing thread's
	 * stack, which is not accessible to other threads. Stack confinement (also
	 * called within-thread or thread-local usage, but not to be confused with
	 * the ThreadLocal library class) is simpler to maintain and less fragile
	 * than ad-hoc thread confinement.
	 * 
	 * For primitively typed local variables, such as numPairs in loadTheArk in
	 * Listing 3.9, you cannot violate stack confinement even if you tried.
	 * There is no way to obtain a reference to a primitive variable, so the
	 * language semantics ensure that primitive local variables are always stack
	 * confined.
	 */
	public int loadTheArk(Collection<Animal> candidates) {
		SortedSet<Animal> animals;
		int numPairs = 0;
		Animal candidate = null;

		// animals confined to method, don't let them escape!
		animals = new TreeSet<Animal>(new SpeciesGenderComparator());
		animals.addAll(candidates);
		for (Animal a : animals) {
			if (candidate == null || !candidate.isPotentialMate(a))
				candidate = a;
			else {
				ark.load(new AnimalPair(candidate, a));
				++numPairs;
				candidate = null;
			}
		}
		return numPairs;
	}

	class Animal {
		Species species;
		Gender gender;

		public boolean isPotentialMate(Animal other) {
			return species == other.species && gender != other.gender;
		}
	}

	enum Species {
		AARDVARK, BENGAL_TIGER, CARIBOU, DINGO, ELEPHANT, FROG, GNU, HYENA, IGUANA, JAGUAR, KIWI, LEOPARD, MASTADON, NEWT, OCTOPUS, PIRANHA, QUETZAL, RHINOCEROS, SALAMANDER, THREE_TOED_SLOTH, UNICORN, VIPER, WEREWOLF, XANTHUS_HUMMINBIRD, YAK, ZEBRA
	}

	enum Gender {
		MALE, FEMALE
	}

	class AnimalPair {
		private final Animal one, two;

		public AnimalPair(Animal one, Animal two) {
			this.one = one;
			this.two = two;
		}
	}

	class SpeciesGenderComparator implements Comparator<Animal> {
		public int compare(Animal one, Animal two) {
			int speciesCompare = one.species.compareTo(two.species);
			return (speciesCompare != 0) ? speciesCompare : one.gender.compareTo(two.gender);
		}
	}

	class Ark {
		private final HashSet<AnimalPair> loadedAnimals = new HashSet<AnimalPair>();

		public void load(AnimalPair pair) {
			loadedAnimals.add(pair);
		}
	}
}
