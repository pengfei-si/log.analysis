//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.10.21 at 03:56:46 PM PDT 
//


package etl.flow.oozie.coord;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ASYNCDATASET complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ASYNCDATASET">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uri-template" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{uri:oozie:coordinator:0.5}IDENTIFIER" />
 *       &lt;attribute name="sequence-type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="initial-version" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ASYNCDATASET", propOrder = {
    "uriTemplate"
})
public class ASYNCDATASET {

    @XmlElement(name = "uri-template", required = true)
    protected String uriTemplate;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "sequence-type", required = true)
    protected String sequenceType;
    @XmlAttribute(name = "initial-version", required = true)
    protected String initialVersion;

    /**
     * Gets the value of the uriTemplate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUriTemplate() {
        return uriTemplate;
    }

    /**
     * Sets the value of the uriTemplate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUriTemplate(String value) {
        this.uriTemplate = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the sequenceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSequenceType() {
        return sequenceType;
    }

    /**
     * Sets the value of the sequenceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSequenceType(String value) {
        this.sequenceType = value;
    }

    /**
     * Gets the value of the initialVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitialVersion() {
        return initialVersion;
    }

    /**
     * Sets the value of the initialVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitialVersion(String value) {
        this.initialVersion = value;
    }

}
