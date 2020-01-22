package life.qbic.portal.portlet

import com.vaadin.annotations.Theme
import com.vaadin.annotations.Widgetset
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Button
import com.vaadin.ui.Layout
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import groovy.transform.CompileStatic
import groovy.util.logging.Log4j2

import javax.xml.transform.TransformerFactoryConfigurationError

/**
 * Entry point for portlet docx4j-investigator-portlet. This class derives from {@link QBiCPortletUI}, which is found in the {@code portal-utils-lib} library.
 *
 * @see <ahref=https://github.com/qbicsoftware/portal-utils-lib>    portal-utils-lib</a>
 */
@Theme("mytheme")
@SuppressWarnings("serial")
@Widgetset("life.qbic.portal.portlet.AppWidgetSet")
@Log4j2
@CompileStatic
class SamplePortlet extends QBiCPortletUI {

    @Override
    protected Layout getPortletContent(final VaadinRequest request) {
        log.info "Generating content for {}"

        def layout = new VerticalLayout()
        def text = new TextField("Welcome", "This is shown by default.")
        def button = new Button("Simulate docx4j", new Button.ClickListener() {
            @Override
            void buttonClick(Button.ClickEvent clickEvent) {
                log.debug("Calling simulation method")
                try {
                    SampleClass.simulateInstantiateTransformerFactory()
                } catch (Error e) {
                    log.error(e.getClass().getSimpleName() + " in docx4j simulation.")
                    text.setValue("Failure: " + e)
                    text.setWidth("100%")
                    log.error(e.getMessage(), e)
                    log.info(System.getProperties())
                    return
                }
                log.debug("Successfully simulated Transformer Factory creation.")
                text.setValue("Success")
            }
        })
        layout.addComponents(text, button)
        return layout
    }
}

@CompileStatic
@Log4j2
class SampleClass {
    private static String TRANSFORMER_FACTORY_PROCESSOR_XALAN = "org.docx4j.org.apache.xalan.processor.TransformerFactoryImpl";
    private static javax.xml.transform.TransformerFactory transformerFactory;

    def static simulateInstantiateTransformerFactory() {
        String originalSystemProperty = System.getProperty("javax.xml.transform.TransformerFactory")
        try {
            log.info("original system property: " + originalSystemProperty)
            System.setProperty("javax.xml.transform.TransformerFactory",
                    TRANSFORMER_FACTORY_PROCESSOR_XALAN);
            log.info("new system property: " + System.getProperty("javax.xml.transform.TransformerFactory"))
            transformerFactory = javax.xml.transform.TransformerFactory
                    .newInstance();

        } catch(Exception e) {
            log.error(e)
            throw e
        } catch(TransformerFactoryConfigurationError e) {
            log.error(e)
            throw e
        } catch(Error e) {
            throw e
        } finally {
            // We've got our factory now, so set it back again!
            if (originalSystemProperty == null) {
                System.clearProperty("javax.xml.transform.TransformerFactory");
            } else {
                System.setProperty("javax.xml.transform.TransformerFactory",
                        originalSystemProperty);
            }
        }
    }
}
