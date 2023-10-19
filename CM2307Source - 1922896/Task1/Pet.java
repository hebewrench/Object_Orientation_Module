public class Pet implements Vegetarian {
	protected String name;
	public String classOfAnimal() {return("Pet"); }
	public void setName(String aName) { name=aName; }
	public String getName() { return name; }
	public String food() {return ("beans");}
}
