package life.qbic.portal.portlet;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link SamplePortlet}.
 */
public class SamplePortletTest {

    @Test
    public void mainUIExtendsQBiCPortletUI() {
        assertTrue("The main UI class must extend life.qbic.portlet.QBiCPortletUI", 
            QBiCPortletUI.class.isAssignableFrom(SamplePortlet.class));
    }

    @Test
    public void mainUIIsNotQBiCPortletUI() {
        assertNotEquals("The main UI class must be different to life.qbic.portlet.QBiCPortletUI",
            QBiCPortletUI.class, SamplePortlet.class);
    }
}