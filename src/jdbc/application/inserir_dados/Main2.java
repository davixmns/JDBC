package jdbc.application.inserir_dados;

import jdbc.db.DB;
import jdbc.db.DbException;

import java.sql.*;

public class Main2 {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement statement = null;

        try{
            connection = DB.getConnection();

            statement = connection.prepareStatement(
                    "INSERT INTO department (Name) VALUES  ('D1'), ('D2')",
                    Statement.RETURN_GENERATED_KEYS
            );

            int rowsAffected = statement.executeUpdate();

            if(rowsAffected > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                while(resultSet.next()){
                    int id = resultSet.getInt(1);
                    System.out.println("id = " + id);
                }
            }

        }catch (SQLException e){
            throw new DbException(e.getMessage());

        }finally {
            DB.closeStatement(statement);
            DB.closeConnection();
        }
    }
}
