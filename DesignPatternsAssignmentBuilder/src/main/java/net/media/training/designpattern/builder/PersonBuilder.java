package net.media.training.designpattern.builder;

public class PersonBuilder {
    private Person person;

    public Person build() {
        if (person == null)
            createClient();
        return person;
    }

    public PersonBuilder withName(String name) {
        if (person == null)
            createClient();
        person.setName(name);
        return this;
    }

    public PersonBuilder withId(int id) {
        if (person == null)
            createClient();
        person.setId(id);
        return this;
    }

    public PersonBuilder withCity(String city) {
        if (person == null)
            createClient();
        person.setCity(city);
        return this;
    }

    public PersonBuilder withCountry(String country) {
        if (person == null)
            createClient();
        person.setCountry(country);
        return this;
    }

    private void createClient() {
        person = new Person();
    }
}