package teste.junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import br.com.project.report.util.DateUtils;

public class TesteData {

	@Test
	public void testData() {
		
		
		try {
			
			assertEquals("09052018",DateUtils.getDateAtualReportName());
		} catch (Exception e) {
			e.printStackTrace();
		
		}
			
	}

}
