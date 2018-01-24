
public class Dog {

	public int numberOfLegs;
	public double weight;
	public String colorFur;
	
	// Constructor
	public Dog(int n, double w, String c) {
		this.numberOfLegs = n;
		this.weight = w;
		this.colorFur = c;
	}
	
	public void eat(double food) {
		weight = weight + food;
	}
	
}
