package sample;

import java.beans.XMLDecoder;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Dictionary
{
    private Connection c;

    public Dictionary() throws SQLException, ClassNotFoundException
    {
        this.c = dbUtil.dbConnection.getConnection();
        initTable();
    }
    private void initTable() throws SQLException
    {
        Statement stmt = c.createStatement();
        stmt.executeUpdate("CREATE TABLE if not exists tab(key VARCHAR(20), definition VARCHAR(20))");
    }
    void add(Object keyValue, Object dataValue) throws SQLException
    {
        String k = keyValue.toString();
        String v = dataValue.toString();
        String sql = "INSERT INTO tab(key, definition) VALUES(?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        preparedStatement.setString(1, k);
        preparedStatement.setString(2, v);
        preparedStatement.executeUpdate();
    }
    void delete(Object keyValue) throws SQLException
    {
        if(keyValue == null);
        else
        {
            String sql = "DELETE FROM tab WHERE key = ?";
            PreparedStatement preparedStatement = c.prepareStatement(sql);
            preparedStatement.setString(1, keyValue.toString());
            preparedStatement.executeUpdate();
        }
    }
    Object get(Object keyValue) throws SQLException
    {
        String query = "SELECT * FROM tab WHERE key = ?";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1, keyValue.toString());
        ResultSet rs = preparedStatement.executeQuery();
        if(!rs.next()) return null;
        else
        {
            Node node;
            node = new Node(rs.getString(1), rs.getString(2));
            return node;
        }
    }
    void save(File file) throws SQLException
    {
        System.out.println("Saving objects to file...");

        try (FileOutputStream fo = new FileOutputStream(file))
        {
            ObjectOutputStream os = new ObjectOutputStream(fo);
            os.writeObject(this.getList());
            os.close();
        } catch (IOException ie)
        {
            ie.printStackTrace();
        }
    }
    void load(File file) throws SQLException
    {
        System.out.println("Reading objects from file...");
        String sql = "INSERT INTO tab(key, definition) VALUES(?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(sql);

        try (FileInputStream fi = new FileInputStream(file))
        {
            ObjectInputStream oi = new ObjectInputStream(fi);
            ArrayList<Node> list = new ArrayList<>((ArrayList<Node>) oi.readObject());
            for(Node n : list)
            {
                System.out.println(n);
                preparedStatement.setString(1, n.getKey().toString());
                preparedStatement.setString(2, n.getValue().toString());
                preparedStatement.executeUpdate();
            }
            oi.close();
        } catch (IOException | ClassNotFoundException ie)
        {
            ie.printStackTrace();
        }
    }
    void load_as_XML(File file) throws SQLException, IOException
    {
        System.out.println("Reading objects from file...");
        String sql = "INSERT INTO tab(key, definition) VALUES(?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
        ArrayList<Node> list = new ArrayList<>((ArrayList<Node>) xmlDecoder.readObject());
        for(Node n : list)
        {
            System.out.println(n);
            preparedStatement.setString(1, n.getKey().toString());
            preparedStatement.setString(2, n.getValue().toString());
            preparedStatement.executeUpdate();
        }
    }
    public void show() throws SQLException
    {
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT key, definition from tab");
        while(rs.next())
        {
            System.out.println(
                    rs.getString("key") + "\t" +
                    rs.getString("definition"));
        }
    }
    ArrayList<Node> getList() throws SQLException
    {
        ArrayList<Node> arrayList = new ArrayList<>();
        String query = "SELECT * FROM tab";
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Node node;
        while(rs.next())
        {
            node = new Node(rs.getString(1), rs.getString(2));
            arrayList.add(node);
        }
        return arrayList;
    }
}