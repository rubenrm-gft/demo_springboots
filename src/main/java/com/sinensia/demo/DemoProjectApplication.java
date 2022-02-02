package com.sinensia.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Generated;
import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_DOWN;

@SpringBootApplication
@RestController
public class DemoProjectApplication {

	@Generated(value="org.springframework.boot")
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
	public Object add(
			@RequestParam(value="a", defaultValue = "0") Float a,
			@RequestParam(value="b", defaultValue = "0") Float b
	) {
		float sum = a+b;
		float decimals = sum - (int) sum;
		if(decimals!=0) {
			return sum;
		}
		return (int) sum;
	}

	@GetMapping("/multiply")
	public Object multiply(
			@RequestParam(value="a", defaultValue = "0") Float a,
			@RequestParam(value="b", defaultValue = "0") Float b
	) {
		float product = a * b;
		float decimals = product - (int) product;
		if(decimals!=0) {
			return product;
		}
		return (int) product;
	}

	@GetMapping("/subtract")
	public Object subtract(
			@RequestParam(value="a", defaultValue = "0") Float a,
			@RequestParam(value="b", defaultValue = "0") Float b
	) {
		float sub = a - b;
		float decimals = sub - (int) sub;
		if(decimals!=0) {
			return sub;
		}
		else{
			return (int) sub;
		}
	}

	@GetMapping("/divide")
	public BigDecimal divide(
			@RequestParam(value="a", defaultValue = "0") BigDecimal a,
			@RequestParam(value="b", defaultValue = "0") BigDecimal b
	) {
		return a.divide(b, 2, HALF_DOWN);
	}

	@GetMapping("/sqrt")
	public BigDecimal squareroot(
			@RequestParam(value="a", defaultValue = "0") double a
	) {
		if(a<0){
			a = 0;
		}
			return truncateDecimal(Math.sqrt(a));

	}

	private static BigDecimal truncateDecimal(double x)
	{
			return new BigDecimal(String.valueOf(x)).setScale(2, BigDecimal.ROUND_CEILING);

	}

}
