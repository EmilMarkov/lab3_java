package model.transports;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"brand", "model", "maxLoad", "maxPassengerCount", "maxSpeed", "type", "equipment"})
public class Bus extends Transport{
    private String id;
    private String type;
    private String equipment;

    // default constructor
    public Bus() {
    }

    // constructor with parameters
    public Bus(String type, String equipment)
    {
        this.type = type;
        this.equipment = equipment;
    }

    // getters and setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }
    //---------------------------

    @XmlAttribute
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

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