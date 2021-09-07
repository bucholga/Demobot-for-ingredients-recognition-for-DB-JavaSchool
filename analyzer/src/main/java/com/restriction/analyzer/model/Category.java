package com.restriction.analyzer.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "category_id")
    int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "category_name")
    String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "ingredients_categories", fetch = FetchType.EAGER)
    Set<Ingredient> ingredients;

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @ManyToMany(mappedBy = "users_categories", fetch = FetchType.EAGER)
    Set<User> users;
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}