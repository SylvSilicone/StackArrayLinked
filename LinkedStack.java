import java.util.LinkedList;
public class LinkedStack<T> implements StackInterface<T> 
{
    LinkedList<T> elements = new LinkedList<T>();
    public LinkedStack()
    {
    }
    // returns the logical size of the stack
    public int size()
    {
        return elements.size();
    }   
    
    // tests if this stack is empty
    public  boolean empty()
    {
        if(elements.size() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    // looks at the object at the top of this stack
    // without removing it from the stack
    public T peek() throws StackUnderflowException
    {
            return elements.get(0);
    }
    
    // removes the object at the top of this stack 
    // and returns that object as the value of this function
    public T pop() throws StackUnderflowException
    {
        T item;
        return item;
    }
    
    // pushes an item onto the top of this stack
    public T push(T item)
    {
        return item;
    }
        
    // removes all of the elements from this stack
    public void clear()
    {
        for(int i = 0; i < elements.size(); i++)
        {
            elements.remove(i);
        }
    }
    
    // returns the 1 based position where an object is on this stack
    // note: when the method ends, the stack is the same as it was at the start
    public int search(Object o)
    {
        int index = elements.indexOf(o);
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
