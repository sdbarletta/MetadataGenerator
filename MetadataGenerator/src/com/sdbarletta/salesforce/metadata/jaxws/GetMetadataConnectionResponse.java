
package com.sdbarletta.salesforce.metadata.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getMetadataConnectionResponse", namespace = "http://metadata.salesforce.sdbarletta.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMetadataConnectionResponse", namespace = "http://metadata.salesforce.sdbarletta.com/")
public class GetMetadataConnectionResponse {

    @XmlElement(name = "return", namespace = "")
    private com.sforce.soap.metadata.MetadataConnection _return;

    /**
     * 
     * @return
     *     returns MetadataConnection
     */
    public com.sforce.soap.metadata.MetadataConnection getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(com.sforce.soap.metadata.MetadataConnection _return) {
        this._return = _return;
    }

}
