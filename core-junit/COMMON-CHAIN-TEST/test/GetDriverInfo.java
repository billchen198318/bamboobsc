package test;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class GetDriverInfo implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public boolean execute(Context context) throws Exception {
		System.out.println(this.getClass().getName());
		context.put("name", "Bill Chen");
		return false;
	}

}
