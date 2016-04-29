//java -cp .:mysql-connector-java-5.1.23-bin.jar DBMSass2
//java -cp .;mysql-connector-java-5.1.23-bin.jar DBMSass2

import java.sql.*;
import java.util.*;

public class DBMSass2{

    public static void main(String[] args){

        Connection con;
        Statement stmt;
        String url = "jdbc:mysql://10.5.18.68:3306/13CS10049";
        String username = "13CS10049";
        String password = "cse12";
        
        String query;
        ResultSet rst;
        PreparedStatement dynamicQuery;

        try{
            Class.forName("java.sql.Driver");
            System.out.println("Connection Established");
        }
        catch(java.lang.ClassNotFoundException e){
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }
        
        
        try{
            
            con = DriverManager.getConnection(url,username,password);
            stmt = con.createStatement();

            // Static Query
            System.out.print("\t\nSTATIC QUERY =>");
            //First Query
            System.out.println("\n\t1) List all the films in which an actor with firstname \"Shahrukh\" and lastname \"Khan\" acted");
            System.out.println("Movie Title - ");
            query =   "SELECT movie.title \"Movie Title\"\n" +
                            "FROM movie\n" +
                            "JOIN acts ON acts.mov_id = movie.mov_id\n" +
                            "JOIN actor ON actor.act_id = acts.act_id\n" +
                            "WHERE actor.first_name='Shahrukh' AND actor.last_name='Khan';";
            rst = stmt.executeQuery(query);

            while(rst.next())
            {
                String movie_name = rst.getString("Movie Title");
                System.out.println(movie_name);
            }
            rst.close();
 
            //Second Query
            System.out.println("\n\t2) List all movies for which \"Shahrukh\" \"Khan\" was an actor as well as director");
            System.out.println("Movie Title - ");
            query =   "SELECT movie.title \"Movie Title\"\n" +
                            "FROM movie\n" +
                            "JOIN directs ON movie.mov_id = directs.mov_id\n" +
                            "JOIN acts ON acts.mov_id = directs.mov_id\n" +
                            "JOIN actor ON actor.act_id = acts.act_id\n" +
                            "JOIN director ON directs.dir_id = director.dir_id\n" +
                            "WHERE actor.first_name='Shahrukh' AND actor.last_name='Khan' AND\n" +
                            "director.first_name = 'Shahrukh' AND director.last_name = 'Khan';";
            rst = stmt.executeQuery(query);

            while(rst.next())
            {
                String movie_name = rst.getString("Movie Title");
                System.out.println(movie_name);
            }
            rst.close();
            
            //Third Query
            System.out.println("\n\t3) List all \"thrillers\" released after the year 2000 in which \"Shahrukh\" \"Khan\" acted");
            System.out.println("Movie Title - ");
            query =   "SELECT movie.title \"Movie Title\"\n" +
                            "FROM movie\n" +
                            "JOIN acts ON acts.mov_id = movie.mov_id\n" +
                            "JOIN actor ON actor.act_id = acts.act_id\n" +
                            "WHERE actor.first_name='Shahrukh' AND actor.last_name='Khan' AND\n" +
                            "movie.year>2000 AND (movie.genre LIKE '%Thriller%');";
            rst = stmt.executeQuery(query);

            while(rst.next())
            {
                String movie_name = rst.getString("Movie Title");
                System.out.println(movie_name);
            }
            rst.close();
            
            //Fourth Query
            System.out.println("\n\t4) List all Co-actors of \"Shahrukh\" \"Khan\" who are younger than him by 10 years");
            System.out.println("Co-Actors - ");
            query =   "SELECT DISTINCT concat(b.first_name,concat(\" \",b.last_name)) \"Co-Actors\"\n" +
                            "FROM (actor a,actor b)\n" +
                            "JOIN acts c ON a.act_id = c.act_id\n" +
                            "JOIN acts d ON c.mov_id = d.mov_id\n" +
                            "WHERE b.act_id = d.act_id AND a.first_name='Shahrukh' AND a.last_name='Khan'\n" +
                            "AND DATEDIFF(b.dob,a.dob)>=3650;";
            rst = stmt.executeQuery(query);

            while(rst.next())
            {
                String co_actors = rst.getString("Co-Actors");
                System.out.println(co_actors);
            }
            rst.close();
            
            //Fifth Query
            System.out.println("\n\t5) Who is the Director in whose movie \"Shahrukh\" \"Khan\" acted most number of times?");
            System.out.println("Director - ");
            query =   "SELECT DISTINCT concat(director.first_name,concat(\" \",director.last_name)) \"Director\"\n" +
                            "FROM actor\n" +
                            "JOIN acts ON actor.act_id = acts.act_id\n" +
                            "JOIN movie ON acts.mov_id = movie.mov_id\n" +
                            "JOIN directs ON directs.mov_id = movie.mov_id\n" +
                            "JOIN director ON director.dir_id = directs.dir_id\n" +
                            "WHERE actor.first_name ='Shahrukh' AND actor.last_name='Khan'\n" +
                            "GROUP BY director.dir_id\n" +
                            "ORDER BY count(*) desc limit 1;";
            rst = stmt.executeQuery(query);

            while(rst.next())
            {
                String director = rst.getString("Director");
                System.out.println(director);
            }
            rst.close();
            
            //closing the statement
            stmt.close();
            
            

            //Dynamic Query
            System.out.print("\t\n\nDYNAMIC QUERY =>");
            //First Query
            System.out.println("\n\t1) List all the films in which an actor with firstname \"Shahrukh\" and lastname \"Khan\" acted");
            System.out.println("Movie Title - ");
            query =   "SELECT movie.title \"Movie Title\"\n" +
                            "FROM movie\n" +
                            "JOIN acts ON acts.mov_id = movie.mov_id\n" +
                            "JOIN actor ON actor.act_id = acts.act_id\n" +
                            "WHERE actor.first_name=? AND actor.last_name=?;";
            dynamicQuery = con.prepareStatement(query);
            dynamicQuery.setString(1,"Shahrukh");
            dynamicQuery.setString(2,"Khan");
            rst = dynamicQuery.executeQuery();

            while(rst.next())
            {
                String movie_name = rst.getString("Movie Title");
                System.out.println(movie_name);
            }
            rst.close();
            
            //Second Query
            System.out.println("\n\t2) List all movies for which \"Shahrukh\" \"Khan\" was an actor as well as director");
            System.out.println("Movie Title - ");
            query =   "SELECT movie.title \"Movie Title\"\n" +
                            "FROM movie\n" +
                            "JOIN directs ON movie.mov_id = directs.mov_id\n" +
                            "JOIN acts ON acts.mov_id = directs.mov_id\n" +
                            "JOIN actor ON actor.act_id = acts.act_id\n" +
                            "JOIN director ON directs.dir_id = director.dir_id\n" +
                            "WHERE actor.first_name=? AND actor.last_name=? AND\n" +
                            "director.first_name =? AND director.last_name =?;";
            dynamicQuery = con.prepareStatement(query);
            dynamicQuery.setString(1,"Shahrukh");
            dynamicQuery.setString(2,"Khan");
            dynamicQuery.setString(3,"Shahrukh");
            dynamicQuery.setString(4,"Khan");
            rst = dynamicQuery.executeQuery();

            while(rst.next())
            {
                String movie_name = rst.getString("Movie Title");
                System.out.println(movie_name);
            }
            rst.close();
            
            //Third Query
            System.out.println("\n\t3) List all \"thrillers\" released after the year 2000 in which \"Shahrukh\" \"Khan\" acted");
            System.out.println("Movie Title - ");
            query =   "SELECT movie.title \"Movie Title\"\n" +
                            "FROM movie\n" +
                            "JOIN acts ON acts.mov_id = movie.mov_id\n" +
                            "JOIN actor ON actor.act_id = acts.act_id\n" +
                            "WHERE actor.first_name=? AND actor.last_name=? AND\n" +
                            "movie.year>? AND (movie.genre LIKE '%Thriller%');";
            dynamicQuery = con.prepareStatement(query);
            dynamicQuery.setString(1,"Shahrukh");
            dynamicQuery.setString(2,"Khan");
            dynamicQuery.setInt(3,2000);
            rst = dynamicQuery.executeQuery();
            
            while(rst.next())
            {
                String movie_name = rst.getString("Movie Title");
                System.out.println(movie_name);
            }
            rst.close();
            
            //Fourth Query
            System.out.println("\n\t4) List all Co-actors of \"Shahrukh\" \"Khan\" who are younger than him by 10 years");
            System.out.println("Co-Actors - ");
            query =   "SELECT DISTINCT concat(b.first_name,concat(\" \",b.last_name)) \"Co-Actors\"\n" +
                            "FROM (actor a,actor b)\n" +
                            "JOIN acts c ON a.act_id = c.act_id\n" +
                            "JOIN acts d ON c.mov_id = d.mov_id\n" +
                            "WHERE b.act_id = d.act_id AND a.first_name=? AND a.last_name=?\n" +
                            "AND DATEDIFF(b.dob,a.dob)>=3650;";
            dynamicQuery = con.prepareStatement(query);
            dynamicQuery.setString(1,"Shahrukh");
            dynamicQuery.setString(2,"Khan");
            rst = dynamicQuery.executeQuery();
            
            while(rst.next())
            {
                String co_actors = rst.getString("Co-Actors");
                System.out.println(co_actors);
            }
            rst.close();
            
            //Fifth Query
            System.out.println("\n\t5) Who is the Director in whose movie \"Shahrukh\" \"Khan\" acted most number of times?");
            System.out.println("Director - ");
            query =   "SELECT DISTINCT concat(director.first_name,concat(\" \",director.last_name)) \"Director\"\n" +
                            "FROM actor\n" +
                            "JOIN acts ON actor.act_id = acts.act_id\n" +
                            "JOIN movie ON acts.mov_id = movie.mov_id\n" +
                            "JOIN directs ON directs.mov_id = movie.mov_id\n" +
                            "JOIN director ON director.dir_id = directs.dir_id\n" +
                            "WHERE actor.first_name =? AND actor.last_name=?\n" +
                            "GROUP BY director.dir_id\n" +
                            "ORDER BY count(*) desc limit 1;";
            dynamicQuery = con.prepareStatement(query);
            dynamicQuery.setString(1,"Shahrukh");
            dynamicQuery.setString(2,"Khan");
            rst = dynamicQuery.executeQuery();

            while(rst.next())
            {
                String director = rst.getString("Director");
                System.out.println(director);
            }
            rst.close();
            
            // closing the prepared statement
            dynamicQuery.close();
            // closing the connection
            con.close();
            
        } 
        catch (SQLException ex) {
            System.err.println("-----SQLException-----");
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("Message: " + ex.getMessage());
            System.err.println("Vendor: " + ex.getErrorCode());
        }
    }
}

