package runtimedata;

import java.util.EmptyStackException;

/**
 * Author: zhangxin
 * Time: 2017/5/4 0004.
 * Desc: 并没有采用数组的形式来保存栈帧,而是使用单向链表的形式,Zframe中保存这前一个帧的引用;
 * 最多持有1024个栈帧,当然这个值可以设置;
 */
public class Zstack {
    int maxSize;    //虚拟机栈中所包含栈帧的最大容量
    int size;       //当前虚拟机栈中包含帧的数量
    private Zframe _top; //栈顶的帧

    public Zstack(int maxSize) {
        this.maxSize = maxSize;
    }


    //新添加一个栈帧,将这个栈帧设置为top,当然如果当前栈之前有元素,那么将要push进的frame的lower是指为之前的top,当前frame变为top;
    void push(Zframe frame) {
        if (size > maxSize) {
            //throw new RuntimeException("java.lang.StackOverflowError");
            //如果栈已经满了，按照Java虚拟机规范，应该抛出StackOverflowError异常
            throw new StackOverflowError();
        }
        if (_top != null) {
            frame.lower = _top; // frame中保存前一个帧的引用,使得当前帧被push的时,前一个帧顶上去;
        }

        _top = frame;
        size++;
    }

    Zframe pop() {
        if (_top == null) {
            throw new EmptyStackException();
        }
        Zframe tmp = _top;
        _top = tmp.lower;
        tmp.lower = null;  //tmp是带pop出的栈帧,既然要pop出来,那么将其lower设置为null,不在持有栈中的帧,避免内存泄露;
        size--;
        return tmp;
    }

    Zframe top() {
        if (_top == null) {
            throw new EmptyStackException();
        }
        return _top;
    }

}
