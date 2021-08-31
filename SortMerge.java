import java.util.*;
class P {
    int x, y;
    public P(int x_, int y_) {
        x = x_;
        y = y_;
    }
}

public class SortMerge {
    static void sort(int[] array) {
        Queue<P> q = new Queue<>();
        int x = 0;
        for (int i=1;i<array.length;i++) {
            if (array[i] < array[i-1]) {
                if (x == 0) {
                    q.enqueue(new P(x, i-1));
                } else {
                    q.enqueue(new P(x, i-1));
                }
                x = i;
            }
        }
        q.enqueue(new P(x, array.length-1));

        while (!q.isEmpty()) {
            if (q.size() == 1) {
                break;
            }
            P a = q.peek();
            q.dequeue();
            P b = q.peek();
            if (a.y + 1 == b.x) {
                q.dequeue();
                merge(array, a.x, a.y, b.x, b.y);
                q.enqueue(new P(a.x, b.y));
            } else {
                q.enqueue(a);
            }
        }
    }
    static void merge(int[] array, int x1, int y1, int x2, int y2) {
        int[] tmp = new int[array.length];
        int k = 0;
        int i = x1, j = x2;
        while (i <= y1 && j <= y2) {
            if (array[i] < array[j]) {
                tmp[k++] = array[i++];
            } else {
                tmp[k++] = array[j++];
            }
        }
        while (i <= y1) {
            tmp[k++] = array[i++];
        }
        while (j <= y2) {
            tmp[k++] = array[j++];
        }
        for (int o=0;o<k;o++) {
            array[x1+o] = tmp[o];
        }
    }
}
