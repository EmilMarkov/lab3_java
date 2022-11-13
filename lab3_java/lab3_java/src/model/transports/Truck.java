package model.transports;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"brand", "model", "maxLoad", "maxPassengerCount", "maxSpeed", "bodyType", "equipment", "isTrailer", "trailer"})
public class Truck extends Transport{
    private String id;
    private String bodyType;
    private String equipment;
    private boolean isTrailer;
    private Trailer trailer;

    // default constructor
    public Truck() {
    }

    // constructor with parameters
    public Truck(String bodyType, String equipment, Trailer... trailer)
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

    public String getBodyType() { return bodyType; }
    public void setBodyType(String body_type) { this.bodyType = body_type; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    public boolean getIsTrailer() { return isTrailer; }
    public void setIsTrailer(boolean is_trailer) { this.isTrailer = is_trailer; }

    public Trailer getTrailer() { return trailer; }
    public void setTrailer(Trailer trailer) { this.trailer = trailer; }
    //---------------------------

    @Override
    @XmlElement
    public String getBrand() {
        return brand;
    }

    @Override
    @XmlElement
    public String getModel() {
        return model;
    }

    @Override
    @XmlElement
    public double getMaxLoad() {
        return maxLoad;
    }

    @Override
    @XmlElement
    public double getMaxPassengerCount() {
        return maxPassengerCount;
    }

    @Override
    @XmlElement
    public double getMaxSpeed() {
        return maxSpeed;
    }
}