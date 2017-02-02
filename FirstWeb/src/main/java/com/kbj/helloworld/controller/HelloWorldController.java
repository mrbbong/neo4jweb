package com.kbj.helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.List;
  
@Controller
public class HelloWorldController {
    //String message = "Welcome to Spring MVC!";
  
    @RequestMapping("/user")
    public ModelAndView showMessage(
            @RequestParam(value = "uid", required = false, defaultValue = "1") String uid) {
        //System.out.println("in controller");
    	
    	List<String> ratingList = new ArrayList<String>();
    	
    	Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j","1234" ) );
		try ( Session session = driver.session() ) {
			
			try ( Transaction tx = session.beginTransaction() ) {
				StatementResult result = tx.run( "MATCH p=(u:User)-[r:Rating]->(m:Movie)  " +
					    "WHERE u.uid = {uid} " +
						"RETURN r.rate AS rate, r.timestamp AS timestamp",
						parameters( "uid", Integer.parseInt(uid)) );
				
				while ( result.hasNext() ) {
					Record record = result.next();
					String rate = record.get( "rate" ).toString();
					String timestamp =  record.get( "timestamp" ).toString();
					ratingList.add(rate + " - " + timestamp);
				}
			}
		}
		driver.close();
  
        ModelAndView mv = new ModelAndView("helloworld");
        mv.addObject("ratingList", ratingList);
        mv.addObject("uid", uid);
        return mv;
    }
}
