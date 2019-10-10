package ethz.ch.pp.seq;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import ethz.ch.pp.util.DatasetGenerator;

public class SeqTest {

	  @Test
	  public void testSeqSmall() {
	    Assert.assertEquals(new Sequence(2, 3), LongestCommonSequence.longestCommonSequence(new int[]{1, 2, 3, 3, 2}));
	    Assert.assertEquals(new Sequence(0, 0), LongestCommonSequence.longestCommonSequence(new int[]{1, 2, 3, 2}));
	    Assert.assertEquals(new Sequence(0, 0), LongestCommonSequence.longestCommonSequence(new int[]{1}));
	    Assert.assertEquals(new Sequence(0, 2), LongestCommonSequence.longestCommonSequence(new int[]{1, 1, 1}));
	    Assert.assertEquals(new Sequence(0, 5), LongestCommonSequence.longestCommonSequence(new int[]{1, 1, 1, 1, 1, 1}));
	    Assert.assertEquals(new Sequence(2, 3), LongestCommonSequence.longestCommonSequence(new int[]{1, 2, 3, 3, 4, 5}));
	  }

	  @Test
	  public void testSeqRand() {
		  DatasetGenerator gen = new DatasetGenerator();
		  for (int j = 0; j < 20; j++) {
			  int[] input = gen.generate();
			  Sequence result = LongestCommonSequence.longestCommonSequence(input);

			  System.out.println("Sequence: " + result.startIndex + " - " + result.endIndex);
			  for (int i = result.startIndex; i <= result.endIndex; i++) {
				  Assert.assertEquals(input[result.startIndex], input[i]);
			  }
		  }
	  }

	  @Test
	  public void testMultiSmall() {
	    Assert.assertEquals(new Sequence(2, 3), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1, 2, 3, 3, 2}));
	    Assert.assertEquals(new Sequence(0, 0), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1, 2, 3, 2}));
	    Assert.assertEquals(new Sequence(0, 0), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1}));
	    Assert.assertEquals(new Sequence(0, 2), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1, 1, 1}));
	    Assert.assertEquals(new Sequence(0, 5), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1, 1, 1, 1, 1, 1}));
	    Assert.assertEquals(new Sequence(2, 3), LongestCommonSequenceMulti.longestCommonSequence(new int[]{1, 2, 3, 3, 4, 5}));
	  }

	  @Test
	  public void testMultiRand() {
		  DatasetGenerator gen = new DatasetGenerator();
		  for (int j = 0; j < 20; j++) {
			  int[] input = gen.generate();
			  Sequence result = LongestCommonSequenceMulti.longestCommonSequence(input);

			  System.out.println("Sequence: " + result.startIndex + " - " + result.endIndex);
			  for (int i = result.startIndex; i <= result.endIndex; i++) {
				  Assert.assertEquals(input[result.startIndex], input[i]);
			  }
		  }
	  }

	  @Test
	  public void testCompare() {
		  DatasetGenerator gen = new DatasetGenerator(20);
		  for (int j = 0; j < 1000; j++) {
			  int[] input = gen.generate();
			  Assert.assertEquals(
					  Arrays.toString(input),
					  LongestCommonSequence.longestCommonSequence(input),
					  LongestCommonSequenceMulti.longestCommonSequence(input)
					  );
		  }
	  }
}
