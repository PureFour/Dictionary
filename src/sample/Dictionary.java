
package sample;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayList;

public class Dictionary
{

    public ArrayList<Node> list;

    public Dictionary()
    {
        this.list = new ArrayList<>();
    }
    public void add(Object dataValue, Object keyValue)
    {
        Node n = new Node(keyValue, dataValue);
        this.list.add(n);
    }
    public void delete(Object keyValue)
    {
        if(keyValue == null) return;
        if(list.isEmpty())
        {
            System.out.println("Directionary is empty!");
            return;
        }
        else this.list.remove(get(keyValue));
    }
    public Object get(Object keyValue)
    {
        if(list.isEmpty())
        {
            System.out.println("Directionary is empty!");
            return null;
        }
        for(int i = 0; i < this.list.size(); i++)
        {
            if(keyValue.equals(list.get(i).getKey()))
            {
                return this.list.get(i);
            }
        }
        System.out.println("Element has not been found!");
        return null;
    }

    public void save(File file)
    {
        System.out.println("Saving objects to file...");

        try (FileOutputStream fo = new FileOutputStream(file))
        {
            ObjectOutputStream os = new ObjectOutputStream(fo);
            os.writeObject(this.list);
            os.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException ie)
        {
            ie.printStackTrace();
        }
    }

    public void load(File file)
    {
        System.out.println("Reading objects from file...");

        try (FileInputStream fi = new FileInputStream(file))
        {
            ObjectInputStream oi = new ObjectInputStream(fi);
            this.list.addAll((ArrayList<Node>)oi.readObject());
            for(Object o : list)
            {
                System.out.println(o);
            }

            oi.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }catch (IOException ie)
        {
            ie.printStackTrace();
        }catch (ClassNotFoundException ce)
        {
            ce.printStackTrace();
        }
    }
    public void show()
    {
        if(list.isEmpty())
        {
            System.out.println("Directionary is empty!");
            return;
        }
        for(Node n : list)
        {
            System.out.print(n);
        }
        System.out.println();
    }
    public ArrayList<Node> getList(){return this.list;}
}