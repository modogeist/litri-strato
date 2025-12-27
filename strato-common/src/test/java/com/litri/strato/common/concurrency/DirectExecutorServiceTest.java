package com.litri.strato.common.concurrency;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;

public class DirectExecutorServiceTest {

	private DirectExecutorService executor = new DirectExecutorService();

	@Test
	public void isShutdown_ShutdownCalled_Success() {
		// Setup
		DirectExecutorService e1 = new DirectExecutorService();
		DirectExecutorService e2 = new DirectExecutorService();
		
		// Execute
		e1.shutdown();
		e2.shutdownNow();
		
		// Assert
		Assert.assertTrue(e1.isShutdown());
		Assert.assertTrue(e2.isShutdown());
	}
	
	@Test
	public void isTerminated_TerminateCalled_Success() throws Exception {
		// Setup
		DirectExecutorService e1 = new DirectExecutorService();
		
		// Execute
		e1.shutdown();
		e1.awaitTermination(0, TimeUnit.SECONDS);
		
		// Assert
		Assert.assertTrue(e1.isTerminated());
	}
	
	@Test
	public void execute_UpdateStats_ReturnDone() {
		// Setup
		Result result = new Result("start");

		// Execute
		this.executor.execute(() -> result.status = "done");

		// Assert
		Assert.assertEquals("done", result.status);
	}

	@Test
	public void submit_UpdateStats_ReturnDone() throws Exception {
		// Setup
		Result result = new Result("start");

		// Execute
		Future<Result> actualResult = this.executor.submit(() -> {
			result.status = "done";
			return result;
		});

		// Assert
		Assert.assertEquals("done", actualResult.get().status);
	}

	private static class Result {
		String status;

		Result(String status) {
			this.status = status;
		}
	}

}
