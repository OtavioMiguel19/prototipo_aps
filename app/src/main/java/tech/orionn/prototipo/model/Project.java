package tech.orionn.prototipo.model;

import java.util.List;

public class Project {
    String nome;
    List<String> users;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
