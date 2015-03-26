import java.lang.InterruptedException;
import java.lang.Override;
import java.util.Random;
import java.util.LinkedList;

public class OddSelector implements Runnable {

    @Override
    public void run() {

        // We run the selector until no more odds are present
        while( (Program2.odds.size()) > 0 || Program2.oddGroup.size() > 0) {
            try {
                Program2.oddSelector.acquire();
                if(Program2.odds.size() == 0 && Program2.oddGroup.size() == 0) {
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // System call for random
            Random random = new Random();

            // Creates new odd wrench group if one is too small
            if(Program2.oddGroup.size() == 0 && Program2.odds.size() > 0) {
              LinkedList<Integer> list = new LinkedList<Integer>();
              for(int i = 0; (i < Program2.odds.size() && i < Program2.numberOfWrenches); i++) {
                int value = Program2.odds.pop();
                list.add(new Integer(value));
              }
              Program2.oddGroup = list;
            }

            // Selects our next random number
            int index = random.nextInt(Program2.oddGroup.size());
            Program2.selectedOdd = Program2.oddGroup.get(index);
        }
    }

}
