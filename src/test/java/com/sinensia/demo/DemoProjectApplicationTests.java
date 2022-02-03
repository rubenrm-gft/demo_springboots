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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class DemoProjectApplicationTests {

    private static final String Y_PARAM_STRING = "&b=";
    @Autowired transient TestRestTemplate restTemplate;

	@Autowired private transient DemoProjectApplication app;

	@Test
	void contextLoads() {
	}

	@Test
	void rootTest() {
		assertThat(restTemplate.getForObject("/", String.class))
				.isEqualTo("Hola ke ase");
	}

	@Test
	void helloTest() {
		assertThat(restTemplate.getForObject("/hello", String.class))
				.isEqualTo("Hello World!");
	}

	@Test
	void helloNameTest(@Autowired TestRestTemplate restTemplate) {
		assertThat(restTemplate.getForObject("/hello?name=Javier", String.class))
				.isEqualTo("Hello Javier!");
	}

	@Test
	void helloNames() {
		String[] arr = {"Javier","Javier Arturo","Rodriguez"};
		for(String name: arr) {
			assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
					.isEqualTo("Hello "+name+"!");
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"Javier",
			"Arturo",
			"Rodriguez"
	})
	void helloParamNames(String name) {
		assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
				.isEqualTo("Hello "+name+"!");
	}

	@DisplayName(value="test multiple input values")
	@ParameterizedTest(name="{displayName} [{index}] ({arguments}) \"{0}\" -> \"{1}\" ")
	@CsvSource({
			"a,            Hello a!",
			"b,            Hello b!",
			",             Hello null!",
			"'',           Hello World!",
			"' ',          Hello  !",
			"first+last,   Hello first last!"
	})
	void helloParamNamesCsv(String name, String expected) {
		assertThat(restTemplate.getForObject("/hello?name="+name, String.class))
				.isEqualTo(expected);
	}

	@Test
	void canAdd() {
		assertThat(restTemplate.getForObject("/add?a=1" + Y_PARAM_STRING + "2", String.class))
				.isEqualTo("3");
	}


	@Test
	void canAddFloatException() {
		assertThrows(RestClientException.class, ()-> restTemplate.getForObject("/add?a=hola" + Y_PARAM_STRING + "2", Float.class));
	}

	@DisplayName("multiple additions")
	@ParameterizedTest(name="{displayName} [{index}] {0} + {1} = {2}")
	@CsvSource({
			"1,   2,   3",
			"1,   1,   2",
			"1.0, 1.0, 2",
			"1,  -2,  -1",
			"1.5, 2,   3.5",
			"'',  2,   2",
			"1.5, 1.5, 3"
	})
	void canAddCsvParameterizedFloat(String a, String b, String expected) {
		assertThat(restTemplate.getForObject("/add?a="+a+ Y_PARAM_STRING +b, Float.class))
				.isEqualTo(Float.parseFloat(expected));
	}

	/* Loss-of-precision by converting Float return value into Integer
	@Test
	void canAddInteger() {
		assertThat(restTemplate.getForObject("/add?a=1.5&b=2", Integer.class))
				.isEqualTo(3.5f);
	}
	*/

	@Nested
	@DisplayName(value="Application tests")
	class AppTests {



		@Test
		void addCanAddNull() {
			Exception thrown = assertThrows(NullPointerException.class, ()-> app.add(null, 2f));
			assertTrue(thrown.toString().contains("NullPointerException"));
			// alternatively check thrown.getMessage().contains("");
		}

	}

	@Nested
	class MultiplicationTests {

		@DisplayName("multiplication")
		@ParameterizedTest(name="{displayName} [{index}] {0} * {1} = {2}")
		@CsvSource({
				"1,   2,   2",
				"1,   1,   1",
				"1.0, 1.0, 1",
				"1,  -2,  -2",
				"1.5, 2,   3",
				"'',  2,   0",
				"1.5, 1.5, 2.25"
		})
		void canMultiply(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/multiply?a="+a+ Y_PARAM_STRING +b, String.class))
					.isEqualTo(expected);
		}

	}

	@Nested
	class DivideTests {
		@DisplayName("multiple divisions")
		@ParameterizedTest(name="{displayName} [{index}] {0} / {1} = {2}")
		@CsvSource({
				"10,   2,   5.00",
				"10,  -1, -10.00",
				" 1.0, 1.0, 1.00",
				"10,   3,   3.33"
		})
		void canAddCsvParameterizedFloat(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/divide?a="+a+ Y_PARAM_STRING +b, Float.class))
					.isEqualTo(Float.parseFloat(expected));
		}

		@Test
		void divideByZero() {
			assertThrows(RestClientException.class, ()-> restTemplate.getForObject("/divide?a=10" + Y_PARAM_STRING + "0", Float.class));
		}
	}


	@Nested
	class SquareRootTests {
		@DisplayName("multiple roots")
		@ParameterizedTest(name="{displayName} [{index}] {0}'(-1/2) = {1}")
		@CsvSource({
				"4, 2",
				"100, 10",
				"1.0, 1.0",
				"-3, 0",
				"-0.12, -0",
				"2, 1.41"
		})
		void canSqrtCsvParameterizedFloat(String a, String expected) {
			assertThat(restTemplate.getForObject("/sqrt?a="+a, Float.class))
					.isEqualTo(Float.parseFloat(expected));
		}


	}

	@Nested
	class SubtractionTests {

		@DisplayName("subtraction")
		@ParameterizedTest(name="{displayName} [{index}] {0} * {1} = {2}")
		@CsvSource({
				"1,   2,   -1",
				"1,   1,   0",
				"1.0, 1.0, 0",
				"5,  2,  3",
				"3.5, 2, 1.5"

		})
		void canSubtract(String a, String b, String expected) {
			assertThat(restTemplate.getForObject("/subtract?a="+a+ Y_PARAM_STRING +b, String.class))
					.isEqualTo(expected);
		}

	}

}
