package net.media.training.designpattern.builder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by IntelliJ IDEA.
 * User: path
 * Date: Jul 19, 2011
 * Time: 9:13:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class PeopleSourceTest {
    PersonBuilder personBuilder;

    @Test
    public void TestPeopleXml() {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new PersonBuilder().withName("Wu").withId(25).withCity("Shanghai").withCountry("China").build());
        persons.add(new PersonBuilder().withName("Kobayashi").withId(200).withCity("Kanto").withCountry("Japan").build());
        persons.add(new PersonBuilder().withName("Vasily").withId(1).withCity("Leningrad").withCountry("Russia").build());
        String actual = PeopleDataSource.getPeopleXml(persons);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><People number=\"3\"><Person id=\"25\" name=\"Wu\"><Address><City>Shanghai</City><Country>China</Country></Address></Person><Person id=\"200\" name=\"Kobayashi\"><Address><City>Kanto</City><Country>Japan</Country></Address></Person><Person id=\"1\" name=\"Vasily\"><Address><City>Leningrad</City><Country>Russia</Country></Address></Person></People>", actual);
    }
}