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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.w3c.dom.ls.LSInput;

/**
 * Used in Resource Resolver to supply schema values to the Schema Factory.
 */
public class LSInputImpl implements LSInput {

    private String publicId;
    private String systemId;

    @Override
    public String getPublicId() {
        return publicId;
    }

    @Override
    public void setPublicId(final String publicId) {
        this.publicId = publicId;
    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public InputStream getByteStream() {
        return null;
    }

    @Override
    public boolean getCertifiedText() {
        return false;
    }

    @Override
    public Reader getCharacterStream() {
        return null;
    }

    @Override
    public String getEncoding() {
        return null;
    }

    @Override
    public String getStringData() {
        synchronized (inputStream) {
            try {
                final byte[] input = new byte[inputStream.available()];
                inputStream.read(input);
                final String contents = new String(input);
                return contents;
            } catch (final IOException e) {
                return null;
            }
        }
    }

    @Override
    public void setBaseURI(final String baseURI) {
    }

    @Override
    public void setByteStream(final InputStream byteStream) {
    }

    @Override
    public void setCertifiedText(final boolean certifiedText) {
    }

    @Override
    public void setCharacterStream(final Reader characterStream) {
    }

    @Override
    public void setEncoding(final String encoding) {
    }

    @Override
    public void setStringData(final String stringData) {
    }

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

    public BufferedInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(final BufferedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    private BufferedInputStream inputStream;

    public LSInputImpl(final String publicId, final String sysId, final InputStream input) {
        this.publicId = publicId;
        systemId = sysId;
        inputStream = new BufferedInputStream(input);
    }
}