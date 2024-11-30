package com.anantweb.restspringdemo.controller;

import com.anantweb.restspringdemo.model.Employee;
import com.anantweb.restspringdemo.model.NewEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    @GetMapping("/MainController")
    public String landing(){
        return "Hello This is a Main Controller.";
    }

    @ResponseBody
    //@PostMapping("/MainPostController")
    //@RequestMapping(value = "/MainPostController")
    @RequestMapping(value = "/MainPostController", method = RequestMethod.POST)
    public String landingPost(){
        return "Hello This is a Main POST Controller.";
    }

    @GetMapping("/MainController/{name}/{age}")
    public String landing(@PathVariable("name") String name, @PathVariable("age") int age){
        if(name.length()<0) name = "DUMMY";
        if(age <= 0 ) age = 0;
        return "Hello " + name + " your age is-" + age +" & This is a Main Controller.";
    }

    @RequestMapping(value="/home", method = RequestMethod.GET)
    public ModelAndView home(Model model){

        model.addAttribute("name","Anant Dargude");
        model.addAttribute("age",29);

        ModelAndView mav = new ModelAndView("index.html", "myModel", model);

        return mav;
    }

    @Autowired
    Employee employee;
    //    @GetMapping(value="/givejson", produces="application/json")
    @GetMapping(value="/givejson", produces="application/xml")
    public ResponseEntity<Employee> giveJson(){
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<Employee> empReturn = new ResponseEntity<>(employee, headers, HttpStatus.CREATED);
        return empReturn;
    }

    @GetMapping(value="/acceptjson")
    public String acceptJson(){
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Employee> empResponse = rest.getForEntity("http://localhost:8080/givejson", Employee.class);

        return empResponse.getBody().getId()
                + ", " + empResponse.getBody().getName()
                + ", " + empResponse.getBody().getRole()
                + "<hr>" + empResponse.getHeaders();
    }

    @GetMapping(value = {"/jdbcResponse","/check"}, produces = "application/xml")
    public ResponseEntity<List<NewEmployee>> jdbcResponse() throws ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/new_emp_db");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String sql = "select * from new_emp_db.employee";

//        List<NewEmployee> newEmployeeList = new ArrayList<NewEmployee>();

//        jdbcTemplate.query(sql, new RowMapper<List<NewEmployee>>() {
//            @Override
//            public List<NewEmployee> mapRow(ResultSet rs, int rowNum) throws SQLException {
//                while(rs.next()){
//                    NewEmployee e = new NewEmployee();
//
//                    e.setEmpNo(rs.getInt(1));
//                    e.setDateOfBirth(rs.getString(2));
//                    e.setFirstName(rs.getString(3));
//                    e.setLastName(rs.getString(4));
//                    e.setGender(rs.getString(5));
//                    e.setJoinDate(rs.getString(6));
//
//                    newEmployeeList.add(e);
//                }
//                return newEmployeeList;
//            }
//        });

        List<NewEmployee> newEmployeeList = jdbcTemplate.query(sql, new ResultSetExtractor<List<NewEmployee>>() {
            @Override
            public List<NewEmployee> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<NewEmployee> newList = new ArrayList<NewEmployee>();
                while(rs.next()){
                    NewEmployee e = new NewEmployee();

                    e.setEmpNo(rs.getInt(1));
                    e.setDateOfBirth(rs.getString(2));
                    e.setFirstName(rs.getString(3));
                    e.setLastName(rs.getString(4));
                    e.setGender(rs.getString(5));
                    e.setJoinDate(rs.getString(6));

                    newList.add(e);
                }
                return newList;
            }
        });

        ResponseEntity<List<NewEmployee>> responseEntity = new ResponseEntity<>(newEmployeeList, HttpStatus.FOUND);

        return responseEntity;
    }
}
