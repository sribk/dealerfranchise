package com.sri.dealerfranchise.data.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Dealer {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    @NotNull(message = "name is required.")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "franchise_id", referencedColumnName = "id", nullable = false)
    @NotNull(message = "franchise is required.")
    private Franchise franchise;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Franchise getFranchise() {
        return franchise;
    }

    public void setFranchise(Franchise franchise) {
        this.franchise = franchise;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dealer dealer = (Dealer) o;
        return Objects.equals(id, dealer.id) &&
                Objects.equals(name, dealer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "id='" + id + 
                ", name='" + name + 
                ", Franchise=" + franchise +
                '}';
    }
}
