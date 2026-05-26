package br.upf.trabalhofinalacademia.controller;

import br.upf.trabalhofinalacademia.enumeration.EspecialidadeEnum;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.model.SelectItem;
import java.io.Serializable;

@Named(value = "especialidadeController")
@SessionScoped
public class EspecialidadeController implements Serializable {

    public SelectItem[] getEspecialidades() {
        SelectItem[] items = new SelectItem[EspecialidadeEnum.values().length];
        int i = 0;
        for (EspecialidadeEnum e : EspecialidadeEnum.values()) {
            items[i++] = new SelectItem(e.name(), e.getValue());
        }
        return items;
    }
}