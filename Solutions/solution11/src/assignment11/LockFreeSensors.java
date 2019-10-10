package assignment11;

import java.util.concurrent.atomic.AtomicReference;

class LockFreeSensors implements Sensors {
    
	AtomicReference<SensorData> data;
    
    LockFreeSensors()
    {
    	 data = new AtomicReference<SensorData>();
    }

    // store data and timestamp
    // if and only if data stored previously is older (lower timestamp)
    public void update(long timestamp, double[] val)
    {
        SensorData old_data;
        SensorData new_data = new SensorData(timestamp, val);
        do {
            old_data = data.get();
            if (old_data != null && old_data.getTimestamp() >= new_data.getTimestamp()) {
                return; // could not update, other thread was faster
            }
        } while (!data.compareAndSet(old_data, new_data));

    }

    // pre: val != null
    // pre: val.length matches length of data written via update
    // if no data has been written previously, return 0
    // otherwise return current timestamp and fill current data to array passed as val
    public long get(double val[])
    {
		SensorData d = data.get();
		double[] v = d.getValues();
		if (v == null) return 0;
		for (int i=0; i<v.length; ++i)
			val[i] = v[i];
		return d.getTimestamp();
    }

}
