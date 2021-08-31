import java.util.ArrayList;
import java.util.Iterator;

public class PHashtable {
    private ArrayList<Patient>[] table;
    private int tablesize;
    private int patient_num;
    public PHashtable(){
        table = new ArrayList[11];
        for(int i=0;i<table.length;i++) {
            table[i] = new ArrayList<>();
        }
        tablesize = 11;
        patient_num = 0;
    }
    public Patient get(String name) {
        int h = hashcode(name);
        for (Patient p: table[h]) {
            if (p.name().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public void put(Patient p) {
        int h = hashcode(p.name());
        if (table[h].contains(p)) {
            return;
        }
        table[h].add(p);
        patient_num += 1;
        if (patient_num >= 2 * tablesize) {
            resize(getNextPrime(2*tablesize));
        }
    }

    public Patient remove(String name) {
        int h = hashcode(name);
        Patient ret = null;
        Iterator<Patient> iter = table[h].iterator();
        while(iter.hasNext()) {
            Patient p = iter.next();
            if (name.equals(p.name())) {
                ret = p;
                iter.remove();
                patient_num--;
            }
        }
        return ret;
    }

    public int hashcode(String s) {
        int h = s.hashCode() % tablesize;
        if (h < 0) h += tablesize;
        return h;
    }

    public void resize(int size) {
        tablesize = size;
        ArrayList<Patient> old[] = table.clone();
        table = new ArrayList[tablesize];
        for(int i=0;i<table.length;i++) {
            table[i] = new ArrayList<>();
        }
        patient_num = 0;
        for (int i=0;i<old.length;i++) {
            for(Patient p: old[i]) {
                put(p);
            }
        }
    }

    public int size() {
        return patient_num;
    }

    public ArrayList<Patient>[] getArray() {
        return table;
    }

    public void setPatientInfo(String name, int urgency, int pos) {
        int h = hashcode(name);
        for (Patient p: table[h]) {
            if (p.name().equals(name)) {
                p.setUrgency(urgency);
                p.setPosInQueue(pos);
                break;
            }
        }
        return;
    }

    private int getNextPrime(int num) {
        if (num == 2 || num == 3) {
            return num;
        }
        int rem = num % 6;
        switch (rem) {
            case 0: num++; break;
            case 2: num+=3;break;
            case 3: num+=2;break;
            case 4: num++;break;
        }
        while(!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }
        return num;
    }

    private boolean isPrime(int num) {
        int x = 3;
        for(int i=x;i<=(int)Math.sqrt(num);i+=2) {
            if (num % x == 0) {
                return false;
            }
        }
        return true;
    }
}
