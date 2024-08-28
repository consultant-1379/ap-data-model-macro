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
 * Runs validation against all batch project samples that are delivered as part of schemas and samples.
 */
@RunWith(MockitoJUnitRunner.class)
public class SchemaBatchProjectTest extends SchemaTest {

    private final static String BATCH_PROJECT_DIRECTORY = SAMPLES_AND_SCHEMAS_DIRECTORY + "/batch-project";
    private final static String HARDWARE_SERIAL_NUMBER = "ABC123456789";
    private final static String IP_ADDRESS = "1.2.3.4";
    private final static String NODE_NAME = "NodeOne";
    private final static String USER_LABEL = "Athlone-East";

    private static final Map<String, String> PROJECT_INFO_SUBSTITUION_VALUES;
    private static final Map<String, String> NODE_INFO_SUBSTITUTION_VALUES;
    private static final Map<String, String> SITE_BASIC_SUBSTITUTION_VALUES;
    private static final Map<String, String> SITE_INSTALLATION_SUBSTITUATION_VALUES;
    private static final Map<String, String> RADIO_SUBSTITUTION_VALUES;
    private static final Map<String, String> TRANSPORT_SUBSTITION_VALUES;

    static {
        PROJECT_INFO_SUBSTITUION_VALUES = new HashMap<>();
        PROJECT_INFO_SUBSTITUION_VALUES.put("NodeName", NODE_NAME);
    }

    static {
        NODE_INFO_SUBSTITUTION_VALUES = new HashMap<>();
        NODE_INFO_SUBSTITUTION_VALUES.put("NodeName", NODE_NAME);
        NODE_INFO_SUBSTITUTION_VALUES.put("NodeIpAddress", IP_ADDRESS);
        NODE_INFO_SUBSTITUTION_VALUES.put("UserLabel", USER_LABEL);
        NODE_INFO_SUBSTITUTION_VALUES.put("HardwareSerialNumber", HARDWARE_SERIAL_NUMBER);
    }

    static {
        SITE_BASIC_SUBSTITUTION_VALUES = new HashMap<>();
        SITE_BASIC_SUBSTITUTION_VALUES.put("SB_ip_dnsServer1", IP_ADDRESS);
        SITE_BASIC_SUBSTITUTION_VALUES.put("SB_ip_dnsServer2", IP_ADDRESS);
        SITE_BASIC_SUBSTITUTION_VALUES.put("SB_ip_ntpServerAddressPrimary", IP_ADDRESS);
    }

    static {
        SITE_INSTALLATION_SUBSTITUATION_VALUES = new HashMap<>();
        SITE_INSTALLATION_SUBSTITUATION_VALUES.put("SI_oam_ip", IP_ADDRESS);
        SITE_INSTALLATION_SUBSTITUATION_VALUES.put("NodeName", NODE_NAME);
        SITE_INSTALLATION_SUBSTITUATION_VALUES.put("rbsIntegrationCode", "31");
    }

    static {
        RADIO_SUBSTITUTION_VALUES = new HashMap<>();
        RADIO_SUBSTITUTION_VALUES.put("MeContextId", NODE_NAME);
        RADIO_SUBSTITUTION_VALUES.put("R_UtranCellFDD_id", "UtranCellOne");
        RADIO_SUBSTITUTION_VALUES.put("R_CellId", "CellOne");
        RADIO_SUBSTITUTION_VALUES.put("R_UtranCell_tac", "9");
        RADIO_SUBSTITUTION_VALUES.put("NodeName", NODE_NAME);
    }

    static {
        TRANSPORT_SUBSTITION_VALUES = new HashMap<>();
        TRANSPORT_SUBSTITION_VALUES.put("NodeName", NODE_NAME);
    }

    @Test
    public void when_batch_projectInfo_is_validated_then_verify_it_validates_against_scham() {
        final List<String> projectInfoXsds = new ArrayList<>();
        final String xml = retriveResourceAsString(BATCH_PROJECT_DIRECTORY + "/projectInfo.xml");
        final String updatedXml = substituteBatchValues(xml, PROJECT_INFO_SUBSTITUION_VALUES);
        projectInfoXsds.add(retriveResourceAsString("/ProjectInfo.xsd"));

        assertTrue(validateXmlAgainstSchema(updatedXml, projectInfoXsds).isValid());
    }

    @Test
    public void when_batch_nodeInfo_is_validated_then_verify_it_validates_against_schema() {
        final List<String> projectInfoXsds = new ArrayList<>();

        final String xml = retriveResourceAsString(BATCH_PROJECT_DIRECTORY + "/nodeInfo.xml");
        final String updatedXml = substituteBatchValues(xml, NODE_INFO_SUBSTITUTION_VALUES);
        projectInfoXsds.add(retriveResourceAsString("/erbs/ap/NodeInfo.xsd"));

        assertTrue(validateXmlAgainstSchema(updatedXml, projectInfoXsds).isValid());
    }

    @Test
    public void when_batch_SiteBasic_is_validated_then_verify_it_validates_against_schema() {
        final List<String> projectInfoXsds = new ArrayList<>();
        final String xml = retriveResourceAsString(BATCH_PROJECT_DIRECTORY + "/siteBasic.xml");
        final String updatedXml = substituteBatchValues(xml, SITE_BASIC_SUBSTITUTION_VALUES);
        projectInfoXsds.add(retriveResourceAsString("/erbs/g.1.220/SiteBasic.xsd"));

        assertTrue(validateXmlAgainstSchema(updatedXml, projectInfoXsds).isValid());
    }

    @Test
    public void when_batch_SiteEquipment_is_validated_then_verify_it_validates_against_schema() {
        final List<String> projectInfoXsds = new ArrayList<>();
        final String xml = retriveResourceAsString(BATCH_PROJECT_DIRECTORY + "/siteEquipment.xml");
        projectInfoXsds.add(retriveResourceAsString("/erbs/g.1.220/SiteEquipment.xsd"));

        assertTrue(validateXmlAgainstSchema(xml, projectInfoXsds).isValid());
    }

    @Test
    public void when_batch_SiteInstallation_is_validated_then_verify_it_validates_against_schema() {
        final List<String> projectInfoXsds = new ArrayList<>();
        final String xml = retriveResourceAsString(BATCH_PROJECT_DIRECTORY + "/siteInstallation.xml");
        final String updatedXml = substituteBatchValues(xml, SITE_INSTALLATION_SUBSTITUATION_VALUES);
        projectInfoXsds.add(retriveResourceAsString("/erbs/g.1.220/SiteInstallation.xsd"));

        assertTrue(validateXmlAgainstSchema(updatedXml, projectInfoXsds).isValid());
    }

    @Test
    public void when_batch_radio_is_validated_then_verify_it_is_well_formed() {
        final String xml = retriveResourceAsString(BATCH_PROJECT_DIRECTORY + "/Bulk-CM-Configuration-file.xml");
        final String updatedXml = substituteBatchValues(xml, RADIO_SUBSTITUTION_VALUES);
        assertTrue(validateXmlWellFormedness(updatedXml));
    }
}
