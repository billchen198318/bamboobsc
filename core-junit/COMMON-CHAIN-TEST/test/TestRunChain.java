package test;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;

public class TestRunChain extends ChainBase {
	
	public TestRunChain() {
		super();
		this.addCommand(new GetDriverInfo());
		this.addCommand(new TestCar());
	}
	
	public static void main(String[] args) throws Exception { 
		Command process = new TestRunChain();
		Context context = new ContextBase(); 
		process.execute(context);
		System.out.println(context);		
	}

}
