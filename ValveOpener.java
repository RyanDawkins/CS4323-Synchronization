import java.lang.InterruptedException;
import java.lang.Override;
import java.lang.Runnable;

public class ValveOpener implements Runnable
{

    private int id;
    private long threadId;

    public ValveOpener(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        this.threadId = Thread.currentThread().getId();


        if(id % 2 == 0) {
            // This one is even

            // Acquiring the semaphor to stop other evens from running
            try {
                Program2.even.acquire();
            } catch(InterruptedException e){}

            // Here we check to see if we are the selected even
            while(Program2.selectedInt != this.id){

                // Release because we are not selected
                Program2.even.release();
                try {
                    Program2.even.acquire();
                } catch(InterruptedException e){}
            }

            // We have the assumption we are the even running so we acquire the semaphore to
            // block others from running
            try {
                Program2.even.release();
                Program2.even.acquire();
            } catch(InterruptedException e){}

            // This is the critical section
            // The buffer to print is critical and so is the number of valves open
            Program2.numberOfValvesClosed--;
            System.out.println("ThreadID: "+this.threadId+"\tValve ID: "+this.id+"\tClosed Valves: "+Program2.numberOfValvesClosed);
            System.out.flush();

            // We check to see if there are priority items. If there are we can
            // infer that this is a priority item. So we should remove it from the list
            if(Program2.priority.size() > 0) {
                Program2.priority.remove(new Integer(this.id));
            } else {
                Program2.evens.remove(new Integer(this.id));
            }

            // Here we release the odds if the odds are ready to go.
            if( (Program2.evens.size()+Program2.priority.size()) == 0) {
                Program2.odd.release();
                Program2.oddSelector.release();
            }

            Program2.even.release();
            Program2.evenSelector.release();
            return;
        } else {
            // This one is odd

            // Stops all odds from running
            try {
                Program2.odd.acquire();
            } catch (InterruptedException e) {}

            // We try to become the next odd here
            while(Program2.selectedOdd != this.id) {
                Program2.odd.release();
            }

            // Since we are running we grab the odd semaphore
            try {
                Program2.odd.release();
                Program2.odd.acquire();
            } catch(InterruptedException e){}

            // Here's the odd critical section
            Program2.numberOfValvesClosed--;
            System.out.println("ThreadID: "+this.threadId+"\tValve ID: " + this.id+"\tClosed Valves: "+Program2.numberOfValvesClosed);
            System.out.flush();

            // Removes the integer from the grouping
            Program2.oddGroup.remove(new Integer(this.id));
            Program2.odd.release();

            Program2.oddSelector.release();
            return;
        }
    }

}
