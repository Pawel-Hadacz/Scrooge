package com.scrooge.Model;

public class Debtor {
    private Long id;
    private String name;
    private double debt;

    public Debtor() {
    }

    public Debtor(Long id, String name, double debt) {
        this.id = id;
        this.name = name;
        this.debt = debt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Debtor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", debt=" + debt +
                '}';
    }
}
