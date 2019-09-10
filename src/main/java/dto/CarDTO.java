package dto;

import entities.Car;
import java.util.Objects;

public class CarDTO
{

    private Long ID;
    private int Year;
    private String Make;
    private String Model;
    private double Price;

    public CarDTO()
    {
    }

    public CarDTO(Long ID, int Year, String Make, String Model, double Price)
    {
        this.ID = ID;
        this.Year = Year;
        this.Make = Make;
        this.Model = Model;
        this.Price = Price;
    }
    
    public CarDTO(Car car)
    {
        this.ID = car.getId();
        this.Year = Year;
        this.Make = Make;
        this.Model = Model;
        this.Price = Price;
    }

    public Long getID()
    {
        return ID;
    }

    public void setID(Long ID)
    {
        this.ID = ID;
    }

    public int getYear()
    {
        return Year;
    }

    public void setYear(int Year)
    {
        this.Year = Year;
    }

    public String getMake()
    {
        return Make;
    }

    public void setMake(String Make)
    {
        this.Make = Make;
    }

    public String getModel()
    {
        return Model;
    }

    public void setModel(String Model)
    {
        this.Model = Model;
    }

    public double getPrice()
    {
        return Price;
    }

    public void setPrice(double Price)
    {
        this.Price = Price;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.ID);
        hash = 97 * hash + this.Year;
        hash = 97 * hash + Objects.hashCode(this.Make);
        hash = 97 * hash + Objects.hashCode(this.Model);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.Price) ^ (Double.doubleToLongBits(this.Price) >>> 32));
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
        final CarDTO other = (CarDTO) obj;
        if (this.Year != other.Year)
        {
            return false;
        }
        if (Double.doubleToLongBits(this.Price) != Double.doubleToLongBits(other.Price))
        {
            return false;
        }
        if (!Objects.equals(this.Make, other.Make))
        {
            return false;
        }
        if (!Objects.equals(this.Model, other.Model))
        {
            return false;
        }
        if (!Objects.equals(this.ID, other.ID))
        {
            return false;
        }
        return true;
    }

    
}
