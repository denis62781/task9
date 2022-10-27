import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;

public class MainApp {
    private static Connection connection;
    private static Statement statement;

    public static void main (String[] args) throws InvocationTargetException, IllegalAccessException {

        Object[] cats = new Cat[5];
        cats[0] = new Cat(0,"Барсик",10,10,1);
        cats[1] = new Cat(1,"Генадий",20,10,1);
        cats[2] = new Cat(2,"Маруся",40,30,1);
        cats[3] = new Cat(3,"Гриша",60,40,1);
        cats[4] = new Cat(4,"Мурзик",100,70,1);

        Class myClass = Cat.class;
        String text = "";
        String text0 = "";
        String[][] txt = new String[5][3];
        String obj = ((Table)myClass.getAnnotation(Table.class)).title();
        Field[] f = myClass.getDeclaredFields();


        for (Field i:f) {
            if (i.isAnnotationPresent(Column.class)){
                if (text != "") text+=", ";
                text += i.getName();}
        }

        for (Field i:f) {
            if (i.isAnnotationPresent(Column.class)){
                if (text0 != "") text0+=",";
                text0 += i.getName() + " " + checkIntOrString(i.getType()) + " ";}
        }

        Method[] ff = myClass.getDeclaredMethods();
        for (int j = 0;j < cats.length;j++) {
            for (int i = 0; i < f.length; i++) {
                if (f[i].isAnnotationPresent(Column.class)) txt[j][i] = texter(f[i].getType(),ff[i].invoke(cats[j]).toString());
            }

        }


        System.out.println(text);
        System.out.println(text0);
        if (myClass.isAnnotationPresent(Table.class))
            System.out.println(((Table)myClass.getAnnotation(Table.class)).title());
        try{
            connect();
            statement.executeUpdate("CREATE TABLE "+obj+" ("+text0+");");
            for (int i = 0; true; i++) {
                if (i>=5){
                    break;
                }
                StringBuilder sb = new StringBuilder("INSERT INTO ").append(obj).append(" (").append(text).append(") ").append("VALUES(").append(txt[i][2]+", ").append("\""+txt[i][0]+"\", ").append(txt[i][1]+");");

                System.out.println(sb.toString());

                statement.executeUpdate(sb.toString());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            disconnection();
        }
    }

    public static String texter(Class a,Object b){
        if (a == int.class){
            return (String) b;
        }
        else{
            return (String) b;
        }
    }

    //проверка класса на то строка он или
    public static String checkIntOrString(Class a) {
        if (a == int.class)
            return "INTEGER";
        else
            return "VARCHAR";
    }

    public static void connect() throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            statement = connection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            throw  new SQLException("Не удалось подключиться");
        }
    }
    public static void disconnection(){
        try{
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }try{
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}