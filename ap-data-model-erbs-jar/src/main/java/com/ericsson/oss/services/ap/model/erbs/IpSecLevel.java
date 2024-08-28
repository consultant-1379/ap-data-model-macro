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


import com.ericsson.oss.itpf.modeling.annotation.EModel;
import com.ericsson.oss.itpf.modeling.annotation.edt.EdtDefinition;
import com.ericsson.oss.itpf.modeling.annotation.edt.EdtMember;

/**
 * Class containing IpSecLevel definition.
 */
@EModel(namespace = "ap_erbs", name = "IpSecLevel", version = ApErbsVersion.MODEL_VERSION, description = "The Node's IP security level")
@EdtDefinition
public enum IpSecLevel {

    @EdtMember(value = 1, description = "Transport Security (Control Plane (CP) and User Plane (UP) traffic on S1 and X2 interfaces, Synchronization over IP (SoIP)")
    CUS,

    @EdtMember(value = 2, description = "O&M Security (MUI interface)")
    OAM,

    @EdtMember(value = 3, description = "Both Transport and O&M Security")
    CUSOAM
}
