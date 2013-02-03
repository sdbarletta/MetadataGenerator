
package com.sdbarletta.salesforce.metadata.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "createVisualforcePagesResponse", namespace = "http://metadata.salesforce.sdbarletta.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createVisualforcePagesResponse", namespace = "http://metadata.salesforce.sdbarletta.com/")
public class CreateVisualforcePagesResponse {

    @XmlElement(name = "return", namespace = "", nillable = true)
    private com.sdbarletta.salesforce.metadata.MetadataResult[] _return;

    /**
     * 
     * @return
     *     returns MetadataResult[]
     */
    public com.sdbarletta.salesforce.metadata.MetadataResult[] getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(com.sdbarletta.salesforce.metadata.MetadataResult[] _return) {
        this._return = _return;
    }

}
