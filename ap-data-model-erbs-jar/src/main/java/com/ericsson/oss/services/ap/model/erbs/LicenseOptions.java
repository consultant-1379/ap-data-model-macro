/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2018
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
import com.ericsson.oss.itpf.modeling.annotation.DefaultValue;
import com.ericsson.oss.itpf.modeling.annotation.EModel;
import com.ericsson.oss.itpf.modeling.annotation.EModelAttribute;
import com.ericsson.oss.services.ap.model.AbstractLicenseOptions;

/**
 * Class containing License Options.
 */
@EModel(namespace = "ap_erbs", description = "ERBS License Options", name = "LicenseOptions", version = ApErbsVersion.MODEL_VERSION)
@PrimaryTypeDefinition()
@Hierarchical(parentMoTypeUrn = "//ap/Node/*")
@OnReadWrite(onRead = ReadBehavior.READ_FROM_PERSISTENT_STORAGE, onWrite = WriteBehavior.WRITE_TO_PERSISTENT_STORAGE_ONLY)
public class LicenseOptions extends AbstractLicenseOptions {

    private static final long serialVersionUID = 1L;

    @EModelAttribute(description = "The name of the license key file for this node", mandatory = false)
    @PrimaryTypeAttribute
    public String licenseFile;

    @EModelAttribute(description = "Install the license key file for this node")
    @DefaultValue(value = "false")
    @PrimaryTypeAttribute
    public Boolean installLicense;

    @EModelAttribute(description = "Activate the license key file for this node")
    @DefaultValue(value = "false")
    @PrimaryTypeAttribute
    public Boolean activateLicense;

    @EModelAttribute(description = "The name of the file containing mandatory license keys", mandatory = false)
    @PrimaryTypeAttribute
    public String mandatoryLicenseKeys;

}
