/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2020
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.services.oss.ap.schema;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests to ensure NodeInfo and included schemas validate as expected
 */
@RunWith(MockitoJUnitRunner.class)
public class NodeInfoSchemaTest extends SchemaTest {

    private final Map<String, String> NODE_INFO_SUBSTITUTION_VALUES = new HashMap<>();

    private final String NODE_INFO_TEMPLATE = retriveResourceAsString("/nodeInfo_template.xml");

    private static final String IP_ADDRESS_VALIDATION_ERROR = "is not facet-valid with respect to pattern '((((25[0-5])|(2[0-4][0-9])|([01]?[0-9]?[0-9]))\\.){3}((25[0-5])|(2[0-4][0-9])|([01]?[0-9]?[0-9])))|(s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*)' for type 'Ipv4orIpv6'";

    private static final String NODE_TYPE_VALIDATION_ERROR = "is not facet-valid with respect to pattern 'ERBS|MSRBS_V1|RadioNode|RBS|vPP|vSD|vRM|vRSM|vTIF|Router6672|Router6675|Router6x71|Controller6610|Router6673|FRONTHAUL-6000|Router6000-2' for type 'NodeType'.";

    private static final String HARDWARE_SERIAL_NUMBER_VALIDATION_ERROR = "is not facet-valid with respect to pattern '[A-HJ-NP-Z0-9()]{10,13}' for type 'HardwareSerialNumber'";

    private static final String ENROLLMENT_MODE_VALIDATION_ERROR = "is not facet-valid with respect to enumeration '[SCEP, CMPv2_VC, CMPv2_INITIAL]'. It must be a value from the enumeration.";

    private static final String IP_SEC_LEVEL_VALIDATION_ERROR = "is not facet-valid with respect to enumeration '[CUS, OAM, CUSOAM]'. It must be a value from the enumeration.";

    private static final String NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR = "is not facet-valid with respect to pattern '\\S.*' for type 'NonEmptyString'";

    private static final String STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR = "is not facet-valid with respect to pattern '\\S*' for type 'StringWithoutWhiteSpace'";

    private String[][] VALID_NODE_NAME = {
            {"NodeName", "NodeOne"},
            {"NodeName", "Node.Two"},
            {"NodeName", "Node-3_4"}
    };

    private String[][] VALID_NODE_ID = {
            {"NodeIdentifier", "A"},
            {"NodeIdentifier", "a"},
            {"NodeIdentifier", ";"},
            {"NodeIdentifier", "1998-184-092"}
    };

    private String[][] VALID_IP_ADDRESS = {

            {"NodeIpAddress", "0.0.0.0"},
            {"NodeIpAddress", "12.23.34.45"},
            {"NodeIpAddress", "123.213.245.199"},
            {"NodeIpAddress", "43:0dB8:0000:0000:0000:ff00:0042:8329"},
    };

    private String[][] VALID_NODE_TYPE = {

            {"NodeType", "ERBS"},
            {"NodeType", "MSRBS_V1"},
            {"NodeType", "RadioNode"},
            {"NodeType", "RBS"},
            {"NodeType", "vPP"},
            {"NodeType", "vSD"},
            {"NodeType", "vRM"},
            {"NodeType", "vRSM"},
            {"NodeType", "vTIF"},
            {"NodeType", "Router6672"},
            {"NodeType", "Router6675"},
            {"NodeType", "Router6x71"},
            {"NodeType", "Router6673"},
            {"NodeType", "FRONTHAUL-6000"},
            {"NodeType", "Router6000-2"}
    };

    private String[][] VALID_USER_LABEL = {
            {"UserLabel", "Dublin East"},
            {"UserLabel", "Athlone West"},
            {"UserLabel", "UserLabelsUserLabelsUserLabels UserLabelsUserLabelsUserLabelsUserLabelsUserLabelsUserLabel"}
    };

    private String[][] VALID_HARDWARE_SERIAL_NUMBER = {
            {"HardwareSerialNumber", "ABC567891234"},
            {"HardwareSerialNumber", "S0J1W348L309"},
            {"HardwareSerialNumber", "ABC(1234)LKDE"}
    };

    private String[][] VALID_OSS_PREFIX = {
            {"OssPrefix", "B"},
            {"OssPrefix", "b"},
            {"OssPrefix", ";"},
            {"OssPrefix", "SubNetwork=EnmSn,MeContext=Standard_ERBS_Node1"}
    };

    private String[][] VALID_TIME_ZONE = {

            {"TimeZone", "GB-EireEire"},
            {"TimeZone", "UTC-UK"},
            {"TimeZone", "Whatever_it_is"}
    };

