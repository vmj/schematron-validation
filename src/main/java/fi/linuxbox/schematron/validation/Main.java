package fi.linuxbox.schematron.validation;

import name.dmaus.schxslt.Result;
import name.dmaus.schxslt.Schematron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

public final class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(final String... args) throws Exception {
        // This example Schematron file was obtained from
        //   https://docs.peppol.eu/poacc/upgrade-3/
        // Specifically,
        //   https://docs.peppol.eu/poacc/upgrade-3/files/PEPPOLBIS-Upgrade-Schematron.zip
        final Source sch = getResource("/PEPPOLBIS-Upgrade-Schematron/PEPPOLBIS-T01.sch");

        // The example XML document was obtained from the same page, specifically
        //   https://docs.peppol.eu/poacc/upgrade-3/files/PEPPOLBIS-Examples.zip
        final Source xml = getResource("/PEPPOLBIS-Examples/Order_Example.xml");

        final Schematron schematron = new Schematron(sch);
        final Result result = schematron.validate(xml);

        if(result.isValid()) {
            log.info("Document is valid.");
        } else {
            log.warn("Document is NOT valid. Validation messages:");
            result.getValidationMessages().forEach(log::warn);
        }
    }

    private static Source getResource(final String resource) {
        final InputStream inputStream = Main.class.getResourceAsStream(resource);
        return new StreamSource(inputStream, resource);
    }
}
