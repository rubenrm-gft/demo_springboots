package com.sinensia.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProjectApplication.class, args);
	}

	@GetMapping("/")
	public String root() {
		return "Hola ke ase";
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/add")
	public String adding(@RequestParam(value = "a", defaultValue = "0") float a, @RequestParam(value="b", defaultValue = "0") float b) {
		float resultado = 0;
		System.out.println("a: "+a+" y b:"+b);
		resultado = a+b;
		if(resultado !=0 && checkIfFloat(resultado)==-1){ //es decimal
			return Float.toString(resultado);
		}
		else{
			return Integer.toString(checkIfFloat(resultado));
		}
	}

	@GetMapping("/multi")
	public String multiply(@RequestParam(value = "a", defaultValue = "0") float a, @RequestParam(value="b", defaultValue = "0") float b) {
		float resultado = 0;
		System.out.println("a: "+a+" y b:"+b);
		resultado = a*b;
		if(resultado !=0 && checkIfFloat(resultado)==-1){ //es decimal
			return Float.toString(resultado);
		}
		else{
			return Integer.toString(checkIfFloat(resultado));
		}
	}


	public int checkIfFloat(float check){
		int auxA = Math.round(check);
		if(check-auxA != 0){
			return -1;
		}
		else{
			return auxA;
		}
	}
}