    private String[][] VALID_ARTIFACT = {

            {"Artifact", "siteBasic.xml"},
            {"Artifact", "s"},
            {"Artifact", ";"}
    };

    private String[][] VALID_ARTIFACT_CONFIGURATION = {
            {"ArtifactConfiguration", "c"},
            {"ArtifactConfiguration", "C"},
            {"ArtifactConfiguration", "@"},
            {"ArtifactConfiguration", "Bulk-CM-Configuration-file.xml"}
    };

    private String[][] VALID_AUTO_INTEGRATION_PACKAGE = {
            {"AutoIntegrationPackage", "D"},
            {"AutoIntegrationPackage", "d"},
            {"AutoIntegrationPackage", "!"},
            {"AutoIntegrationPackage", "CXP102051_1_R4D25"}
    };

    private String[][] VALID_AUTO_INTEGRATION_BOOLEAN = {

            {"AutoIntegrationBoolean", "true"},
            {"AutoIntegrationBoolean", "false"}
    };

    private String[][] VALID_MINIMUM_SECURITY_LEVEL = {
            {"MinimumSecurityLevel", "1"},
            {"MinimumSecurityLevel", "2"}
    };

    private String[][] VALID_OPTIMUM_SECURITY_LEVEL = {

            {"OptimumSecurityLevel", "1"},
            {"OptimumSecurityLevel", "2"}
    };

    private String[][] VALID_ENROLLMENT_MODE = {

            {"EnrollmentMode", "SCEP"},
            {"EnrollmentMode", "CMPv2_VC"},
            {"EnrollmentMode", "CMPv2_INITIAL"}
    };

    private String[][] VALID_IP_SECURITY_LEVEL = {

            {"IpSecLevel", "CUS"},
            {"IpSecLevel", "OAM"},
            {"IpSecLevel", "CUSOAM"}
    };

    private String[][] VALID_IP_SUBJECT_ALT_NAME_TYPE = {

            {"IpSubjectAltNameType", "IPV4"},
            {"IpSubjectAltNameType", "IPV6"},
            {"IpSubjectAltNameType", "FQDN"},
    };

    private String[][] VALID_IP_SUBJECT_ALT_NAME = {
            {"IpSubjectAltName", "J"},
            {"IpSubjectAltName", "j"},
            {"IpSubjectAltName", "#"},
            {"IpSubjectAltName", "www.google.com"}
    };

    private String[][] VALID_SECURITY_TARGET_GROUP = {
            {"SecurityTargetGroup", "f"},
            {"SecurityTargetGroup", "F"},
            {"SecurityTargetGroup", "NewGroup"}
    };

    private String[][] VALID_LOCATION_LATITUDE = {
            {"Latitude", "-90.0"},
            {"Latitude", "90.0"},
            {"Latitude", "-89.00001"},
            {"Latitude", "89.00001"},
            {"Latitude", "45.01231"},
            {"Latitude", "-4.123456"},
    };

    private String[][] VALID_LOCATION_LONGITUDE = {

            {"Longitude", "-180.0"},
            {"Longitude", "180.0"},
            {"Longitude", "147.00001"},
            {"Longitude", "-116.00001"},
            {"Longitude", "12.000234"},
            {"Longitude", "-2.1212398"},
    };

    private String[][] VALID_SUPERVISION_STATUS = {

            {"SupvisionStatus", "disabled"},
            {"SupvisionStatus", "enabled"}
    };

    private String[][] VALID_SUPERVISION_MANAGEMENT_STATE = {

            {"SupvisionManagementState", "MANUAL"},
            {"SupvisionManagementState", "AUTOMATIC"}
    };

    private String[][] VALID_INSTALL_LICENSE = {

            {"InstallLicense", "true"},
            {"InstallLicense", "false"}
    };

    private String[][] VALID_DHCP_INITIAL_IP_ADDRESS = {

            {"DhcpInitialIpAddress", "h"},
            {"DhcpInitialIpAddress", "H"},
            {"DhcpInitialIpAddress", "#"},
            {"DhcpInitialIpAddress", "1.2.3.4"}
    };

    private String[][] VALID_DHCP_DEFAULT_ROUTER = {

            {"DhcpNtpServer", "4.7.3.1"},
            {"DhcpNtpServer", "42.3.64.5"},
            {"DhcpNtpServer", "234.21.53.184"},
            {"DhcpNtpServer", "2ab1:0dB8:0000:0000:0000:ff00:0042:8329"},
    };

    private String[][] VALID_DHCP_NTP_SERVER = {

            {"DhcpNtpServer", "1.2.3.4"},
            {"DhcpNtpServer", "12.23.34.45"},
            {"DhcpNtpServer", "234.21.53.184"},
            {"DhcpNtpServer", "2001:0dB8:0000:0000:0000:ff00:0042:8329"},
            {"DhcpNtpServer", "111.112.133.145</ntpServer>\r\n<ntpServer>25.21.43.22"}
    };

