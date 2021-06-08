package com.example.listapersonagens.dao;

import com.example.listapersonagens.model.Personagem;

import java.util.ArrayList;
import java.util.List;

public class PersonagemDAO {
    //Salvar personagens
    private final static List<Personagem> personagens = new ArrayList<>();
    private static int contadorId = 1;

    //Salvando info personagem
    public void salva(Personagem personagemSalvo) {
        personagemSalvo.setId(contadorId);
        personagens.add(personagemSalvo);
        contadorId++;
    }

    //Edita personagem
    public void edita(Personagem personagem) {
        Personagem personagemEscolhido = null;
        for (Personagem p: personagens) {
            if(p.getId() == personagem.getId()) {
                personagemEscolhido = p;
            }
        }
        if(personagemEscolhido != null) {
            int posicaoPersonagem = personagens.indexOf(personagemEscolhido);
            personagens.set(posicaoPersonagem, personagem);
        }

    }

    //Busca o ID do personagem
    private Personagem buscarPersonagemId(Personagem personagem) {
        for (Personagem p : personagens) {
            if(p.getId() == personagem.getId()) {
                return p;
            }
        }
        return null;
    }

    //exibe todos os personagens salvos
    public List<Personagem> todos() {
        return new ArrayList<>(personagens);
    }

    //remove um personagem
    public void remove(Personagem personagem) {
        Personagem personagemDevolvido = buscarPersonagemId(personagem);
        if(personagemDevolvido != null) {
            personagens.remove(personagemDevolvido);
        }
    }
}
