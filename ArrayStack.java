import java.util.ArrayList;
public class ArrayStack<T> implements StackInterface<T> {
    private ArrayList<T> elements;
    public ArrayStack() 
    {
        elements = new ArrayList<>();
    }
    
    public int size() {
        return elements.size();
    }

    public boolean empty() {
        return elements.isEmpty();
    }

    public T peek() throws StackUnderflowException {
        if (empty()) {
            throw new StackUnderflowException("Stack is empty");
        }
        return (T) elements.get(size() - 1);
    }

   public T pop() throws StackUnderflowException {
        if (empty()) {
            throw new StackUnderflowException("Stack is empty");
        }
        return (T) elements.remove(size() - 1);
    }

   public void clear() {
        elements.clear();
    }

        public T push(T var1)
        {
            elements.add(var1);
            return var1;
        }

    public int search(Object var1) 
    {
        int index = elements.indexOf(var1);
        if(index == -1)
        {
            return -1;
        }
        else
        {
            return elements.size() - index;
        }
    }

}
