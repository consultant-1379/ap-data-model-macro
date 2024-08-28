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
 * Class containing SubjectAltNameType definition.
 */
@EModel(namespace = "ap_erbs", name = "SubjectAltNameType", version = ApErbsVersion.MODEL_VERSION, description = "The Node's IP security Subject Alt Name Type")
@EdtDefinition
public enum SubjectAltNameType {

    @EdtMember(value = 1, description = "Subject Alt Name in IPv4 format")
    IPV4,

    @EdtMember(value = 2, description = "Subject Alt Name in Fully Qualified Distinguished Name format")
    FQDN,

    @EdtMember(value = 3, description = "Subject Alt Name in IPv6 format")
    IPV6
}
