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
package com.ericsson.oss.services.ap.model.erbs;

import com.ericsson.oss.itpf.modeling.annotation.EModel;
import com.ericsson.oss.itpf.modeling.annotation.LifeCycle;
import com.ericsson.oss.itpf.modeling.annotation.LifeCycleState;
import com.ericsson.oss.itpf.modeling.annotation.edt.EdtDefinition;
import com.ericsson.oss.itpf.modeling.annotation.edt.EdtMember;

/**
 * Class containing EnrollmentMode enum definition
 */
@EModel(namespace = "ap_erbs", name = "EnrollmentMode", version = ApErbsVersion.MODEL_VERSION, description = "The Node's IP Enrollment Mode")
@EdtDefinition
public enum EnrollmentMode {

    @EdtMember(value = 1, description = "Simple Certificate Enrollment Protocol")
    SCEP,

    @EdtMember(value = 2, description = "Initial Certificate Enrollment with CMPV2 with vendor-signed certificate based authentication")
    @LifeCycle(value = LifeCycleState.OBSOLETE, lifeCycleDescription = "This attribute has been deprecated")
    CMPV2_VC,

    @EdtMember(value = 3, description = "Initial Certificate Enrollment with CMPV2 with password based authentication")
    @LifeCycle(value = LifeCycleState.OBSOLETE, lifeCycleDescription = "This attribute has been deprecated")
    CMPV2_INITIAL,

    @EdtMember(value = 4, description = "Initial Certificate Enrollment with CMPv2 with vendor-signed certificate based authentication")
    CMPv2_VC,

    @EdtMember(value = 5, description = "Initial Certificate Enrollment with CMPv2 with password based authentication")
    CMPv2_INITIAL
}
