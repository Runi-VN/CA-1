package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;


@Entity
@NamedQuery(name = "Car.deleteAllRows", query = "DELETE from Car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int car_year;
    private String car_make;
    private String car_model;
    private double car_price;
    //The buyer is only allowed information about the car's condition if they 
    //contact us directly (which they can't)
    private String car_condition;
    
    
    public Car() {
    }
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCar_year()
    {
        return car_year;
    }

    public void setCar_year(int car_year)
    {
        this.car_year = car_year;
    }

    public String getCar_make()
    {
        return car_make;
    }

    public void setCar_make(String car_make)
    {
        this.car_make = car_make;
    }

    public String getCar_model()
    {
        return car_model;
    }

    public void setCar_model(String car_model)
    {
        this.car_model = car_model;
    }

    public double getCar_price()
    {
        return car_price;
    }

    public void setCar_price(double car_price)
    {
        this.car_price = car_price;
    }

    public String getCar_condition()
    {
        return car_condition;
    }

    public void setCar_condition(String car_condition)
    {
        this.car_condition = car_condition;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        hash = 71 * hash + this.car_year;
        hash = 71 * hash + Objects.hashCode(this.car_make);
        hash = 71 * hash + Objects.hashCode(this.car_model);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.car_price) ^ (Double.doubleToLongBits(this.car_price) >>> 32));
        hash = 71 * hash + Objects.hashCode(this.car_condition);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Car other = (Car) obj;
        if (this.car_year != other.car_year)
        {
            return false;
        }
        if (Double.doubleToLongBits(this.car_price) != Double.doubleToLongBits(other.car_price))
        {
            return false;
        }
        if (!Objects.equals(this.car_make, other.car_make))
        {
            return false;
        }
        if (!Objects.equals(this.car_model, other.car_model))
        {
            return false;
        }
        if (!Objects.equals(this.car_condition, other.car_condition))
        {
            return false;
        }
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString()
    {
        return "\nCar with ID: " + id + "\nYear: " + car_year + 
                "\nMake: " + car_make + "\nModel: " + car_model + 
                "\nPrice: " + car_price + "\nCondition: " + car_condition;
    }
    
    
   
}
