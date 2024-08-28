/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2016
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.services.oss.ap.schema;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.InputSource;

/**
 * Runs validation against all samples that are delivered as part of schemas and samples
 */
abstract class SchemaTest {

    protected final static String SAMPLES_AND_SCHEMAS_DIRECTORY = "/erbs/default";
    protected final static SchemaFactory SCHEMA_FACTORY = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    protected String substituteBatchValues(String xml, final Map<String, String> substitutionValues) {
        final Matcher m = Pattern.compile("%(.*?)%").matcher(xml);
        while (m.find()) {
            final String matchedSubstitutionValue = m.group(1);
            xml = xml.replace("%" + matchedSubstitutionValue + "%", substitutionValues.get(matchedSubstitutionValue));
        }
        return xml;
    }

    protected String retriveResourceAsString(final String resource) {
        try {
            final URL url = getClass().getResource(resource);
            final Path resPath = java.nio.file.Paths.get(url.toURI());
            final String resourceContent = new String(java.nio.file.Files.readAllBytes(resPath), "UTF8");
            return resourceContent;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected ValidationResult validateXmlAgainstSchema(final String xml, final List<String> xsds) {
        try {
            int count = 0;
            final Source source[] = new Source[xsds.size()];
            for (final String xsd : xsds) {
                source[count] = new StreamSource(new ByteArrayInputStream(xsd.getBytes()));
                count++;
            }

            SCHEMA_FACTORY.setResourceResolver(new ResourceResolver());
            final Schema schema = SCHEMA_FACTORY.newSchema(source);
            final javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(xml.getBytes())));
            return new ValidationResult(true, null);
        } catch (final Exception exception) {
            return new ValidationResult(false, exception.getMessage());
        }
    }

    protected boolean validateXmlWellFormedness(final String xml) {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(new InputSource(new StringReader(xml)));
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
}
