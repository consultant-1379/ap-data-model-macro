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
import com.ericsson.oss.services.ap.model.AbstractSupervisionOptions;

@EModel(namespace = "ap_erbs", description = "ERBS Auto Provisioning Node Supervision", name = "SupervisionOptions", version = ApErbsVersion.MODEL_VERSION)
@PrimaryTypeDefinition()
@Hierarchical(parentMoTypeUrn = "//ap/Node/*")
@OnReadWrite(onRead = ReadBehavior.READ_FROM_PERSISTENT_STORAGE, onWrite = WriteBehavior.WRITE_TO_PERSISTENT_STORAGE_ONLY)
public class SupervisionOptions extends AbstractSupervisionOptions {

    private static final long serialVersionUID = 1L;

    @EModelAttribute(description = "Enable FM Supervision")
    @DefaultValue(value = "enabled")
    @PrimaryTypeAttribute
    public String fm;

    @EModelAttribute(description = "Enable PM Supervision")
    @DefaultValue(value = "enabled")
    @PrimaryTypeAttribute
    public String pm;

    @EModelAttribute(description = "Enable Inventory Supervision")
    @DefaultValue(value = "enabled")
    @PrimaryTypeAttribute
    public String inventory;
}