    private String[][] VALID_DHCP_DNS_SERVER = {

             {"DhcpDnsServer", "12.23.34.45"},
             {"DhcpDnsServer", "234.21.53.184"},
             {"DhcpDnsServer", "2001:0dB8:0000:0000:0000:ff00:0042:8329"},
             {"DhcpDnsServer", "111.112.133.145</dnsServer>\r\n<dnsServer>25.21.43.22</dnsServer>\r\n<dnsServer>32.35.43.15"}
     };

    private String[][] INVALID_NODE_NAME = {
            {"NodeName", "Node!One", "is not facet-valid with respect to pattern '([a-zA-Z0-9._-])*' for type 'NodeName'"},
            {"NodeName", "No", "is not facet-valid with respect to minLength '3' for type 'NodeName'"},
            {"NodeName", "NodeNodeNodeNodeNodeNodeNodeNodeNodeNodeNodeNodeNodeNodeNodeNoded", "is not facet-valid with respect to maxLength '64' for type 'NodeName'"}
    };

    private String[][] INVALID_NODE_ID = {
            {"NodeIdentifier", "", "is not facet-valid with respect to minLength '1' for type 'StringWithoutWhiteSpace'"},
            {"NodeIdentifier", " ", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"NodeIdentifier", "1998 184 092", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR}
    };

