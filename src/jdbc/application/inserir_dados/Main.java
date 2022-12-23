package jdbc.application.inserir_dados;

import jdbc.db.DB;
import jdbc.db.DbException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main { //Inserindo dados no banco
    public static void main(String[] args) {
        Connection connection;
        PreparedStatement statement = null;
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

        try{
            connection = DB.getConnection();

            statement = connection.prepareStatement(
                    "INSERT INTO seller" +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                            "VALUES " +
                            "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, "Davi Ximenes");
            statement.setString(2, "dropperdavi@gmail.com");
            statement.setDate(3, new Date(date.parse("28/08/2002").getTime()));
            statement.setDouble(4, 3500.0);
            statement.setInt(5, 1);

            int rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                while (resultSet.next()){
                    int id = resultSet.getInt(1);
                    System.out.println("id = " + id + " affected");
                }
            } else {
                System.out.println("No rows affected");
            }

        }catch (SQLException e){
            throw new DbException(e.getMessage());

        } catch (ParseException e) {
            throw new RuntimeException(e);

        } finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
