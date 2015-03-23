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

            try {
                Program2.even.acquire();
            } catch(InterruptedException e){}

            while(Program2.selectedInt != this.id){
                Program2.even.release();
                try {
                    Program2.even.acquire();
                } catch(InterruptedException e){}
            }

            try {
                Program2.even.release();
                Program2.even.acquire();
            } catch(InterruptedException e){}

            System.out.println("Even: "+this.id);
            System.out.flush();

            if(Program2.priority.size() > 0) {
                Program2.priority.remove(new Integer(this.id));
            } else {
                Program2.evens.remove(new Integer(this.id));
            }

            if( (Program2.evens.size()+Program2.priority.size()) == 0) {
                Program2.odd.release();
            }

            Program2.even.release();
            Program2.evenSelector.release();
            Program2.oddSelector.release();
        } else {
            // This one is odd

            try {
                Program2.odd.acquire();
            } catch (InterruptedException e) {}

            while(Program2.selectedOdd != this.id) {
                Program2.odd.release();
            }

            try {
                Program2.odd.release();
                Program2.odd.acquire();
            } catch(InterruptedException e){}
            System.out.println("Odd : " + this.id);
            System.out.flush();

            Program2.oddGroup.remove(new Integer(this.id));
            Program2.odd.release();

            Program2.oddSelector.release();
        }
    }

}
