package oc.ceiba.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import oc.ceiba.domain.Person;
import oc.ceiba.testdatabuilder.PersonTestDataBuilder;

public class NotifyPersonServiceTest {
	private NotifyPersonService notifyPersonService;
	private EmailService emailService;

	@Before
	public void setup() {
		// emailService = new EmailService();
		emailService = Mockito.mock(EmailService.class);
		Mockito.when(emailService.sendMail(Mockito.anyString())).thenReturn("Hola mundo");
		notifyPersonService = new NotifyPersonService(emailService);
	}

	@Test
	public void notifyTest() {
		// Arrange
		Person person = new PersonTestDataBuilder().build();
		// Act
		String message = notifyPersonService.notify(person);
		// Assert
		System.out.println(message);
		Assert.assertNotNull(message);
	}
}
