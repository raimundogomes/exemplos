package br.gov.testes.exemplo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class PacienteServiceWSTest {

	private static final String URL = "http://localhost/sgr/service/paciente/prontuario/1";
	Client client;

	@Before
	public void setUp() throws Exception {

		client = Client.create();

	}
	
	@After
	public void setDown(){
		client.destroy();
	}

	@Test
	@Ignore
	public void test() {

		WebResource webResource = client.resource(URL);

		String input = "{\"nome\":\"mais em\",\"prontuario\":\"1\",\"nomeMae\":\"Mariazinha\",\"cpf\":\"aa\" ,\"cns\":\"098\",\"dataNascimento\":\"18/12/2000\"}";

		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);

		webResource.post();

		if (response.getStatus() != 201) {
			fail("Failed : HTTP error code : " + response.getStatus());
		}

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);

	}

	@Test
	public void testConsultalPaciente() {

		WebResource webResource = client.resource(URL);

		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		String output = response.getEntity(String.class);

		Gson gson = new GsonBuilder().create();

		Paciente paciente = gson.fromJson(output, Paciente.class);

		Assert.assertEquals("machado.assis@literatura.br", paciente.getEmail());

		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

	}

	public static void main(String[] args) throws IOException {
		
		String path = new File("src/resources/entrada/arquivo.csv").getCanonicalPath();
		
		Scanner scanner = new Scanner(new FileReader(path));
		
		scanner.useDelimiter(";|\\n");
		
		while (scanner.hasNext()) {
			String campo1 = scanner.next();
			String campo2 = scanner.next();
			String campo3 = scanner.next();
			String campo4 = scanner.next();
			String campo5 = scanner.next();
			System.out.print(campo1);
			System.out.print(campo2);
			System.out.print(campo3);
			System.out.print(campo4);
			System.out.println(campo5);
		}
		scanner.close();
	}

}
