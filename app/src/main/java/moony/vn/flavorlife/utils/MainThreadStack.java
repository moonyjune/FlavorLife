package moony.vn.flavorlife.utils;

import java.util.Stack;

import com.ntq.utils.Utils;

public class MainThreadStack extends Stack<Object> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public synchronized boolean isEmpty() {
        Utils.ensureOnMainThread();
        return super.isEmpty();
    }

    @Override
    public synchronized Object peek() {
        Utils.ensureOnMainThread();
        return super.peek();
    }

    public synchronized Object pop() {
        Utils.ensureOnMainThread();
        return super.pop();
    }

    public synchronized Object push(Object obj) {
        Utils.ensureOnMainThread();
        return super.push(obj);
    }
}
