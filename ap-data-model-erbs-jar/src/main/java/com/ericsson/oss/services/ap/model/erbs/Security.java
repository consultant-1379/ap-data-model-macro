/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2014
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.oss.services.ap.model.erbs;

import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.Hierarchical;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.OnReadWrite;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.PrimaryTypeAttribute;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.PrimaryTypeDefinition;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.ReadBehavior;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.WriteBehavior;
import com.ericsson.oss.itpf.modeling.annotation.EModel;
import com.ericsson.oss.itpf.modeling.annotation.EModelAttribute;
import com.ericsson.oss.services.ap.model.AbstractSecurity;

/**
 * ERBS node security information.
 */
@EModel(namespace = "ap_erbs", description = "ERBS Auto Provisioning Node Security", name = "Security", version = ApErbsVersion.MODEL_VERSION)
@PrimaryTypeDefinition()
@Hierarchical(parentMoTypeUrn = "//ap/Node/*")
@OnReadWrite(onRead = ReadBehavior.READ_FROM_PERSISTENT_STORAGE, onWrite = WriteBehavior.WRITE_TO_PERSISTENT_STORAGE_ONLY)
public class Security extends AbstractSecurity {

    private static final long serialVersionUID = 1L;

    @EModelAttribute(description = "Node's minimum O&M security level", mandatory = true)
    @PrimaryTypeAttribute
    public String minimumSecurityLevel;

    @EModelAttribute(description = "Node's optimum O&M security level", mandatory = true)
    @PrimaryTypeAttribute
    public String optimumSecurityLevel;

    @EModelAttribute(description = "The Node's enrollment mode", mandatory = true)
    @PrimaryTypeAttribute
    public EnrollmentMode enrollmentMode;

    @EModelAttribute(description = "The Node's IP security level")
    @PrimaryTypeAttribute
    public IpSecLevel ipSecLevel;

    @EModelAttribute(description = "The Node's IP security Subject Alt Name Type")
    @PrimaryTypeAttribute
    public SubjectAltNameType subjectAltNameType;

    @EModelAttribute(description = "The Node's IP security Subject Alt Name")
    @PrimaryTypeAttribute
    public String subjectAltName;

    @EModelAttribute(description = "RBS Integrity Code")
    @PrimaryTypeAttribute
    public String rbsIntegrityCode;

    @EModelAttribute(description = "Security Config Check Sum")
    @PrimaryTypeAttribute
    public String securityConfigChecksum;

    @EModelAttribute(description = "Location of ISCF file on SMRS")
    @PrimaryTypeAttribute
    public String iscfFileLocation;

    @EModelAttribute(description = "A comma-delimited list of Target Groups for the node")
    @PrimaryTypeAttribute
    public String targetGroups;
}
