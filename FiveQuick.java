public class FiveQuick {
    static void swap(int[] array, int i, int j) {
        int t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
    static void sort(int[] array) {
        sort(array, 0, array.length-1);
    }

    static void sort(int[] a, int l, int r) {
        if (l+1 > r) return;
        int pivot1 = a[l], pivot2 = a[r];
        if (pivot1 > pivot2) {
            swap(a,l,r);
            int t = pivot1;
            pivot1 = pivot2;
            pivot2 = t;
        }
        int p=l,q=l,i=r,j=r,k=l+1;
        while(k < i) {
            if(a[k]<pivot1) {
                swap(a,k,p++);
                swap(a,++q,k++);
            }
            else if(a[k]==pivot1) swap(a,++q,k++);
            else if(a[k]>=pivot2) {
                while(i>k && a[i-1]>=pivot2) {
                    i--;
                    if(a[i]>pivot2) swap(a,j--,i);
                }
                if(i<=k) break;
                if(a[k]==pivot2) swap(a,k,--i);
                else {
                    swap(a,k,j--);
                    swap(a,k,--i);
                }
            } else k++;
        }
        sort(a,l,p-1);
        sort(a,q+1,i-1);
        sort(a,j+1,r);
    }
}
