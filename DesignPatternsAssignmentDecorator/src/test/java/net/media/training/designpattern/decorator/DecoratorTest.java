package net.media.training.designpattern.decorator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 18, 2011
 * Time: 10:43:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class DecoratorTest {
    private Application application1 = new Application(9, 1500, 115);
    private Application application2 = new Application(9.5f, 1550, 30);
    private Application application3 = new Application(9.5f, 600, 117);
    private Application application4 = new Application(4.0f, 1550, 119);
    private Registrar registrar = new Registrar();

    @Test
    public void allApplicantsExceptTheLastAreSelectedOnGPACriteria() {
        Criteria criteria = new GPAEval();
        assertTrue(registrar.evaluate(application1, criteria));
        assertTrue(registrar.evaluate(application2, criteria));
        assertTrue(registrar.evaluate(application3, criteria));
        assertFalse(registrar.evaluate(application4, criteria));
    }

    @Test
    public void firstTwoApplicantsAreSelectedOnGPAAndGRECriteria() {
        Criteria criteria = new GREDecorator(new GPAEval());
        assertTrue(registrar.evaluate(application1, criteria));
        assertTrue(registrar.evaluate(application2, criteria));
        assertFalse(registrar.evaluate(application3, criteria));
        assertFalse(registrar.evaluate(application4, criteria));
    }


    @Test
    public void firstAndThirdApplicantsAreSelectedOnGPAAndTOEFLCriteria() {
        Criteria criteria = new TOEFLDecorator(new GPAEval());
        assertTrue(registrar.evaluate(application1, criteria));
        assertFalse(registrar.evaluate(application2, criteria));
        assertTrue(registrar.evaluate(application3, criteria));
        assertFalse(registrar.evaluate(application4, criteria));
    }


    @Test
    public void onlyFirstApplicantIsSelectedOnGRE_GPAAndTOEFLCriteria() {
        Criteria criteria = new GREDecorator(new TOEFLDecorator(new GPAEval()));
        assertTrue(registrar.evaluate(application1, criteria));
        assertFalse(registrar.evaluate(application2, criteria));
        assertFalse(registrar.evaluate(application3, criteria));
        assertFalse(registrar.evaluate(application4, criteria));
    }

}
