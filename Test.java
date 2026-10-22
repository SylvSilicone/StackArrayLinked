import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Tests both the ArrayStack and LinkedStack classes.
 */
public class StackTest<T>
{
    private static boolean failed = false;
    private static boolean arrayFailed, linkedFailed;
    private static ArrayList<String> methodNames = new ArrayList<String>();
    private static StackInterface<Integer> stack;
    private static String methodName;
    private static Class<?> c;
    private static boolean display = true;

    public static void main(String[] args)
    {
        testInterface();
        testStack("ArrayStack");
        arrayFailed = failed;

        failed = false;

        testStack("LinkedStack");
        linkedFailed = failed;

        if (arrayFailed || linkedFailed)
            System.out.println("Bummer, you have not finished the Stack assignment. Try again.");
        else
            System.out.println("Fantabulous! You have completed the Stack assignment");
    }

    private static void testStack(String type)
    {
        stackTest(type);

        if (failed)
            System.out.println(type + " class does not pass the test\n");
    }

    private static void testInterface()
    {
        try
        {
            c = Class.forName("StackInterface");
            Member[] methods = c.getMethods();

            for (Member method : methods)
                methodNames.add(((Method) method).toGenericString());
        }
        catch (ClassNotFoundException e)
        {
            failure("Epic Failure: missing interface StackInterface");
            return;
        }

        methodName = "public abstract void StackInterface.clear()";
        if (!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract int StackInterface.size()";
        if (!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract T StackInterface.pop() throws StackUnderflowException";
        if (!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract T StackInterface.push(T)";
        if (!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract T StackInterface.peek() throws StackUnderflowException";
        if (!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract boolean StackInterface.empty()";
        if (!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        methodName = "public abstract int StackInterface.search(java.lang.Object)";
        if (!methodNames.contains(methodName))
            failure("Missing interface method: " + methodName);

        if (failed)
            System.out.println("\nCorrupted interface StackInterface");
    }

    private static void stackTest(String type)
    {
        System.out.println("Now testing your " + type + " class:");

        try
        {
            if (type.equals("ArrayStack"))
                stack = new ArrayStack<Integer>();
            else
                stack = new LinkedStack<Integer>();

            c = Class.forName(type);
        }
        catch (ClassNotFoundException | NoClassDefFoundError | NoSuchMethodError e)
        {
            failure("Epic Failure: missing or invalid " + type + " class");
            return;
        }

        if (!(stack instanceof StackInterface))
        {
            failure("Failed: " + type + " does not implement StackInterface");
            return;
        }

        display(type + " implements StackInterface test");

        try
        {
            if (stack.size() != 0)
            {
                failure("Failed: incorrect initial size value");
                return;
            }

            if (!stack.empty())
            {
                failure("Failed: incorrect empty method");
                return;
            }

            if (stack.push(1) != 1)
                failure("push(element) method is not correct");

            if (stack.size() != 1)
                failure("Failed: incorrect size value after push");

            if (stack.empty())
                failure("Failed: incorrect empty method after push");

            if (stack.peek() != 1)
                failure("peek method is not correct");

            if (stack.pop() != 1)
                failure("pop method is not correct");

            if (stack.size() != 0)
                failure("Failed: incorrect size value after pop");

            if (!stack.empty())
                failure("Failed: incorrect empty method after pop");

            // Test pop on an empty stack.
            try
            {
                stack.pop();
                failure("pop method did not throw StackUnderflowException");
            }
            catch (StackUnderflowException e)
            {
                display("pop method correctly throws StackUnderflowException");
            }

            // Test peek on an empty stack.
            try
            {
                stack.peek();
                failure("peek method did not throw StackUnderflowException");
            }
            catch (StackUnderflowException e)
            {
                display("peek method correctly throws StackUnderflowException");
            }

            // Test dynamic expansion and push.
            for (int i = 10; i <= 1000; i += 10)
                stack.push(i);

            if (stack.size() != 100)
                failure("Failed: incorrect size value after 100 pushes");

            if (stack.peek() != 1000)
                failure("push and/or peek methods are incorrect");

            // Test pop and peek repeatedly.
            int x = 1000;

            while (x > 10)
            {
                if (stack.pop() != x)
                    failure("push and/or pop methods are incorrect");

                x -= 10;

                if (stack.peek() != x)
                    failure("push and/or peek methods are incorrect");
            }

            if (stack.size() != 1)
                failure("Failed: incorrect size value");

            if (stack.empty())
                failure("Failed: incorrect empty method");

            if (stack.peek() != 10)
                failure("peek method is not correct");

            if (stack.pop() != 10)
                failure("pop method is not correct");

            if (stack.size() != 0)
                failure("Failed: incorrect size value after final pop");

            if (!stack.empty())
                failure("Failed: incorrect empty method after final pop");

            // Test empty stack exceptions again.
            try
            {
                stack.pop();
                failure("pop method did not throw StackUnderflowException again");
            }
            catch (StackUnderflowException e)
            {
                display("pop method again correctly throws StackUnderflowException");
            }

            try
            {
                stack.peek();
                failure("peek method did not throw StackUnderflowException again");
            }
            catch (StackUnderflowException e)
            {
                display("peek method again correctly throws StackUnderflowException");
            }

            if (!failed)
            {
                display("size method is correct");
                display("empty method is correct");
                display("push method is correct");
                display("peek method is correct");
                display("pop method is correct");
            }

            // Test search and make sure it restores the stack.
            for (int i = 10; i > 0; i--)
                stack.push(i);

            if (stack.search(3) != 3)
                failure("search method is incorrect for 3");

            if (stack.search(8) != 8)
                failure("search method is incorrect for 8");

            if (stack.search(1) != 1)
                failure("search method is incorrect for 1");

            if (stack.search(10) != 10)
                failure("search method is incorrect for 10");

            if (stack.search(12) != -1)
                failure("search method is incorrect for missing item");

            for (int i = 1; i <= 10; i++)
            {
                if (stack.pop() != i)
                {
                    failure("search method did not restore stack to original state");
                    break;
                }
            }

            if (!failed)
                display("search method is correct");

            // Test clear.
            for (int i = 0; i < 500; i++)
                stack.push(i);

            stack.clear();

            if (stack.size() != 0)
                failure("clear method did not reset size to zero");

            if (!stack.empty())
                failure("clear method did not make stack empty");

            try
            {
                stack.pop();
                failure("clear method did not leave stack empty");
            }
            catch (StackUnderflowException e)
            {
                display("clear method is correct");
            }

            if (!failed)
                System.out.println("Congratulations! Your " + type + " class is correct\n");
        }
        catch (StackUnderflowException e)
        {
            failure("Unexpected StackUnderflowException in " + type + ": " + e.getMessage());
        }
        catch (RuntimeException e)
        {
            failure("Unexpected runtime error in " + type + ": " + e);
        }
    }

    private static void failure(String str)
    {
        if (display)
            System.out.println("*** " + str);

        failed = true;
    }

    private static void display(String str)
    {
        if (display)
            System.out.println("Passed " + str);
    }
}
