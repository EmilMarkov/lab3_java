package model.transports;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"brand", "model", "maxLoad", "maxPassengerCount", "maxSpeed", "bodyType", "equipment", "isTrailer", "trailer"})
public class Car extends Transport {
    private String id;
    private String bodyType;
    private String equipment;
    private boolean isTrailer;
    private Trailer trailer = new Trailer();

    // default constructor
    public Car() {
    }

    // constructor with parameters
    public Car(String bodyType, String equipment, Trailer... trailer)
    {
        this.bodyType = bodyType;
        this.equipment = equipment;
        if (trailer == null)
        {
            this.isTrailer = false;
        }
        else
        {
            this.isTrailer = true;
            this.trailer = trailer[0];
        }
    }

    // getters and setters
    @XmlAttribute
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @XmlElement
    public String getBodyType() { return bodyType; }
    public void setBodyType(String bodyType) { this.bodyType = bodyType; }

    @XmlElement
    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    @XmlElement
    public boolean getIsTrailer() { return isTrailer; }
    public void setIsTrailer(boolean isTrailer) { this.isTrailer = isTrailer; }

    @XmlElement
    public Trailer getTrailer() { return trailer; }
    public void setTrailer(Trailer trailer) { this.trailer = trailer; }
    //---------------------------

    @XmlElement
    public String getBrand() {
        return brand;
    }

    @XmlElement
    public String getModel() {
        return model;
    }

    @XmlElement
    public double getMaxLoad() {
        return maxLoad;
    }

    @XmlElement
    public double getMaxPassengerCount() {
        return maxPassengerCount;
    }

    @XmlElement
    public double getMaxSpeed() {
        return maxSpeed;
    }
}