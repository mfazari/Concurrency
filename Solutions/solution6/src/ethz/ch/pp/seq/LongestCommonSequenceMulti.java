package ethz.ch.pp.seq;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class LongestCommonSequenceMulti extends RecursiveTask<Sequence> {
	
	private static final long serialVersionUID = 4179716026313995745L;
	
	private int[] input;
	private int start;
	private int length;
	
	private LongestCommonSequenceMulti(int[] input, int start, int length) {		
		this.input = input;
		this.start = start;
		this.length = length;
	}
	
	public static Sequence longestCommonSequence(int[] input) {
		ForkJoinPool fjp = new ForkJoinPool();
		long t0 = System.currentTimeMillis();
		Sequence result = fjp.invoke(new LongestCommonSequenceMulti(input, 0, input.length));
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ") FibMulti takes " + (t1 - t0) + " msec. Result: " + result.startIndex + " - " + result.endIndex);		
		return result;
	}

	@Override
	protected Sequence compute() {
		if (this.length <= 100) {
			return LongestCommonSequence.longestCommonSequence(this.input, this.start, this.length);
		} else {			
			int halfSize = (this.length) / 2;

			LongestCommonSequenceMulti firstHalf = 
					new LongestCommonSequenceMulti(this.input, this.start, halfSize);
			
			LongestCommonSequenceMulti secondHalf = 
					new LongestCommonSequenceMulti(this.input, 
							this.start + halfSize, this.length - halfSize);						
			
			firstHalf.fork();
			Sequence second = secondHalf.compute();
			Sequence first = firstHalf.join();
			
			Sequence best = (first.isGreaterThan(second)) ? first : second;
			// If the end and beginning of the partitions are the same then there could have been a sequence that spans both
			// Alternative would be to not to choose such split in the first place.
			if (this.input[this.start + halfSize - 1] != this.input[this.start + halfSize]) {				
				return best;
			} else {				
				Sequence middleSeq = LongestCommonSequence.longestCommonSequence(
						this.input, 
						this.start + Math.max(0, halfSize - best.lenght()),
						Math.min(best.lenght() * 2, this.length));
				best = (middleSeq.isGreaterThan(best)) ? middleSeq : best;
				return best;
			}
			
			
		}
	}
}
