package test;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class TestCar implements Command {

	@Override
	public boolean execute(Context context) throws Exception {
		System.out.println(this.getClass().getName());
		System.out.println("get before put data=" + context.get("name"));
		return false;
	}

}
