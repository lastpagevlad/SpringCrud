package org.crudSpringApp.dao;


import org.crudSpringApp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //СОЗДАНИЕ ПОДКЛЮЧЕНИЯ К БАЗЕ ДАННЫХ ВРУЧНУЮ, ТО ЕСТЬ БЕЗ JDBCTEMPLATE, АНАЛОГ ТАКОГО ПОДКЛЮЧЕНИЯ ОПИСАН СЕЙЧАС В СПРИНГ КОНФИГЕ, КАК БИН
//    private static final String URL = "jdbc:postgresql://localhost:5433/first_db";
//    private static final String USERNAME = "postgres";
//    private static final String PASSWORD = "soad13";
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//        } catch (SQLException throwables){
//            throwables.printStackTrace();
//        }
//    }


//  До использования бина
//    public List<Person> index(){
//        List<Person> people = new ArrayList<>();
//        try {
//            Statement statement = connection.createStatement();
//            String SQL = "SELECT * FROM Person";
//            ResultSet resultSet = statement.executeQuery(SQL);
//            while(resultSet.next()){
//                Person person = new Person();
//                person.setId(resultSet.getInt("id"));
//                person.setName(resultSet.getString("name"));
//                person.setEmail(resultSet.getString("email"));
//                person.setAge(resultSet.getInt("age"));
//                people.add(person);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return people;
//    }
    public List<Person> index(){
        return jdbcTemplate.query("select * from person", new PersonMapper());
    }

    //Так же можно не писать свой PersonMapper, а воспользоваться стандартным бином, который будет работать точно так же, как и дефолтный ручной.
//    public List<Person> index(){
//        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
//    }



//    public Person show(int id){
//        Person person = null;
//        try {
//            PreparedStatement preparedStatement =
//                    connection.prepareStatement("Select * from Person where id=?");
//            preparedStatement.setInt(1,id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            person = new  Person();
//            person.setId(resultSet.getInt("id"));
//            person.setName(resultSet.getString("name"));
//            person.setEmail(resultSet.getString("email"));
//            person.setAge(resultSet.getInt("age"));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return person;
//    }

    public Person show(int id){
        return (Person) jdbcTemplate.query("select * from person where id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

//    public void save(Person person){
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Person VALUES (1,?,?,?)");
//            preparedStatement.setString(1,person.getName());
//            preparedStatement.setInt(2,person.getAge());
//            preparedStatement.setString(3,person.getEmail());
//            preparedStatement.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//
//    }
public void save(Person person){
        jdbcTemplate.update("insert into person values(1,?,?,?)", person.getName(),person.getAge(),person.getEmail());
}

//    public void update(int id, Person updatePerson){
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Person set name=?, age=?, email=? where id=?");
//            preparedStatement.setString(1,updatePerson.getName());
//            preparedStatement.setInt(2,updatePerson.getAge());
//            preparedStatement.setString(3,updatePerson.getEmail());
//            preparedStatement.setInt(4,id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

public void update(int id, Person updatePerson){
        jdbcTemplate.update("update person set name=?, age=?, email=? where id=?", updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), id);
}

//    public void delete(int id){
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("delete from person where id=?");
//            preparedStatement.setInt(1,id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

public void delete(int id){
    jdbcTemplate.update("delete fromn person where id=?", id);
}
}
