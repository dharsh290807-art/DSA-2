import java.util.*;

public class Main {

    static class SegmentTreeLazy {

        double[] tree;
        double[] lazy;
        int n;

        SegmentTreeLazy(int n) {
            this.n = n;
            tree = new double[4 * n];
            lazy = new double[4 * n];
        }

        void build(double[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
                return;
            }

            int mid = (start + end) / 2;

            build(arr, 2 * node, start, mid);
            build(arr, 2 * node + 1, mid + 1, end);

            tree[node] = Math.max(tree[2 * node],
                                  tree[2 * node + 1]);
        }

        void pushDown(int node) {
            if (lazy[node] != 0) {

                tree[2 * node] += lazy[node];
                lazy[2 * node] += lazy[node];

                tree[2 * node + 1] += lazy[node];
                lazy[2 * node + 1] += lazy[node];

                lazy[node] = 0;
            }
        }

        void updateRange(int node,
                         int start,
                         int end,
                         int left,
                         int right,
                         double delta) {

            if (right < start || left > end)
                return;

            if (left <= start && end <= right) {
                tree[node] += delta;
                lazy[node] += delta;
                return;
            }

            pushDown(node);

            int mid = (start + end) / 2;

            updateRange(2 * node, start, mid,
                        left, right, delta);

            updateRange(2 * node + 1, mid + 1, end,
                        left, right, delta);

            tree[node] =
                Math.max(tree[2 * node],
                         tree[2 * node + 1]);
        }

        double queryMax(int node,
                        int start,
                        int end,
                        int left,
                        int right) {

            if (right < start || left > end)
                return Double.NEGATIVE_INFINITY;

            if (left <= start && end <= right)
                return tree[node];

            pushDown(node);

            int mid = (start + end) / 2;

            double l =
                queryMax(2 * node, start, mid,
                         left, right);

            double r =
                queryMax(2 * node + 1, mid + 1, end,
                         left, right);

            return Math.max(l, r);
        }
    }

    public static void main(String[] args) {

        int n = 16;

        double[] zones = new double[n];
        Arrays.fill(zones, 1.0);

        SegmentTreeLazy st =
                new SegmentTreeLazy(n);

        st.build(zones, 1, 0, n - 1);

        st.updateRange(1, 0, n - 1,
                       3, 9, 0.5);

        st.updateRange(1, 0, n - 1,
                       7, 14, 0.3);

        System.out.println(
            "max[0,15] = "
            + st.queryMax(1, 0, n - 1,
                          0, 15));

        st.updateRange(1, 0, n - 1,
                       2, 6, 0.7);

        System.out.println(
            "max[4,10] = "
            + st.queryMax(1, 0, n - 1,
                          4, 10));
    }
}