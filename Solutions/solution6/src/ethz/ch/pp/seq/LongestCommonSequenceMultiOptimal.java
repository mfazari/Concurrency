package ethz.ch.pp.seq;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;



public class LongestCommonSequenceMultiOptimal extends RecursiveTask<SequenceOptimal> {
	
	private static final long serialVersionUID = 4179716026313995745L;
	
	private int[] input;
	private int start;
	private int length;
	
	private LongestCommonSequenceMultiOptimal(int[] input, int start, int length) {		
		this.input = input;
		this.start = start;
		this.length = length;
	}
	
	public static Sequence longestCommonSequence(int[] input) {
		ForkJoinPool fjp = new ForkJoinPool();
		long t0 = System.currentTimeMillis();
		SequenceOptimal result = fjp.invoke(new LongestCommonSequenceMultiOptimal(input, 0, input.length));
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ") LongestCommonSequenceMultiOptimal takes " + (t1 - t0) + " msec. Result: " + result.seq.startIndex + " - " + result.seq.endIndex);	
		return result.seq;
	}

	@Override
	protected SequenceOptimal compute() {
		if (this.length <= 100) {
			int left_endIndex = this.start;
			int right_startIndex = this.start;
			boolean left_found = false;

			Sequence result = new Sequence(this.start,this.start);		
			
			int currLength = 1;
			for (int i = this.start + 1; i < this.start + this.length; i++) {
				
				// Using Math.exp makes the computation more compute-intensive while 
				// retaining the task semantics (i.e., finding equal values)
				if (Math.exp(input[i - 1]) == Math.exp(input[i])) {
					currLength++;				 
					if(!left_found)
						left_endIndex++;
										
				} else {
					left_found=true;
					right_startIndex = i;
					if (result.lenght() < currLength) {
						result = new Sequence(i - currLength, i - 1);
					}
					currLength = 1;
				}
			}
			// In case the longest sequence ends at the last input element
			if (result.lenght() < currLength) {
				result = new Sequence(this.start + this.length - currLength, this.start + this.length - 1);
			}
			
			return new SequenceOptimal(result,this.start,left_endIndex,right_startIndex,this.start + this.length-1) ;
			
		} else {			
			int halfSize = (this.length) / 2;

			LongestCommonSequenceMultiOptimal firstHalf = 
					new LongestCommonSequenceMultiOptimal(this.input, this.start, halfSize);
			
			LongestCommonSequenceMultiOptimal secondHalf = 
					new LongestCommonSequenceMultiOptimal(this.input, 
							this.start + halfSize, this.length - halfSize);						
			
			firstHalf.fork();
			SequenceOptimal second = secondHalf.compute();
			SequenceOptimal first = firstHalf.join();
			
			SequenceOptimal Merged = SequenceOptimal.merge(first,second,this.input);
			
			return Merged;
		
			
			
		}
	}
}
