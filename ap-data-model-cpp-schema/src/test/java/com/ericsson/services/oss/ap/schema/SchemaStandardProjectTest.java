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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Runs validation against all standard project samples that are delivered as part of schemas and samples.
 */
@RunWith(MockitoJUnitRunner.class)
public class SchemaStandardProjectTest extends SchemaTest {

    private static final String PROJECT_DIRECTORY = SAMPLES_AND_SCHEMAS_DIRECTORY + "/standard-project";
    private static final String NODE_DIRECTORY = PROJECT_DIRECTORY + "/Node1";

    @Test
    public void when_projectInfo_is_validated_then_verify_validates_against_schema() {
        final List<String> projectInfoXSDs = new ArrayList<>();
        final String xml = retriveResourceAsString(PROJECT_DIRECTORY + "/projectInfo.xml");
        projectInfoXSDs.add(retriveResourceAsString("/ProjectInfo.xsd"));
        assertTrue(validateXmlAgainstSchema(xml, projectInfoXSDs).isValid());
    }

    @Test
    public void when_nodeInfo_is_validated_then_verify_validates_against_schema() {
        final List<String> projectInfoXsds = new ArrayList<>();
        final String xml = retriveResourceAsString(NODE_DIRECTORY + "/nodeInfo.xml");
        projectInfoXsds.add(retriveResourceAsString("/erbs/ap/NodeInfo.xsd"));

        assertTrue(validateXmlAgainstSchema(xml, projectInfoXsds).isValid());
    }

    @Test
    public void when_node_SiteBasic_is_validated_verify_it_validates_against_schema() {
        final List<String> projectInfoXsds = new ArrayList<>();
        final String xml = retriveResourceAsString(NODE_DIRECTORY + "/siteBasic.xml");
        projectInfoXsds.add(retriveResourceAsString("/erbs/g.1.220/SiteBasic.xsd"));

        assertTrue(validateXmlAgainstSchema(xml, projectInfoXsds).isValid());
    }

    @Test
    public void when_node_SiteEquipment_is_validated_verify_it_validates_against_schema() {
        final List<String> projectInfoXsds = new ArrayList<>();
        final String xml = retriveResourceAsString(NODE_DIRECTORY + "/siteEquipment.xml");
        projectInfoXsds.add(retriveResourceAsString("/erbs/g.1.220/SiteEquipment.xsd"));

        assertTrue(validateXmlAgainstSchema(xml, projectInfoXsds).isValid());
    }

    @Test
    public void when_node_SiteInstallation_is_validated_verify_it_validates_against_schema() {
        final List<String> projectInfoXsds = new ArrayList<>();
        final String xml = retriveResourceAsString(NODE_DIRECTORY + "/siteInstallation.xml");
        projectInfoXsds.add(retriveResourceAsString("/erbs/g.1.220//SiteInstallation.xsd"));

        assertTrue(validateXmlAgainstSchema(xml, projectInfoXsds).isValid());
    }

    @Test
    public void when_node_radio_is_validated_then_verify_it_is_well_formed() {
        final String xml = retriveResourceAsString(NODE_DIRECTORY + "/Bulk-CM-Configuration-file.xml");
        assertTrue(validateXmlWellFormedness(xml));
    }

}
