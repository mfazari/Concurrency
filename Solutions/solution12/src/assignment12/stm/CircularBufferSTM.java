package assignment12.stm;

import scala.concurrent.stm.Ref;
import scala.concurrent.stm.TArray;
import scala.concurrent.stm.japi.STM;

import java.util.concurrent.Callable;

/**
 * This class implements a {@link assignment7.stm.CircularBuffer} using software-transactional memory (more
 * specifically using ScalaSTM [http://nbronson.github.io/scala-stm/]).
 */
public class CircularBufferSTM<E> implements CircularBuffer<E> {
    private final Ref.View<Integer> count = STM.newRef(0);      // items in the queue
    private final Ref.View<Integer> putIndex = STM.newRef(0);   // enqueue pointer
    private final Ref.View<Integer> takeIndex = STM.newRef(0);  // dequeue pointer
    private TArray.View<E> items;                               // queue array

    CircularBufferSTM(int capacity) {
        items = STM.newTArray(capacity);
    }

    public void put(final E item) {
        STM.atomic(new Runnable() {
            @Override
            public void run() {
                if (isFull())
                    STM.retry();
                items.update(putIndex.get(), item);
                putIndex.set(next(putIndex.get()));
                STM.increment(count, 1);
            }
        });
    }

    public E take() {
        return STM.atomic(new Callable<E>() {
            @Override
            public E call() {
                if (isEmpty())
                    STM.retry();
                E item = items.refViews().apply(takeIndex.get()).get();
                items.update(takeIndex.get(), null);
                takeIndex.set(next(takeIndex.get()));
                STM.increment(count, -1);
                return item;
            }
        });
    }

    private int next(int i) {
        return (i + 1) % items.length();
    }

    public boolean isEmpty() {
        return STM.atomic(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return count.get() == 0;
            }
        });
    }

    public boolean isFull() {
        return STM.atomic(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return count.get() == items.length();
            }
        });
    }
}
