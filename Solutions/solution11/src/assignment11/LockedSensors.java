package assignment11;

class ReadersWritersLock{
	int writers;
	int writersWaiting, writersWait;
	int readersWaiting;
	int readers;
	final int Readers = 0;
	final int Writers = 1;
	final int Fair = 2;
	final int priority = Writers; 
	
	public ReadersWritersLock()
	{
		writers = 0;
		readers = 0;
		writersWaiting = 0;
		readersWaiting = 0;
		writersWait=0;
	}

	synchronized void AcquireRead()
	{
		if (priority == Fair) readersWaiting++;
		while (writers > 0 || writersWaiting > 0 && writersWait <= 0)
			try {
				wait();
			} catch (InterruptedException e) { e.printStackTrace(); }
		if (priority == Fair) {
			readersWaiting--;
			writersWait--;
		}
		readers++;
	}
	
	synchronized void ReleaseRead()
	{
		readers--;
		notifyAll();
	}
	
	synchronized void AcquireWrite()
	{
		if (priority == Fair || priority == Writers) writersWaiting++;
		while (writers > 0 || readers > 0 || writersWait > 0)
			try {
				wait();
			} catch (InterruptedException e) { e.printStackTrace(); }
		if (priority == Fair || priority == Writers) writersWaiting--;
		writers++;
	}
	
	synchronized void ReleaseWrite()
	{
		writers--;
		writersWait = readersWaiting;
		notifyAll();
	}
	
}

class LockedSensors implements Sensors {
    
	ReadersWritersLock lock;
	long time = 0;
	double data[]; 
    
	LockedSensors() {
    	time = 0;
    	data = null;
    	lock = new ReadersWritersLock();
    }

    // store data and timestamp
    // if and only if data stored previously is older (lower timestamp)
    public void update(long timestamp, double[] data)
    { 
    	lock.AcquireWrite();
    	try {
       		if (timestamp > time) {
       			if (this.data == null)
       				this.data = new double[data.length];
    			time = timestamp;
    			for (int i=0; i<data.length;++i)
    				this.data[i]= data[i];
    		}
    	}
    	finally{
    		lock.ReleaseWrite();
    	}
    }

    // pre: val != null
    // pre: val.length matches length of data written via update
    // if no data has been written previously, return 0
    // otherwise return current timestamp and fill current data to array passed as val
    public long get(double val[])
    {
    	lock.AcquireRead();
    	 try {
    		 if (time == 0)
    			 return 0;
    		 else{
    			 for (int i = 0; i<data.length; ++i)
    				 val[i] = data[i];
    			 return time;
    		 }
    	 }finally{
    		 lock.ReleaseRead();
    	 }
    }
}
