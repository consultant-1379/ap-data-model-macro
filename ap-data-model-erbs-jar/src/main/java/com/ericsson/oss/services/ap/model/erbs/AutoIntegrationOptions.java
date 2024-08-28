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

import static com.ericsson.oss.itpf.modeling.annotation.LifeCycleState.OBSOLETE;

import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.Hierarchical;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.OnReadWrite;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.PrimaryTypeAttribute;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.PrimaryTypeDefinition;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.ReadBehavior;
import com.ericsson.oss.itpf.datalayer.dps.modeling.annotation.WriteBehavior;
import com.ericsson.oss.itpf.modeling.annotation.DefaultValue;
import com.ericsson.oss.itpf.modeling.annotation.EModel;
import com.ericsson.oss.itpf.modeling.annotation.EModelAttribute;
import com.ericsson.oss.itpf.modeling.annotation.LifeCycle;
import com.ericsson.oss.services.ap.model.AbstractAutoIntegrationOptions;

/**
 * Class containing Auto Integration Options.
 */
@EModel(namespace = "ap_erbs", description = "ERBS Auto Integration Options", name = "AutoIntegrationOptions", version = ApErbsVersion.MODEL_VERSION)
@PrimaryTypeDefinition()
@Hierarchical(parentMoTypeUrn = "//ap/Node/*")
@OnReadWrite(onRead = ReadBehavior.READ_FROM_PERSISTENT_STORAGE, onWrite = WriteBehavior.WRITE_TO_PERSISTENT_STORAGE_ONLY)
public class AutoIntegrationOptions extends AbstractAutoIntegrationOptions {

    private static final long serialVersionUID = 1L;

    @EModelAttribute(description = "The name of the software upgrade package to be installed on the node. References software package uploaded to SHM", mandatory = false)
    @PrimaryTypeAttribute
    public String upgradePackageName;

    @EModelAttribute(description = "The name of the basic software package to be installed on the node. References software package uploaded to SHM", mandatory = false)
    @PrimaryTypeAttribute
    public String basicPackageName;

    @EModelAttribute(description = "Install the license key file for this node")
    @DefaultValue(value = "false")
    @PrimaryTypeAttribute
    @LifeCycle(value = OBSOLETE, lifeCycleDescription = "This attribute has been removed")
    public Boolean installLicense;

    @EModelAttribute(description = "Activate the license key file for this node")
    @DefaultValue(value = "false")
    @PrimaryTypeAttribute
    @LifeCycle(value = OBSOLETE, lifeCycleDescription = "This attribute has been removed")
    public Boolean activateLicense;

    @EModelAttribute(description = "UnlockCells after Auto Integration")
    @DefaultValue(value = "false")
    @PrimaryTypeAttribute
    public Boolean unlockCells;

    @EModelAttribute(description = "Upload Configuration Version (CV) from the Node after Configuration is activated in the Live Bucket")
    @DefaultValue(value = "false")
    @PrimaryTypeAttribute
    @LifeCycle(value = OBSOLETE, lifeCycleDescription = "This attribute has been removed")
    public Boolean uploadCVAfterConfiguration;

    @EModelAttribute(description = "Upload Configuration Version (CV) from the Node after Integration")
    @DefaultValue(value = "false")
    @PrimaryTypeAttribute
    public Boolean uploadCVAfterIntegration;

    @EModelAttribute(description = "The name of the file containing mandatory license keys", mandatory = false)
    @PrimaryTypeAttribute
    @LifeCycle(value = OBSOLETE, lifeCycleDescription = "This attribute has been removed")
    public String mandatoryLicenseKeys;
}
