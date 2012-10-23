package io;
import java.sql.*;
import java.util.Arrays;

public class TestSelect {
  public static void main(String[] args) throws Exception {
      Class.forName("org.sqlite.JDBC");
      Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
      Statement stat = conn.createStatement();
//      stat.executeUpdate("drop table if exists people;");
//      stat.executeUpdate("create table people (name, occupation);");
//      PreparedStatement prep = conn.prepareStatement(
//          "insert into people values (?, ?);");
//
//      prep.setString(1, "Gandhi");
//      prep.setString(2, "politics");
//      prep.addBatch();
//      prep.setString(1, "Turing");
//      prep.setString(2, "computers");
//      prep.addBatch();
//      prep.setString(1, "Wittgenstein");
//      prep.setString(2, "smartypants");
//      prep.addBatch();
//
//      conn.setAutoCommit(false);
//      prep.executeBatch();
//      conn.setAutoCommit(true);

//      ResultSet rs = stat.executeQuery("select * from RelMateria;");
//      while (rs.next()) {
//          System.out.println("Id Materia  : " + rs.getInt(1));
//          System.out.println("Id Tipo Aula: " + rs.getInt(2));
//          System.out.println("Id Recurso  : " + rs.getInt(3));
//          System.out.println("Id Facultad : " + rs.getInt(4));
//          System.out.println("Id Plan     : " + rs.getInt(5));
//          System.out.println("Id Cupo Max.: " + rs.getInt(6));
//          System.out.println("Id Cupo Prob: " + rs.getInt(7));
//          System.out.println("------------------");
//      }
      ResultSet rs = stat.executeQuery("select * from RelDocente;");
      while (rs.next()) {
          System.out.println("Id Docente  : " + rs.getInt(1));
          System.out.println("Id Materia  : " + rs.getInt(2));
          System.out.println("Id Dia      : " + rs.getInt(3));
          System.out.println("Id Turno    : " + rs.getInt(4));
          System.out.println("------------------");
      }
      rs.close();
      conn.close();
  }
}