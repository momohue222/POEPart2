package poepart3;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import poepart2.registration;

public class registrationNGTest {

    @Test
    public void testCheckUserName() {
        registration instance = new registration();
        assertTrue(instance.checkUserName("kyl_1"));
        assertFalse(instance.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity() {
        registration instance = new registration();
        assertTrue(instance.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertFalse(instance.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber() {
        registration instance = new registration();
        assertTrue(instance.checkCellPhoneNumber("+27838968976"));
        assertFalse(instance.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testRegisterUser() {
        registration instance = new registration();
        String validResult = instance.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(validResult, "Username successfully captured. Password successfully captured. Cell phone number successfully added.");
    }

    @Test
    public void testRegisterStatus() {
        registration instance = new registration();
        assertTrue(instance.registerStatus("kyl_1", "Ch&&sec@ke99!", "+27838968976"));
        assertFalse(instance.registerStatus("kyl_1", "password", "08966553"));
    }
}
