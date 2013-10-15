package lisa.kapitonova.Lists;
import lisa.kapitonova.Lists.MyList.Element;

public class Inverter implements Visitor{

	@Override
	public void visit(Element e) {
		e.invert();
	}
 
}
