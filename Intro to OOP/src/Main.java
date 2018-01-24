
public class Main {

	public static void main(String[] args) {
		Dog pepper = new Dog(4, 10.0, "White");
		Dog scooby_doo = new Dog( 4, 150.0, "Brown");
		
		pepper.eat(0.5);
		System.out.println(pepper.weight);
		System.out.println(scooby_doo.weight);
		scooby_doo.numberOfLegs = 3;
		System.out.println(scooby_doo.numberOfLegs);
		
		
		
		
		
	}
	
}
