package com.sinensia.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.client.RestClientException;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class DemoProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void rootTest(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/", String.class))
				.isEqualTo("Hola ke ase");
	}

	@Test
	void helloTest(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/hello", String.class))
				.isEqualTo("Hello World!");
	}

	@Test
	void helloNames(@Autowired TestRestTemplate restTemplate) {
		String[] arr = {"Ruben","Ruben+Ramirez","Ramirez"};
		for(String name: arr) {
			assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
					.isEqualTo("Hello "+name+"!");
		}
	}

	@Autowired TestRestTemplate restTemplate;
	@ParameterizedTest
	@ValueSource(strings = {"Javier","Javier+Arturo","Arturo","Rodriguez"})
	void helloParamNames(String name) {
		assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
				.isEqualTo("Hello "+name+"!");
	}

	@ParameterizedTest
	@CsvSource({
			"a, Hello a!",
			"B, Hello B!"
	})
	void helloParanNamesCSV(String name, String expected){
		assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
				.isEqualTo(expected);
	}


	@Test
	void canAdd(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=2", String.class))
				.isEqualTo("3");
	}

	@Test
	void canAddNull(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=", String.class))
				.isEqualTo("1");
	}

	@Test
	void canAddFraction(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/add?a=1&b=2.5", String.class))
				.isEqualTo("3.5");
	}

	@Test
	void canAddNegative(@Autowired TestRestTemplate restTemplate){
		assertThat(restTemplate.getForObject("/add?a=-1&b=2", String.class)).isEqualTo("1");
	}

	@Test
	void appCanAddNUll(){
		app.adding(0, 2f);
	}

	@ParameterizedTest
	@CsvSource({
			"1,2,3"
	})
	void canAddCsvParameterized(String a, String b, String resultado){
		assertThat(restTemplate.getForObject("/add?a="+a+"&b="+b, String.class))
				.isEqualTo(resultado);
	}

	@Test
	void canAddFloat(){
		assertThat(restTemplate.getForObject("/add?=a=1.5&b=2", Float.class)).isEqualTo(3.5f);
	}

	/*
	@Test
	void canAddFloatException(){
		Exception thrown = assertThrows(RestClientException.class()->{
			restTemplate.getForObject("/add?a=hola&b=2", Float.class)
		})
	}
*/
	@Autowired
	private DemoProjectApplication app;

	@Test
	void appCanAdd(){
		assertThat(app.adding(1f,2f)).isEqualTo(3);
	}

	@Nested
	class MultiplicationTests{
		@DisplayName("various products")
		@ParameterizedTest(name="{displayName} [{index}] {0} + {1} = {2}")
		@CsvSource({
				"1,   2,   2",
				"2,   6,   12",
				"1.0, 1.0, 1",
				"1,  -2,  -2",
				"-1, -2,   2",
				"'',  2,   0",
				"1.5, 1.5, 2.25"
		})
		void canMUltyplyCsvParameterized(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/multi?a="+a+"&b="+b, String.class))
					.isEqualTo(expected);
		}
	}


}
