package ethz.ch.pp.searchAndCount;

import org.junit.Assert;
import org.junit.Test;

import ethz.ch.pp.searchAndCount.SearchAndCountMultiple;
import ethz.ch.pp.searchAndCount.SearchAndCountSingle;
import ethz.ch.pp.util.DatasetGenerator;
import ethz.ch.pp.util.Workload;

public class SearchAndCountTest {

	@Test
	public void test100HEAVYrandom() {

		DatasetGenerator dg = new DatasetGenerator(100);
		int[] testNumbers = dg.generate();
		int resSingle = SearchAndCountSingle.countNoAppearances(testNumbers, Workload.Type.HEAVY);
		int resMultiple = SearchAndCountMultiple.countNoAppearances(
				testNumbers, 2, Workload.Type.HEAVY, 2);
		
		Assert.assertEquals(resSingle, resMultiple);
	}

	@Test
	public void test100Lightrandom() {

		DatasetGenerator dg = new DatasetGenerator(100);
		int[] testNumbers = dg.generate();
		int resSingle = SearchAndCountSingle.countNoAppearances(testNumbers, Workload.Type.LIGHT);
		int resMultiple = SearchAndCountMultiple.countNoAppearances(
				testNumbers, 2, Workload.Type.LIGHT, 2);

		Assert.assertEquals(resSingle, resMultiple);
	}

	@Test
	public void test1000000HEAVYrandom() {

		DatasetGenerator dg = new DatasetGenerator(100000);
		int[] testNumbers = dg.generate();
		int resSingle = SearchAndCountSingle.countNoAppearances(testNumbers, Workload.Type.HEAVY);
		int resMultiple = SearchAndCountMultiple.countNoAppearances(
				testNumbers, 10000, Workload.Type.HEAVY, 4);

		Assert.assertEquals(resSingle, resMultiple);
	}

	@Test
	public void test1000000Lightrandom() {

		DatasetGenerator dg = new DatasetGenerator(1000000);
		int[] testNumbers = dg.generate();
		int resSingle = SearchAndCountSingle.countNoAppearances(testNumbers, Workload.Type.LIGHT);
		int resMultiple = SearchAndCountMultiple.countNoAppearances(
				testNumbers, 100, Workload.Type.LIGHT, 2);
		
		Assert.assertEquals(resSingle, resMultiple);
	}

}