    private String[][] INVALID_IP_ADDRESS = {
            // IPv4
            {"NodeIpAddress", "String", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "256.2.3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "1.256.3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "1.2.256.4", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "1.2.3.256", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "1.2.3", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "1.2.3.", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", ".2.3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "1..3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "1.2..4", IP_ADDRESS_VALIDATION_ERROR},

            // IPv6
            {"NodeIpAddress", "Afraid", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "G001:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "G01:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "G1:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "G:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "20010dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "2001:0dB80000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "2001:0dB8:00000000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "2001:0dB8:0000:00000000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "2001:0dB8:0000:0000:0000ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "2001:0dB8:0000:0000:0000:ff000042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "2001:0dB8:0000:0000:0000:ff00:00428329", IP_ADDRESS_VALIDATION_ERROR},
            {"NodeIpAddress", "2001:0dB8:0000:0000:0000:ff00:00428329", IP_ADDRESS_VALIDATION_ERROR}
    };

    private String[][] INVALID_NODE_TYPE = {
            {"NodeType", "Node", NODE_TYPE_VALIDATION_ERROR},
            {"NodeType", "ERBS ", NODE_TYPE_VALIDATION_ERROR},
            {"NodeType", " ERBS ", NODE_TYPE_VALIDATION_ERROR},
            {"NodeType", "ER BS", NODE_TYPE_VALIDATION_ERROR}
    };

    private String[][] INVALID_USER_LABEL = {
            {"UserLabel", "Athlone\nEast", "is not facet-valid with respect to pattern '\\S.*' for type 'UserLabel'"},
            {"UserLabel", "", "is not facet-valid with respect to pattern '\\S.*' for type 'UserLabel'"},
            {"UserLabel", " ", "is not facet-valid with respect to pattern '\\S.*' for type 'UserLabel'"},
            {"UserLabel", "UserLabelsUserLabelsUserLabelsUserLabelsUserLabelsUserLabelsUserLabelsUserLabelsUserLabelss", "is not facet-valid with respect to maxLength '90' for type 'UserLabel'"}
    };

    private String[][] INVALID_HARDWARE_SERIAL_NUMBER = {
            {"HardwareSerialNumber", "ABI567891234", HARDWARE_SERIAL_NUMBER_VALIDATION_ERROR},
            {"HardwareSerialNumber", "AOC567891234", HARDWARE_SERIAL_NUMBER_VALIDATION_ERROR},
            {"HardwareSerialNumber", "ABC567891", HARDWARE_SERIAL_NUMBER_VALIDATION_ERROR},
            {"HardwareSerialNumber", "A1B2C3D4E5F6G7", HARDWARE_SERIAL_NUMBER_VALIDATION_ERROR}
    };

    private String[][] INVALID_OSS_PREFIX = {
            {"OssPrefix", "", "is not facet-valid with respect to minLength '1' for type 'StringWithoutWhiteSpace'"},
            {"OssPrefix", " ", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"OssPrefix", "SubNetwork=EnmSn,MeContext=Standard_ERBS_Node1 ", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"OssPrefix", " SubNetwork=EnmSn,MeContext=Standard_ERBS_Node1", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"OssPrefix", "SubNetwork=EnmSn, MeContext=Standard_ERBS_Node1", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR}
    };

    private String[][] INVALID_TIME_ZONE = {
            {"TimeZone", "", "is not facet-valid with respect to minLength '1' for type 'StringWithoutWhiteSpace'"},
            {"TimeZone", " ", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"TimeZone", " GB-EireEire", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"TimeZone", "GB-EireEire ", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"TimeZone", "GB EireEire", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR}
    };

    private String[][] INVALID_ARTIFACT = {
            {"Artifact", "", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"Artifact", " ", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"Artifact", " siteBasic.xml", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"Artifact", "siteBasic\n.xml", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"Artifact", "siteBasic.xml\n", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"Artifact", "\nsiteBasic.xml", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR}
    };

    private String[][] INVALID_ARTIFACT_CONFIGURATION = {
            {"ArtifactConfiguration", "", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"ArtifactConfiguration", " ", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"ArtifactConfiguration", " Bulk-CM-Configuration-file.xml", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"ArtifactConfiguration", "Bulk-CM-Configuration-file\n.xml", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"ArtifactConfiguration", "Bulk-CM-Configuration-file.xml\n", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"ArtifactConfiguration", "\nBulk-CM-Configuration-file.xml", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR}
    };

    private String[][] INVALID_AUTO_INTEGRATION_PACKAGE = {
            {"AutoIntegrationPackage", "", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"AutoIntegrationPackage", " ", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"AutoIntegrationPackage", " CXP102051_1_R4D25", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"AutoIntegrationPackage", "CXP102051_1\nR4D25", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"AutoIntegrationPackage", "CXP102051_1_R4D25\n", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"AutoIntegrationPackage", "\nCXP102051_1_R4D25", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR}
    };

    private String[][] INVALID_AUTO_INTEGRATION_BOOLEAN = {
            {"AutoIntegrationBoolean", "else", "is not facet-valid with respect to enumeration '[false, true]'. It must be a value from the enumeration."},
            {"AutoIntegrationBoolean", " true", "is not facet-valid with respect to enumeration '[false, true]'. It must be a value from the enumeration."},
            {"AutoIntegrationBoolean", " false", "is not facet-valid with respect to enumeration '[false, true]'. It must be a value from the enumeration."}
    };

    private String[][] INVALID_MINIMUM_SECURITY_LEVEL = {
            {"MinimumSecurityLevel", "else", "'else' is not a valid value for 'integer'"},
            {"MinimumSecurityLevel", "1s", "'1s' is not a valid value for 'integer'"},
            {"MinimumSecurityLevel", "1 2", "'1 2' is not a valid value for 'integer'"},
            {"MinimumSecurityLevel", "0", "is not facet-valid with respect to minInclusive '1' for type 'SecurityLevel'"},
            {"MinimumSecurityLevel", "3", "is not facet-valid with respect to maxInclusive '2' for type 'SecurityLevel'"}
    };

    private String[][] INVALID_OPTIMUM_SECURITY_LEVEL = {
            {"OptimumSecurityLevel", "1ink", "'1ink' is not a valid value for 'integer'"},
            {"OptimumSecurityLevel", "2t", "'2t' is not a valid value for 'integer'"},
            {"OptimumSecurityLevel", "1 2", "'1 2' is not a valid value for 'integer'"},
            {"OptimumSecurityLevel", "0", "is not facet-valid with respect to minInclusive '1' for type 'SecurityLevel'"},
            {"OptimumSecurityLevel", "3", "is not facet-valid with respect to maxInclusive '2' for type 'SecurityLevel'"}
    };

    private String[][] INVALID_ENROLLMENT_MODE = {
            {"EnrollmentMode", "ELSE", ENROLLMENT_MODE_VALIDATION_ERROR},
            {"EnrollmentMode", " SCEP", ENROLLMENT_MODE_VALIDATION_ERROR},
            {"EnrollmentMode", "SCEP ", ENROLLMENT_MODE_VALIDATION_ERROR},
            {"EnrollmentMode", "CMPv2 VC", ENROLLMENT_MODE_VALIDATION_ERROR},
            {"EnrollmentMode", " CMPv2 INITIAL", ENROLLMENT_MODE_VALIDATION_ERROR}
    };

    private String[][] INVALID_IP_SECURITY_LEVEL = {
            {"IpSecLevel", "ELSE", IP_SEC_LEVEL_VALIDATION_ERROR},
            {"IpSecLevel", " CUS", IP_SEC_LEVEL_VALIDATION_ERROR},
            {"IpSecLevel", "CUS ", IP_SEC_LEVEL_VALIDATION_ERROR},
            {"IpSecLevel", "CUS OAM", IP_SEC_LEVEL_VALIDATION_ERROR},
    };

    private String[][] INVALID_IP_SUBJECT_ALT_NAME_TYPE = {
            {"IpSubjectAltNameType", "TCP", "is not facet-valid with respect to enumeration '[IPV4, IPV6, FQDN]'. It must be a value from the enumeration."},
            {"IpSubjectAltNameType", "IPv4", "is not facet-valid with respect to enumeration '[IPV4, IPV6, FQDN]'. It must be a value from the enumeration."},
            {"IpSubjectAltNameType", "fqdn ", "is not facet-valid with respect to enumeration '[IPV4, IPV6, FQDN]'. It must be a value from the enumeration."},
            {"IpSubjectAltNameType", "IPV5", "is not facet-valid with respect to enumeration '[IPV4, IPV6, FQDN]'. It must be a value from the enumeration."},
    };

    private String[][] INVALID_IP_SUBJECT_ALT_NAME = {
            {"IpSubjectAltName", "", "is not facet-valid with respect to minLength '1' for type 'StringWithoutWhiteSpace'"},
            {"IpSubjectAltName", " ", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"IpSubjectAltName", " 1.2.3.4", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"IpSubjectAltName", "1.2.3.4 ", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR},
            {"IpSubjectAltName", "www.google    .com", STRING_WITHOUT_WHITE_SPACE_VALIDATION_ERROR}
    };

    private String[][] INVALID_SECURITY_TARGET_GROUP = {
            {"SecurityTargetGroup", "", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"SecurityTargetGroup", " ", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR},
            {"SecurityTargetGroup", " NewGroup", NON_EMPTY_STRING_PATTERN_VALIDATION_ERROR}
    };

    private String[][] INVALID_LOCATION_LATITUDE = {
            {"Latitude", "", "is not a valid value for 'decimal'"},
            {"Latitude", "-90.1", "is not facet-valid with respect to minInclusive '-90.0' for type 'Latitude'"},
            {"Latitude", "90.1", "is not facet-valid with respect to maxInclusive '90.0' for type 'Latitude'"},
            {"Latitude", "89.000001", "has 8 total digits, but the number of total digits has been limited to 7."},
            {"Latitude", "-89.000001", "has 8 total digits, but the number of total digits has been limited to 7."}
    };

    private String[][] INVALID_LOCATION_LONGITUDE = {
            {"Longitude", "", "is not a valid value for 'decimal'"},
            {"Longitude", "-180.001", "is not facet-valid with respect to minInclusive '-180.0' for type 'Longitude'"},
            {"Longitude", "180.001", "is not facet-valid with respect to maxInclusive '180.0' for type 'Longitude'"},
            {"Longitude", "147.000001", "has 9 total digits, but the number of total digits has been limited to 8."},
            {"Longitude", "-116.000001", "has 9 total digits, but the number of total digits has been limited to 8."}
    };

    private String[][] INVALID_SUPERVISION_STATUS = {
            {"SupvisionStatus", "able", "is not facet-valid with respect to enumeration '[enabled, disabled]'. It must be a value from the enumeration."},
            {"SupvisionStatus", "disabled ", "is not facet-valid with respect to enumeration '[enabled, disabled]'. It must be a value from the enumeration."},
            {"SupvisionStatus", " enabled", "is not facet-valid with respect to enumeration '[enabled, disabled]'. It must be a value from the enumeration."},
            {"SupvisionStatus", "ENABLED", "is not facet-valid with respect to enumeration '[enabled, disabled]'. It must be a value from the enumeration."}
    };

    private String[][] INVALID_SUPERVISION_MANAGEMENT_STATE = {
            {"SupvisionManagementState", "man", "is not facet-valid with respect to enumeration '[MANUAL, AUTOMATIC]'. It must be a value from the enumeration."},
            {"SupvisionManagementState", "manual", "is not facet-valid with respect to enumeration '[MANUAL, AUTOMATIC]'. It must be a value from the enumeration."},
            {"SupvisionManagementState", "MANUAL ", "is not facet-valid with respect to enumeration '[MANUAL, AUTOMATIC]'. It must be a value from the enumeration."},
            {"SupvisionManagementState", " AUTOMATIC", "is not facet-valid with respect to enumeration '[MANUAL, AUTOMATIC]'. It must be a value from the enumeration."},
    };

    private String[][] INVALID_INSTALL_LICENSE = {
            {"InstallLicense", "else", "is not facet-valid with respect to enumeration '[false, true]'. It must be a value from the enumeration."},
            {"InstallLicense", " true", "is not facet-valid with respect to enumeration '[false, true]'. It must be a value from the enumeration."},
            {"InstallLicense", " false", "is not facet-valid with respect to enumeration '[false, true]'. It must be a value from the enumeration."}
    };

    private String[][] INVALID_DHCP_INITIAL_IP_ADDRESS = {
            {"DhcpInitialIpAddress", "", "is not facet-valid with respect to minLength '1' for type 'FilledString'"},
            {"DhcpInitialIpAddress", "1.2.3.5</initialIpAddress>\r\n<initialIpAddress>2.3.4.5", "Invalid content was found starting with element 'initialIpAddress'. One of '{defaultRouter, ntpServer, dnsServer}' is expected."}
    };

    private String[][] INVALID_DHCP_DEFAULT_ROUTER = {

            // IPv4
            {"DhcpDefaultRouter", "String", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "256.2.3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "1.256.3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "1.2.256.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "1.2.3.256", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "1.2.3", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "1.2.3.", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", ".2.3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "1..3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "1.2..4", IP_ADDRESS_VALIDATION_ERROR},

            // IPv6
            {"DhcpDefaultRouter", "Afraid", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "G001:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "G01:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "G1:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "G:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "20010dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "2001:0dB80000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "2001:0dB8:00000000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "2001:0dB8:0000:00000000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "2001:0dB8:0000:0000:0000ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "2001:0dB8:0000:0000:0000:ff000042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "2001:0dB8:0000:0000:0000:ff00:00428329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpDefaultRouter", "2001:0dB8:0000:0000:0000:ff00:00428329", IP_ADDRESS_VALIDATION_ERROR},

            // Occurrence
            {"DhcpDefaultRouter", "1.2.3.5</defaultRouter>\r\n<defaultRouter>2.3.4.5", "Invalid content was found starting with element 'defaultRouter'. One of '{ntpServer, dnsServer}' is expected."}
    };

    private String[][] INVALID_DHCP_NTP_SERVER = {

            // IPv4
            {"DhcpNtpServer", "String", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "256.2.3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "1.256.3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "1.2.256.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "1.2.3.256", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "1.2.3", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "1.2.3.", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", ".2.3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "1..3.4", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "1.2..4", IP_ADDRESS_VALIDATION_ERROR},

            // IPv6
            {"DhcpNtpServer", "Afraid", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "G001:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "G01:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "G1:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "G:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "20010dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "2001:0dB80000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "2001:0dB8:00000000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "2001:0dB8:0000:00000000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "2001:0dB8:0000:0000:0000ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "2001:0dB8:0000:0000:0000:ff000042:8329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "2001:0dB8:0000:0000:0000:ff00:00428329", IP_ADDRESS_VALIDATION_ERROR},
            {"DhcpNtpServer", "2001:0dB8:0000:0000:0000:ff00:00428329", IP_ADDRESS_VALIDATION_ERROR},

            // Occurrence
            {"DhcpNtpServer", "10.11.12.13</ntpServer>\r\n<ntpServer>52.73.144.5</ntpServer>\r\n<ntpServer>22.32.42.25", "ne of '{dnsServer}' is expected."}
    };

    private String[][] INVALID_DHCP_DNS_SERVER = {

             // IPv4
             {"DhcpDnsServer", "String", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "256.2.3.4", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "1.256.3.4", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "1.2.256.4", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "1.2.3.256", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "1.2.3", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "1.2.3.", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", ".2.3.4", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "1..3.4", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "1.2..4", IP_ADDRESS_VALIDATION_ERROR},

             // IPv6
             {"DhcpDnsServer", "Afraid", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "G001:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "G01:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "G1:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "G:0dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "20010dB8:0000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "2001:0dB80000:0000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "2001:0dB8:00000000:0000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "2001:0dB8:0000:00000000:ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "2001:0dB8:0000:0000:0000ff00:0042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "2001:0dB8:0000:0000:0000:ff000042:8329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "2001:0dB8:0000:0000:0000:ff00:00428329", IP_ADDRESS_VALIDATION_ERROR},
             {"DhcpDnsServer", "2001:0dB8:0000:0000:0000:ff00:00428329", IP_ADDRESS_VALIDATION_ERROR},

             // Occurrence
             {"DhcpDnsServer", "111.112.133.145</dnsServer>\r\n<dnsServer>25.21.43.22</dnsServer>\r\n<dnsServer>32.35.43.15</dnsServer>\r\n<dnsServer>3.5.4.1", "No child element is expected at this point."}
     };

    public void setUp() {
        NODE_INFO_SUBSTITUTION_VALUES.put("NodeName", "NodeOne");
        NODE_INFO_SUBSTITUTION_VALUES.put("NodeIdentifier", "1998-184-092");
        NODE_INFO_SUBSTITUTION_VALUES.put("NodeIpAddress", "1.2.3.4");
        NODE_INFO_SUBSTITUTION_VALUES.put("NodeType", "ERBS");
        NODE_INFO_SUBSTITUTION_VALUES.put("UserLabel", "Athlone-East");
        NODE_INFO_SUBSTITUTION_VALUES.put("HardwareSerialNumber", "ABC567891234");
        NODE_INFO_SUBSTITUTION_VALUES.put("OssPrefix", "SubNetwork=EnmSn,MeContext=Standard_ERBS_Node1");
        NODE_INFO_SUBSTITUTION_VALUES.put("TimeZone", "GB-Eire");
        NODE_INFO_SUBSTITUTION_VALUES.put("Artifact", "siteBasic.xml");
        NODE_INFO_SUBSTITUTION_VALUES.put("ArtifactConfiguration", "Bulk-CM-Configuration-file.xml");
        NODE_INFO_SUBSTITUTION_VALUES.put("AutoIntegrationPackage", "CXP102051_1_R4D25");
        NODE_INFO_SUBSTITUTION_VALUES.put("AutoIntegrationBoolean", "true");
        NODE_INFO_SUBSTITUTION_VALUES.put("MinimumSecurityLevel", "1");
        NODE_INFO_SUBSTITUTION_VALUES.put("OptimumSecurityLevel", "2");
        NODE_INFO_SUBSTITUTION_VALUES.put("EnrollmentMode", "SCEP");
        NODE_INFO_SUBSTITUTION_VALUES.put("IpSecLevel", "CUS");
        NODE_INFO_SUBSTITUTION_VALUES.put("IpSubjectAltNameType", "IPV4");
        NODE_INFO_SUBSTITUTION_VALUES.put("IpSubjectAltName", "1.2.3.4");
        NODE_INFO_SUBSTITUTION_VALUES.put("SecurityTargetGroup", "SampleTargetGroup1");
        NODE_INFO_SUBSTITUTION_VALUES.put("Latitude", "53.42911");
        NODE_INFO_SUBSTITUTION_VALUES.put("Longitude", "-7.94398");
        NODE_INFO_SUBSTITUTION_VALUES.put("SupvisionStatus", "disabled");
        NODE_INFO_SUBSTITUTION_VALUES.put("SupvisionManagementState", "MANUAL");
        NODE_INFO_SUBSTITUTION_VALUES.put("InstallLicense", "true");
        NODE_INFO_SUBSTITUTION_VALUES.put("DhcpInitialIpAddress", "1.2.3.4");
        NODE_INFO_SUBSTITUTION_VALUES.put("DhcpDefaultRouter", "10.10.10.10");
        NODE_INFO_SUBSTITUTION_VALUES.put("DhcpNtpServer", "2001:0dB8:0000:0000:0000:ff00:0042:8329");
        NODE_INFO_SUBSTITUTION_VALUES.put("DhcpDnsServer", "5.6.7.8");
    }

    @Test
    public void when_nodeInfo_contains_valid_nodename_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_NODE_NAME) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    }

    @Test
    public void when_nodeInfo_contains_valid_nodeid_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_NODE_ID) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    }

    @Test
    public void when_nodeInfo_contains_valid_node_ip_address_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_IP_ADDRESS) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    }

    @Test
    public void when_nodeInfo_contains_valid_node_type_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_NODE_TYPE) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    }

    @Test
    public void when_nodeInfo_contains_valid_user_label_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_USER_LABEL) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    }

    @Test
    public void when_nodeInfo_contains_valid_hardware_serial_number_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_HARDWARE_SERIAL_NUMBER) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    }

    @Test
    public void when_nodeInfo_contains_valid_oss_prefix_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_OSS_PREFIX) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    }

    @Test
    public void when_nodeInfo_contains_valid_time_zone_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_TIME_ZONE) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_artifact_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_ARTIFACT) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    }

    @Test
    public void when_nodeInfo_contains_valid_artifact_configuration_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_ARTIFACT_CONFIGURATION) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_auto_integration_package_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_AUTO_INTEGRATION_PACKAGE) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_auto_integration_boolean_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_AUTO_INTEGRATION_BOOLEAN) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_minimum_security_level_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_MINIMUM_SECURITY_LEVEL) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_optimum_security_level_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_OPTIMUM_SECURITY_LEVEL) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_enrollment_mode_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_ENROLLMENT_MODE) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_ip_security_level_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_IP_SECURITY_LEVEL) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_ip_subject_alt_name_type_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_IP_SUBJECT_ALT_NAME_TYPE) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_ip_subject_alt_name_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_IP_SUBJECT_ALT_NAME) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_security_target_group_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_SECURITY_TARGET_GROUP) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_latitute_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_LOCATION_LATITUDE) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_longitude_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_LOCATION_LONGITUDE) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_supervision_status_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_SUPERVISION_STATUS) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_supervision_management_state_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_SUPERVISION_MANAGEMENT_STATE) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_install_license_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_INSTALL_LICENSE) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_dhcp_initial_ip_address_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_DHCP_INITIAL_IP_ADDRESS) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_dhcp_default_router_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_DHCP_DEFAULT_ROUTER) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_dhcp_ntp_server_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_DHCP_NTP_SERVER) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_valid_dhcp_dns_server_then_verify_schema_validation_success() {
        for (final String[] validData : VALID_DHCP_DNS_SERVER) {
            validateValidNodeInfo(validData[0], validData[1]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_nodename_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_NODE_NAME) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    }

    @Test
    public void when_nodeInfo_contains_invalid_nodeid_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_NODE_ID) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    }

    @Test
    public void when_nodeInfo_contains_invalid_node_ip_address_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_IP_ADDRESS) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    }

    @Test
    public void when_nodeInfo_contains_invalid_node_type_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_NODE_TYPE) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    }

    @Test
    public void when_nodeInfo_contains_invalid_user_label_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_USER_LABEL) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    }

    @Test
    public void when_nodeInfo_contains_invalid_hardware_serial_number_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_HARDWARE_SERIAL_NUMBER) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    }

    @Test
    public void when_nodeInfo_contains_invalid_oss_prefix_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_OSS_PREFIX) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    }

    @Test
    public void when_nodeInfo_contains_invalid_time_zone_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_TIME_ZONE) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    }

    @Test
    public void when_nodeInfo_contains_invalid_artifact_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_ARTIFACT) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    }

    @Test
    public void when_nodeInfo_contains_invalid_artifact_configuration_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_ARTIFACT_CONFIGURATION) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_auto_integration_package_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_AUTO_INTEGRATION_PACKAGE) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_auto_integration_boolean_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_AUTO_INTEGRATION_BOOLEAN) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_minimum_security_level_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_MINIMUM_SECURITY_LEVEL) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_optimum_security_level_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_OPTIMUM_SECURITY_LEVEL) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_enrollment_mode_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_ENROLLMENT_MODE) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_ip_security_level_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_IP_SECURITY_LEVEL) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_ip_subject_alt_name_type_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_IP_SUBJECT_ALT_NAME_TYPE) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_ip_subject_alt_name_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_IP_SUBJECT_ALT_NAME) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_security_target_group_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_SECURITY_TARGET_GROUP) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_latitute_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_LOCATION_LATITUDE) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_longitude_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_LOCATION_LONGITUDE) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_supervision_status_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_SUPERVISION_STATUS) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_supervision_management_state_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_SUPERVISION_MANAGEMENT_STATE) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_install_license_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_INSTALL_LICENSE) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_dhcp_initial_ip_address_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_DHCP_INITIAL_IP_ADDRESS) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_dhcp_default_router_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_DHCP_DEFAULT_ROUTER) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_dhcp_ntp_server_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_DHCP_NTP_SERVER) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    @Test
    public void when_nodeInfo_contains_invalid_dhcp_dns_server_then_verify_schema_validation_fails() {
        for (final String[] invalidData : INVALID_DHCP_DNS_SERVER) {
            validateInvalidNodeInfo(invalidData[0], invalidData[1], invalidData[2]);
        }
    };

    public void validateInvalidNodeInfo(final String substitutionKey, final String substitutionValue, final String errorValue) {

        final List<String> nodeInfoXsds = new ArrayList<>();
        setUp();
        NODE_INFO_SUBSTITUTION_VALUES.put(substitutionKey, substitutionValue);
        final String updatedXml = substituteBatchValues(NODE_INFO_TEMPLATE, NODE_INFO_SUBSTITUTION_VALUES);
        nodeInfoXsds.add(retriveResourceAsString("/erbs/ap/NodeInfo.xsd"));

        final ValidationResult result = validateXmlAgainstSchema(updatedXml, nodeInfoXsds);
        assertFalse(result.isValid());
        assertThat(result.getErrorMessage(), containsString(errorValue));
    }

    public void validateValidNodeInfo(final String substitutionKey, final String substitutionValue) {

        final List<String> nodeInfoXsds = new ArrayList<>();
        setUp();
        NODE_INFO_SUBSTITUTION_VALUES.put(substitutionKey, substitutionValue);
        final String updatedXml = substituteBatchValues(NODE_INFO_TEMPLATE, NODE_INFO_SUBSTITUTION_VALUES);
        nodeInfoXsds.add(retriveResourceAsString("/erbs/ap/NodeInfo.xsd"));

        final ValidationResult result = validateXmlAgainstSchema(updatedXml, nodeInfoXsds);
        assertTrue(result.isValid());
    }
}