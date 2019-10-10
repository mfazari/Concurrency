package ethz.ch.pp.seq;

public class SequenceOptimal {
	public final int left_startIndex;
	public final int left_endIndex;
	
	public final Sequence  seq;
	
	public final int right_startIndex;
	public final int right_endIndex;
	
	
	public SequenceOptimal(Sequence seq, int left_startIndex, int left_endIndex, int right_startIndex, int right_endIndex) {
		this.seq = seq;
		
		this.left_startIndex = left_startIndex;
		this.left_endIndex = left_endIndex;
		
		this.right_startIndex = right_startIndex;
		this.right_endIndex = right_endIndex;
	}
	
	int lenght() {
		return seq.lenght();
	}	
	 

	@Override
	public String toString() {
		return seq.toString() +"Left [start=" + left_startIndex + ", end=" + left_endIndex + "]" + " Right [start=" + right_startIndex + ", end=" + right_endIndex + "]";
	}
	
	
	public static SequenceOptimal merge(SequenceOptimal first, SequenceOptimal second, int [] input){
		Sequence best = (first.seq.isGreaterThan(second.seq)) ? first.seq : second.seq;	
		
		int left_startIndex = first.left_startIndex;
		int left_endIndex = first.left_endIndex;
		int right_startIndex = second.right_startIndex;
		int right_endIndex = second.right_endIndex;
		
		if(input[first.right_endIndex] == input[second.left_startIndex]) {
			Sequence middle = new Sequence(first.right_startIndex,second.left_endIndex);
			best = (middle.isGreaterThan(best)) ? middle : best;
			if(first.left_startIndex == first.right_startIndex)
				left_endIndex = second.left_endIndex;
			if(second.left_startIndex == second.right_startIndex)
				right_startIndex = first.right_startIndex;
		}	
		
		return new SequenceOptimal(best,left_startIndex,left_endIndex,right_startIndex,right_endIndex);
			
	}
	
	
}