import java.lang.InterruptedException;
import java.lang.Override;
import java.util.Random;
import java.util.LinkedList;

public class RandomSelector implements Runnable {

    @Override
    public void run() {

        while( (Program2.evens.size()+Program2.priority.size()) > 0) {
            try {
                Program2.evenSelector.acquire();
                if((Program2.evens.size()+Program2.priority.size()) == 0) {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random random = new Random();

            LinkedList<Integer> list;
            int size = 0;
            if( Program2.priority.size() > 0 ) {
                list = Program2.priority;
            } else {
                list = Program2.evens;
            }
            int index = random.nextInt(list.size());
            Program2.selectedInt = list.get(index);
        }

    }

}