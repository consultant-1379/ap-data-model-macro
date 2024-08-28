/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2015
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
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * Used to resolve the location of all the schema resources.
 */
public class ResourceResolver implements LSResourceResolver {

    @SuppressWarnings("resource")
    @Override
    public LSInput resolveResource(final String type, final String namespaceURI, final String publicId, final String systemId, final String baseURI) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(systemId);
        if (stream == null) {
            try {final URL url = getClass().getResource("/erbs/ap/" +systemId);
            final Path resPath = Paths.get(url.toURI());
            final String resourceContent = new String(java.nio.file.Files.readAllBytes(resPath), "UTF8");
            stream = new ByteArrayInputStream(resourceContent.getBytes());
            } catch (final Exception e) {
                stream = null;
            }
        }
        final LSInputImpl input = new LSInputImpl(publicId, systemId, stream);
        return input;
    }
}
