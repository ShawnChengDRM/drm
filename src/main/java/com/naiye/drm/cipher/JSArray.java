package com.naiye.drm.cipher;

import java.util.ArrayList;
import java.util.List;

public class JSArray<T>
{
    private List<T> list = new ArrayList<T>();

    public JSArray()
    {
    }

    public JSArray(T[] ts)
    {
        for(T t : ts)
        {
            this.list.add(t);
        }
    }

    public T get(int index)
    {
        return list.get(index);
    }

    public void push(T t)
    {
        list.add(t);
    }

    public void push(JSArray<T> array)
    {
        list.addAll(array.list());
    }

    public T pop()
    {
        return list.remove(list.size() - 1);
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(T[] t)
    {
        return (T[])list.toArray(t);
    }

    public String join()
    {
        String result = "";
        for(T t : list)
        {
            if(t instanceof String)
            {
                result += t;
            }
            else if (t == null)
            {

            }
            else
            {
                result += t.toString();
            }
        }
        return result;
    }

    public List<T> list()
    {
        return list;
    }

    public void set(T[] ts)
    {
        list.clear();
        for(T t : ts)
        {
            this.list.add(t);
        }
    }

}
