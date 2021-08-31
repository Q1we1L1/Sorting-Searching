public class MaxPQ {
	private Patient[] array;
	private int nextOpen;
	private PHashtable table;

	/***
	 *constructor: constructs a new
	 *MaxPQ with starting capacity of 10
	 ***/
	public MaxPQ() {
		this.array = new Patient[10];
		this.nextOpen = 1;
		this.table = new PHashtable();
	}

	/***
	 *@param item to be inserted into PQ
	 *if the array is full after the insert,
	 *resize the array to be twice as large
	 ***/
	public void insert(Patient item) {
		item.setPosInQueue(nextOpen);

		array[nextOpen] = item;
		swim(nextOpen);
		nextOpen++;
		if(nextOpen == array.length) {
			resize(2*array.length);
		}
		table.put(item);
	}

	/***
	 *@return and remove the max item in the PQ and re-heapify
	 *throw EmptyQueueException if the PQ is empty
	 ***/
	public Patient delMax() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException();
		Patient max = array[1];
		nextOpen--;
		swap(1, nextOpen);
		sink(1);

		table.remove(max.name());
		max.setPosInQueue(-1);
		return max;
	}

	/***
	 *@return but do not remove the max item in the PQ
	 *throw EmptyQueueException if the PQ is empty
	 ***/
	public Patient getMax() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException();
		return array[1];
	}

	/***
	 *@return the number of items currently in
	 *the PQ
	 ***/
	public int size() {
		return nextOpen - 1;
	}

	/***
	 *@return true if the PQ is empty and false
	 *otherwise
	 ***/
	public boolean isEmpty() {
		return size() == 0;
	}

	/***
	 *@return the patient with name s
	 *remove and return the patient with name s
	 ***/
	public Patient remove(String s) {
		Patient p = table.get(s);
		table.remove(s);
		int idx = p.posInQueue();
		nextOpen--;
		swap(idx, nextOpen);
		sink(idx);
		p.setPosInQueue(-1);
		return p;
	}

	/***
	 *@param s the name of the patient
	 *@param urgency the patient's new urgency level
	 ***/
	public void update(String s, int urgency) {
		Patient p = table.get(s);
		if (p == null) return;

		int idx = p.posInQueue();
		int old = array[idx].urgency();
		array[idx].setUrgency(urgency);
		if (urgency > old) {
			swim(idx);
		} else if (urgency < old) {
			sink(idx);
		}
		return;
	}
	   

	/***
	 *@param i the index of the element
	 *that may need to swim up the heap
	 ***/
	private void swim(int i) {
		int p = (int)(i/2);
		while(p > 0) {
			if(array[i].compareTo(array[p]) > 0) {
				swap(i, p);
				i = p;
				p = (int)(i/2);
				swim(i);
			}
			else
				return;
		}
	}

	/***
	 *@param i the index of the element
	 *that may need to sink down the heap
	 ***/
	private void sink(int i) {
		if(array[i] == null)
			return;
		int lc = 2*i;
		int rc = 2*i + 1;
		if(lc > nextOpen - 1)
			return;
		if(rc > nextOpen - 1) {
			if(array[i].compareTo(array[lc]) < 0) {
				swap(i, lc);
				sink(lc);
			}
			return;
		}
		int max = 0;
		if(array[lc].compareTo(array[rc]) >= 0)
			max = lc;
		else
			max = rc;
		if(array[i].compareTo(array[max]) < 0) {
			swap(i, max);
			sink(max);
		}
	}

	/***
	 *@param i
	 *@param j
	 *swap array[i] and array[j]
	 ***/
	private void swap(int i, int j) {
		if(i == j)
			return;
		Patient temp = array[i];
		array[i] = array[j];
		array[j] = temp;

		array[i].setPosInQueue(i);
		array[j].setPosInQueue(j);

	}

	/***
	 *resize array to have the capacity
	 *@param newCap
	 ***/
	private void resize(int newCap) {
		Patient[] newArray = new Patient[newCap + 1];
		for(int i = 1; i < array.length; i++)
			newArray[i] = array[i];
		this.array = newArray;
	}
}
    
